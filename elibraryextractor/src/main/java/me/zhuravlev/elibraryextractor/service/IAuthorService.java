package me.zhuravlev.elibraryextractor.service;

import me.zhuravlev.elibraryextractor.model.Author;

import java.util.Optional;

public interface IAuthorService {
    Optional<Author> getByeLibraryId(Long eLibraryId);
    Optional<Author> getByeLibraryId(Long eLibraryId, boolean refresh);
}
