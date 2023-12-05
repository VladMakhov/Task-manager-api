package api.model.dto;

import lombok.Data;

@Data
public class CommentResponse {
    private int id;
    private String comment;
    private UserResponse author;
}
