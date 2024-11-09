package com.hrushi.bookstand.domain.works;

import java.util.List;

public record CreateWorkCommand(
        String openLibraryKey,
        String title,
        String subtitle,
        String coverId,
        List<String> authorOpenLibraryKeys
) {
}
