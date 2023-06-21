package com.maksimpegov.users;

import com.maksimpegov.users.models.User;
import com.maksimpegov.users.models.UserRegistrationRequest;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.models.UsersControllerResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public record UsersController(UsersService usersService) {
    @PostMapping
    public UserServiceResponse registerUser(@RequestBody User user) {
        UserServiceResponse response = usersService.registerUser(user);
        return response;
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable String userId) {
        String result = usersService.deleteUser(userId);
        return result;
    }

}
