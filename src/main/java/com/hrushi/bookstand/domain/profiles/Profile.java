package com.hrushi.bookstand.domain.profiles;

public record Profile(String firstName, String lastName, String country, String email, String bio) {
    static Profile dummyProfile() {
        return new Profile("", "", "", "", "");
    }
}
