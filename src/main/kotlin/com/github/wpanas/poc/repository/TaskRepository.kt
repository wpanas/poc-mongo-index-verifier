package com.github.wpanas.poc.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : MongoRepository<Task, UUID> {
    fun findByStatus(status: Task.TaskStatus): Set<Task>
    fun findByTitle(title: String): Set<Task>
    fun findByStatusOrderByTitle(status: Task.TaskStatus): Set<Task>
}