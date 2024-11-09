package com.hrushi.bookstand.domain.authors;

public record CreateAuthorCommand(
        String openLibraryKey,
        String name
) {
}
