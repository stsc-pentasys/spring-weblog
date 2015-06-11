package workshop.microservices.weblog.resource;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Detailed representation of an existing article.
 */
public class ArticleResponse {

    private String articleId;
    private String title;
    private String content;
    private String author;
    private String emailAddress;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="CET")
    private Date created;

    public ArticleResponse(String articleId, String title, String content, String author, String emailAddress, Date created) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.emailAddress = emailAddress;
        this.created = created;
    }

    /**
     * @return the article's unique id
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * @return the article's title
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
     * @return the full name of the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the author's contact
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @return the article's creation date
     */
    public Date getCreated() {
        return created;
    }
}
