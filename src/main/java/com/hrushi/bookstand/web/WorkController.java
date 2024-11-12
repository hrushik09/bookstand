package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.SecuredUser;
import com.hrushi.bookstand.domain.works.*;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping(value = "/{id}/rating", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String updateRating(@PathVariable("id") Long workId, @Valid UpdateRatingRequest request, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        UpdateRatingCommand cmd = new UpdateRatingCommand(securedUser.id(), workId, request.rating());
        workService.updateRating(cmd);
        model.addAttribute("id", workId);
        model.addAttribute("rating", request.rating());
        return "works/rating";
    }

    @PutMapping(value = "/{id}/review")
    String updateReview(@PathVariable("id") Long workId, @Valid UpdateReviewRequest request, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        UpdateReviewCommand cmd = new UpdateReviewCommand(securedUser.id(), workId, request.review());
        workService.updateReview(cmd);
        model.addAttribute("id", workId);
        model.addAttribute("review", request.review());
        return "works/showuserreview";
    }
}
