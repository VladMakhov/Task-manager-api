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
    public List<TaskDto> getAllTasks(PageRequest pageRequest) {
        return taskRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(this::taskEntityToDto)
                .toList();
    }

    @Override
    public TaskDto createTask(TaskRequest request) {
        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus()
        );
        task.setAuthor(userService.getUserById(request.getAuthor()));
        if (request.getExecutors() != null) {
            task.setExecutor(userRepository.findAllByIds(request.getExecutors()));
        }
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto updateStatus(int id, String status) {
        Task task = getTaskById(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (task.getAuthor().getEmail().equals(username)) {
            task.setStatus(status);
        } else {
            for (User user : task.getExecutor()) {
                if (Objects.equals(user.getEmail(), username)) {
                    task.setStatus(status);
                    break;
                }
            }
        }
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto addComment(int taskId, AddCommentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(username).orElseThrow(
                () -> new UserNotFoundException("User with username " + username + " not found"));

        Comment comment = new Comment(
                request.getId(),
                request.getComment(),
                userService.getUserById(user.getId()));

        Task task = getTaskById(taskId);
        task.getComment().add(comment);
        commentRepository.save(comment);
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto getTaskResponse(int id) {
        return taskEntityToDto(getTaskById(id));
    }

    @Override
    public List<TaskDto> getTasksByAuthor(int id) {
        User author = userService.getUserById(id);
        if (author == null) throw new UserNotFoundException("User with id " + id + " not found");
        return taskRepository.findAllByAuthor(author)
                .stream()
                .map(this::taskEntityToDto)
                .toList();
    }

    @Override
    public List<TaskDto> getTasksByExecutor(int id) {
        User user = userService.getUserById(id);
        if (user == null) throw new UserNotFoundException("User with id " + id + " not found");
        return taskRepository.findAllByExecutorId(id).stream()
                .map(this::getTaskById)
                .filter(Objects::nonNull)
                .map(this::taskEntityToDto)
                .toList();
    }

    @Override
    public TaskDto removeTask(int id) {
        TaskDto task = getTaskResponse(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (task.getAuthor().getEmail().equals(username)) {
            taskRepository.deleteById(id);
        } else {
            for (UserDto user : task.getExecutors()) {
                if (Objects.equals(user.getEmail(), username)) {
                    taskRepository.deleteById(id);
                    break;
                }
            }
        }
        return task;
    }

    public TaskDto taskEntityToDto(Task task) {
        TaskDto response = new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                userService.entityToDto(task.getAuthor())
        );
        response.setExecutors(task.getExecutor().stream().map(userService::entityToDto).toList());
        response.setComments(task.getComment().stream().map(this::commentEntityToDto).toList());
        return response;
    }

    public CommentDto commentEntityToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getComment(),
                userService.entityToDto(comment.getAuthor()));
    }

    private Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("ERROR: Task with id " + id + " not found"));
    }
}
