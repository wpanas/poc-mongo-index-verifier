package com.github.wpanas.poc

import com.github.wpanas.poc.fixtures.MongoDBTestContainer
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension

object ProjectConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeProject() {
        MongoDBTestContainer.start()
    }

    override suspend fun afterProject() {
        MongoDBTestContainer.stop()
    }
}