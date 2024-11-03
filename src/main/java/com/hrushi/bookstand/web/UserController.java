package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
