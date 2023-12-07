package api.service;

import api.exception.UserNotFoundException;
import api.model.User;
import api.model.dto.UserResponse;
import api.repo.UserRepository;
import org.springframework.stereotype.Service;

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
    public UserResponse updateUser(User user, int id) {
        User prev = getUserById(id);
        if (user.getFirstName() != null) prev.setFirstName(user.getFirstName());
        if (user.getLastName() != null) prev.setLastName(user.getLastName());
        if (user.getPosition() != null) prev.setPosition(user.getPosition());
        prev.setId(id);
        return EntityToDto(userRepository.save(prev));
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

