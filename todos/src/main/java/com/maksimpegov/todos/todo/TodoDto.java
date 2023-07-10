package com.maksimpegov.todos.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    Long id;
    String text;
    boolean completed;
    Date createdAt;
    Date updatedAt;
    Date closedAt;
}
