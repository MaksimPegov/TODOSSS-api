package com.maksimpegov.todos.todo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "All other info about todo, that not included in TodoDto")
public class TodoInfo {
    Long id;
    Date createdAt;
    Date updatedAt;
    Date closedAt;
}
