package com.hrushi.bookstand.domain.works;

import java.util.List;

public record AllShelves(
        List<ShelfItem> currentlyReading,
        List<ShelfItem> wantToRead
) {
}
