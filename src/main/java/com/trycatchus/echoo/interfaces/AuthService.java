package com.trycatchus.echoo.interfaces;

import com.auth0.jwt.interfaces.DecodedJWT;

import com.trycatchus.echoo.dto.HttpEntity;
import com.trycatchus.echoo.dto.payload.login.LoginPayload;
import com.trycatchus.echoo.dto.responses.login.AuthResponse;

public interface AuthService {
    HttpEntity<AuthResponse> loginAsync(LoginPayload payload);
    DecodedJWT decodeTokenAsync(String token);
}