package com.hrushi.bookstand.domain.works;

import java.util.List;

public record WorkReviewLikes(
        Long workReviewId,
        boolean hasCurrentUserLiked,
        List<Long> likedByUserIds
) {
}
