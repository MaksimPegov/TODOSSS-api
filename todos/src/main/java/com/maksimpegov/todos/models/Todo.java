package com.maksimpegov.todos.models;

import java.util.Date;

public class Todo {
    private String id;
    private String text;
    private boolean completed;
    private String userId;
    private Date createdAt;
    private Date closedAt;

    public Todo() {}

    public Todo(String text, String userId, Date createdAt) {
        this.text = text;
        this.userId = userId;
        this.createdAt = createdAt;
        this.id = java.util.UUID.randomUUID().toString();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }
}
