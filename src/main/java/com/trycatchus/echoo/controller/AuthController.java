package com.trycatchus.echoo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dto.payload.auth.DecodePayload;
import com.trycatchus.echoo.dto.payload.auth.LoginPayload;
import com.trycatchus.echoo.dto.responses.DataResponse;
import com.trycatchus.echoo.dto.responses.UserResponse;
import com.trycatchus.echoo.dto.responses.auth.AuthResponse;
import com.trycatchus.echoo.interfaces.AuthService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@RestController()
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public DataResponse<AuthResponse> login(@RequestBody @Valid LoginPayload payload) {
        AuthResponse response = authService.login(payload);
        return new DataResponse<AuthResponse>("Successful login.", response);
    }

    @PostMapping("/decode")
    public DataResponse<UserResponse> verifyToken(@RequestBody @Valid DecodePayload payload) {
        UserResponse response = authService.verifyToken(payload.token());
        return new DataResponse<UserResponse>("Successful token decoding.", response);
    }
}
