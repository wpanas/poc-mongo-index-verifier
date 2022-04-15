package com.github.wpanas.poc.interceptors

import com.github.wpanas.poc.query.QueryIndexVerifier
import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.data.mongodb.core.query.Query

class FindWithQueryMethodInterceptor(
    private val queryIndexVerifier: QueryIndexVerifier
) : MethodInterceptor {
    override fun invoke(myInvocation: MethodInvocation): Any? {
        if (myInvocation.method.name == FIND_WITH_QUERY_MATCHING_METHOD) {
            val query = myInvocation.arguments.first() as Query
            queryIndexVerifier.verify(query)
        }

        return myInvocation.proceed()
    }

    companion object {
        const val FIND_WITH_QUERY_MATCHING_METHOD = "matching"
    }
}