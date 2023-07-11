package com.maksimpegov.users;

import com.maksimpegov.users.models.ErrorResponse;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.user.UserDto;
import com.maksimpegov.users.user.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    private static final UserMapper mapper;

    static {
        mapper = UserMapper.INSTANCE;
    }

    public static ResponseEntity<Object> build(UserServiceResponse response) {
        HttpStatus httpStatus = getStatusFromCode(response.getStatus());
        if (httpStatus.isError()) {
            ErrorResponse errorResponse = new ErrorResponse(response.getMessage());
            return ResponseEntity.status(httpStatus).body(errorResponse);
        } else {
            if (response.getData() == null) {
                return ResponseEntity.status(httpStatus).build();
            }
            UserDto userDto = mapper.userToUserDto(response.getData());
            return ResponseEntity.status(httpStatus).body(userDto);
        }
    }

    private static HttpStatus getStatusFromCode(String status) {
        HttpStatus httpStatus;
        try {
            int statusCode = Integer.parseInt(status);
            httpStatus = HttpStatus.valueOf(statusCode);
        } catch (NumberFormatException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return httpStatus;
    }
}
