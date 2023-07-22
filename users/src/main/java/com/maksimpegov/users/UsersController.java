package com.maksimpegov.users;

import com.maksimpegov.users.models.PasswordEditRequest;
import com.maksimpegov.users.user.UserDto;
import com.maksimpegov.users.user.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Endpoints")
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @ApiOperation(value = "Register new user", notes = "Provide username and password in body")
    @PostMapping(path = "/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto userDto) {
        usersService.registerUser(userDto);
    }

    @ApiOperation(value = "Login user", notes = "Provide username and password in body", response = UserDto.class)
    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        UserDto response = usersService.loginUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Get user info", notes = "Provide username in path", response = UserInfo.class)
    @GetMapping(path = "/{username}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String username) {
        UserInfo response = usersService.getUserInfo(username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
}
