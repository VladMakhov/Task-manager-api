package api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response that user receives after adding comments to task
 * */
@Data
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String comment;
    private UserDto author;
}
