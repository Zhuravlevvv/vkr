package me.zhuravlev.elibraryextractor.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;
    @Column(unique = true)
    private Long eLibraryId = 0L;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    })
    @JoinTable(
            name = "author_articles",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> authorArticles = new LinkedList<>();

    @ElementCollection
    private List<Long> authorArticleIDs = new ArrayList<>();

    private String fullName;

    private String organization;

    private String department;

    public Long getId() {
        return id;
    }

    public Author setId(Long id) {
        this.id = id;
        return this;
    }

    public Long geteLibraryId() {
        return eLibraryId;
    }

    public Author seteLibraryId(Long eLibraryId) {
        this.eLibraryId = eLibraryId;
        return this;
    }

    public List<Article> getArticles() {
        return authorArticles;
    }

    public Author setArticles(List<Article> articles) {
        this.authorArticles = articles;
        return this;
    }

    public List<Long> getAuthorArticleIDs() {
        return authorArticleIDs;
    }

    public Author setAuthorArticleIDs(List<Long> authorArticleIDs) {
        this.authorArticleIDs = authorArticleIDs;
        return this;
    }

    public String getFullName() {
        if (fullName == null)
            return "";
        return fullName;
    }

    public Author setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getOrganization() {
        if (organization == null)
            return "";
        return organization;
    }

    public Author setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getDepartment() {
        if (department == null)
            return "";
        return department;
    }

    public Author setDepartment(String department) {
        this.department = department;
        return this;
    }
}
