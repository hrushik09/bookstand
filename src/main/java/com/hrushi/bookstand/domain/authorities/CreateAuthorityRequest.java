package com.hrushi.bookstand.domain.authorities;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorityRequest(@NotBlank String value) {
}
