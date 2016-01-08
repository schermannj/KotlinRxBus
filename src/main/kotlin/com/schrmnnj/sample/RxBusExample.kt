package com.schrmnnj.sample

import com.schrmnnj.rx.IRxBus
import com.schrmnnj.rx.RxBus
import com.schrmnnj.rx.annotation.SubscribeOn
import com.schrmnnj.sample.events.SimpleRxEvent

/**
 * Created on 05.01.16.
 */
fun main(args: Array<String>): Unit {
    val rxBus: IRxBus = RxBus()

    val someActivity = SomeActivity(rxBus)
    rxBus.register(someActivity)

//    someActivity.subscribe()

    rxBus.send(SimpleRxEvent("Test event"))

    println("Exit!")
}

class SomeActivity(val rxBus: IRxBus) : ActivitySimulator {

    override fun onPause() {
        rxBus.unsubscribe()
    }

    override fun onResume() {
        subscribe()
    }

    fun subscribe() {
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

    @SubscribeOn
    fun subscribeAnnotated(event: SimpleRxEvent) {
        println("It's working too, I'm the best :3. Event - ${event.name}")
    }
}

interface ActivitySimulator {
    fun onPause()
    fun onResume()
}