package com.maksimpegov.todos;

import com.maksimpegov.todos.exeption.ApiRequestException;
import com.maksimpegov.todos.models.AddTodoRequest;
import com.maksimpegov.todos.todo.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public record TodosService(TodoRepository todoRepository, TodoMapper mapper) {

    public List<TodoDto> getTodos(Long userId) {
        try {
            List<Todo> todos = todoRepository.getAllByUserId(userId);

            return todos.stream().map(mapper::map).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Internal Server Error", e.getMessage(), 500);
        }
    }

    public TodoInfo getTodoInfo(Long todoId) {
        try {
            if (!todoRepository.existsById(todoId)) {
                throw new ApiRequestException("Invalid id", "Todo with this id does not exist", 404);
            }
            Todo todo = todoRepository.getOne(todoId);

            return mapper.mapInfo(todo);
        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Internal Server Error", e.getMessage(), 500);
        }
    }

    public TodoDto addTodo(AddTodoRequest text, Long userId) {
        try {
            Todo todo = new Todo();
            todo.setText(text.getText());
            todo.setCreatedAt(new Date());
            todo.setUserId(userId);
            if (!todo.todoValidation()) {
                throw new ApiRequestException("Empty todo", "You provided empty todo", 400);
            }

            Todo createdTodo = todoRepository.save(todo);
            return mapper.map(createdTodo);
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Internal Server Error", e.getMessage(), 500);
        }
    }

    public TodoDto editTodo(TodoDto newTodoPartDto) {
        try {
            Todo newTodoPart = mapper.map(newTodoPartDto);

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
            return mapper.map(newTodo);
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Internal Server Error", e.getMessage(), 500);
        }
    }

    public void deleteTodo(String todoId) {
        try {
            if (!todoRepository.existsById(Long.parseLong(todoId))) {
                throw new ApiRequestException("Invalid id", "Todo with this id does not exist", 404);
            }

            todoRepository.getOne(Long.parseLong(todoId));
            todoRepository.deleteById(Long.parseLong(todoId));
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Internal Server Error", e.getMessage(), 500);
        }
    }

    public void clearTodos(Long userId) {
        try {
            todoRepository.deleteTodosByUserId(userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("Internal Server Error", e.getMessage(), 500);
        }
    }
}
