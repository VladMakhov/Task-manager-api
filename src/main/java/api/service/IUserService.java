package api.service;

import api.model.User;

public interface IUserService {
    User createUser(User user);
    User getUserById(int id);
    User deleteUserById(int id);
    User updateUser(User user);
}
