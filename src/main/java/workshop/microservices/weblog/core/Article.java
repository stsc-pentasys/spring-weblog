package workshop.microservices.weblog.core;

import java.io.Serializable;
import java.util.Date;

/**
 * A single article.
 */
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String articleId;

    private final String title;

    private final String content;

    private final Author publishedBy;

    private final Date created;

    public Article(String articleId, String title, String content, Author publishedBy, Date created) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.publishedBy = publishedBy;
        this.created = created;
    }

    public Article(Article old, String newTitle, String newContent) {
        this(old.articleId, newTitle, newContent, old.publishedBy, old.created);
    }

    /**
     * @return the article's unique id
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the article's content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the article's original author
     */
    public Author getPublishedBy() {
        return publishedBy;
    }

    /**
     * @return timestamp of the first publication
     */
    public Date getCreated() {
        return created;
    }
}
