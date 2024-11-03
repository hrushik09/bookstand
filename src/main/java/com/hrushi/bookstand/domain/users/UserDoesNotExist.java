package com.hrushi.bookstand.domain.users;

class UserDoesNotExist extends RuntimeException {
    public UserDoesNotExist(String username) {
        super("User with username " + username + " does not exist");
    }
}
