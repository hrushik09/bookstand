package com.hrushi.bookstand.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
class LoginController {
    @GetMapping
    String login() {
        return "login";
    }
}
