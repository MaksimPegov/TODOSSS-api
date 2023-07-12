package com.maksimpegov.todos;

import com.maksimpegov.todos.exeption.ApiRequestException;
import com.maksimpegov.todos.models.AddTodoRequest;
import com.maksimpegov.todos.models.TodoServiceResponse;
import com.maksimpegov.todos.todo.Todo;
import com.maksimpegov.todos.todo.TodoDto;
import com.maksimpegov.todos.todo.TodoInfo;
import com.maksimpegov.todos.todo.TodoMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseEntity<?> getTodos(@PathVariable Long userId) {
        TodoServiceResponse response = todosService.getTodos(userId);
        mapData(response, mapper::mapTodo);
        return ResponseBuilder.build(response);
    }

    @ApiOperation(value = "Get todo info", notes = "Provide todoId in path", response = TodoInfo.class)
    @GetMapping(path = "/todo/{todoId}")
    public ResponseEntity<?> getTodoInfo(@PathVariable Long todoId) {
        TodoServiceResponse response = todosService.getTodoById(todoId);
        mapData(response, mapper::mapTodo);
        return ResponseBuilder.build(response);
    }

    @ApiOperation(value = "Add a todo for a user", notes = "Provide userId in path and text of todo in body", response = TodoDto.class)
    @PostMapping(path = "{userId}")
    public ResponseEntity<?> addTodos(@RequestBody AddTodoRequest todoText, @PathVariable Long userId) {
        TodoServiceResponse response = todosService.addTodo(todoText, userId);
        mapData(response, mapper::mapTodo);
        return ResponseBuilder.build(response);
    }

    @ApiOperation(value = "Edit a todo", notes = "Provide only todoId and a todo field that you want to edit in body", response = TodoDto.class)
    @PatchMapping
    public ResponseEntity<?> editTodos(@RequestBody TodoDto newTodoPartDto) {
        Todo newTodoPart = mapper.mapDto(newTodoPartDto);
        TodoServiceResponse response = todosService.editTodo(newTodoPart);
        mapData(response, mapper::mapTodo);
        return ResponseBuilder.build(response);

    }

    @ApiOperation(value = "Delete a todo", notes = "Provide todoId in path")
    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
    public ResponseEntity<?> deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        TodoServiceResponse response = todosService.deleteTodo(todoId);
        return ResponseBuilder.build(response);
    }

    @ApiOperation(value = "Delete all todos for a user", notes = "Provide userId in path")
    @DeleteMapping(path = "/clear/{userId}")
    public ResponseEntity<?> clearTodos(@PathVariable Long userId) {
        TodoServiceResponse response = todosService.clearTodos(userId);
        return ResponseBuilder.build(response);
    }

    private List<?> mapData(TodoServiceResponse response, Function<Todo, ?> mapper) {
        if (response.getData() != null) {
            return response.getData().stream().map(todo -> mapper.apply((Todo) todo)).collect(Collectors.toList());
        }
        return null;
    }
}
