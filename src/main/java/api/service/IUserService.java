package api.service;

import api.model.User;
import api.model.dto.UserResponse;

public interface IUserService {
    User createUser(User user);
    User getUserById(int id);
    User deleteUserById(int id);
    User updateUser(User user);
    UserResponse getUserResponse(int id);
}
