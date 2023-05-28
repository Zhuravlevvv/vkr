package me.zhuravlev.elibraryextractor.controller;

import me.zhuravlev.elibraryextractor.model.Author;
import me.zhuravlev.elibraryextractor.service.IAuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AuthorController {

    private final IAuthorService authorService;

    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(path = "/author",  method = RequestMethod.GET)
    public String getAuthor(@RequestParam Map<String, String> params, Model model) {
        if (params.containsKey("id")) {
            var id = Long.parseLong(params.get("id"));
            var refresh = false;
            if (params.containsKey("refresh")) refresh = Boolean.parseBoolean(params.get("refresh"));

            var author = authorService.getByeLibraryId(id, refresh);

            if (author.isEmpty())
                return "error";

            var links = author.get().getAuthorArticleIDs();
            for (var article : author.get().getArticles())
                links.remove(article.geteLibraryId());

            model.addAttribute("author", author.get());
            model.addAttribute("links", links);
            return "showAuthor";
        }

        return "error";
    }

    // API
    @RequestMapping(path = "/api/author", method = RequestMethod.GET)
    public ResponseEntity<Author> getAuthor(@RequestParam Map<String, String> params) {
        if (params.containsKey("id")) {
            var id = Long.parseLong(params.get("id"));
            var author = authorService.getByeLibraryId(id);

            return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
