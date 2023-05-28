package me.zhuravlev.elibraryextractor.service;

import me.zhuravlev.elibraryextractor.model.Author;
import me.zhuravlev.elibraryextractor.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService implements IAuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> getByeLibraryId(Long eLibraryId) {
        return getByeLibraryId(eLibraryId, false);
    }

    public Optional<Author> getByeLibraryId(Long eLibraryId, boolean refresh) {
        var author = authorRepository.getByeLibraryId(eLibraryId);

        if (author.isPresent() && !refresh) {
            return author;
        }
        var id = author.isPresent() ? author.get().getId() : -1L;
        var articles = author.isPresent() ? author.get().getArticles() : null;

        var eLibrary = new ELibraryService();
        author = eLibrary.getAuthor(eLibraryId);
        if (author.isPresent()) {
            if (id != -1) author.get().setId(id);
            if (articles != null) author.get().setArticles(articles);

            // save
            authorRepository.save(author.get());
        } else return Optional.empty();

        return author;
    }
}
