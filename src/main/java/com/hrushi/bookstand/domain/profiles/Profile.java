package com.hrushi.bookstand.domain.profiles;

public record Profile(String firstName, String lastName, String country, String email, String bio,
                      String classToAppendToIcon) {
    static Profile dummyProfile() {
        return new Profile("", "", "", "", "", "");
    }
}
