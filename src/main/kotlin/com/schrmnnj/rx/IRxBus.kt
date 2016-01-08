package com.schrmnnj.rx

import com.schrmnnj.rx.event.IRxBusEvent
import rx.Observable
import rx.functions.Action1
import javax.xml.ws.Holder

/**
 * Created on 05.01.16.
 */
interface IRxBus {
    fun send(event: IRxBusEvent): Unit

    fun <T> getObservable(type: Class<T>): Observable<T>

    fun unsubscribe(): Unit

    fun register(holder: Any): Unit

    fun <T> subscribeOn(type: Class<T>, success: (t: T) -> Unit, error: (Throwable) -> Unit): Unit

    fun <T> subscribeOn(type: Class<T>, success: (t: T) -> Unit): Unit
}