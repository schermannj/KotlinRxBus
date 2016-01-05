package com.schrmnnj.rx

import com.schrmnnj.rx.event.IRxBusEvent
import rx.Observable

/**
 * Created on 05.01.16.
 */
interface IRxBus {
    fun send(event: IRxBusEvent): Unit

    fun <T> getObservable(type: Class<T>): Observable<T>

    fun unsubscribe(): Unit
}