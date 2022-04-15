package com.github.wpanas.poc.query

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.QueryMapper

class QueryIndexVerifierFactory(
    private val mongoTemplate: MongoTemplate,
    private val queryMapper: QueryMapper
) {
    fun of(domainType: Class<*>): QueryIndexVerifier {
        val mongoDatabase = MongoDatabaseBuilder(mongoTemplate).build()
        val queryExplainer = QueryExplainer(mongoDatabase)

        val queryToDocumentMapper = buildQueryToDocumentMapper(domainType)

        return QueryIndexVerifier(queryExplainer, queryToDocumentMapper)
    }

    private fun buildQueryToDocumentMapper(domainType: Class<*>): QueryToDocumentMapper {
        val collectionName = mongoTemplate.getCollectionName(domainType)
        return QueryToDocumentMapper(queryMapper, collectionName)
    }
}