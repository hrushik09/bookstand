package com.hrushi.bookstand.domain.works;

import com.hrushi.bookstand.domain.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface WorkShelfRepository extends JpaRepository<WorkShelfEntity, Long> {
    @Query("SELECT ws FROM WorkShelfEntity ws WHERE ws.userEntity.id = :userId AND ws.workEntity.id = :workId")
    Optional<WorkShelfEntity> findByUserIdAndWorkId(Long userId, Long workId);

    @Query("""
            SELECT COUNT(ws) FROM WorkShelfEntity ws
                        WHERE ws.workEntity.id = :workId AND ws.shelf = :shelf
            """)
    Long findWorkShelfCount(Long workId, Shelf shelf);
}
