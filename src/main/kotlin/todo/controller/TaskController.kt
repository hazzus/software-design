package todo.controller

import org.springframework.web.bind.annotation.*

import todo.entity.Task
import todo.service.TaskService

@RestController
@RequestMapping("/api/v1/task")
class TaskController(private val taskService: TaskService) {
    @PostMapping
    fun create(task: Task) = taskService.add(task)

    @PutMapping
    fun update(@RequestBody task: Task) = taskService.update(task)

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = taskService.findById(id)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = taskService.deleteById(id)

    @GetMapping
    fun readAll() = taskService.findAll()
}