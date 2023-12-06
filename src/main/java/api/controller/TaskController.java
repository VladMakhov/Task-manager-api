package api.controller;

import api.model.dto.*;
import api.service.ITaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Class distributes all operation related to task management like create, get, update, delete and commenting
 * */
@RestController
@RequestMapping("/api/")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("task/all")
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(taskService.getAllTasks(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("task/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id), HttpStatus.OK);
    }

    @GetMapping("task/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsFromTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getComments(), HttpStatus.OK);
    }

    @GetMapping("task/{id}/executors")
    public ResponseEntity<List<UserResponse>> getListOfExecutors(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getExecutors(), HttpStatus.OK);
    }

    @GetMapping("task/{id}/author")
    public ResponseEntity<UserResponse> getAuthor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getAuthor(), HttpStatus.OK);
    }

    @GetMapping("user/{id}/backlog")
    public ResponseEntity<List<TaskResponse>> getTasksByAuthor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTasksByAuthor(id), HttpStatus.OK);
    }

    @GetMapping("user/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByExecutor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTasksByExecutor(id), HttpStatus.OK);
    }

    @PostMapping("task/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.OK);
    }

    @PutMapping("task/{id}/updateStatus")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable int id, @RequestBody UpdateStatusRequest request) {
        return new ResponseEntity<>(taskService.updateStatus(id, request.getStatus()), HttpStatus.OK);
    }

    @PutMapping("task/{id}/addComment")
    public ResponseEntity<TaskResponse> addComment(@PathVariable int id, @RequestBody AddCommentRequest request) {
        return new ResponseEntity<>(taskService.addComment(id, request), HttpStatus.OK);
    }

    @DeleteMapping("task/{id}/delete")
    public ResponseEntity<TaskResponse> removeTask(@PathVariable int id) {
        return new ResponseEntity<>(taskService.removeTask(id), HttpStatus.OK);
    }

}
