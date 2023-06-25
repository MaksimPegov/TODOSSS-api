package com.maksimpegov.users;

import com.maksimpegov.users.models.PasswordEditRequest;
import com.maksimpegov.users.models.User;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final RestTemplate restTemplate;
    private UsersRepository usersRepository;

    @Autowired
    public UsersService(RestTemplate restTemplate, UsersRepository usersRepository) {
        this.restTemplate = restTemplate;
        this.usersRepository = usersRepository;
    }

    public UserServiceResponse registerUser(User user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                return new UserServiceResponse("400", "Your request is not valid");

            } else if (!user.userValidation()) {
                return new UserServiceResponse("400", "Username or password is not valid. Username: min 3 symbols, password: min 6 symbols)");

            } else if (usersRepository.findByUsername(user.getUsername()) != null) {
                return new UserServiceResponse("409", "User with this username already exists");
            }

            user.setCreated_at(new Date());
            usersRepository.save(user);
            return new UserServiceResponse("201", "User created successfully");
        } catch (Exception e) {
            return new UserServiceResponse("500", "Error. " + e.getMessage());
        }
    }

    public UserServiceResponse loginUser(User user) {
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                return new UserServiceResponse("400", "Your request is not valid");
            }
            User userFromDb = usersRepository.findByUsername(user.getUsername());

            if (userFromDb == null) {
                return new UserServiceResponse("404", "User with this username does not exist");
            } else if (!userFromDb.getPassword().equals(user.getPassword())) {
                return new UserServiceResponse("401", "Wrong password");
            }

            userFromDb.hidePassword();
            return new UserServiceResponse("200", "User logged in successfully", userFromDb);
        } catch (Exception e) {
            return new UserServiceResponse("500", "Error. " + e.getMessage());
        }
    }

    public UserServiceResponse editPassword(PasswordEditRequest editRequest) {
        try {
            if (editRequest.getUserId() == null || editRequest.getOldPassword() == null || editRequest.getNewPassword() == null) {
                return new UserServiceResponse("400", "Your request is not valid");
            }
            Optional<User> userFromDb = usersRepository.findById(editRequest.getUserId());

            if (userFromDb.isEmpty()) {
                return new UserServiceResponse("404", "User does not exist");
            }
            User user = userFromDb.get();

            if (!user.getPassword().equals(editRequest.getOldPassword())) {
                return new UserServiceResponse("401", "You provided wrong old password. Denied");
            }
            user.setPassword(editRequest.getNewPassword());

            if (!user.userValidation()) {
                return new UserServiceResponse("400", "New password is not valid. Password: min 6 symbols");
            }
            usersRepository.save(user);
            return new UserServiceResponse("200", "Password edited successfully");
        } catch (Exception e) {
            return new UserServiceResponse("500", "Error. " + e.getMessage());
        }
    }

    public UserServiceResponse deleteUser(Long userId) {
        try {
            if (usersRepository.findById(userId).isEmpty()) {
                return new UserServiceResponse("404", "User does not exist");
            }
            String url = "http://todos/api/todos/v1/clear/" + userId;
            // request to todos-microservice to delete all todos of this user
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

            if (response.getStatusCodeValue() < 400) {
                usersRepository.deleteById(userId);
                return new UserServiceResponse("204", "User deleted successfully");
            }
            return new UserServiceResponse(String.valueOf(response.getStatusCodeValue()), "Unable to delete user todos");
        } catch (Exception e) {
            return new UserServiceResponse("500", "Unable to delete user. " + e.getMessage());
        }
    }
}
