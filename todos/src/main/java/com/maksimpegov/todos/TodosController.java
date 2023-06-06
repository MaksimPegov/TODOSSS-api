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

    @PostMapping()
    public TodoServiceResponse addTodos(@RequestBody Todo todo) {
        todo.setCreatedAt(new java.util.Date());
        String response = todosService.addTodo(todo.getUserId(), todo);
        System.out.println(response);
        return new TodoServiceResponse(todo.getUserId(), response, todosService.getTodos(todo.getUserId()));
    }

    @DeleteMapping(path = "/{userId}/{todoId}")
    public TodoServiceResponse deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        String response = todosService.deleteTodo(userId, todoId);
        System.out.println(response);
        return new TodoServiceResponse(userId, response, todosService.getTodos(userId));
    }

    @DeleteMapping(path = "/clear/{userId}")
    public TodoServiceResponse clearTodos(@PathVariable String userId) {
        String response = todosService.clearTodos(userId);
        System.out.println(response);
        return new TodoServiceResponse(userId, response);
    }
}
