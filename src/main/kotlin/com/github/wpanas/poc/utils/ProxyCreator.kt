package com.github.wpanas.poc.utils

import org.aopalliance.intercept.MethodInterceptor
import org.springframework.aop.framework.ProxyFactory

object ProxyCreator {
    fun <T : Any> createProxy(targetClass: Class<T>, target: Any, methodInterceptor: MethodInterceptor): Any {
        val newProxyFactory = ProxyFactory()
        newProxyFactory.targetClass = targetClass
        newProxyFactory.isProxyTargetClass = true
        newProxyFactory.setTarget(target)
        newProxyFactory.addAdvice(methodInterceptor)
        return newProxyFactory.proxy
    }
}