package api.service;

import api.model.User;
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
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("ERROR: User with id " + id + " not found"));
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
}
