package workshop.microservices.weblog.persistence.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.core.ArticlePersistenceAdapter;
import workshop.microservices.weblog.persistence.BlogEntryEntity;
import workshop.microservices.weblog.persistence.BlogEntryRepository;

/**
 * Adapter implementation as bridge between technology-independent API and Spring Data JPA specific implementation.
 */
@Component
@Transactional
public class JpaArticlePersistenceAdapter implements ArticlePersistenceAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(JpaArticlePersistenceAdapter.class);

    private BlogEntryRepository blogEntryRepository;

    private ArticleMapper articleMapper;

    private BlogEntryEntityMapper blogEntryEntityMapper;

    @Autowired
    public JpaArticlePersistenceAdapter(
            BlogEntryRepository blogEntryRepository,
            ArticleMapper articleMapper,
            BlogEntryEntityMapper blogEntryEntityMapper) {
        this.blogEntryRepository = blogEntryRepository;
        this.articleMapper = articleMapper;
        this.blogEntryEntityMapper =blogEntryEntityMapper;
        LOG.info("{} initialized", getClass().getSimpleName());
    }

    /**
     * Test only.
     */
    JpaArticlePersistenceAdapter() {
    }

    @Override
    public List<Article> findAll() {
        List<BlogEntryEntity> entities = blogEntryRepository.findAllByCustomQuery();
        LOG.debug("Retrieving {} entries form database", entities.size());
        return createBlogEntryList(entities);
    }

    private List<Article> createBlogEntryList(List<BlogEntryEntity> entities) {
        List<Article> result = new ArrayList<>(entities.size());
        result.addAll(entities.stream().map(blogEntryEntityMapper::map).collect(Collectors.toList()));
        return result;
    }

    @Override
    public Article findById(String entryId) {
        BlogEntryEntity entity = blogEntryRepository.findByEntryId(entryId);
        LOG.debug("Found {}", entity);
        return blogEntryEntityMapper.map(entity);
    }

    @Override
    public void save(Article newEntry) {
        BlogEntryEntity entity = articleMapper.map(newEntry);
        LOG.debug("Saving {}", entity);
        blogEntryRepository.save(entity);
    }

    @Override
    public void update(Article updated) {
        BlogEntryEntity entity = blogEntryRepository.findByEntryId(updated.getArticleId());
        entity.setTitle(updated.getTitle());
        entity.setContent(updated.getContent());
        LOG.debug("Updating {}", entity);
    }
}
