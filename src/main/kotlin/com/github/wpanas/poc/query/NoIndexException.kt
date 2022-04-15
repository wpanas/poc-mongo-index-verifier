package com.github.wpanas.poc.query

import org.bson.Document

class NoIndexException(query: Document) : RuntimeException("No index for query: ${query.toJson()}")