package com.schrmnnj.sample

import com.schrmnnj.rx.IRxBus
import com.schrmnnj.rx.RxBus
import com.schrmnnj.rx.annotation.Subscribe
import com.schrmnnj.sample.events.SimpleRxEvent

/**
 * Created on 05.01.16.
 */
fun main(args: Array<String>): Unit {
    val rxBus: IRxBus = RxBus()

    subscribe(rxBus)

    rxBus.send(SimpleRxEvent())

    println("Exit!")
}

@Subscribe(SimpleRxEvent::class)
fun subscribe(rxBus: IRxBus): Unit {
    rxBus.getObservable(SimpleRxEvent::class.java).subscribe({ event ->
        println("Hello, RxEvent!")
    }, {
        println("Unexpected error. Message: ${it.message}")
    })
}