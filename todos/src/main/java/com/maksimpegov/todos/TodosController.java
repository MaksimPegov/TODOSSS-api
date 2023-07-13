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
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<TodoDto>> getTodos(@PathVariable Long userId) {
        TodoServiceResponse response = todosService.getTodos(userId);
        List<TodoDto> data = mapToDto(response.getData());
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(data);
    }

    @ApiOperation(value = "Get todo info", notes = "Provide todoId in path", response = TodoInfo.class)
    @GetMapping(path = "/todo/{todoId}")
    public ResponseEntity<List<TodoInfo>> getTodoInfo(@PathVariable Long todoId) {
        TodoServiceResponse response = todosService.getTodoById(todoId);
        List<TodoInfo> data = mapToTodoInfo(response.getData());
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(data);
    }

    @ApiOperation(value = "Add a todo for a user", notes = "Provide userId in path and text of todo in body", response = TodoDto.class)
    @PostMapping(path = "{userId}")
    public ResponseEntity<List<TodoDto>> addTodos(@RequestBody AddTodoRequest todoText, @PathVariable Long userId) {
        TodoServiceResponse response = todosService.addTodo(todoText, userId);
        List<TodoDto> data = mapToDto(response.getData());
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(data);
    }

    @ApiOperation(value = "Edit a todo", notes = "Provide only todoId and a todo field that you want to edit in body", response = TodoDto.class)
    @PatchMapping
    public ResponseEntity<List<TodoDto>> editTodos(@RequestBody TodoDto newTodoPartDto) {
        Todo newTodoPart = mapper.map(newTodoPartDto);
        TodoServiceResponse response = todosService.editTodo(newTodoPart);
        List<TodoDto> data = mapToDto(response.getData());
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(data);
    }

    @ApiOperation(value = "Delete a todo", notes = "Provide todoId in path")
    @DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
        todosService.deleteTodo(todoId);
    }

    @ApiOperation(value = "Delete all todos for a user", notes = "Provide userId in path")
    @DeleteMapping(path = "/clear/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void clearTodos(@PathVariable Long userId) {
        todosService.clearTodos(userId);
    }

    private HttpStatus getStatus(int status) {
        HttpStatus httpStatus;
        try {
            httpStatus = HttpStatus.valueOf(status);
        } catch (NumberFormatException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return httpStatus;
    }

    public List<TodoDto> mapToDto(List<Todo> todos) {
        return todos.stream().map(mapper::map).toList();
    }

    public List<TodoInfo> mapToTodoInfo(List<Todo> todos) {
        return todos.stream().map(mapper::mapInfo).toList();
    }
}
