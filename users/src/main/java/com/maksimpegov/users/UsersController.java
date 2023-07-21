package com.maksimpegov.users;

import com.maksimpegov.users.models.PasswordEditRequest;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.user.User;
import com.maksimpegov.users.user.UserDto;
import com.maksimpegov.users.user.UserInfo;
import com.maksimpegov.users.user.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Endpoints")
@RequestMapping("/api/users/v1/")
public class UsersController {

    private final UserMapper mapper;
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
        this.mapper = UserMapper.INSTANCE;
    }

    @ApiOperation(value = "Register new user", notes = "Provide username and password in body")
    @PostMapping(path = "/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        usersService.registerUser(user);
    }

    @ApiOperation(value = "Login user", notes = "Provide username and password in body", response = UserDto.class)
    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        UserServiceResponse response = usersService.loginUser(user);
        User data = response.getData();
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(mapUser(data));
    }

    @ApiOperation(value = "Get user info", notes = "Provide username in path", response = UserInfo.class)
    @GetMapping(path = "/{username}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String username) {
        UserServiceResponse response = usersService.getUserInfo(username);
        User data = response.getData();
        HttpStatus status = getStatus(response.getStatus());
        return ResponseEntity.status(status).body(mapToInfo(data));
    }

    @ApiOperation(value = "Edit user password", notes = "Provide username, old password and new password in body")
    @PatchMapping(path = "/password")
    @ResponseStatus(value = HttpStatus.OK)
    public void editPassword(@RequestBody PasswordEditRequest editRequest) {
        usersService.editPassword(editRequest);
    }

    @ApiOperation(value = "Delete user", notes = "Provide username and password in body")
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

    private UserDto mapUser(User user) {
        return mapper.userToUserDto(user);
    }

    private UserInfo mapToInfo(User user) {
        return mapper.userToUserInfo(user);
    }
}
