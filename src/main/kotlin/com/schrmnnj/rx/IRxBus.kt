package com.schrmnnj.rx

import com.schrmnnj.rx.event.IRxBusEvent
import rx.Observable
import rx.Subscription
import rx.functions.Action1
import javax.xml.ws.Holder

/**
 * Created on 05.01.16.
 */
interface IRxBus {
    fun send(event: IRxBusEvent): Unit

    fun <T> getObservable(type: Class<T>): Observable<T>

    fun register(holder: Any): String

    fun <T> subscribeOn(type: Class<T>, success: (t: T) -> Unit, error: (Throwable) -> Unit): Subscription

    fun <T> subscribeOn(type: Class<T>, success: (t: T) -> Unit): Subscription

    open fun unsubscribe(key: String)
}