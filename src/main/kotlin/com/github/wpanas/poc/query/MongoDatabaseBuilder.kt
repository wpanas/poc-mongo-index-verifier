package com.github.wpanas.poc.query

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.springframework.data.mongodb.core.MongoTemplate

class MongoDatabaseBuilder(private val mongoTemplate: MongoTemplate) {
    fun build(): MongoDatabase = mongoTemplate.db
        .withCodecRegistry(
            codecRegistry(ExplainedPlan.codecProvider())
        )

    private fun codecRegistry(pojoCodecProvider: PojoCodecProvider?) =
        CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(pojoCodecProvider)
        )
}