package com.hrushi.bookstand.domain.works;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface WorkReviewRepository extends JpaRepository<WorkReviewEntity, Long> {
    @Query("SELECT wre FROM WorkReviewEntity wre WHERE wre.userEntity.id = :userId AND wre.workEntity.id = :workId")
    Optional<WorkReviewEntity> findByUserIdAndWorkId(Long userId, Long workId);
}
