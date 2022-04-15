package com.github.wpanas.poc.repository

import com.github.wpanas.poc.MongoIndexVerifierApplication
import com.github.wpanas.poc.query.NoIndexException
import com.github.wpanas.poc.fixtures.MongoDBTestContainer.Companion.mongoDBContainer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(classes = [MongoIndexVerifierApplication::class])
class TaskRepositoryTest(taskRepository: TaskRepository) : StringSpec({
    "should find tasks by status without indexing issues" {
        // given 2 new tasks & one started task
        val newTasks = setOf(Task(), Task())
        val startedTask = Task().start()

        // and stored in repository
        taskRepository.saveAll(newTasks + startedTask)

        // when fetched from repository only new tasks
        val fetchedTasks = taskRepository.findByStatus(Task.TaskStatus.NEW)

        fetchedTasks shouldBe newTasks
    }

    "should throw NoIndexException when filtering by unindexed field" {
        shouldThrow<NoIndexException> {
            taskRepository.findByTitle("My title")
        }
    }

    "should throw NoIndexException when sorting by unindexed field" {
        shouldThrow<NoIndexException> {
            taskRepository.findByStatusOrderByTitle(Task.TaskStatus.NEW)
        }
    }
}) {
    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun mongoProperties(registry: DynamicPropertyRegistry) {
            val container = mongoDBContainer()
            registry.add("spring.data.mongodb.uri") { container.replicaSetUrl }
            registry.add("spring.data.mongodb.auto-index-creation") { true }
        }
    }
}

