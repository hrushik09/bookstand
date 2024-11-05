package com.hrushi.bookstand.domain.profiles;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String country,
        String email,
        String bio
) {
}
