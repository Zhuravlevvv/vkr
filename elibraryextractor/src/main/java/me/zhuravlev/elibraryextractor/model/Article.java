package me.zhuravlev.elibraryextractor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;
    @Column(unique = true, nullable = false)
    private Long eLibraryId = 0L;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    },mappedBy = "authorArticles")
    @JsonIgnore
    private List<Author> authors = new LinkedList<>();

    private String fullName;

    private String publishingHouse;

    private String publishingType;

    private String publishingDate;
    private String pages;

    private String conference;

    private String indexation;

    @ElementCollection
    private List<String> sources = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public Article setId(Long id) {
        this.id = id;
        return this;
    }

    public Long geteLibraryId() {
        return eLibraryId;
    }

    public Article seteLibraryId(Long eLibraryId) {
        this.eLibraryId = eLibraryId;
        return this;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Article setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public String getFullName() {
        if (fullName == null)
            return "";
        return fullName;
    }

    public Article setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPublishingHouse() {
        if (publishingHouse == null)
            return "";
        return publishingHouse;
    }

    public Article setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
        return this;
    }

    public String getPublishingType() {
        if (publishingType == null)
            return "";
        return publishingType;
    }

    public Article setPublishingType(String publishingType) {
        this.publishingType = publishingType;
        return this;
    }

    public String getPublishingDate() {
        if (publishingDate == null)
            return "";
        return publishingDate;
    }

    public Article setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
        return this;
    }

    public String getConference() {
        if (conference == null)
            return "";
        return conference;
    }

    public Article setConference(String conference) {
        this.conference = conference;
        return this;
    }

    public String getIndexation() {
        if(indexation == null)
            return "";
        return indexation;
    }

    public Article setIndexation(String indexation) {
        this.indexation = indexation;
        return this;
    }

    public List<String> getSources() {
        return sources;
    }

    public Article setSources(List<String> sources) {
        this.sources = sources;
        return this;
    }

    public String getPages() {
        if(pages == null)
            return "";
        return pages;
    }

    public Article setPages(String pages) {
        this.pages = pages;
        return this;
    }
}
