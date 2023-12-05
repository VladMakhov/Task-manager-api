package api.service;

import api.model.Comment;
import api.model.Task;
import api.model.User;
import api.model.dto.*;
import api.repo.CommentRepository;
import api.repo.TaskRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public TaskResponse createTask(TaskRequest request) {
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
        return taskEntityToDto(taskRepository.save(createdTask));
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("ERROR: Task with id " + id + " not found"));
    }

    @Override
    public TaskResponse deleteTaskById(int id) {
        Task task = getTaskById(id);
        taskRepository.deleteById(id);
        return taskEntityToDto(task);
    }

    @Override
    public TaskResponse updateStatus(int id, String status) {
        Task task = getTaskById(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> executors = task.getListOfExecutors();
        boolean exist = false;
        for (User user : executors) {
            if (user.getEmail().equals(username)) {
                exist = true;
                break;
            }
        }
        if (exist) {
            task.setStatus(status);
        }
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskResponse addComment(int taskId, AddCommentRequest request) {
        User author = userService.getUserById(request.getAuthorId());

        Task task = getTaskById(taskId);
        Comment comment = new Comment();

        comment.setId(request.getId());
        comment.setAuthor(author);
        comment.setComment(request.getComment());
        task.getComments().add(comment);
        commentRepository.save(comment);
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskResponse getTaskResponse(int id) {
        Task task = getTaskById(id);
        return taskEntityToDto(task);
    }

    public TaskResponse taskEntityToDto(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setAuthor(UserService.EntityToDto(task.getAuthor()));
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : task.getListOfExecutors()) {
            userResponseList.add(UserService.EntityToDto(user));
        }
        response.setListOfExecutors(userResponseList);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : task.getComments()) {
            commentResponseList.add(commentEntityToDto(comment));
        }
        response.setComments(commentResponseList);
        return response;
    }

    public CommentResponse commentEntityToDto(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setComment(comment.getComment());
        response.setAuthor(UserService.EntityToDto(comment.getAuthor()));
        return response;
    }
}
