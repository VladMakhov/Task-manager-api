package api.model.dto;

import lombok.Data;

/**
 * Request for adding comments
 * */
@Data
public class AddCommentRequest {
    private int id;
    private String comment;
}
