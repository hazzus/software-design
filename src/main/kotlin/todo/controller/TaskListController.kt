package todo.controller

import org.springframework.web.bind.annotation.*
import todo.entity.TaskList
import todo.service.TaskListService

@RestController
@RequestMapping("/api/v1/list")
class TaskListController(private val taskListService: TaskListService) {
    @PostMapping
    fun create(taskList: TaskList) = taskListService.add(taskList)

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = taskListService.findById(id)

    @PutMapping
    fun update(taskList: TaskList) = taskListService.update(taskList)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = taskListService.deleteById(id)

    @GetMapping
    fun findAll() = taskListService.findAll()

    @PostMapping("/{listId}/add/{taskId}")
    fun addTask(@PathVariable listId: Long, @PathVariable taskId: Long) = taskListService.addTask(listId, taskId)
}