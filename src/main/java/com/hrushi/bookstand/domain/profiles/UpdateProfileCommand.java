package com.hrushi.bookstand.domain.profiles;

public record UpdateProfileCommand(
        Long userId,
        String firstName,
        String lastName,
        String country,
        String email,
        String bio
) {
}
