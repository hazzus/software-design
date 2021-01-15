package todo.service

import org.springframework.stereotype.Service
import todo.entity.TaskList
import todo.repository.TaskListRepository

@Service
class TaskListService(private val taskService: TaskService, private val taskListRepository: TaskListRepository) {
    fun findById(id: Long) =
        taskListRepository.findById(id).orElseThrow { NoSuchElementException("Task list $id not found") }

    fun findAll() = taskListRepository.findAll()

    fun add(taskList: TaskList) = taskListRepository.save(taskList)

    fun addTask(taskListId: Long, taskId: Long): TaskList {
        val taskList = findById(taskListId)
        val task = taskService.findById(taskId)
        println(taskList)
        println(task)
        taskList.tasks?.add(task)
        return taskListRepository.save(taskList)
    }

    fun update(taskList: TaskList) = taskListRepository.save(taskList)

    fun deleteById(id: Long) = findById(id).let { taskListRepository.delete(it) }
}