package me.zhuravlev.elibraryextractor.service;

import me.zhuravlev.elibraryextractor.helper.Constants;
import me.zhuravlev.elibraryextractor.helper.SeleniumHelper;
import me.zhuravlev.elibraryextractor.model.Article;
import me.zhuravlev.elibraryextractor.model.Author;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ELibraryService {
    public ELibraryService() {
        internet = new SeleniumHelper();
    }

    SeleniumHelper internet;

    public Optional<Author> getAuthor(Long authorId) {
        var driver = internet.getWebdriver();
        try {
            var url = Constants.authorUrl + authorId;
            driver.get(url);
            checkUrl(driver.getCurrentUrl(), url);

            var nameElem = internet.fluentWait(driver, By.cssSelector(Constants.authorName));
            var orgElem = internet.fluentWait(driver, By.cssSelector(Constants.authorOrg));
            var depElem = internet.fluentWait(driver, By.cssSelector(Constants.authorDep));

            var author = new Author();
            author.seteLibraryId(authorId);
            author.setFullName(nameElem.getText());
            author.setOrganization(orgElem.getText());
            author.setDepartment(internet.ownText(depElem));
            // get articles ids
            var articleIDs = new ArrayList<Long>();
            do {
                var articlesTable = internet.fluentWait(driver, By.cssSelector(Constants.authorArticles));
                var articlesElem = articlesTable.findElements(By.tagName("tr"));
                for (var articleElem : articlesElem) {
                    var idRaw = articleElem.getAttribute("id");
                    if (!Objects.equals(idRaw, "")) {
                        idRaw = idRaw.replaceAll("[^0-9]", "");
                        // fill  articles ids
                        var id = Long.parseLong(idRaw);
                        articleIDs.add(id);
                    }
                }
                //Pagination
                var pagesElem = driver.findElements(By.cssSelector(Constants.authorArticlesPages)).stream().findFirst();
                if (pagesElem.isEmpty()) break;
                var nextPageButton = getNextPageButton(pagesElem.get());
                if (nextPageButton.isEmpty()) break;
                nextPageButton.get().click();
            } while (true);
            driver.quit();

            author.setAuthorArticleIDs(articleIDs);

            return Optional.of(author);
        } catch (Exception ex) {
            driver.quit();
            return Optional.empty();
        }
    }

    public Optional<Article> getArticle(Long eLibraryId) {
        var driver = internet.getWebdriver();
        try {
            var url = Constants.articleUrl + eLibraryId;
            driver.get(url);
            checkUrl(driver.getCurrentUrl(), url);

            var nameElem = internet.fluentWait(driver, By.cssSelector(Constants.articleName));

            var article = new Article();
            article.seteLibraryId(eLibraryId);
            article.setFullName(nameElem.getText());

            var descriptionElem = internet.fluentWait(driver, By.cssSelector(Constants.articleDescriptionBlock));
            var fonts = descriptionElem.findElements(By.tagName("font"));
            for (var font : fonts) {
                var text = internet.previousSibling(driver, font);
                if (text.contains("Тип:"))
                    article.setPublishingType(font.getText());
                if (text.contains("Страницы:"))
                    article.setPages(font.getText());
                if (text.contains("Дата") && text.contains("публикации"))
                    article.setPublishingDate(font.getText());
                if (text.contains("Издательство:"))
                    article.setPublishingHouse(font.getText());
                if (text.contains("Конференция:"))
                    article.setConference(font.getText());
                if (text.contains("Индексация:"))
                    article.setIndexation(font.getText());
//                if (text.contains("Ссылки на источники:"))
//                    article.setSources(font.getText().split());

            }

            var authors = getArticleAuthors(driver);
            article.setAuthors(authors);

            driver.quit();
            return Optional.of(article);
        } catch (Exception ex) {
            ex.printStackTrace();
            driver.quit();
            return Optional.empty();
        }
    }

    private boolean checkUrl(String url, String wantedUrl) throws Exception {
        if(url.contains("page_captcha"))
            throw new Exception("captcha. url: " + wantedUrl);
        else if(url.contains("page_404"))
            throw new Exception("page not found. url: " + wantedUrl);
        else if(url.contains("ip_restricted"))
            throw new Exception("blocked by IP");

        return true;
    }

    private Optional<WebElement> getNextPageButton(final WebElement pagesElem) {
        for (var pageButton : pagesElem.findElements(By.tagName("td"))) {
            if (pageButton.getText().equals(">>")) {
                var a = pageButton.findElements(By.tagName("a"));
                if (!a.isEmpty()) {
                    var href = a.get(0).getAttribute("href");
                    if (href != null && href.contains("javascript"))
                        return Optional.of(pageButton);
                }

                var font = pageButton.findElements(By.tagName("font"));
                if (!font.isEmpty()) {
                    var color = font.get(0).getAttribute("color");
                    if (color.equals("#aaaaaa"))
                        return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    private List<Author> getArticleAuthors(final WebDriver driver) {
        var authors = new LinkedList<Author>();
        var authorsElem = driver.findElement(By.cssSelector(Constants.articleAuthorsBlock));
        for (var authorElem : authorsElem.findElements(By.tagName("div"))) {
            var font = authorElem.findElements(By.tagName("font")).stream().findFirst();
            if (font.isPresent()) {
                font.get().click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                var authorName = font.get().getText();
                var author = new Author();

                var tooltips = driver.findElements(By.cssSelector(Constants.tooltip));
                for (var tooltip : tooltips) {
                    if (tooltip.isDisplayed()) {
                        Optional<Long> Id = Optional.empty();
                        for (var a : tooltip.findElements(By.tagName("a"))) {
                            var href = a.getAttribute("href");
                            if (href.contains("authorid")) {
                                var IdRaw = href.replaceAll("[^0-9]", "");
                                Id = Optional.of(Long.parseLong(IdRaw));
                                break;
                            }
                        }
                        if (Id.isPresent()) {
                            var name = tooltip.findElements(By.tagName("font")).stream().findFirst();
                            if (name.isPresent()) authorName = name.get().getText();
                            var org = internet.ownText(tooltip);
                            author.seteLibraryId(Id.get());
                            author.setOrganization(org);
                        }
                    }
                }
                author.setFullName(authorName);
                authors.add(author);
            }
        }
        return authors;
    }
}
