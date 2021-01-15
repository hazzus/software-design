package todo.service

import org.springframework.stereotype.Service
import todo.entity.Task
import todo.repository.TaskRepository

@Service
class TaskService(private val taskRepository: TaskRepository) {
    fun findById(id: Long) = taskRepository.findById(id).orElseThrow { NoSuchElementException("Task $id not found") }

    fun findAll() = taskRepository.findAll()

    fun add(task: Task) = taskRepository.save(task)

    fun update(task: Task) = taskRepository.save(task)

    fun deleteById(id: Long) = findById(id).let { taskRepository.delete(it) }
}