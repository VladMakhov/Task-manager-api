package api.controller;

import api.model.dto.UserDto;
import api.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class distributes some operation related to user management
 */
@RestController
@RequestMapping("/api/user/")
@Api("Controller for managing user info")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @ApiOperation("Get specific user by its ID")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserResponse(id), HttpStatus.OK);
    }

    @PutMapping("{id}/update")
    @ApiOperation("Create new user")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto user) {
        user.setId(id);
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

}
