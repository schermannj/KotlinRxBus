package com.schrmnnj.sample.events

import com.schrmnnj.rx.event.IRxBusEvent

/**
 * Created on 08.01.16.
 */
class ExtendedRxBusEvent(val name: String, val age: Int) : IRxBusEvent