package com.maksimpegov.users;

import com.maksimpegov.users.models.PasswordEditRequest;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.user.User;
import com.maksimpegov.users.user.UserDto;
import com.maksimpegov.users.user.UserInfo;
import com.maksimpegov.users.user.UserMapper;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        usersService.registerUser(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        UserServiceResponse response = usersService.loginUser(user);
        User data = response.getData();
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(mapUser(data));
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String username) {
        UserServiceResponse response = usersService.getUserInfo(username);
        User data = response.getData();
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(mapToInfo(data));
    }

    @PatchMapping(path = "/password")
    @ResponseStatus(value = HttpStatus.OK)
    public void editPassword(@RequestBody PasswordEditRequest editRequest) {
        usersService.editPassword(editRequest);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestBody UserDto deleteRequest) {
        usersService.deleteUser(deleteRequest);
    }

    private HttpStatus getStatus(int status) {
        HttpStatus httpStatus;
        try {
            httpStatus = HttpStatus.valueOf(status);
        } catch (NumberFormatException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return httpStatus;
    }

    public UserDto mapUser(User user) {
        return mapper.userToUserDto(user);
    }

    public UserInfo mapToInfo(User user) {
        return mapper.userToUserInfo(user);
    }
}
