package com.github.wpanas.poc.fixtures

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoDBTestContainer : MongoDBContainer(
    DockerImageName.parse("mongo:4.4.13")
) {
    companion object {
        private lateinit var instance: MongoDBTestContainer

        @JvmStatic
        @JvmName("startContainer")
        fun start() {
            if (!Companion::instance.isInitialized) {
                instance = MongoDBTestContainer()
                instance.start()
            }
        }

        @JvmStatic
        @JvmName("stopContainer")
        fun stop() {
            instance.stop()
        }

        @JvmStatic
        fun mongoDBContainer(): MongoDBContainer = instance
    }
}