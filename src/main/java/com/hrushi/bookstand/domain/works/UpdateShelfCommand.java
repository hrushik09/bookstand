package com.hrushi.bookstand.domain.works;

public record UpdateShelfCommand(
        Long userId,
        Long workId,
        String shelf
) {
}
