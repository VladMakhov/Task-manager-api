package api.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskRequest {
    private int id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private int author;
    private List<Integer> listOfExecutors;
}
