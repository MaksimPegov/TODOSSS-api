package com.maksimpegov.todos.models;

import java.util.List;

public class TodoControllerResponse {
    private String status = "200";

    private String message = "OK";

    private List<Todo> todos;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public TodoControllerResponse() {
    }

    public TodoControllerResponse(String status, String message, List<Todo> todos) {
        this.status = status;
        this.message = message;
        this.todos = todos;
    }
}
