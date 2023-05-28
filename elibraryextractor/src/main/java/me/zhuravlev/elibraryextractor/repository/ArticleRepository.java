package me.zhuravlev.elibraryextractor.repository;

import me.zhuravlev.elibraryextractor.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Query("from me.zhuravlev.elibraryextractor.model.Article arts WHERE arts.eLibraryId = ?1")
    Optional<Article> getByeLibraryId(Long eLibraryId);
}
