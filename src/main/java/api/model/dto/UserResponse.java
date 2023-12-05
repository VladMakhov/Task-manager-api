package api.model.dto;

import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
}
