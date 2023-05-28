package me.zhuravlev.elibraryextractor.service;

import me.zhuravlev.elibraryextractor.model.Article;
import me.zhuravlev.elibraryextractor.repository.ArticleRepository;
import me.zhuravlev.elibraryextractor.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService implements IArticleService {
    private final AuthorRepository authorRepository;
    private final ArticleRepository articleRepository;

    public ArticleService(AuthorRepository authorRepository, ArticleRepository articleRepository) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Optional<Article> getByeLibraryId(Long eLibraryId) {
        var article = articleRepository.getByeLibraryId(eLibraryId);

        if (article.isEmpty()) {
            var eLibrary = new ELibraryService();
            article = eLibrary.getArticle(eLibraryId);
            if (article.isPresent()) {
                // check doubles
                for (int i = 0; i < article.get().getAuthors().size(); i++) {
                    var newAuthor = article.get().getAuthors().get(i);
                    var author = authorRepository.getByeLibraryId(newAuthor.geteLibraryId());
                    if (author.isPresent())
                        article.get().getAuthors().set(i, author.get());
                }
                // add new article to author_articles
                for (var author : article.get().getAuthors()) author.getArticles().add(article.get());
                // save
                articleRepository.save(article.get());
            } else return Optional.empty();
        }
        return article;
    }

    @Override
    public Optional<Article> updateArticle(Article newArticle) {
        var article = articleRepository.getByeLibraryId(newArticle.geteLibraryId());

        if (article.isPresent()) {
            var result = articleRepository.save(newArticle);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    @Override
    public Boolean removeByeLibraryId(Long eLibraryId) {
        var article = articleRepository.getByeLibraryId(eLibraryId);

        if (article.isPresent()) {
            var authors = article.get().getAuthors();
            for (var author : authors) {
                author.getArticles().remove(article.get());
                authorRepository.save(author);
            }
            articleRepository.delete(article.get());
            return true;
        }
        return false;
    }
}
