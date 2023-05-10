package com.maksimpegov.todos;

import com.maksimpegov.todos.models.Todo;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;

@Service
public record TodosService() {
    public List<Todo> getTodos(String userId) {
        List<Todo> todos = List.of(new Todo("Sleep", "1", new java.util.Date()), new Todo("Eat", "2", new java.util.Date()));
        return todos;
    }
}
