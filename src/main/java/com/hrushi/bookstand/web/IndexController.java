package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.SecuredUser;
import com.hrushi.bookstand.domain.works.AllShelves;
import com.hrushi.bookstand.domain.works.WorkService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.NumberFormat;
import java.util.Locale;

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
        Locale locale = LocaleContextHolder.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        String format = numberFormat.format(15678.43);
        model.addAttribute("localeExample", format);
        return "index";
    }
}
