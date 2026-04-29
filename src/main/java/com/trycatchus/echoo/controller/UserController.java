package com.trycatchus.echoo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dto.payload.user.UserPayload;
import com.trycatchus.echoo.dto.payload.user.UserUpdatePayload;
import com.trycatchus.echoo.dto.responses.DataResponse;
import com.trycatchus.echoo.dto.responses.UserResponse;
import com.trycatchus.echoo.interfaces.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public DataResponse<UserResponse> createUser(@RequestBody @Valid UserPayload payload) {
        UserResponse response = userService.create(payload);
        return new DataResponse<UserResponse>("User created successfully", response);
    }

    @PatchMapping("/{id}")
    public DataResponse<UserResponse> updateUser(
        @PathVariable String id,
        @RequestBody @Valid UserUpdatePayload payload
    ) {
        UserResponse response = userService.update(id, payload);
        return new DataResponse<UserResponse>("User updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public DataResponse<UserResponse> getUser(@PathVariable String id) {
        UserResponse response = userService.findById(id);
        return new DataResponse<UserResponse>("User retrieved successfully", response);
    }
    
    @GetMapping
    public DataResponse<java.util.List<UserResponse>> getAllUsers() {
        java.util.List<UserResponse> response = userService.findAll();
        return new DataResponse<java.util.List<UserResponse>>("Users retrieved successfully", response);
    }

}