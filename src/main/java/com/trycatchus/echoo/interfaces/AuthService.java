package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.payload.auth.LoginPayload;
import com.trycatchus.echoo.dto.responses.UserResponse;
import com.trycatchus.echoo.dto.responses.auth.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginPayload payload);
    UserResponse verifyToken(String token);
}