package com.github.wpanas.poc.query

import org.bson.Document
import org.springframework.data.mongodb.core.convert.QueryMapper
import org.springframework.data.mongodb.core.query.Query

class QueryToDocumentMapper(
    private val queryMapper: QueryMapper,
    private val collectionName: String
) {
    fun mapQueryDocument(query: Query): Document {
        val mappedQuery = queryMapper.getMappedObject(query.queryObject, null)
        val queryDocument = Document()
        queryDocument["find"] = collectionName
        queryDocument["filter"] = mappedQuery
        queryDocument["sort"] = query.sortObject
        queryDocument["skip"] = query.skip
        queryDocument["limit"] = query.limit
        return queryDocument
    }
}