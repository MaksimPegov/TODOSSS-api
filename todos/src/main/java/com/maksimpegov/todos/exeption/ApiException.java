package com.maksimpegov.todos.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ApiException {
    private final String message;
    private final int HttpStatus;
    private final ZonedDateTime timestamp;
}
