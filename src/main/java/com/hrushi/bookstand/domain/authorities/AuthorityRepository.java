package com.hrushi.bookstand.domain.authorities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    boolean existsByValue(String value);

    Optional<AuthorityEntity> findByValue(String value);
}
