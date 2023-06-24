package com.maksimpegov.users.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordEditRequest {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
