package api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response of user without private info.
 * */
@Data
@AllArgsConstructor
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
}
