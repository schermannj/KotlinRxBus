package com.schrmnnj.sample

import com.schrmnnj.rx.IRxBus
import com.schrmnnj.rx.RxBus
import com.schrmnnj.rx.annotation.SubscribeOn
import com.schrmnnj.sample.events.ExtendedRxBusEvent
import com.schrmnnj.sample.events.SimpleRxBusEvent
import rx.subscriptions.CompositeSubscription

/**
 * Created on 05.01.16.
 */
fun main(args: Array<String>): Unit {
    val rxBus: IRxBus = RxBus()
    val someActivity = SomeActivity(rxBus)

    someActivity.onResume()

    rxBus.send(SimpleRxBusEvent("Simple Event"))
    rxBus.send(ExtendedRxBusEvent("ExtendedRxBusEvent", 12))

    someActivity.onPause()

    println("Done!")
}

class SomeActivity(val rxBus: IRxBus) : ActivitySimulator {
    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onPause() {
        rxBus.unsubscribe(this)
        compositeSubscription.unsubscribe()
    }

    override fun onResume() {
        subscribe()
        rxBus.register(this)
    }

    fun subscribe() {
        val subscription1 = rxBus.getObservable(SimpleRxBusEvent::class.java).subscribe({ event ->
            println("method: subscribe() :  Hello, name - ${event.name}, SimpleRxBusEvent!")
        }, {
            println("Unexpected error. Message: ${it.message}")
        })

        compositeSubscription.add(subscription1)

        val subscription2 = rxBus.subscribeOn(ExtendedRxBusEvent::class.java, { event ->
            println("method: subscribe() :  Hello, name - ${event.name}, age - ${event.age}, ExtendedRxBusEvent!")
        }, {
            println("Unexpected error. Message: ${it.message}")
        })

        compositeSubscription.add(subscription2)
    }

    @SubscribeOn
    fun checkAnnotationSubscription1(event: SimpleRxBusEvent) {
        println("method: checkAnnotationSubscription1() : Event - ${event.name}; SimpleRxBusEvent")
    }

    @SubscribeOn
    fun checkAnnotationSubscription2(event: ExtendedRxBusEvent) {
        println("method: checkAnnotationSubscription2() : Event: name - ${event.name}, age - ${event.age}; ExtendedRxBusEvent")
    }
}

interface ActivitySimulator {
    fun onPause()
    fun onResume()
}