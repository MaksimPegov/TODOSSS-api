package com.maksimpegov.todos;

import com.maksimpegov.todos.models.TodoServiceResponse;
import com.maksimpegov.todos.todo.Todo;
import com.maksimpegov.todos.todo.TodoDto;
import com.maksimpegov.todos.todo.TodoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos/v1")
public class TodosController {

    private final TodosService todosService;

    private final TodoMapper mapper;

    public TodosController(TodosService todosService) {
        this.todosService = todosService;
        this.mapper = TodoMapper.INSTANCE;
    }


    @GetMapping(path = "{userId}")
    public ResponseEntity<Object> getTodos(@PathVariable Long userId) {
        TodoServiceResponse response = todosService.getTodos(userId);
        return ResponseBuilder.build(response);
    }

    @PostMapping(path = "{userId}")
    public ResponseEntity<Object> addTodos(@RequestBody TodoDto todoDto, @PathVariable Long userId) {
        Todo todo = mapper.map(todoDto);
        TodoServiceResponse response = todosService.addTodo(todo, userId);
        return ResponseBuilder.build(response);
    }

    @PatchMapping()
    public ResponseEntity<Object> editTodos(@RequestBody TodoDto newTodoPartDto) {
        Todo newTodoPart = mapper.map(newTodoPartDto);
        TodoServiceResponse response = todosService.editTodo(newTodoPart);
        return ResponseBuilder.build(response);
    }

    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
    public ResponseEntity<Object> deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        TodoServiceResponse response = todosService.deleteTodo(todoId);
        return ResponseBuilder.build(response);
    }

    @DeleteMapping(path = "/clear/{userId}")
    public ResponseEntity<Object> clearTodos(@PathVariable Long userId) {
        TodoServiceResponse response = todosService.clearTodos(userId);
        return ResponseBuilder.build(response);
    }
}
