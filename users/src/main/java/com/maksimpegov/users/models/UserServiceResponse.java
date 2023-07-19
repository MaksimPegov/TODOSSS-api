package com.maksimpegov.users.models;

import com.maksimpegov.users.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceResponse {
    private int status;

    private String message;

    private User data;

    public UserServiceResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
