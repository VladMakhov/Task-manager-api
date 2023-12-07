package api.model.dto;

import lombok.Data;

/**
 * Response of user without private info.
 * */
@Data
public class UserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
}
