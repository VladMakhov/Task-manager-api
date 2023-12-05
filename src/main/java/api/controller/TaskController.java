package api.controller;

import api.model.Comment;
import api.model.Task;
import api.model.User;
import api.model.dto.AddCommentRequest;
import api.model.dto.TaskRequest;
import api.model.dto.UpdateStatusRequest;
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
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsFromTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskById(id).getComments(), HttpStatus.OK);
    }

    @GetMapping("{id}/executors")
    public ResponseEntity<List<User>> getListOfExecutors(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskById(id).getListOfExecutors(), HttpStatus.OK);
    }

    @GetMapping("{id}/author")
    public ResponseEntity<User> getAuthor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskById(id).getAuthor(), HttpStatus.OK);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Task> deleteTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.deleteTaskById(id), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.OK);
    }

    @PutMapping("{id}/updateStatus")
    public ResponseEntity<Task> updateStatus(@PathVariable int id, @RequestBody UpdateStatusRequest request) {
        return new ResponseEntity<>(taskService.updateStatus(id, request.getStatus()), HttpStatus.OK);
    }

    @PutMapping("{id}/addComment")
    public ResponseEntity<Task> addComment(@PathVariable int id, @RequestBody AddCommentRequest request) {
        return new ResponseEntity<>(taskService.addComment(id, request), HttpStatus.OK);
    }


}
