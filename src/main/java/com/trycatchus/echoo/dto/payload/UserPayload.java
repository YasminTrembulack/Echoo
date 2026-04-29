package com.trycatchus.echoo.dto.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public record UserPayload(

        @NotBlank(message = "Field 'firstName' is required")
        String firstName,

        @NotBlank(message = "Field 'lastName' is required")
        String lastName,

        @NotBlank(message = "Field 'username' is required")
        String username,

        @NotBlank(message = "Field 'cpf' is required")
        String cpf,

        String birthDate,

        @Email(message = "Invalid email")
        @NotBlank(message = "Field 'email' is required")
        String email,

        @NotBlank(message = "Field 'password' is required")
        @Size(min = 8, message = "Field 'password' must have at least 8 characters")
        String password,

        @NotBlank(message = "Field 'userRole' is required")
        String userRole

) {}