package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.CreateUserCommand;
import com.hrushi.bookstand.domain.users.CreateUserRequest;
import com.hrushi.bookstand.domain.users.UserAlreadyExists;
import com.hrushi.bookstand.domain.users.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/signup")
class SignupController {
    private final UserService userService;

    SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String signup() {
        return "signup";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String create(@Valid CreateUserRequest request) {
        CreateUserCommand cmd = new CreateUserCommand(request.username(), request.password(), List.of("temp", "temp:read"));
        try {
            userService.createUser(cmd);
        } catch (UserAlreadyExists e) {
            return "redirect:/signup?error";
        }
        return "redirect:/login";
    }
}
