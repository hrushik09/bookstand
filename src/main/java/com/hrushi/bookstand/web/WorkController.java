package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.SecuredUser;
import com.hrushi.bookstand.domain.works.UpdateRatingCommand;
import com.hrushi.bookstand.domain.works.UpdateRatingRequest;
import com.hrushi.bookstand.domain.works.Work;
import com.hrushi.bookstand.domain.works.WorkService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/works")
class WorkController {
    private final WorkService workService;

    WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping("/{id}")
    String getWork(@PathVariable("id") Long workId, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        Work work = workService.getWork(securedUser.id(), workId);
        model.addAttribute("work", work);
        return "works/index";
    }

    @PostMapping(value = "/{id}/rating", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String updateRating(@PathVariable("id") Long workId, @Valid UpdateRatingRequest request, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        UpdateRatingCommand cmd = new UpdateRatingCommand(securedUser.id(), workId, request.rating());
        workService.updateRating(cmd);
        model.addAttribute("id", workId);
        model.addAttribute("rating", request.rating());
        return "works/rating";
    }
}
