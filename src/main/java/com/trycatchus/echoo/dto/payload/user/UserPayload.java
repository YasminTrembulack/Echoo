package com.trycatchus.echoo.dto.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import com.trycatchus.echoo.enums.UserRole;


public record UserPayload(
        @NotBlank(message = "Field 'firstName' is required")
        String firstName,

        @NotBlank(message = "Field 'lastName' is required")
        String lastName,

        @NotBlank(message = "Field 'username' is required")
        String username,

        @NotBlank(message = "Field 'cpf' is required")
        String cpf,

        LocalDate birthDate,

        @Email(message = "Invalid email")
        @NotBlank(message = "Field 'email' is required")
        String email,

        @NotBlank(message = "Field 'password' is required")
        @Size(min = 8, message = "Field 'password' must have at least 8 characters")
        String password,

        @NotNull(message = "Field 'userRole' is required")
        UserRole userRole

) {}