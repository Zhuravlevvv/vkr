package me.zhuravlev.elibraryextractor.controller;

import me.zhuravlev.elibraryextractor.model.Article;
import me.zhuravlev.elibraryextractor.service.IArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ArticleController {

    private final IArticleService articleService;

    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(path = "/article", method = RequestMethod.GET)
    public String getArticle(@RequestParam Map<String, String> params, Model model) {
        if (params.containsKey("id")) {
            var id = Long.parseLong(params.get("id"));
            var article = articleService.getByeLibraryId(id);

            if (article.isEmpty())
                return "error";

            model.addAttribute("article", article.get());
            return "showArticle";
        }
        return "error";
    }

    @RequestMapping(path = "/article/update", method = RequestMethod.GET)
    public String updateArticle(@RequestParam Map<String, String> params, Model model) {
        if (params.containsKey("id")) {
            var id = Long.parseLong(params.get("id"));
            var article = articleService.getByeLibraryId(id);

            if (article.isEmpty())
                return "error";

            model.addAttribute("article", article.get());
            return "updateArticle";
        }
        return "error";
    }

    // API
    @RequestMapping(path = "/api/article", method = RequestMethod.GET)
    public ResponseEntity<Article> getArticle(@RequestParam Map<String, String> params) {
        if (params.containsKey("id")) {
            var id = Long.parseLong(params.get("id"));
            var article = articleService.getByeLibraryId(id);

            return article.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "/api/article", method = RequestMethod.PUT)
    public ResponseEntity<Article> updateArticle(@RequestBody Article body) {
        if (body != null) {
            var article = articleService.updateArticle(body);

            return article.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "/api/article", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteArticle(@RequestParam Map<String, String> params) {
        if (params.containsKey("id")) {
            var id = Long.parseLong(params.get("id"));
            var result = articleService.removeByeLibraryId(id);

            if (result) return new ResponseEntity<>(result, HttpStatus.OK);
            else new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
