package com.schrmnnj.rx.annotation

import com.schrmnnj.rx.event.IRxBusEvent
import kotlin.reflect.KClass

/**
 * Created on 06.01.16.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Subscribe(val eventType: KClass<out IRxBusEvent>)