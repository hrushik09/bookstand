package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.works.Work;
import com.hrushi.bookstand.domain.works.WorkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/works")
class WorkController {
    private final WorkService workService;

    WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping("/{id}")
    String getWork(@PathVariable Long id, Model model) {
        Work work = workService.getWork(id);
        model.addAttribute("work", work);
        return "work";
    }
}
