package com.maksimpegov.todos;

import com.maksimpegov.todos.models.ErrorResponse;
import com.maksimpegov.todos.models.TodoServiceResponse;
import com.maksimpegov.todos.todo.Todo;
import com.maksimpegov.todos.todo.TodoMapper;
import com.maksimpegov.todos.todo.TodoMapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class ResponseBuilder {

    public static ResponseEntity<Object> build(TodoServiceResponse response, Function<Todo, Object> mapper) {
        HttpStatus httpStatus = getStatusFromCode(response.getStatus());

        if (httpStatus.isError()) {
            ErrorResponse errorResponse = new ErrorResponse(response.getMessage());
            return ResponseEntity.status(httpStatus).body(errorResponse);
        } else {
            if (response.getData() != null) {
                return ResponseEntity.status(httpStatus).body(
                        response
                                .getData()
                                .stream()
                                .map(todo -> mapper.apply((Todo) todo))
                );

            }
            return ResponseEntity.status(httpStatus).build();
        }
    }

    private static HttpStatus getStatusFromCode(String status) {
        HttpStatus httpStatus;
        try {
            int statusCode = Integer.parseInt(status);
            httpStatus = HttpStatus.valueOf(statusCode);
        } catch (NumberFormatException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return httpStatus;
    }
}
