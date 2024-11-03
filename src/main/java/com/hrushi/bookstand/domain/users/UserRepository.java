package com.hrushi.bookstand.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    @Query("SELECT ue FROM UserEntity ue JOIN FETCH ue.authorities WHERE ue.username = :username")
    Optional<UserEntity> findByUsername(String username);
}
