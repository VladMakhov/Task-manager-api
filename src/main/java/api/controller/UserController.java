package api.controller;

import api.model.User;
import api.model.dto.UserResponse;
import api.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserResponse(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<User> deleteUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

}
