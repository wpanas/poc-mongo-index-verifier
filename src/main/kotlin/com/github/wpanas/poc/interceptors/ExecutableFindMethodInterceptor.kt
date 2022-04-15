package com.github.wpanas.poc.interceptors

import com.github.wpanas.poc.query.QueryIndexVerifier
import com.github.wpanas.poc.utils.ProxyCreator.createProxy
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class ExecutableFindMethodInterceptor(
    private val queryIndexVerifier: QueryIndexVerifier
) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? =
        if (invocation.method.name == EXECUTABLE_FIND_AS_METHOD) {
            createProxy(
                targetClass = invocation.method.returnType,
                target = invocation.proceed() as Any,
                methodInterceptor = FindWithQueryMethodInterceptor(queryIndexVerifier)
            )
        } else invocation.proceed()

    companion object {
        const val EXECUTABLE_FIND_AS_METHOD = "as"
    }
}