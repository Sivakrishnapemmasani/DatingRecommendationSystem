package com.estuate.datingapp.datingrecommendationengine.controller;

import com.estuate.datingapp.datingrecommendationengine.dto.UserRequest;
import com.estuate.datingapp.datingrecommendationengine.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.estuate.datingapp.datingrecommendationengine.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest user) {
        UserResponse savedUser = userService.registerUser(user);

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserResponse> getUserByName(@PathVariable String name) {

        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id); // Cache will be evicted automatically
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int id, @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.updateUser(id, userRequest); // Cache will be evicted automatically
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<UserResponse> updateUserByName(@PathVariable String name, @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.updateUserByName(name, userRequest); // Update user by name
        return ResponseEntity.ok(updatedUser);
    }

}

