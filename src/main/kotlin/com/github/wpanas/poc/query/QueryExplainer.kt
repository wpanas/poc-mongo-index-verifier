package com.github.wpanas.poc.query

import com.github.wpanas.poc.interceptors.FindWithQueryMethodInterceptor
import com.mongodb.client.MongoDatabase
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class QueryExplainer(
    private val mongoDatabase: MongoDatabase
) {
    fun explain(queryDocument: org.bson.Document) {
        val command = org.bson.Document()
        command["explain"] = queryDocument

        logger.info(command.toJson())

        logger.debug(mongoDatabase.runCommand(command).toJson())

        val explain = mongoDatabase.runCommand(command, ExplainedPlan::class.java)

        logger.info(explain.toString())

        assertIndexIsUsed(explain, queryDocument)
    }

    private fun assertIndexIsUsed(
        explain: ExplainedPlan,
        queryDocument: org.bson.Document
    ) {
        if (explain.isNotUsingIndex()) {
            throw NoIndexException(queryDocument)
        }
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(FindWithQueryMethodInterceptor::class.java)
    }

}