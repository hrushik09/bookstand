package com.hrushi.bookstand.domain.works;

import jakarta.validation.constraints.NotNull;

public record UpdateRatingRequest(
        @NotNull
        Integer rating
) {
}
