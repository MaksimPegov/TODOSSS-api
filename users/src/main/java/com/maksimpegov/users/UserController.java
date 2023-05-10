package com.maksimpegov.users;

import com.maksimpegov.users.models.UserRegistrationRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public record UserController(UserService userService) {
    @PostMapping
    public String registerUser(@RequestBody UserRegistrationRequest request) {
        String result = userService.registerUser(request);
        return result;
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable String userId) {
        String result = userService.deleteUser(userId);
        return result;
    }

}
