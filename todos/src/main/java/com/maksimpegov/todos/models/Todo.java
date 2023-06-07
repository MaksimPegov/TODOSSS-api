package com.maksimpegov.todos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Todo")
@Table(name = "todo", uniqueConstraints = {
//        @UniqueConstraint(name = "todo_text_unique", columnNames = "text")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // to avoid hibernate extra properties
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

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "closed_at")
    private Date closedAt;

    // empty constructor
    public Todo() {
    }

    // constructor for creating new Todo
    public Todo(String text, String userId, Date createdAt) {
        this.text = text;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // constructor for closing Todo
    public Todo(String userId, Date completedAt) {
        this.id = id;
        this.closedAt = completedAt;
    }

    // constructor for updating Todo text
    public Todo(Long id, String text, String userId) {
        this.id = id;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
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



    public String getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", closedAt=" + closedAt +
                '}';
    }
}
