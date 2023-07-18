package com.maksimpegov.todos.models;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Request for adding new todo")
public class AddTodoRequest {
    private String text;
}
