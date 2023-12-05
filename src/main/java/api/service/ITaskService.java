package api.service;

import api.model.Task;
import api.model.dto.TaskRequest;

public interface ITaskService {
    Task createTask(TaskRequest request);
    Task getTaskById(int id);
}
