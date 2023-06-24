package com.maksimpegov.todos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TodoServiceResponse {
    private String status;

    private String message;

    private List<Todo> data;

    public TodoServiceResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
