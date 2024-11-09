package com.hrushi.bookstand.web;

import com.hrushi.bookstand.domain.authors.Author;
import com.hrushi.bookstand.domain.authors.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
class AuthorController {
    private final AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/{id}")
    String getAuthor(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "author";
    }
}
