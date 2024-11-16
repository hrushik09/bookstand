package com.hrushi.bookstand.domain.works;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface WorkReviewLikeRepository extends JpaRepository<WorkReviewLikeEntity, Long> {
    @Query("SELECT wrl FROM WorkReviewLikeEntity wrl WHERE wrl.userEntity.id = :userId AND wrl.workReviewEntity.id = :workReviewId")
    Optional<WorkReviewLikeEntity> findByUserIdAndWorkReviewId(Long userId, Long workReviewId);

    @Query("SELECT wrl FROM WorkReviewLikeEntity wrl WHERE wrl.workReviewEntity.id = :workReviewId")
    List<WorkReviewLikeEntity> findByWorkReviewId(Long workReviewId);
}
