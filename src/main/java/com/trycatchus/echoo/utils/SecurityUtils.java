package com.trycatchus.echoo.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.trycatchus.echoo.dto.responses.auth.UserDecodedResponse;

public class SecurityUtils {
    public static UserDecodedResponse getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserDecodedResponse user)) {
            throw new SecurityException("User not authenticated");
        }

        return user;
    }
}