package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.profiles.Profile;
import com.hrushi.bookstand.domain.profiles.ProfileService;
import com.hrushi.bookstand.domain.profiles.UpdateProfileCommand;
import com.hrushi.bookstand.domain.profiles.UpdateProfileRequest;
import com.hrushi.bookstand.domain.users.SecuredUser;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profiles")
class ProfileController {
    private final ProfileService profileService;

    ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    String profiles(Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        Profile profile = profileService.findProfile(securedUser.id());
        model.addAttribute("profile", profile);
        return "profiles/index";
    }

    @GetMapping("/update")
    String edit(Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        Profile profile = profileService.findProfile(securedUser.id());
        model.addAttribute("profile", profile);
        return "profiles/update";
    }

    @PutMapping
    String updateProfile(@Valid UpdateProfileRequest request, @AuthenticationPrincipal SecuredUser securedUser, Model model) {
        UpdateProfileCommand cmd = new UpdateProfileCommand(securedUser.id(), request.firstName(), request.lastName(), request.country(), request.email(), request.bio());
        profileService.updateProfile(cmd);
        Profile profile = profileService.findProfile(securedUser.id());
        model.addAttribute("profile", profile);
        return "profiles/profilecard";
    }
}
