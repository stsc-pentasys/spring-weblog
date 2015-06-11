package workshop.microservices.weblog.resource;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A representation of Article useful for aggregation in lists.
 */
public class ArticleListResponse {

    private String title;
    private String link;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="CET")
    private Date created;

    public ArticleListResponse(String title, Date created, String link) {
        this.title = title;
        this.created = created;
        this.link = link;
    }

    /**
     * @return the article's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the article's creation date
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @return the article's absolute URL as String
     */
    public String getLink() {
        return link;
    }
}
