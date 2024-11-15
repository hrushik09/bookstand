package com.hrushi.bookstand.domain.works;

class WorkReviewLikeAlreadyExists extends RuntimeException {
    public WorkReviewLikeAlreadyExists(Long id) {
        super("Work Review Like for id " + id + " already exists");
    }
}
