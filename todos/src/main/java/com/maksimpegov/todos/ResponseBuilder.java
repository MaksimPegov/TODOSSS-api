package com.maksimpegov.todos;

import com.maksimpegov.todos.models.ErrorResponse;
import com.maksimpegov.todos.models.TodoServiceResponse;
import com.maksimpegov.todos.todo.TodoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private static final TodoMapper mapper;

    static {
        mapper = TodoMapper.INSTANCE;
    }

    public static ResponseEntity<Object> build(TodoServiceResponse response) {
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
                                .map(mapper::map)
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
