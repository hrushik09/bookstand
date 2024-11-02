package com.hrushi.bookstand.domain.authorities;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public void createAuthority(CreateAuthorityCommand cmd) {
        if (authorityRepository.existsByValue(cmd.value())) {
            throw new AuthorityAlreadyExists(cmd.value());
        }
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setValue(cmd.value());
        authorityRepository.save(authorityEntity);
    }

    public List<String> findAll() {
        return authorityRepository.findAll()
                .stream().map(AuthorityEntity::getValue)
                .toList();
    }
}
