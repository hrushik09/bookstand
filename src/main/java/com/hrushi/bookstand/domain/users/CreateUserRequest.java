package com.hrushi.bookstand.domain.users;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
