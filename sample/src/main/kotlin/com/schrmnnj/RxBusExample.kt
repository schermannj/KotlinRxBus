package com.schrmnnj

import com.schrmnnj.events.SimpleRxEvent
import com.schrmnnj.rx.IRxBus
import com.schrmnnj.rx.RxBus
import com.schrmnnj.rx.annotation.Subscribe
import java.util.concurrent.TimeUnit

/**
 * Created on 05.01.16.
 */
class RxBusExample {

    fun main(args: Array<String>): Unit {
        val rxBus: IRxBus = RxBus()

//        subscribe(rxBus)

        rxBus.send(SimpleRxEvent())

        println("Exit!")
    }
}

@Subscribe(SimpleRxEvent::class)
fun subscribe(rxBus: IRxBus): Unit {
    rxBus.getObservable(SimpleRxEvent::class.java).delay(3L, TimeUnit.SECONDS).toBlocking().subscribe({ event ->
        println("Hello, RxEvent!")
    }, {
        println("Unexpected error. Message: ${it.message}")
    })
}