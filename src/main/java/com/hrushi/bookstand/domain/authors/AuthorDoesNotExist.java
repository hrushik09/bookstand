package com.hrushi.bookstand.domain.authors;

class AuthorDoesNotExist extends RuntimeException {
    public AuthorDoesNotExist(Long id) {
        super("Author with id " + id + " does not exist");
    }
}
