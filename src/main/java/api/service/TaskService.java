package api.service;

import api.exception.TaskNotFoundException;
import api.exception.UserNotFoundException;
import api.model.Comment;
import api.model.Task;
import api.model.User;
import api.model.dto.*;
import api.repo.CommentRepository;
import api.repo.TaskRepository;
import api.repo.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final IUserService userService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, IUserService userService, CommentRepository commentRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskResponse> getAllTasks(PageRequest pageRequest) {
        return taskRepository.findAll(pageRequest).getContent().stream().map(this::taskEntityToDto).toList();
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
        if (request.getExecutors() != null) {
            List<User> existingExecutors = new ArrayList<>();
            for (int id : request.getExecutors()) {
                User user = userService.getUserById(id);
                if (user != null) {
                    existingExecutors.add(user);
                }
            }
            createdTask.setExecutor(existingExecutors);
        }
        return taskEntityToDto(taskRepository.save(createdTask));
    }

    private Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("ERROR: Task with id " + id + " not found"));
    }

    @Override
    public TaskResponse updateStatus(int id, String status) {
        Task task = getTaskById(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> executors = task.getExecutor();
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User with username " + username + " not found"));
        User author = userService.getUserById(user.getId());
        Task task = getTaskById(taskId);
        Comment comment = new Comment();
        comment.setId(request.getId());
        comment.setAuthor(author);
        comment.setComment(request.getComment());
        task.getComment().add(comment);
        commentRepository.save(comment);
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskResponse getTaskResponse(int id) {
        Task task = getTaskById(id);
        return taskEntityToDto(task);
    }

    @Override
    public List<TaskResponse> getTasksByAuthor(int id) {
        User author = userService.getUserById(id);
        if (author == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return taskRepository.findAllByAuthor(author).stream().map(this::taskEntityToDto).toList();
    }

    @Override
    public List<TaskResponse> getTasksByExecutor(int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return taskRepository.findAllByExecutorId(id).stream()
                .map(this::getTaskById)
                .filter(Objects::nonNull)
                .map(this::taskEntityToDto)
                .toList();
    }

    @Override
    public TaskResponse removeTask(int id) {
        TaskResponse task = getTaskResponse(id);
        taskRepository.deleteById(id);
        return task;
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
        for (User user : task.getExecutor()) {
            userResponseList.add(UserService.EntityToDto(user));
        }
        response.setExecutors(userResponseList);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment : task.getComment()) {
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
