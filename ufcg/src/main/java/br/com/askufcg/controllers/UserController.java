package br.com.askufcg.controllers;

import br.com.askufcg.dtos.user.UserRequest;
import br.com.askufcg.dtos.user.UserResponse;
import br.com.askufcg.dtos.user.UserMapper;
import br.com.askufcg.models.User;
import br.com.askufcg.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        var users = userService.getUsers().stream()
                                                        .map(u -> userMapper.fromUserToResponse(u))
                                                        .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return new ResponseEntity<>(userMapper.fromUserToResponse(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest user) {
        var userSaved = userService.saveUser(userMapper.toUser(user));
        var userResponse = userMapper.fromUserToResponse(userSaved);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        var user = userMapper.toUser(userRequest);
        var updatedUser = userService.updateUser(id, user);
        var userResponse = userMapper.fromUserToResponse(updatedUser);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
