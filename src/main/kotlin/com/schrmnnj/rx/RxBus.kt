package com.schrmnnj.rx

import com.schrmnnj.rx.annotation.SubscribeOn
import com.schrmnnj.rx.event.IRxBusEvent
import com.schrmnnj.rx.exception.RxEventException
import com.schrmnnj.rx.exception.UnexpectedRxBusException
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created on 05.01.16.
 */
class RxBus : IRxBus {
    private val bus: Subject<Any, Any> = SerializedSubject(PublishSubject());
    private val compositeSubscriptionsHolder: MutableMap<String, CompositeSubscription> = hashMapOf()

    override fun send(event: IRxBusEvent): Unit {
        bus.onNext(event)
    }

    override fun register(holder: Any): String {
        val key: String = UUID.randomUUID().toString()
        val compositeSubscription: CompositeSubscription = CompositeSubscription()

        holder.javaClass.methods
                .filter({
                    it.isAnnotationPresent(SubscribeOn::class.java)
                })
                .forEach({ method ->
                    val parameters = method.parameterTypes

                    if (parameters.size == 0 || parameters.size > 1) {
                        throw RxEventException("""Method annotated with '@SubscribeOn' in registered class ${holder.javaClass}
                                                should have only one parameter - event object.""")
                    }

                    val subscription = subscribeOn(parameters.first(), { event ->
                        method.invoke(holder, event)
                    })

                    compositeSubscription.add(subscription)
                })

        compositeSubscriptionsHolder.put(key, compositeSubscription)

        return key
    }

    override fun <T> getObservable(type: Class<T>): Observable<T> {
        return bus.ofType(type)
    }

    override fun <T> subscribeOn(type: Class<T>, success: (T) -> Unit, error: (Throwable) -> Unit): Subscription {
        return getObservable(type).subscribe(success, error)
    }

    override fun <T> subscribeOn(type: Class<T>, success: (T) -> Unit): Subscription {
        return subscribeOn(type, success, {
            throw UnexpectedRxBusException("Unexpected RxBus exception. Cause: ${it.message}")
        })
    }

    override fun unsubscribe(key: String): Unit {
        val compositeSubscription = compositeSubscriptionsHolder[key] ?: throw RxEventException("Not found any subscription for given key.")

        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe()
        }
    }
}