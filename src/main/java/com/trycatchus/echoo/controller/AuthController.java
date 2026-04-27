package com.trycatchus.echoo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dto.payload.login.LoginPayload;
import com.trycatchus.echoo.dto.responses.DataResponse;
import com.trycatchus.echoo.dto.responses.login.AuthResponse;
import com.trycatchus.echoo.interfaces.AuthService;

@RestController()
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public DataResponse<AuthResponse> login(LoginPayload payload) {
        AuthResponse response = authService.loginAsync(payload);
        return new DataResponse<AuthResponse>("Successful login.", response);
    }

    @GetMapping("/decode")
    public DataResponse<String> decodeToken(String token) {
        var decodedToken = authService.decodeTokenAsync(token);
        return new DataResponse<String>("Successful token decoding.", decodedToken.getSubject());
    }
}
