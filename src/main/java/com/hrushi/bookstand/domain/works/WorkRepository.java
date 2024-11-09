package com.hrushi.bookstand.domain.works;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface WorkRepository extends JpaRepository<WorkEntity, Long> {
    @Query("SELECT we FROM WorkEntity we JOIN FETCH we.authors WHERE we.id = :id")
    Optional<WorkEntity> findWork(Long id);
}
