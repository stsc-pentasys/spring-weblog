package workshop.microservices.weblog.persistence;

import java.util.Date;

import workshop.microservices.weblog.TestData;

/**
 * Creates test data.
 */
public class BlogEntryEntityBuilder {

    private Long objectId;

    private Long version;

    private String entryId;

    private String title;

    private String content;

    private UserEntityBuilder author;

    private Date created;

    public BlogEntryEntityBuilder withObjectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public BlogEntryEntityBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }

    public BlogEntryEntityBuilder withEntryId(String entryId) {
        this.entryId = entryId;
        return this;
    }

    public BlogEntryEntityBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BlogEntryEntityBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public BlogEntryEntityBuilder fromAuthor(UserEntityBuilder author) {
        this.author = author;
        return this;
    }

    public BlogEntryEntityBuilder createdOn(Date created) {
        this.created = created;
        return this;
    }

    public BlogEntryEntity build() {
        BlogEntryEntity entity = new BlogEntryEntity();
        entity.setObjectId(objectId);
        entity.setVersion(version);
        entity.setEntryId(entryId);
        entity.setTitle(title);
        entity.setContent(content);
        entity.setAuthor(author != null ? author.build() : null);
        entity.setCreated(created);
        return entity;
    }

    public static BlogEntryEntityBuilder defaultBlogEntryEntity() {
        return new BlogEntryEntityBuilder()
                .withObjectId(1L)
                .withVersion(1L)
                .withEntryId(TestData.ARTICLE_ID)
                .withTitle(TestData.TITLE)
                .withContent(TestData.CONTENT)
                .fromAuthor(UserEntityBuilder.defaultUserEntity())
                .createdOn(new Date());
    }
}
