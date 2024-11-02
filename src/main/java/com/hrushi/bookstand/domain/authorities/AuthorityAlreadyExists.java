package com.hrushi.bookstand.domain.authorities;

class AuthorityAlreadyExists extends RuntimeException {
    public AuthorityAlreadyExists(String value) {
        super("Authority with value " + value + " already exists");
    }
}
