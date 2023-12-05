package api.controller;

import api.model.Task;
import api.model.User;
import api.model.dto.TaskRequest;
import api.service.ITaskService;
import api.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class MainController {

    private final IUserService userService;
    private final ITaskService taskService;

    public MainController(IUserService userService, ITaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @PostMapping("task/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.OK);
    }

    @PostMapping("user/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }
}
