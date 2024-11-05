package com.hrushi.bookstand.domain.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    @Query("SELECT pe FROM ProfileEntity pe WHERE pe.userEntity.id = :userId")
    Optional<ProfileEntity> findByUserId(Long userId);
}
