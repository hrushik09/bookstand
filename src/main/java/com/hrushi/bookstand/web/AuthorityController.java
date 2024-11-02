package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.authorities.AuthorityService;
import com.hrushi.bookstand.domain.authorities.CreateAuthorityCommand;
import com.hrushi.bookstand.domain.authorities.CreateAuthorityRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/authorities")
class AuthorityController {
    private final AuthorityService authorityService;

    AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping
    String authorities(Model model) {
        List<String> authorities = authorityService.findAll();
        model.addAttribute("authorities", authorities);
        return "authorities";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String create(@Valid CreateAuthorityRequest request) {
        CreateAuthorityCommand cmd = new CreateAuthorityCommand(request.value());
        authorityService.createAuthority(cmd);
        return "redirect:/authorities";
    }
}
