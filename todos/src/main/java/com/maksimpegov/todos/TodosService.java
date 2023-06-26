package com.maksimpegov.todos;

import com.maksimpegov.todos.models.Todo;
import com.maksimpegov.todos.models.TodoRepository;
import com.maksimpegov.todos.models.TodoServiceResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public record TodosService(TodoRepository todoRepository) {

    public TodoServiceResponse getTodos(Long userId) {
        try {
            List<Todo> todos = todoRepository.getAllByUserId(userId);
            return new TodoServiceResponse("200", "Success", todos);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("404", "Something went wrong. " + e.getMessage());
        }
    }

    public TodoServiceResponse addTodo(Todo todo) {
        try {
            todo.setCreatedAt(new Date());
            if (!todo.todoValidation()) {
                return new TodoServiceResponse("400", "Your todo can not be empty");
            }

            Todo createdTodo = todoRepository.save(todo);
            return new TodoServiceResponse("201", "Todo created", Collections.singletonList(createdTodo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("400", "Your todo is not valid. " + e.getMessage());
        }
    }

    public TodoServiceResponse editTodo(Todo newTodoPart) {
        try {
            Todo oldTodo = todoRepository.getOne(newTodoPart.getId());
            oldTodo.setUpdatedAt(new Date());

            if (newTodoPart.getText() != null) {
                if (!newTodoPart.todoValidation()) {
                    return new TodoServiceResponse("400", "Your todo can not be empty");
                }

                oldTodo.setText(newTodoPart.getText());
            } else if (newTodoPart.isCompleted()) {
                oldTodo.setCompleted(true);
                oldTodo.setClosedAt(new Date());
            } else {
                return new TodoServiceResponse("400", "Your request is not valid");
            }

            Todo newTodo = todoRepository.save(oldTodo);
            return new TodoServiceResponse("200", "Todo was edited", Collections.singletonList(newTodo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("404", "You may provide wrong todo id or todo. " + e.getMessage());
        }
    }

    public TodoServiceResponse deleteTodo(String todoId) {
        try {
            if (!todoRepository.existsById(Long.parseLong(todoId))) {
                return new TodoServiceResponse("404", "Todo with this id does not exist");
            }

            Todo deletedTodo = todoRepository.getOne(Long.parseLong(todoId));
            todoRepository.deleteById(Long.parseLong(todoId));
            return new TodoServiceResponse("204", "Todo deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("404", "Something went wrong. " + e.getMessage());
        }
    }

    public TodoServiceResponse clearTodos(Long userId) {
        try {
            todoRepository.deleteTodosByUserId(userId);
            return new TodoServiceResponse("204", "All todos were deleted");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TodoServiceResponse("500", "Error. " + e.getMessage());
        }
    }
}
