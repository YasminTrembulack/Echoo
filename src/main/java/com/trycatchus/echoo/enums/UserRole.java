package com.trycatchus.echoo.enums;

public enum UserRole {
    ADMIN,
    ORGANIZER,
    USER;

    public static UserRole fromString(String role) {
        return UserRole.valueOf(role.toUpperCase());
    }
}