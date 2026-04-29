package com.trycatchus.echoo.interfaces;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.trycatchus.echoo.dto.payload.auth.LoginPayload;
import com.trycatchus.echoo.dto.responses.auth.AuthResponse;
import com.trycatchus.echoo.dto.responses.auth.UserDecodedResponse;

public interface AuthService {
    AuthResponse login(LoginPayload payload);
    DecodedJWT decodeToken(String auth);
    UserDecodedResponse verifyToken(String token);
}