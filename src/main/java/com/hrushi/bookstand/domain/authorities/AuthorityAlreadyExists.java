package com.hrushi.bookstand.domain.authorities;

public class AuthorityAlreadyExists extends RuntimeException {
    AuthorityAlreadyExists(String value) {
        super("Authority with value " + value + " already exists");
    }
}
