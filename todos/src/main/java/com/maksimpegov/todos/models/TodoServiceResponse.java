package com.maksimpegov.todos.models;

import com.maksimpegov.todos.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TodoServiceResponse {
    private int status;

    private List<Todo> data;

    public TodoServiceResponse(int status) {
        this.status = status;
    }
}
