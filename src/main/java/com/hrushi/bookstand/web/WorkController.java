package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.users.SecuredUser;
import com.hrushi.bookstand.domain.works.*;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("currentUserReview", request.review());
        return "works/showuserreview";
    }

    @GetMapping("/{id}/review/edit")
    String getEditReviewForm(@PathVariable("id") Long workId, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        String review = workService.getReview(securedUser.id(), workId);
        model.addAttribute("id", workId);
        model.addAttribute("currentUserReview", review);
        return "works/edituserreview";
    }

    @GetMapping("/{id}/review")
    String getReview(@PathVariable("id") Long workId, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        String review = workService.getReview(securedUser.id(), workId);
        model.addAttribute("id", workId);
        model.addAttribute("currentUserReview", review);
        return "works/showuserreview";
    }

    @GetMapping("/reviews/{id}")
    String getLikesForReview(@PathVariable("id") Long workReviewId, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        WorkReviewLikes workReviewLikes = workService.getLikesForReview(securedUser.id(), workReviewId);
        model.addAttribute("workReviewLikes", workReviewLikes);
        return "works/reviewlikes";
    }

    @PutMapping("/reviews/{id}/like")
    String addLike(@PathVariable("id") Long workReviewId, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        workService.addLike(securedUser.id(), workReviewId);
        WorkReviewLikes workReviewLikes = workService.getLikesForReview(securedUser.id(), workReviewId);
        model.addAttribute("workReviewLikes", workReviewLikes);
        return "works/reviewlikes";
    }

    @DeleteMapping("/reviews/{id}/unlike")
    String removeLike(@PathVariable("id") Long workReviewId, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        workService.removeLike(securedUser.id(), workReviewId);
        WorkReviewLikes workReviewLikes = workService.getLikesForReview(securedUser.id(), workReviewId);
        model.addAttribute("workReviewLikes", workReviewLikes);
        return "works/reviewlikes";
    }

    @PutMapping("/{id}/shelf")
    String updateShelf(@PathVariable("id") Long workId, @Valid UpdateShelfRequest request, Model model, @AuthenticationPrincipal SecuredUser securedUser) {
        UpdateShelfCommand cmd = new UpdateShelfCommand(securedUser.id(), workId, request.shelf());
        String shelf = workService.updateShelf(cmd);
        model.addAttribute("id", workId);
        model.addAttribute("shelf", shelf);
        return "works/shelf";
    }
}
