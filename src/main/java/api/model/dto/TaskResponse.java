package api.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Response entity of task. Users are striped from their private info.
 * */
@Data
public class TaskResponse {
    private int id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private UserResponse author;
    private List<UserResponse> executors = new ArrayList<>();
    private List<CommentResponse> comments = new ArrayList<>();
}
