package com.maksimpegov.users;

import com.maksimpegov.users.models.PasswordEditRequest;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.user.User;
import com.maksimpegov.users.user.UserDto;
import com.maksimpegov.users.user.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users/v1/")
public class UsersController {

    private final UserMapper mapper;
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
        this.mapper = UserMapper.INSTANCE;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        UserServiceResponse response = usersService.registerUser(user);
        return ResponseBuilder.build(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        UserServiceResponse response = usersService.loginUser(user);
        return ResponseBuilder.build(response);
    }

    @PatchMapping(path = "/password")
    public ResponseEntity<Object> editPassword(@RequestBody PasswordEditRequest editRequest) {
        UserServiceResponse response = usersService.editPassword(editRequest);
        return ResponseBuilder.build(response);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUser(@RequestBody UserDto deleteRequest) {
        UserServiceResponse result = usersService.deleteUser(deleteRequest);
        return ResponseBuilder.build(result);
    }
}
