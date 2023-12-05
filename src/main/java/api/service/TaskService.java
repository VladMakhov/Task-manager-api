package api.service;

import api.model.Comment;
import api.model.Task;
import api.model.User;
import api.model.dto.AddCommentRequest;
import api.model.dto.TaskRequest;
import api.repo.CommentRepository;
import api.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final IUserService userService;
    private final CommentRepository commentRepository;

    public TaskService(TaskRepository taskRepository, IUserService userService, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    @Override
    public Task createTask(TaskRequest request) {
        Task createdTask = new Task();
        createdTask.setId(request.getId());
        createdTask.setTitle(request.getTitle());
        createdTask.setDescription(request.getDescription());
        createdTask.setPriority(request.getPriority());
        createdTask.setStatus(request.getStatus());
        createdTask.setAuthor(userService.getUserById(request.getAuthor()));
        if (request.getListOfExecutors() != null) {
            List<User> existingExecutors = new ArrayList<>();
            for (int id : request.getListOfExecutors()) {
                User user = userService.getUserById(id);
                if (user != null) {
                    existingExecutors.add(user);
                }
            }
            createdTask.setListOfExecutors(existingExecutors);
        }
        return taskRepository.save(createdTask);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("ERROR: Task with id " + id + " not found"));
    }

    @Override
    public Task deleteTaskById(int id) {
        Task task = getTaskById(id);
        taskRepository.deleteById(id);
        return task;
    }

    @Override
    public Task updateStatus(int id, String status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public Task addComment(int taskId, AddCommentRequest request) {
        User author = userService.getUserById(request.getAuthorId());

        Task task = getTaskById(taskId);
        Comment comment = new Comment();

        comment.setId(request.getId());
        comment.setAuthor(author);
        comment.setComment(request.getComment());
        task.getComments().add(comment);
        commentRepository.save(comment);
        return taskRepository.save(task);
    }
}
