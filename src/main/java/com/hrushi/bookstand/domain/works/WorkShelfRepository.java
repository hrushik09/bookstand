package com.hrushi.bookstand.domain.works;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface WorkShelfRepository extends JpaRepository<WorkShelfEntity, Long> {
    @Query("SELECT ws FROM WorkShelfEntity ws WHERE ws.userEntity.id = :userId AND ws.workEntity.id = :workId")
    Optional<WorkShelfEntity> findByUserIdAndWorkId(Long userId, Long workId);
}
