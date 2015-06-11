package workshop.microservices.weblog.core;

import static workshop.microservices.weblog.TestData.*;

import java.util.Date;

/**
 * Creates article test data.
 */
public class ArticleBuilder {

    private String articleId;

    private String title;

    private String content;

    private AuthorBuilder author;

    private Date created;

    public ArticleBuilder withArticleId(String articleId) {
        this.articleId = articleId;
        return this;
    }

    public ArticleBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder andContent(String content) {
        this.content = content;
        return this;
    }

    public ArticleBuilder writtenBy(AuthorBuilder author) {
        this.author = author;
        return this;
    }

    public ArticleBuilder createdOn(Date created) {
        this.created = created;
        return this;
    }

    public Article build() {
        return new Article(articleId, title, content, author.build(), created);
    }

    public static ArticleBuilder defaultArticle() {
        return new ArticleBuilder()
                .withArticleId(ARTICLE_ID)
                .withTitle(TITLE)
                .andContent(CONTENT)
                .writtenBy(AuthorBuilder.defaultAuthor())
                .createdOn(new Date());
    }
}
