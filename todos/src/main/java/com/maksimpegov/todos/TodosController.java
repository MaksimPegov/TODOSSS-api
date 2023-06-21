package com.maksimpegov.todos;

import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoControllerResponse;
import com.maksimpegov.todos.models.TodoServiceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public record TodosController(TodosService todosService) {

    @GetMapping(path = "{userId}")
    public TodoControllerResponse getTodos(@PathVariable String userId) {
        TodoServiceResponse response = todosService.getTodos(userId);
        return createTodoServiceResponse(response);
    }

    @PostMapping()
    public TodoControllerResponse addTodos(@RequestBody Todo todo) {
        TodoServiceResponse response = todosService.addTodo(todo);
        return createTodoServiceResponse(response);
    }

    @PatchMapping()
    public TodoControllerResponse editTodos(@RequestBody Todo newTodoPart) {
        TodoServiceResponse response = todosService.editTodo(newTodoPart);
        return createTodoServiceResponse(response);
    }

    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
    public TodoControllerResponse deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        TodoServiceResponse response = todosService.deleteTodo(todoId);
        return createTodoServiceResponse(response);
    }

    @DeleteMapping(path = "/clear/{userId}")
    public TodoControllerResponse clearTodos(@PathVariable String userId) {
        TodoServiceResponse response = todosService.clearTodos(userId);
        return createTodoServiceResponse(response);
    }

    private TodoControllerResponse createTodoServiceResponse(TodoServiceResponse response) {
        return new TodoControllerResponse(response.getStatus(), response.getMessage(), response.getTodos());
    }
}
