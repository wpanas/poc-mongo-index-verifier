package com.github.wpanas.poc

import com.github.wpanas.poc.interceptors.MongoOperationsMethodInterceptor
import com.github.wpanas.poc.query.QueryIndexVerifierFactory
import com.github.wpanas.poc.utils.ProxyCreator.createProxy
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.MongoConverter
import org.springframework.data.mongodb.core.convert.QueryMapper
import org.springframework.stereotype.Component


@SpringBootApplication
class MongoIndexVerifierApplication

fun main(args: Array<String>) {
    runApplication<MongoIndexVerifierApplication>(*args)
}

@Component
class MongoTemplateProxyBeanPostProcessor(
    private val mongoConverter: MongoConverter
) : BeanPostProcessor {
    override fun postProcessAfterInitialization(mongoTemplate: Any, beanName: String): Any =
        if (mongoTemplate is MongoTemplate) {
            val queryIndexVerifierFactory = QueryIndexVerifierFactory(
                mongoTemplate,
                QueryMapper(mongoConverter)
            )

            createProxy(
                targetClass = mongoTemplate::class.java,
                target = mongoTemplate,
                methodInterceptor = MongoOperationsMethodInterceptor(queryIndexVerifierFactory)
            )
        } else mongoTemplate
}

