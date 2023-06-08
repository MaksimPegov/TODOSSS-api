package com.maksimpegov.todos;

import com.maksimpegov.todos.models.TodoServiceResponse;
import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public record TodosService(TodoRepository todoRepository) {

    public TodoServiceResponse getTodos(String userId) {
        try {
            List<Todo> todos = todoRepository.getAllByUserId(userId);
            return new TodoServiceResponse("200", "Success", todos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("404", "Something went wrong. " + e.getMessage(), null);
        }
    }

    public TodoServiceResponse addTodo(Todo todo) {
        try {
            todo.setCreatedAt(new Date());
            todoRepository.save(todo);
            return new TodoServiceResponse("201", "Success", Collections.singletonList(todo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("400", "Your todo is not valid. " + e.getMessage(), null);
        }
    }

    public TodoServiceResponse editTodo(Todo newTodoPart) {
        try {
            Todo oldTodo = todoRepository.getOne(newTodoPart.getId());
            oldTodo.setUpdatedAt(new Date());

            if (newTodoPart.getText() != null) {
                oldTodo.setText(newTodoPart.getText());
            } else if (newTodoPart.isCompleted()) {
                oldTodo.setCompleted(true);
                oldTodo.setClosedAt(new Date());
            } else {
                return new TodoServiceResponse("400", "Your request is not valid");
            }

            todoRepository.save(oldTodo);
            return new TodoServiceResponse("200", "Success", Collections.singletonList(oldTodo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("404", "You may provide wrong todo id or todo. " + e.getMessage());
        }
    }

    public TodoServiceResponse deleteTodo(String todoId) {
        try {
            if (!todoRepository.existsById(Long.parseLong(todoId))) {
                return new TodoServiceResponse("404", "Todo with this id does not exist.");
            }

            Todo deletedTodo = todoRepository.getOne(Long.parseLong(todoId));
            todoRepository.deleteById(Long.parseLong(todoId));
            return new TodoServiceResponse("200", "Success", Collections.singletonList(deletedTodo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("404", "Something went wrong. " + e.getMessage());
        }
    }

    public TodoServiceResponse clearTodos(String userId) {
        try {
            todoRepository.deleteByUserId(userId);
            return new TodoServiceResponse("200", "Success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("404", "Todo with this id does not exist. " + e.getMessage());
        }
    }
}
