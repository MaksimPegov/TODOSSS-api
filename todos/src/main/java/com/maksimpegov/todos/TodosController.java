package com.maksimpegov.todos;

import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoServiceResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public record TodosController(TodosService todosService) {
    @GetMapping(path = "{userId}")
    public TodoServiceResponse getTodos(@PathVariable String userId) {
        List<Todo> todos = todosService.getTodos(userId);
        return new TodoServiceResponse(userId, "OK", todos);
    }

    @DeleteMapping(path = "/clear/{userId}")
    public String clearTodos(@PathVariable String userId) {
        System.out.println("Todos for user " + userId + " was deleted");
        return "Todos for user " + userId + " was deleted";
    }
}
