package com.hrushi.bookstand.domain;

import java.util.Random;

public enum Icon {
    PERSON("bi-person"),
    PERSON_CIRCLE("bi-person-circle"),
    PERSON_FILL("bi-person-fill"),
    PERSON_SQUARE("bi-person-square"),
    PERSON_WORKSPACE("bi-person-workspace");

    private static final Icon[] icons = values();
    private static final Random RANDOM = new Random();
    private final String classToAppend;

    Icon(String classToAppend) {
        this.classToAppend = classToAppend;
    }

    public static Icon random() {
        return icons[RANDOM.nextInt(icons.length)];
    }

    public String classToAppend() {
        return classToAppend;
    }
}
