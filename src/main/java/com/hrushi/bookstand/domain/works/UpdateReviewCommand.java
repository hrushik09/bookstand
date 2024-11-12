package com.hrushi.bookstand.domain.works;

public record UpdateReviewCommand(
        Long userId,
        Long workId,
        String review
) {
}
