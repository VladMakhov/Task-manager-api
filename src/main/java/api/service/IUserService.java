package api.service;

import api.model.User;
import api.model.dto.UserDto;

public interface IUserService {
    /**
     * Getting user from db and returns full info about user
     * */
    User getUserById(int id);

    /**
     * Updates user info
     * */
    UserDto updateUser(UserDto user);

    /**
     * Method converts full-info user entity to user-friendly response
     * */
    UserDto getUserResponse(int id);

    /**
     * Converts user entity to dto
     * */
    UserDto entityToDto(User user);

}
