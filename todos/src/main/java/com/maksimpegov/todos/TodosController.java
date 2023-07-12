package com.maksimpegov.todos;

import com.maksimpegov.todos.models.AddTodoRequest;
import com.maksimpegov.todos.models.TodoServiceResponse;
import com.maksimpegov.todos.todo.Todo;
import com.maksimpegov.todos.todo.TodoDto;
import com.maksimpegov.todos.todo.TodoInfo;
import com.maksimpegov.todos.todo.TodoMapper;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Endpoints")
@RequestMapping("/api/todos/v1")
public class TodosController {

    private final TodosService todosService;

    private final TodoMapper mapper;

    public TodosController(TodosService todosService) {
        this.todosService = todosService;
        this.mapper = TodoMapper.INSTANCE;
    }

    @ApiOperation(value = "Get all todos for a user", notes = "Provide userId in path", response = TodoDto.class, responseContainer = "List")
    @GetMapping(path = "{userId}")
    public ResponseEntity<Object> getTodos(@PathVariable Long userId) {
        TodoServiceResponse response = todosService.getTodos(userId);
        return ResponseBuilder.build(response, mapper::mapTodo);
    }

    @ApiOperation(value = "Get todo info", notes = "Provide todoId in path", response = TodoInfo.class)
    @GetMapping(path = "/todo/{todoId}")
    public ResponseEntity<Object> getTodoInfo(@PathVariable Long todoId) {
        TodoServiceResponse response = todosService.getTodoById(todoId);
        return ResponseBuilder.build(response, mapper::mapInfo);
    }

    @ApiOperation(value = "Add a todo for a user", notes = "Provide userId in path and text of todo in body", response = TodoDto.class)
    @PostMapping(path = "{userId}")
    public ResponseEntity<Object> addTodos(@RequestBody AddTodoRequest todoText, @PathVariable Long userId) {
        TodoServiceResponse response = todosService.addTodo(todoText, userId);
        return ResponseBuilder.build(response, mapper::mapTodo);
    }

    @ApiOperation(value = "Edit a todo", notes = "Provide only todoId and a todo field that you want to edit in body", response = TodoDto.class)
    @PatchMapping
    public ResponseEntity<Object> editTodos(@RequestBody TodoDto newTodoPartDto) {
        Todo newTodoPart = mapper.mapDto(newTodoPartDto);
        TodoServiceResponse response = todosService.editTodo(newTodoPart);
        return ResponseBuilder.build(response, mapper::mapTodo);
    }

    @ApiOperation(value = "Delete a todo", notes = "Provide todoId in path")
    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
    public ResponseEntity<Object> deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        TodoServiceResponse response = todosService.deleteTodo(todoId);
        return ResponseBuilder.build(response, mapper::mapTodo);
    }

    @ApiOperation(value = "Delete all todos for a user", notes = "Provide userId in path")
    @DeleteMapping(path = "/clear/{userId}")
    public ResponseEntity<Object> clearTodos(@PathVariable Long userId) {
        TodoServiceResponse response = todosService.clearTodos(userId);
        return ResponseBuilder.build(response, mapper::mapTodo);
    }
}
