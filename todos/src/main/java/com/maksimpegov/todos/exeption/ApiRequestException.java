package com.maksimpegov.todos.exeption;

public class ApiRequestException extends RuntimeException{
    private final int HttpStatus;

    public int getHttpStatus() {
        return HttpStatus;
    }

    public ApiRequestException(String message, int httpStatus) {
        super(message);
        HttpStatus = httpStatus;
    }
}
