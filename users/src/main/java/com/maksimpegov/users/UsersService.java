package com.maksimpegov.users;

import com.maksimpegov.users.models.User;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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
            } else if (!user.passwordValidation() || !user.usernameValidation()) {
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

    public String deleteUser(String userId) {
        restTemplate.delete("http://todos/todos/clear/" + userId);
        String response = "User " + userId + " was deleted";
        return response;
    }
}
