package com.hrushi.bookstand.domain.authorities;

class AuthorityDoesNotExist extends RuntimeException {
    AuthorityDoesNotExist(String value) {
        super("Authority with value " + value + " does not exist");
    }
}
