package com.hrushi.bookstand.domain.authors;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByOpenLibraryKey(String openLibraryKey);
}
