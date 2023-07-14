package com.maksimpegov.todos;

import com.maksimpegov.todos.exeption.ApiRequestException;
import com.maksimpegov.todos.models.AddTodoRequest;
import com.maksimpegov.todos.models.TodoServiceResponse;
import com.maksimpegov.todos.todo.Todo;
import com.maksimpegov.todos.todo.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public record TodosService(TodoRepository todoRepository) {

    public TodoServiceResponse getTodos(Long userId) {
        try {
            List<Todo> todos = todoRepository.getAllByUserId(userId);

            return new TodoServiceResponse(200, todos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Something went wrong", e.getMessage(), 404);
        }
    }

    public TodoServiceResponse getTodoById(Long todoId) {
        try {
            if (!todoRepository.existsById(todoId)) {
                throw new ApiRequestException("Invalid id", "Todo with this id does not exist", 404);
            }
            Todo todo = todoRepository.getOne(todoId);

            return new TodoServiceResponse(200, Collections.singletonList(todo));
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException(e.getError(), e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Todo with this id does not exist", e.getMessage(), 404);
        }
    }

    public TodoServiceResponse addTodo(AddTodoRequest text, Long userId) {
        try {
            Todo todo = new Todo();
            todo.setText(text.getText());
            todo.setCreatedAt(new Date());
            todo.setUserId(userId);
            if (!todo.todoValidation()) {
                throw new ApiRequestException("Empty todo", "You provided empty todo", 400);
            }

            Todo createdTodo = todoRepository.save(todo);
            return new TodoServiceResponse(201, Collections.singletonList(createdTodo));
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException(e.getError(), e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Something went wrong", e.getMessage(), 404);
        }
    }

    public TodoServiceResponse editTodo(Todo newTodoPart) {
        try {
            if (!todoRepository.existsById(newTodoPart.getId())) {
                throw new ApiRequestException("Invalid id", "Todo with this id does not exist", 404);
            }
            Todo oldTodo = todoRepository.getOne(newTodoPart.getId());

            oldTodo.setUpdatedAt(new Date());

            if (newTodoPart.getText() != null) {
                if (!newTodoPart.todoValidation()) {
                    throw new ApiRequestException("Empty todo", "You provided empty todo", 400);
                }

                oldTodo.setText(newTodoPart.getText());
            } else if (newTodoPart.isCompleted()) {
                oldTodo.setCompleted(true);
                oldTodo.setClosedAt(new Date());
            } else {
                throw new ApiRequestException(
                        "Your request is not valid",
                        "Check your request, provide todoId and field that you want to change",
                        400
                );
            }

            Todo newTodo = todoRepository.save(oldTodo);
            return new TodoServiceResponse(200, Collections.singletonList(newTodo));
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException(e.getError(), e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Something went wrong", e.getMessage(), 404);
        }
    }

    public void deleteTodo(String todoId) {
        try {
            if (!todoRepository.existsById(Long.parseLong(todoId))) {
                throw new ApiRequestException("Invalid id", "Todo with this id does not exist", 404);
            }

            Todo deletedTodo = todoRepository.getOne(Long.parseLong(todoId));
            todoRepository.deleteById(Long.parseLong(todoId));
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException(e.getError(), e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Something went wrong", e.getMessage(), 404);
        }
    }

    public void clearTodos(Long userId) {
        try {
            todoRepository.deleteTodosByUserId(userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Something went wrong", e.getMessage(), 404);
        }
    }
}
