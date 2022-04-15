package com.github.wpanas.poc.query

import org.springframework.data.mongodb.core.query.Query

class QueryIndexVerifier(
    private val queryExplainer: QueryExplainer,
    private val queryToDocumentMapper: QueryToDocumentMapper,
) {
    fun verify(query: Query) {
        val queryDocument = queryToDocumentMapper.mapQueryDocument(query)
        queryExplainer.explain(queryDocument)
    }
}