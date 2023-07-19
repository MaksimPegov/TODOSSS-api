package com.maksimpegov.users.exeption;

public class ApiRequestException extends RuntimeException {
    private final String error;

    private final int HttpStatus;

    public int getHttpStatus() {
        return HttpStatus;
    }

    public String getError() {
        return error;
    }

    public ApiRequestException(String error, String message, int httpStatus) {
        super(message);
        this.HttpStatus = httpStatus;
        this.error = error;
    }
}
