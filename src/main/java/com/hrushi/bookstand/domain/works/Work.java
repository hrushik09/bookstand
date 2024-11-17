package com.hrushi.bookstand.domain.works;

import java.util.List;

public record Work(
        Long id,
        String title,
        String subtitle,
        String coverUrl,
        Integer rating,
        String currentUserReview,
        List<WorkAuthor> authors,
        List<WorkReview> allOtherReviews,
        String shelf,
        Long currentlyReading,
        Long wantToRead
) {
}
