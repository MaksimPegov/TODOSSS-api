package com.maksimpegov.todos;

import com.maksimpegov.todos.models.ProcessResponse;
import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoServiceResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/todos")
public record TodosController(TodosService todosService) {
    @GetMapping(path = "{userId}")
    public TodoServiceResponse getTodos(@PathVariable String userId) {
        ProcessResponse response = todosService.getTodos(userId);
        return new TodoServiceResponse(userId, response.getStatus(), response.getTodos());
    }

    @PostMapping()
    public TodoServiceResponse addTodos(@RequestBody Todo todo) {
        ProcessResponse response = todosService.addTodo(todo);
        return new TodoServiceResponse(todo.getUserId(), response.getStatus(), response.getTodos());
    }

    @PatchMapping()
    public TodoServiceResponse editTodos(@RequestBody Todo newTodo) {
        ProcessResponse response = todosService.editTodo(newTodo);
        return new TodoServiceResponse(newTodo.getUserId(), response.getStatus(), response.getTodos());
    }

    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in future
    public TodoServiceResponse deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        ProcessResponse response = todosService.deleteTodo(todoId);
        return new TodoServiceResponse(userId, response.getStatus(), response.getTodos());
    }

    @DeleteMapping(path = "/clear/{userId}")
    public TodoServiceResponse clearTodos(@PathVariable String userId) {
        ProcessResponse response = todosService.clearTodos(userId);
        return new TodoServiceResponse(userId, response.getStatus());
    }
}
