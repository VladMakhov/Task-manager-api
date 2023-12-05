package api.service;

import api.model.Task;
import api.model.dto.AddCommentRequest;
import api.model.dto.TaskRequest;
import api.model.dto.TaskResponse;

public interface ITaskService {
    TaskResponse createTask(TaskRequest request);
    Task getTaskById(int id);
    TaskResponse deleteTaskById(int id);
    TaskResponse updateStatus(int id, String status);
    TaskResponse addComment(int taskId, AddCommentRequest request);
    TaskResponse getTaskResponse(int id);
}
