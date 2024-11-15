package com.hrushi.bookstand.domain.works;

class WorkReviewLikeDoesNotExist extends RuntimeException {
    public WorkReviewLikeDoesNotExist(Long userId, Long workReviewId) {
        super("Work Review Like with userId " + userId + " and workReviewId " + workReviewId + " does not exist");
    }
}
