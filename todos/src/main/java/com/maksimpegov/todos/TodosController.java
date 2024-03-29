package com.maksimpegov.todos;

import com.maksimpegov.todos.models.AddTodoRequest;
import com.maksimpegov.todos.todo.TodoDto;
import com.maksimpegov.todos.todo.TodoInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@Api(tags = "Endpoints")
@RequestMapping("/api/todos")
public class TodosController {
	private final TodosService todosService;

	final Logger logger = LoggerFactory.getLogger(TodosController.class);


	public TodosController(TodosService todosService) {
		this.todosService = todosService;
	}

	@GetMapping
	@ApiOperation(value = "Get all todos for a user", response = TodoDto.class, responseContainer = "List")
	@ApiImplicitParam(name = "userId", value = "The genuine User ID or requester (extracted from the user's JWT token)", required = true, dataType = "long", paramType = "header")
	public ResponseEntity<List<TodoDto>> getTodos(@RequestHeader("userId") Long secureUserId) {
		logger.info("Get all todos for user with id " + secureUserId);
		List<TodoDto> response = todosService.getTodos(secureUserId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(path = "/{todoId}")
	@ApiOperation(value = "Get todo info", notes = "Provide todoId in path", response = TodoInfo.class)
	@ApiImplicitParam(name = "userId", value = "The genuine User ID or requester (extracted from the user's JWT token)", required = true, dataType = "long", paramType = "header")
	public ResponseEntity<TodoInfo> getTodoInfo(@PathVariable Long todoId, @RequestHeader("userId") Long secureUserId) {
		logger.info("Get todo info for todo with id " + todoId + " for user with id " + secureUserId);
		TodoInfo response = todosService.getTodoInfo(todoId, secureUserId);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@ApiOperation(value = "Add a todo for a user", notes = "Provide text of todo in body", response = TodoDto.class)
	@ApiImplicitParam(name = "userId", value = "The genuine User ID or requester (extracted from the user's JWT token)", required = true, dataType = "long", paramType = "header")
	public ResponseEntity<TodoDto> addTodos(@RequestBody AddTodoRequest todoText, @RequestHeader("userId") Long secureUserId) {
		logger.info("Add todo with text " + todoText.getText() + " for user with id " + secureUserId);
		TodoDto response = todosService.addTodo(todoText, secureUserId);
		return ResponseEntity.ok(response);
	}

	@PatchMapping
	@ApiOperation(value = "Edit a todo", notes = "Provide only todoId and a todo field that you want to edit in body", response = TodoDto.class)
	@ApiImplicitParam(name = "userId", value = "The genuine User ID or requester (extracted from the user's JWT token)", required = true, dataType = "long", paramType = "header")
	public ResponseEntity<TodoDto> editTodos(@RequestBody TodoDto newTodoPartDto, @RequestHeader("userId") Long secureUserId) {
		logger.info("Edit todo with id " + newTodoPartDto.getId() + " for user with id " + secureUserId);
		TodoDto response = todosService.editTodo(newTodoPartDto, secureUserId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(path = "/{todoId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete a todo", notes = "Provide todoId in path")
	@ApiImplicitParam(name = "userId", value = "The genuine User ID or requester (extracted from the user's JWT token)", required = true, dataType = "long", paramType = "header")
	public void deleteTodos(@PathVariable String todoId, @RequestHeader("userId") Long secureUserId) {
		logger.info("Delete todo with id " + todoId + " for user with id " + secureUserId);
		todosService.deleteTodo(todoId, secureUserId);
	}

	@ApiIgnore
	@DeleteMapping(path = "/clear")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void clearTodos(@RequestHeader("userId") Long secureUserId) {
		logger.info("Clear all todos for user with id " + secureUserId);
		todosService.clearTodos(secureUserId);
	}
}
