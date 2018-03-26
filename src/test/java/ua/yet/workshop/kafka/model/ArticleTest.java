package ua.yet.workshop.kafka.model;

import org.junit.Test;

import java.time.LocalDateTime;

public class ArticleTest {

  @Test(expected = UnsupportedOperationException.class)
  public void articleShouldCreateWithUnmodifiableListOfAuthors() {
    final Article article = new Article("title", "text","site", LocalDateTime.now(),
        new Author("first", "last", "email"));

    article.getAuthors().clear();
  }
}
