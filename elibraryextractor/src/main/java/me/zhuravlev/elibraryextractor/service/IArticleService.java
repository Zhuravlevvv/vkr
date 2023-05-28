package me.zhuravlev.elibraryextractor.service;

import me.zhuravlev.elibraryextractor.model.Article;

import java.util.Optional;

public interface IArticleService {
    Optional<Article> getByeLibraryId(Long eLibraryId);

    Optional<Article> updateArticle(Article newArticle);

    Boolean removeByeLibraryId(Long eLibraryId);
}
