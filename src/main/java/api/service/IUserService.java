package api.service;

import api.model.User;
import api.model.dto.UserResponse;

public interface IUserService {
    /**
     * Getting user from db and returns full info about user
     * */
    User getUserById(int id);

    /**
     * Updates user info
     * */
    UserResponse updateUser(User user, int id);

    /**
     * Method converts full-info user entity to user-friendly response
     * */
    UserResponse getUserResponse(int id);

}
