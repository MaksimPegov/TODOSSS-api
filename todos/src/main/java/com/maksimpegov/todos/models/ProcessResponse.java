package com.maksimpegov.todos.models;

import java.util.Collections;
import java.util.List;

public class ProcessResponse {
    private String status;

    private List<Todo> todos = Collections.emptyList();

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

    public ProcessResponse() {
    }

    public ProcessResponse(String status, List<Todo> todos) {
        this.status = status;
        this.todos = todos;
    }

    public ProcessResponse(String status) {
        this.status = status;
    }
}
