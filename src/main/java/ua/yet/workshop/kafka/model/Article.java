package ua.yet.workshop.kafka.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Article {

    private final String title;

    private final String text;

    private final String site;

    private final LocalDateTime publishDate;

    private final List<Author> authors;

    public Article(final String title, final String text, final String site, final LocalDateTime publishDate,
                   final Author... authors) {
        this.title = title;
        this.text = text;
        this.site = site;
        this.publishDate = publishDate;
        this.authors = Collections.unmodifiableList(Arrays.asList(authors));
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getSite() {
        return site;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
