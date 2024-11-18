package com.hrushi.bookstand.domain.works;

public record ShelfItem(
        Long workId,
        String coverUrl,
        String title
) {
}
