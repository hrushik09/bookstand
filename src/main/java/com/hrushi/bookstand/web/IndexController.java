package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.SecuredUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class IndexController {
    @GetMapping
    String index(Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        model.addAttribute("id", securedUser.id());
        return "index";
    }
}
