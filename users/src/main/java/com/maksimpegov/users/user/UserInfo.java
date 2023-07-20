package com.maksimpegov.users.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "All other info about user, that not included in UserDto")
public class UserInfo {
    private String username;
    private String created_at;
}
