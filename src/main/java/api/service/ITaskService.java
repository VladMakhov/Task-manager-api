package api.service;

import api.model.dto.AddCommentRequest;
import api.model.dto.TaskRequest;
import api.model.dto.TaskResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ITaskService {
    /**
     * Takes from db tasks sorted by page and converts each task entity to presentable task response
     * */
    List<TaskResponse> getAllTasks(PageRequest pageRequest);

    /**
     * Uses task-request to create task entity
     * */
    TaskResponse createTask(TaskRequest request);

    /**
     * If user in the list of executors - he can update status of task by sending new-status and task id
     * */
    TaskResponse updateStatus(int id, String status);

    /**
     * User can add comment to task using add-comment-request and task-id
     * */
    TaskResponse addComment(int taskId, AddCommentRequest request);

    /**
     * Converts task entity to task-response
     * */
    TaskResponse getTaskResponse(int id);

    /**
     * Method finds all tasks that authored by specific user
     * */
    List<TaskResponse> getTasksByAuthor(int id);

    /**
     * Method find all tasks that executed by specific user (ergo - user in list of executors in task)
     * */
    List<TaskResponse> getTasksByExecutor(int id);

    /**
     * Method delete task and additional info like comments
     * */
    TaskResponse removeTask(int id);
}
