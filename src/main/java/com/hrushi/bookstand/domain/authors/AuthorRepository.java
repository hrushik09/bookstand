package com.hrushi.bookstand.domain.authors;

import org.springframework.data.jpa.repository.JpaRepository;

interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
