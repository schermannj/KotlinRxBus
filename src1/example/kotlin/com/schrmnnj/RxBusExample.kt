package com.schrmnnj

import com.schrmnnj.events.SimpleRxEvent
import com.schrmnnj.rx.IRxBus
import com.schrmnnj.rx.RxBus
import java.util.concurrent.TimeUnit

/**
 * Created on 05.01.16.
 */
class RxBusExample {

    fun main(args: Array<String>): Unit {
        val rxBus: IRxBus = RxBus()

        subscribe(rxBus)

        rxBus.send(SimpleRxEvent())

        println("Exit!")
    }
}

fun subscribe(rxBus: IRxBus): Unit {
    rxBus.getObservable(SimpleRxEvent::class.java).delay(3L, TimeUnit.SECONDS).toBlocking().subscribe({ event ->
        println("Hello, RxEvent!")
    }, {
        println("Unexpected error. Message: ${it.message}")
    })
}