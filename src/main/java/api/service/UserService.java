package api.service;

import api.exception.UserNotFoundException;
import api.model.User;
import api.model.dto.UserResponse;
import api.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("ERROR: User with id " + id + " not found"));
    }

    @Override
    public UserResponse getUserResponse(int id) {
        return EntityToDto(getUserById(id));
    }

    @Override
    public User deleteUserById(int id) {
        User user = getUserById(id);
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public static UserResponse EntityToDto(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPosition(user.getPosition());
        return response;
    }
}

