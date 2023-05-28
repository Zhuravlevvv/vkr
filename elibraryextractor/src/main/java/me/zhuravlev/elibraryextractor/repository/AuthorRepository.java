package me.zhuravlev.elibraryextractor.repository;

import me.zhuravlev.elibraryextractor.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Query("from me.zhuravlev.elibraryextractor.model.Author auth WHERE auth.eLibraryId = ?1")
    Optional<Author> getByeLibraryId(Long eLibraryId);
}
