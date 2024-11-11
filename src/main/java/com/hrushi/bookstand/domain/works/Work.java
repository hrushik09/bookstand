package com.hrushi.bookstand.domain.works;

import java.util.List;

public record Work(
        Long id,
        String title,
        String subtitle,
        String coverUrl,
        Integer rating,
        List<WorkAuthor> authors
) {
}
