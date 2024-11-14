package com.hrushi.bookstand.domain.works;

import java.time.Instant;

record WorkReview(Long id, Long userId, String review, Instant createdAt, Instant updatedAt) {
}
