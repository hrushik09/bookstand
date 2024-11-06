package com.hrushi.bookstand.domain.users;

import java.util.List;

public record CreateUserCommand(String username, String password, List<String> authorities) {
}
