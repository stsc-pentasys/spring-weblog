package workshop.microservices.weblog.persistence.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.persistence.BlogEntryEntity;
import workshop.microservices.weblog.persistence.Mapper;

/**
 * Transforms BlogEntryEntity to Article.
 */
@Component
public class BlogEntryEntityMapper implements Mapper<BlogEntryEntity, Article> {

    private UserEntityMapper userEntityMapper;

    @Autowired
    public void setUserEntityMapper(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }

    /**
     * Test only.
     */
    BlogEntryEntityMapper() {
    }

    @Override
    public Article map(BlogEntryEntity blogEntry) {
        Article result = null;
        if (blogEntry != null) {
            result = new Article(
                    blogEntry.getEntryId(),
                    blogEntry.getTitle(),
                    blogEntry.getContent(),
                    userEntityMapper.map(blogEntry.getAuthor()),
                    blogEntry.getCreated()
            );
        }
        return result;
    }

}
