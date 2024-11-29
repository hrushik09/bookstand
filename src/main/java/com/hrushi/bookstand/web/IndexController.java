package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.SecuredUser;
import com.hrushi.bookstand.domain.works.AllShelves;
import com.hrushi.bookstand.domain.works.WorkService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class IndexController {
    private final WorkService workService;

    public IndexController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping
    String index(Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        AllShelves allShelves = workService.getAllShelves(securedUser.id());
        model.addAttribute("allShelves", allShelves);
        return "index";
    }
}
