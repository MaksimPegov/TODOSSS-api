package com.maksimpegov.users;

import com.maksimpegov.users.models.User;
import com.maksimpegov.users.models.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String registerUser(UserRegistrationRequest request) {
        User user = new User(request.username(), request.password());
        return "User " + user.getUsername() + " registered with id " + user.getId();
    }

    public String deleteUser(String userId) {
        restTemplate.delete("http://todos/todos/clear/" + userId);
        String response = "User " + userId + " was deleted";
        return response;
    }
}
