package com.maksimpegov.todos;

import com.maksimpegov.todos.models.ProcessResponse;
import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public record TodosService(TodoRepository todoRepository) {
    public ProcessResponse getTodos(String userId) {
        ProcessResponse response = new ProcessResponse();
        try {
            List<Todo> todos = todoRepository.getAllByUserId(userId);
            response.setTodos(todos);
            response.setStatus("200");
        } catch (Exception e) {
            response.setStatus("404");
        }
        return response;
    }

    public ProcessResponse addTodo(Todo todo) {
        ProcessResponse response = new ProcessResponse();
        try {
            todo.setCreatedAt(new Date());
            todoRepository.save(todo);
            response.setTodos(Collections.singletonList(todo));
            response.setStatus("200");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus("400");
        }
        return response;
    }

    public ProcessResponse editTodo(Todo newTodo) {
        ProcessResponse response = new ProcessResponse();
        try {
            Todo oldTodo = todoRepository.getOne(newTodo.getId());
            oldTodo.setCreatedAt(new Date());
            if (newTodo.getText() != null) {
                oldTodo.setText(newTodo.getText());
            } else if (newTodo.getClosedAt() != null) {
                oldTodo.setClosedAt(newTodo.getClosedAt());
            } else if (newTodo.isCompleted()) {
                oldTodo.setCompleted(true);
                oldTodo.setClosedAt(new Date());
            } else {
                response.setStatus("400");
            }
            todoRepository.save(oldTodo);
            response.setTodos(Collections.singletonList(oldTodo));
            response.setStatus("200");
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus("404");
            return response;
        }

    }

    public ProcessResponse deleteTodo(String todoId) {
        ProcessResponse response = new ProcessResponse();
        try {
            if (!todoRepository.existsById(Long.parseLong(todoId))) {
                response.setStatus("404");
                return response;
            }
            response.setTodos(Collections.singletonList(todoRepository.getOne(Long.parseLong(todoId))));
            todoRepository.deleteById(Long.parseLong(todoId));
            response.setStatus("200");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus("404");
        }
        return response;
    }

    public ProcessResponse clearTodos(String userId) {
        ProcessResponse response = new ProcessResponse();
        try {
            todoRepository.deleteByUserId(userId);
            response.setStatus("200");
        } catch (Exception e) {
            response.setStatus("404");
            System.out.println(e.getMessage());
        }
        return response;
    }
}
