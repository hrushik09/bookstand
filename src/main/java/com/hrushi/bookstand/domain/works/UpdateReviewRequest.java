package com.hrushi.bookstand.domain.works;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateReviewRequest(
        @NotBlank
        @Size(min = 1, max = 2000)
        String review
) {
}
