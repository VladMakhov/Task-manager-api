package api.service;

import api.exception.NoAuthorityException;
import api.exception.UserNotFoundException;
import api.model.User;
import api.model.dto.UserDto;
import api.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserResponse(int id) {
        return entityToDto(getUserById(id));
    }

    @Override
    public UserDto updateUser(UserDto user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals(user.getEmail())) {
            User existingUser = getUserById(user.getId());
            if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) existingUser.setLastName(user.getLastName());
            if (user.getPosition() != null) existingUser.setPosition(user.getPosition());
            return entityToDto(userRepository.save(existingUser));
        }
        throw new NoAuthorityException("ERROR: User has not authority to update this user");
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("ERROR: User with id " + id + " not found"));
    }

    @Override
    public UserDto entityToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPosition()
        );
    }

}

