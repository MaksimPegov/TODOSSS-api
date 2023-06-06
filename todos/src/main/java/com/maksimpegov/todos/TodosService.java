package com.maksimpegov.todos;

import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;

@Service
public record TodosService(TodoRepository todoRepository) {
    public List<Todo> getTodos(String userId) {
//        List<Todo> todos = todoRepository.getAllByUserId(userId);
//        return todos;
        List<Todo> todos = List.of(new Todo("Sleep", "1", new java.util.Date()), new Todo("Eat", "2", new java.util.Date()));
        return todos;
    }

    public String addTodo(String userId, Todo todo) {
        Todo todo1 = new Todo("Sleep", "1", new java.util.Date());
        todoRepository.save(todo1);
        return "OK";
    }

    public String deleteTodo(String userId, String todoId) {
        todoRepository.deleteById(Long.parseLong(todoId));
        return "OK";
    }

    public String clearTodos(String userId) {
        Todo todo1 = new Todo("Sleep", "1", new java.util.Date());
        todoRepository.deleteByUserId(userId);
        return "OK";
    }
}
