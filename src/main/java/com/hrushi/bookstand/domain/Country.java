package com.hrushi.bookstand.domain;

public enum Country {
    INDIA("India"),
    UNITED_STATES("Unites States"),
    UNITED_KINGDOM("United Kingdom"),
    FRANCE("France"),
    GERMANY("Germany"),
    ITALY("Italy"),
    SOUTH_AFRICA("South Africa"),
    BRAZIL("Brazil");

    private final String displayName;

    Country(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
