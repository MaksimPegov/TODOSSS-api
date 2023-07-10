package com.maksimpegov.todos.todo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Todo")
@Table(name = "todo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // to avoid hibernate extra properties
@Getter
@Setter
@NoArgsConstructor
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
    private Long userId;

    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "closed_at")
    private Date closedAt;

    public Boolean todoValidation() {
        setText(text.trim());
        return this.text.length() > 0;
    }

    // constructor for creating new Todo
    public Todo(String text, Long userId, Date createdAt) {
        this.text = text;
        this.userId = userId;
        this.createdAt = createdAt;
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
