package com.hrushi.bookstand.domain.works;

import jakarta.validation.constraints.NotBlank;

public record UpdateShelfRequest(
        @NotBlank
        String shelf
) {
}
