package com.schrmnnj.sample

import com.schrmnnj.rx.IRxBus
import com.schrmnnj.rx.RxBus
import com.schrmnnj.rx.handler.RxBusEventHolder
import com.schrmnnj.sample.events.SimpleRxEvent

/**
 * Created on 05.01.16.
 */
fun main(args: Array<String>): Unit {
    val rxBus: IRxBus = RxBus()

    SomeActivity(rxBus).onResume()

    rxBus.send(SimpleRxEvent())

    println("Exit!")
}

class SomeActivity(val rxBus: IRxBus) : ActivitySimulator, RxBusEventHolder {

    override fun onPause() {
        rxBus.unsubscribe()
    }

    override fun onResume() {
        subscribe()
    }

    override fun subscribe() {
        rxBus.getObservable(SimpleRxEvent::class.java).subscribe({ event ->
            println("Hello, RxEvent!")
        }, {
            println("Unexpected error. Message: ${it.message}")
        })

        rxBus.subscribeOn(SimpleRxEvent::class.java, {
            println("Success")
        }, {
            println("Error")
        })
    }
}

interface ActivitySimulator {
    fun onPause()
    fun onResume()
}