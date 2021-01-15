package todo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import todo.entity.Task
import todo.entity.TaskList
import todo.service.TaskListService
import todo.service.TaskService

@Controller
class PageController(private val taskListService: TaskListService, private val taskService: TaskService) {
    @RequestMapping("/")
    fun index(model: Model): String {
        return "index"
    }
}