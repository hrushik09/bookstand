package com.hrushi.bookstand.domain;

public enum Shelf {
    WANT_TO_READ("Want to Read"),
    CURRENTLY_READING("Current Reading"),
    READ("Read"),
    DID_NOT_FINISH("Did Not Finish"),
    ;

    private final String displayName;

    Shelf(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
