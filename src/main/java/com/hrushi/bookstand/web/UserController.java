package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.CreateUserCommand;
import com.hrushi.bookstand.domain.users.CreateUserRequest;
import com.hrushi.bookstand.domain.users.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    String users(Model model) {
        List<String> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String create(@Valid CreateUserRequest request) {
        CreateUserCommand cmd = new CreateUserCommand(request.username(), request.password(), List.of("temp", "temp:read"));
        userService.createUser(cmd);
        return "redirect:/users";
    }
}
