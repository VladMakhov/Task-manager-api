package api.service;

import api.model.Task;
import api.model.dto.AddCommentRequest;
import api.model.dto.TaskRequest;

public interface ITaskService {
    Task createTask(TaskRequest request);
    Task getTaskById(int id);
    Task deleteTaskById(int id);
    Task updateStatus(int id, String status);
    Task addComment(int taskId, AddCommentRequest request);
}
