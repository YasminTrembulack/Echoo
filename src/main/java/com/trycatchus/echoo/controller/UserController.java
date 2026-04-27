package com.trycatchus.echoo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dto.payload.UserPayload;
import com.trycatchus.echoo.interfaces.UserService;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserPayload payload) {
        return ResponseEntity.ok(userService.create(payload));
    }

}