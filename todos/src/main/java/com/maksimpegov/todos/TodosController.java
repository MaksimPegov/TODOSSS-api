package com.maksimpegov.todos;

import com.maksimpegov.todos.models.ProcessResponse;
import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoServiceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public record TodosController(TodosService todosService) {

    @GetMapping(path = "{userId}")
    public TodoServiceResponse getTodos(@PathVariable String userId) {
        ProcessResponse response = todosService.getTodos(userId);
        return createTodoServiceResponse(response);
    }

    @PostMapping()
    public TodoServiceResponse addTodos(@RequestBody Todo todo) {
        ProcessResponse response = todosService.addTodo(todo);
        return createTodoServiceResponse(response);
    }

    @PatchMapping()
    public TodoServiceResponse editTodos(@RequestBody Todo newTodoPart) {
        ProcessResponse response = todosService.editTodo(newTodoPart);
        return createTodoServiceResponse(response);
    }

    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
    public TodoServiceResponse deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        ProcessResponse response = todosService.deleteTodo(todoId);
        return createTodoServiceResponse(response);
    }

    @DeleteMapping(path = "/clear/{userId}")
    public TodoServiceResponse clearTodos(@PathVariable String userId) {
        ProcessResponse response = todosService.clearTodos(userId);
        return createTodoServiceResponse(response);
    }

    private TodoServiceResponse createTodoServiceResponse(ProcessResponse response) {
        return new TodoServiceResponse(response.getStatus(), response.getMessage(), response.getTodos());
    }
}
