package com.maksimpegov.todos;

import com.maksimpegov.todos.models.AddTodoRequest;
import com.maksimpegov.todos.todo.TodoDto;
import com.maksimpegov.todos.todo.TodoInfo;
import io.swagger.annotations.Api;
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

	@ApiOperation(value = "Get all todos for a user", notes = "Provide userId in path", response = TodoDto.class, responseContainer = "List")
	@GetMapping(path = "/{userId}")
	public ResponseEntity<List<TodoDto>> getTodos(@PathVariable Long userId) {
		List<TodoDto> response = todosService.getTodos(userId);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Get todo info", notes = "Provide todoId in path", response = TodoInfo.class)
	@GetMapping(path = "/todo/{todoId}")
	public ResponseEntity<TodoInfo> getTodoInfo(@PathVariable Long todoId) {
		TodoInfo response = todosService.getTodoInfo(todoId);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Add a todo for a user", notes = "Provide userId in path and text of todo in body", response = TodoDto.class)
	@PostMapping(path = "{userId}")
	public ResponseEntity<TodoDto> addTodos(@RequestBody AddTodoRequest todoText, @PathVariable Long userId) {
		logger.info("POST /api/todos/{userId} with userId = " + userId + " and todoText = " + todoText.getText());
		TodoDto response = todosService.addTodo(todoText, userId);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Edit a todo", notes = "Provide only todoId and a todo field that you want to edit in body", response = TodoDto.class)
	@PatchMapping
	public ResponseEntity<TodoDto> editTodos(@RequestBody TodoDto newTodoPartDto) {
		TodoDto response = todosService.editTodo(newTodoPartDto);
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Delete a todo", notes = "Provide todoId in path")
	@DeleteMapping(path = "/{userId}/{todoId}") // userId may be removed in the future
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteTodos(@PathVariable String userId, @PathVariable String todoId) {
		todosService.deleteTodo(todoId);
	}

	@ApiIgnore
	@DeleteMapping(path = "/clear/{userId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void clearTodos(@PathVariable Long userId) {
		todosService.clearTodos(userId);
	}
}
