package com.maksimpegov.todos.models;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Todo")
@Table(name = "todo", uniqueConstraints = {
//        @UniqueConstraint(name = "todo_text_unique", columnNames = "text")
})
public class Todo {

    // Adding primary key to the row
    @Id
    // Creating generator for primary key
    @SequenceGenerator(name = "todo_sequence", sequenceName = "todo_sequence", allocationSize = 1)
    // Using generator for primary key
    @GeneratedValue(strategy = SEQUENCE, generator = "todo_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "closed_at")
    private Date closedAt;

    public Todo() {
    }

    public Todo(String text, String userId, Date createdAt) {
        this.text = text;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Todo(String text, String userId) {
        this.text = text;
        this.userId = userId;
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

    public Long getId() {
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

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", closedAt=" + closedAt +
                '}';
    }
}
