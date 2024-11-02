package com.hrushi.bookstand.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
}
