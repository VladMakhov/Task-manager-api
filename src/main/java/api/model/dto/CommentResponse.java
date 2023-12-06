package api.model.dto;

import lombok.Data;

/**
 * Response that user receives after adding comments to task
 * */
@Data
public class CommentResponse {
    private int id;
    private String comment;
    private UserResponse author;
}
