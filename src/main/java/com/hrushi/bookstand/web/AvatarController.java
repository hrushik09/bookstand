package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.profiles.Avatar;
import com.hrushi.bookstand.domain.profiles.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/avatars")
class AvatarController {
    private final ProfileService profileService;

    public AvatarController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    String avatar(@PathVariable("id") Long userId, Model model) {
        Avatar avatar = profileService.findAvatar(userId);
        model.addAttribute("avatar", avatar);
        return "profiles/avatar";
    }
}
