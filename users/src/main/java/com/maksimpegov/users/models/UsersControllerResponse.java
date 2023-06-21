package com.maksimpegov.users.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsersControllerResponse {
    private String status;

    private String message;

    Object data;

    public UsersControllerResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

}
