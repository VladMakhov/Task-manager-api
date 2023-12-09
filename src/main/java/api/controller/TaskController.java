package api.controller;

import api.model.dto.*;
import api.service.ITaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("Controller for managing task")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("task/all")
    @ApiOperation("Get all tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(taskService.getAllTasks(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("task/{id}")
    @ApiOperation("Get specific task by its ID")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id), HttpStatus.OK);
    }

    @GetMapping("task/{id}/comments")
    @ApiOperation("Get list of comments from specific task")
    public ResponseEntity<List<CommentDto>> getCommentsFromTaskById(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getComments(), HttpStatus.OK);
    }

    @GetMapping("task/{id}/executors")
    @ApiOperation("Get list of executors of task")
    public ResponseEntity<List<UserDto>> getListOfExecutors(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getExecutors(), HttpStatus.OK);
    }

    @GetMapping("task/{id}/author")
    @ApiOperation("Get author of task")
    public ResponseEntity<UserDto> getAuthor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTaskResponse(id).getAuthor(), HttpStatus.OK);
    }

    @GetMapping("user/{id}/backlog")
    @ApiOperation("Get list of tasks which user is authored")
    public ResponseEntity<List<TaskDto>> getTasksByAuthor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTasksByAuthor(id), HttpStatus.OK);
    }

    @GetMapping("user/{id}/tasks")
    @ApiOperation("Get list of tasks which user is assigned to")
    public ResponseEntity<List<TaskDto>> getTasksByExecutor(@PathVariable int id) {
        return new ResponseEntity<>(taskService.getTasksByExecutor(id), HttpStatus.OK);
    }

    @PostMapping("task/create")
    @ApiOperation("Create new task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.OK);
    }

    @PutMapping("task/{id}/start")
    @ApiOperation("Update task")
    public ResponseEntity<TaskDto> updateStatusToInProgress(@PathVariable int id) {
        return new ResponseEntity<>(taskService.start(id), HttpStatus.OK);
    }

    @PutMapping("task/{id}/done")
    @ApiOperation("Update task")
    public ResponseEntity<TaskDto> updateStatusToDone(@PathVariable int id) {
        return new ResponseEntity<>(taskService.done(id), HttpStatus.OK);
    }

    @PutMapping("task/{id}/addComment")
    @ApiOperation("Add comment to task")
    public ResponseEntity<TaskDto> addComment(@PathVariable int id, @RequestBody AddCommentRequest request) {
        return new ResponseEntity<>(taskService.addComment(id, request), HttpStatus.OK);
    }

    @DeleteMapping("task/{id}/delete")
    @ApiOperation("Delete task")
    public ResponseEntity<TaskDto> removeTask(@PathVariable int id) {
        return new ResponseEntity<>(taskService.removeTask(id), HttpStatus.OK);
    }

}
