package com.maksimpegov.users;

import com.maksimpegov.users.models.User;
import com.maksimpegov.users.models.UserServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users/v1/")
public record UsersController(UsersService usersService) {
    @PostMapping(path = "/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        UserServiceResponse response = usersService.registerUser(user);
        return ResponseBuilder.build(response);
    }

    @PostMapping(path = "/login")
    public UserServiceResponse loginUser(@RequestBody User user) {
//        UserServiceResponse response = usersService.loginUser(user);
        return null;
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable String userId) {
        String result = usersService.deleteUser(userId);
        return result;
    }

}
