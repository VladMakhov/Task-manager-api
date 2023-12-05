package api.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskResponse {
    private int id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private UserResponse author;
    private List<UserResponse> listOfExecutors = new ArrayList<>();
    private List<CommentResponse> comments = new ArrayList<>();
}
