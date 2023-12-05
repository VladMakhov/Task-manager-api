package api.service;

import api.model.Task;
import api.model.User;
import api.model.dto.TaskRequest;
import api.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final IUserService userService;

    public TaskService(TaskRepository taskRepository, IUserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
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
}
