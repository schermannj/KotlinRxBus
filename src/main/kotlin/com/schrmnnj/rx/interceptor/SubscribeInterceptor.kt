package com.schrmnnj.rx.interceptor

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Created on 06.01.16.
 */
class SubscribeInterceptor : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any? {
        println("Lol")
        return method.invoke(args)
    }

}