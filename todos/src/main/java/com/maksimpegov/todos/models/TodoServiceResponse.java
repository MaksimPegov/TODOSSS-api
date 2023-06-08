package com.maksimpegov.todos.models;

import java.util.ArrayList;
import java.util.List;

public class TodoServiceResponse {
    private String status = "200";

    private List<Todo> todos = new ArrayList<>();

    private String message = "OK";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TodoServiceResponse() {
    }

    public TodoServiceResponse(String status, String message, List<Todo> todos) {
        this.status = status;
        this.todos = todos;
        this.message = message;
    }

    public TodoServiceResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
