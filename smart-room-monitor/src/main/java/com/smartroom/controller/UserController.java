package com.smartroom.controller;

import com.smartroom.dto.request.UserRequest;
import com.smartroom.dto.response.UserResponse;
import com.smartroom.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/smart-room-monitor/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers();
        return ResponseEntity.ok(userResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
