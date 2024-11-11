package com.hrushi.bookstand.domain.works;

public record UpdateRatingCommand(
        Long userId,
        Long workId,
        Integer rating
) {
}
