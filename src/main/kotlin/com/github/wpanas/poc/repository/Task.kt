package com.github.wpanas.poc.repository

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Task(
    @Id val id: String = UUID.randomUUID().toString(),
    @Indexed(background = true) val status: TaskStatus = TaskStatus.NEW,
    val title: String = "Default task title"
) {
    enum class TaskStatus {
        NEW,
        IN_PROGRESS,
    }

    fun start(): Task = copy(status = TaskStatus.IN_PROGRESS)
}