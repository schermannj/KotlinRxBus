package com.schrmnnj.rx

import com.schrmnnj.rx.event.IRxBusEvent
import com.schrmnnj.rx.exception.UnexpectedRxBusException
import rx.Observable
import rx.lang.kotlin.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

/**
 * Created on 05.01.16.
 */
class RxBus : IRxBus {
    val bus: Subject<Any, Any> = SerializedSubject(PublishSubject());

    override fun send(event: IRxBusEvent): Unit {
        bus.onNext(event)
    }

    override fun <T> getObservable(type: Class<T>): Observable<T> {
        return bus.ofType(type)
    }

    override fun <T> subscribeOn(type: Class<T>, success: (T) -> Unit, error: (Throwable) -> Unit) {
        getObservable(type).subscribe(success, error)
    }

    override fun <T> subscribeOn(type: Class<T>, success: (T) -> Unit) {
        subscribeOn(type, success, {
            throw UnexpectedRxBusException("Unexpected RxBus exception. Cause: ${it.message}")
        })
    }

    override fun unsubscribe(): Unit {
        bus.onCompleted()
    }
}