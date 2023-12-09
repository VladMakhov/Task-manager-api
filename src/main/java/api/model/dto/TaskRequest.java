package api.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Request for creating task.
 * Difference between Task entity is that users represented as Integers by their ID
 * */
@Data
public class TaskRequest {
    private int id;
    private String title;
    private String description;
    private String priority;
    private int author;
    private List<Integer> executors;
}
