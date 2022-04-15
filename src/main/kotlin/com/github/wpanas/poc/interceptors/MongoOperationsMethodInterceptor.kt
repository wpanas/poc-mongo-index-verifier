package com.github.wpanas.poc.interceptors

import com.github.wpanas.poc.utils.ProxyCreator
import com.github.wpanas.poc.query.QueryIndexVerifierFactory
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation

class MongoOperationsMethodInterceptor(
    private val queryIndexVerifierFactory: QueryIndexVerifierFactory
) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? =
        if (invocation.method.name == MONGO_TEMPLATE_QUERY_METHOD) {
            val domainType = invocation.arguments.first() as Class<*>

            ProxyCreator.createProxy(
                targetClass = invocation.method.returnType,
                target = invocation.proceed() as Any,
                methodInterceptor = ExecutableFindMethodInterceptor(
                    queryIndexVerifierFactory.of(domainType)
                )
            )
        } else invocation.proceed()

    companion object {
        const val MONGO_TEMPLATE_QUERY_METHOD = "query"
    }
}