package com.maksimpegov.todos;

import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos/v1")
public record TodosController(TodosService todosService) {

    @GetMapping(path = "{userId}")
    public ResponseEntity<Object> getTodos(@PathVariable String userId) {
        TodoServiceResponse response = todosService.getTodos(userId);
        return ResponseBuilder.build(response);
    }

    @PostMapping()
    public ResponseEntity<Object> addTodos(@RequestBody Todo todo) {
        TodoServiceResponse response = todosService.addTodo(todo);
        return ResponseBuilder.build(response);
    }

    @PatchMapping()
    public ResponseEntity<Object> editTodos(@RequestBody Todo newTodoPart) {
        TodoServiceResponse response = todosService.editTodo(newTodoPart);
        return ResponseBuilder.build(response);
    }

    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
    public ResponseEntity<Object> deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        TodoServiceResponse response = todosService.deleteTodo(todoId);
        return ResponseBuilder.build(response);
    }

    @DeleteMapping(path = "/clear/{userId}")
    public ResponseEntity<Object> clearTodos(@PathVariable String userId) {
        TodoServiceResponse response = todosService.clearTodos(userId);
        return ResponseBuilder.build(response);
    }
}
