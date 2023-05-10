package com.maksimpegov.todos.models;

import java.util.List;

public class TodoServiceResponse {
    private String userId;
    private String text;
    private List<Todo> todos;

    public TodoServiceResponse(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public TodoServiceResponse(String userId, String text, List<Todo> todos) {
        this.userId = userId;
        this.text = text;
        this.todos = todos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
