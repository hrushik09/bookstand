package com.hrushi.bookstand.domain.users;

class UserAlreadyExists extends RuntimeException {
    UserAlreadyExists(String username) {
        super("User with username " + username + " already exists");
    }
}
