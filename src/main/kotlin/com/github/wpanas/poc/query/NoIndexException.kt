package com.github.wpanas.poc.query

class NoIndexException(query: org.bson.Document) : RuntimeException("No index for query: ${query.toJson()}")