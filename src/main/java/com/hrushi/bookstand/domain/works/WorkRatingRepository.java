package com.hrushi.bookstand.domain.works;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface WorkRatingRepository extends JpaRepository<WorkRatingEntity, Long> {
    @Query("SELECT wre FROM WorkRatingEntity wre WHERE wre.userEntity.id = :userId AND wre.workEntity.id = :workId")
    Optional<WorkRatingEntity> findByUserIdAndWorkId(Long userId, Long workId);
}
