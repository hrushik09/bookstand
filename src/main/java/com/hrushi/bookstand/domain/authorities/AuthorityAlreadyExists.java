package com.hrushi.bookstand.domain.authorities;

class AuthorityAlreadyExists extends RuntimeException {
    AuthorityAlreadyExists(String value) {
        super("Authority with value " + value + " already exists");
    }
}
