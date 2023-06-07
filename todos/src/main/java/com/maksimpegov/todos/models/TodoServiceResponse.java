package com.maksimpegov.todos.models;

import java.util.Collections;
import java.util.List;

public class TodoServiceResponse {
    private String userId;
    private String status;
    private List<Todo> todos = Collections.emptyList();

    public TodoServiceResponse(String userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public TodoServiceResponse(String userId, String status, List<Todo> todos) {
        this.userId = userId;
        this.status = status;
        this.todos = todos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
}
