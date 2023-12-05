package api.service;

import api.model.User;
import api.model.dto.UserResponse;

public interface IUserService {
    User getUserById(int id);
    User updateUser(User user);
    UserResponse getUserResponse(int id);
}
