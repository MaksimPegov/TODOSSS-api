package com.maksimpegov.users;

import com.maksimpegov.users.exeption.ApiRequestException;
import com.maksimpegov.users.models.PasswordEditRequest;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.user.User;
import com.maksimpegov.users.user.UserDto;
import com.maksimpegov.users.user.UserMapper;
import com.maksimpegov.users.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class UsersService {

    @Value("${spring.properties.todos.url}")
    String todoMicroserviceUrl;

    private final RestTemplate restTemplate;
    private final UsersRepository usersRepository;

    private final UserMapper userDTOMapper;

    @Autowired
    public UsersService(RestTemplate restTemplate, UsersRepository usersRepository, UserMapper userDTOMapper) {
        this.restTemplate = restTemplate;
        this.usersRepository = usersRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public UserServiceResponse registerUser(User user) {
        try {

            if (user.getUsername() == null || user.getPassword() == null) {
                throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
            } else if (!user.userValidation()) {
                throw new ApiRequestException("Invalid request", "Username or password is not valid. Username: min 3 symbols, password: min 6 symbols)", 400);

            } else if (usersRepository.findByUsername(user.getUsername()) != null) {
                throw new ApiRequestException("Conflict", "User with this username already exists", 409);
            }

            user.setCreated_at(new Date());
            usersRepository.save(user);
            return new UserServiceResponse(201, "User created successfully");
        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiRequestException("Internal server error", e.getMessage(), 500);
        }
    }

    public UserServiceResponse loginUser(User user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
            }
            User userFromDb = usersRepository.findByUsername(user.getUsername());

            if (userFromDb == null) {
                throw new ApiRequestException("Not found", "User with this username does not exist", 404);
            } else if (!userFromDb.getPassword().equals(user.getPassword())) {
                throw new ApiRequestException("Unauthorized", "Wrong password", 401);
            }

            userFromDb.hidePassword();
            return new UserServiceResponse(200, "User logged in successfully", userFromDb);
        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiRequestException("Internal server error", e.getMessage(), 500);
        }
    }

    public UserServiceResponse getUserInfo(String username) {
        try {
            if (username == null) {
                throw new ApiRequestException("Invalid request", "Username is empty", 400);
            }
            User userFromDb = usersRepository.findByUsername(username);

            if (userFromDb == null) {
                throw new ApiRequestException("Not found", "User with this username does not exist", 404);
            }

            userFromDb.hidePassword();
            return new UserServiceResponse(200, "User info received successfully", userFromDb);
        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiRequestException("Internal server error", e.getMessage(), 500);
        }
    }

    public UserServiceResponse editPassword(PasswordEditRequest editRequest) {
        try {
            if (editRequest.getUsername() == null || editRequest.getOldPassword() == null || editRequest.getNewPassword() == null) {
                throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
            }
            User userFromDb = usersRepository.findByUsername(editRequest.getUsername());

            if (userFromDb == null) {
                throw new ApiRequestException("Not found", "User with this username does not exist", 404);
            }
            if (!userFromDb.getPassword().equals(editRequest.getOldPassword())) {
                throw new ApiRequestException("Unauthorized", "You provided wrong old password", 401);
            }
            userFromDb.setPassword(editRequest.getNewPassword());

            if (!userFromDb.userValidation()) {
                throw new ApiRequestException("Invalid request", "New password is not valid. Password: min 6 symbols", 400);
            }
            usersRepository.save(userFromDb);
            return new UserServiceResponse(200, "Password edited successfully");
        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiRequestException("Internal server error", e.getMessage(), 500);
        }
    }

    public UserServiceResponse deleteUser(UserDto deleteRequest) {
        try {
            if (deleteRequest.getUsername() == null || deleteRequest.getPassword() == null) {
                throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
            }

            User user = usersRepository.findByUsername(deleteRequest.getUsername());

            if (user == null) {
                throw new ApiRequestException("Not found", "User with this username does not exist", 404);
            }

            if (!user.getPassword().equals(deleteRequest.getPassword())) {
                throw new ApiRequestException("Unauthorized", "Wrong password", 401);
            }

            String url = todoMicroserviceUrl + "/clear/" + user.getId();

            // request to todos-microservice to delete all todos of this user
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

            if (response.getStatusCodeValue() < 400) {
                usersRepository.delete(user);
                return new UserServiceResponse(204, "User deleted successfully");
            }
            return new UserServiceResponse(response.getStatusCodeValue(), "Unable to delete user todos");
        } catch (ApiRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiRequestException("Internal server error", e.getMessage(), 500);
        }
    }
}
