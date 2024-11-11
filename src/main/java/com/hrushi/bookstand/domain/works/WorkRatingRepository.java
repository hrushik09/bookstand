package com.hrushi.bookstand.domain.works;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface WorkRatingRepository extends JpaRepository<WorkRatingEntity, Long> {
    Optional<WorkRatingEntity> findByUserIdAndWorkId(Long userId, Long workId);
}
