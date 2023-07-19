package com.maksimpegov.users.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getHttpStatus());

        ApiException exception = new ApiException(
                e.getError(),
                e.getMessage(),
                httpStatus.value(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(exception, httpStatus);
    }
}
