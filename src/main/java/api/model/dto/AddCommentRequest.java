package api.model.dto;

import lombok.Data;

@Data
public class AddCommentRequest {
    private int id;
    private String comment;
}
