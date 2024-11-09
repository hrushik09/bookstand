package com.hrushi.bookstand.domain.works;

class WorkDoesNotExist extends RuntimeException {
    public WorkDoesNotExist(Long id) {
        super("Work with id " + id + " does not exist");
    }
}
