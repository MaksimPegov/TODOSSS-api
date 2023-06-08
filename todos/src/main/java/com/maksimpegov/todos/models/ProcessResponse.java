package com.maksimpegov.todos.models;

import java.util.ArrayList;
import java.util.List;

public class ProcessResponse {
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

    public ProcessResponse() {
    }

    public ProcessResponse(String status, String message, List<Todo> todos) {
        this.status = status;
        this.todos = todos;
        this.message = message;
    }

    public ProcessResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
