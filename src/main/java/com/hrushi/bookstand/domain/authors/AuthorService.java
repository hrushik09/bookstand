package com.hrushi.bookstand.domain.authors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository authorRepository;

    AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void createAuthor(CreateAuthorCommand cmd) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setOpenLibraryKey(cmd.openLibraryKey());
        authorEntity.setName(cmd.name());
        authorRepository.save(authorEntity);
    }
}
