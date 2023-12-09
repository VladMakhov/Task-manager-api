package api.service;

import api.model.dto.AddCommentRequest;
import api.model.dto.TaskRequest;
import api.model.dto.TaskDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ITaskService {
    /**
     * Takes from db tasks sorted by page and converts each task entity to presentable task response
     * */
    List<TaskDto> getAllTasks(PageRequest pageRequest);

    /**
     * Uses task-request to create task entity
     * */
    TaskDto createTask(TaskRequest request);

    /**
     * If user in the list of executors - he can update status of task by sending new-status and task id
     * */
    TaskDto updateStatus(int id, String status);

    /**
     * User can add comment to task using add-comment-request and task-id
     * */
    TaskDto addComment(int taskId, AddCommentRequest request);

    /**
     * Converts task entity to task-response
     * */
    TaskDto getTaskResponse(int id);

    /**
     * Method finds all tasks that authored by specific user
     * */
    List<TaskDto> getTasksByAuthor(int id);

    /**
     * Method find all tasks that executed by specific user (ergo - user in list of executors in task)
     * */
    List<TaskDto> getTasksByExecutor(int id);

    /**
     * Method delete task and additional info like comments
     * */
    TaskDto removeTask(int id);
}
