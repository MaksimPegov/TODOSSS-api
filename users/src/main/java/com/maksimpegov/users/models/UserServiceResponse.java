package com.maksimpegov.users.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceResponse {
    private String status = "200";

    private String message = "OK";

    private User user = null;

    public UserServiceResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
