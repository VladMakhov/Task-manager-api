package api.service;

import api.model.Task;
import api.model.dto.AddCommentRequest;
import api.model.dto.TaskRequest;
import api.model.dto.TaskResponse;

import java.util.List;

public interface ITaskService {
    TaskResponse createTask(TaskRequest request);
    Task getTaskById(int id);
    TaskResponse updateStatus(int id, String status);
    TaskResponse addComment(int taskId, AddCommentRequest request);
    TaskResponse getTaskResponse(int id);
    List<TaskResponse> getTasksByAuthor(int id);
    List<TaskResponse> getTasksByExecutor(int id);
}
