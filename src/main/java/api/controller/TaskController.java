package api.controller;

import api.model.dto.*;
import api.service.ITaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task/")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id), HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsFromTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getComments(), HttpStatus.OK);
    }

    @GetMapping("{id}/executors")
    public ResponseEntity<List<UserResponse>> getListOfExecutors(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getListOfExecutors(), HttpStatus.OK);
    }

    @GetMapping("{id}/author")
    public ResponseEntity<UserResponse> getAuthor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getAuthor(), HttpStatus.OK);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<TaskResponse> deleteTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.deleteTaskById(id), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.OK);
    }

    @PutMapping("{id}/updateStatus")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable int id, @RequestBody UpdateStatusRequest request) {
        return new ResponseEntity<>(taskService.updateStatus(id, request.getStatus()), HttpStatus.OK);
    }

    @PutMapping("{id}/addComment")
    public ResponseEntity<TaskResponse> addComment(@PathVariable int id, @RequestBody AddCommentRequest request) {
        return new ResponseEntity<>(taskService.addComment(id, request), HttpStatus.OK);
    }

}
