package api.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Response entity of task. Users are striped from their private info.
 * */
@Data
public class TaskDto {
    private int id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private UserDto author;
    private List<UserDto> executors = new ArrayList<>();
    private List<CommentDto> comments = new ArrayList<>();

    public TaskDto(int id, String title, String description, String status, String priority, UserDto author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = author;
    }
}
