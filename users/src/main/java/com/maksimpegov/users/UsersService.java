package com.maksimpegov.users;

import com.maksimpegov.users.exeption.ApiRequestException;
import com.maksimpegov.users.models.PasswordEditRequest;
import com.maksimpegov.users.models.UserServiceResponse;
import com.maksimpegov.users.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class UsersService {

	@Value("${spring.constraints.todos.url}")
	String todoMicroserviceUrl;

	@Value("${spring.constraints.security.url}")
	String securityMicroserviceUrl;

	private final RestTemplate restTemplate;
	private final UsersRepository usersRepository;

	private final UserMapper mapper;

	@Autowired
	public UsersService(RestTemplate restTemplate, UsersRepository usersRepository, UserMapper mapper) {
		this.restTemplate = restTemplate;
		this.usersRepository = usersRepository;
		this.mapper = mapper;
	}

	public void registerUser(UserDto userDto) {
		try {
			User user = mapper.userDtoToUser(userDto);

			if (user.getUsername() == null || user.getPassword() == null) {
				throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
			} else if (!user.userValidation()) {
				throw new ApiRequestException("Invalid request", "Username or password is not valid. Username: min 3 symbols, password: min 6 symbols)", 400);

			} else if (usersRepository.findByUsername(user.getUsername()) != null) {
				throw new ApiRequestException("Conflict", "User with this username already exists", 409);
			}

			user.encryptPassword();
			user.setCreated_at(new Date());
			usersRepository.save(user);
		} catch (ApiRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ApiRequestException("Internal server error", e.getMessage(), 500);
		}
	}

	public String loginUser(UserDto userDto) {
		try {
			User user = mapper.userDtoToUser(userDto);

			if (user.getUsername() == null || user.getPassword() == null) {
				throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
			}

			if (!isUserExists(user.getUsername())) {
				throw new ApiRequestException("Not found", "User with this username does not exist", 404);
			}

			if (!passwordVerification(user.getUsername(), user.getPassword())) {
				throw new ApiRequestException("Unauthorized", "Wrong password", 401);
			}

			User userFromDb = usersRepository.findByUsername(user.getUsername());

			String url = securityMicroserviceUrl + "/" + userFromDb.getId();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
			String token = response.getBody();

			userFromDb.hidePassword();
			return token;
		} catch (ApiRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ApiRequestException("Internal server error", e.getMessage(), 500);
		}
	}

	public UserInfo getUserInfo(String username) {
		try {
			if (username == null) {
				throw new ApiRequestException("Invalid request", "Username is empty", 400);
			}

			if (!isUserExists(username)) {
				throw new ApiRequestException("Not found", "User with this username does not exist", 404);
			}

			User userFromDb = usersRepository.findByUsername(username);

			userFromDb.hidePassword();
			return mapper.userToUserInfo(userFromDb);
		} catch (ApiRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ApiRequestException("Internal server error", e.getMessage(), 500);
		}
	}

	public void editPassword(PasswordEditRequest editRequest) {
		try {
			if (editRequest.getUsername() == null || editRequest.getOldPassword() == null || editRequest.getNewPassword() == null) {
				throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
			}

			if (!isUserExists(editRequest.getUsername())) {
				throw new ApiRequestException("Not found", "User with this username does not exist", 404);
			}

			if (!passwordVerification(editRequest.getUsername(), editRequest.getOldPassword())) {
				throw new ApiRequestException("Unauthorized", "You provided wrong old password", 401);
			}

			User userFromDb = usersRepository.findByUsername(editRequest.getUsername());
			userFromDb.setPassword(editRequest.getNewPassword());
			userFromDb.encryptPassword();

			if (!userFromDb.userValidation()) {
				throw new ApiRequestException("Invalid request", "New password is not valid. Password: min 6 symbols", 400);
			}
			usersRepository.save(userFromDb); // everything is ok, save new password
		} catch (ApiRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ApiRequestException("Internal server error", e.getMessage(), 500);
		}
	}

	public void deleteUser(UserDto deleteRequest) {
		try {
			if (deleteRequest.getUsername() == null || deleteRequest.getPassword() == null) {
				throw new ApiRequestException("Invalid request", "Username or password is empty", 400);
			}

			if (!isUserExists(deleteRequest.getUsername())) {
				throw new ApiRequestException("Not found", "User with this username does not exist", 404);
			}

			if (!passwordVerification(deleteRequest.getUsername(), deleteRequest.getPassword())) {
				throw new ApiRequestException("Unauthorized", "Wrong password", 401);
			}

			User user = usersRepository.findByUsername(deleteRequest.getUsername());

			String url = todoMicroserviceUrl + "/clear/" + user.getId();

			// request to todos-microservice to delete all todos of this user
			ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

			if (response.getStatusCodeValue() < 400) {
				usersRepository.delete(user);
				new UserServiceResponse(204, "User deleted successfully");
				return;
			}
			new UserServiceResponse(response.getStatusCodeValue(), "Unable to delete user todos");
		} catch (ApiRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ApiRequestException("Internal server error", e.getMessage(), 500);
		}
	}

	private boolean isUserExists(String username) {
		return usersRepository.findByUsername(username) != null;
	}

	private boolean passwordVerification(String username, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String truePassword = usersRepository.findByUsername(username).getPassword();
		return passwordEncoder.matches(password, truePassword);
	}
}
