package workshop.microservices.weblog.core.internal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.microservices.weblog.core.*;

/**
 * Core service implementation.
 */
@Service
public class BlogServiceBean implements BlogService {

    private static final Logger LOG = LoggerFactory.getLogger(BlogServiceBean.class);

    private ArticlePersistenceAdapter articlePersistenceAdapter;

    private AuthorPersistenceAdapter authorPersistenceAdapter;

    private IdNormalizer idNormalizer;

    private NotificationAdapter notificationAdapter;

    /**
     * Set mandatory dependencies only.
     *
     * @param articlePersistenceAdapter
     * @param authorPersistenceAdapter
     * @param idNormalizer
     */
    @Autowired
    public BlogServiceBean(
            ArticlePersistenceAdapter articlePersistenceAdapter,
            AuthorPersistenceAdapter authorPersistenceAdapter,
            IdNormalizer idNormalizer) {
        this.articlePersistenceAdapter = articlePersistenceAdapter;
        this.authorPersistenceAdapter = authorPersistenceAdapter;
        this.idNormalizer = idNormalizer;

        LOG.info("{} initialized", getClass().getSimpleName());
    }

    /**
     * Test only.
     */
    BlogServiceBean() {}

    /**
     * The NotificationAdapter is optional.
     * A noop implementation is provided by default.
     *
     * @param notificationAdapter
     */
    @Autowired(required = false)
    public void setNotificationAdapter(NotificationAdapter notificationAdapter) {
        this.notificationAdapter = notificationAdapter;
    }

    @Override
    public List<Article> index() throws BlogServiceException {
        try {
            List<Article> articles = articlePersistenceAdapter.findAll();
            LOG.debug("Found {} articles", articles.size());
            return articles;
        } catch (RuntimeException e) {
            throw new BlogServiceException(e);
        }
    }

    @Override
    public Article publish(String nickName, String title, String content) throws BlogServiceException {
        verifyNotEmpty(nickName, title, content);
        Author author = verifyAuthor(nickName);
        String entryId = createUniqueId(title);
        Article article = new Article(entryId, title, content, author, now());
        saveNew(article);
        notificationAdapter.created(article);
        LOG.debug("New article published: {}", article);
        return article;
    }

    private void verifyNotEmpty(String nickName, String title, String content) throws BlogServiceException {
        if (StringUtils.isBlank(nickName) || StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            throw new BlogServiceException("Nickname, title and content must not be empty");
        }
    }

    private Author verifyAuthor(String nickName) throws UnknownAuthorException {
        Optional<Author> author = authorPersistenceAdapter.findById(nickName);
        return author.orElseThrow(() -> new UnknownAuthorException("No author found with id " + nickName));
    }

    private String createUniqueId(String title) throws ArticleAlreadyExistsException {
        String normalized = idNormalizer.normalizeTitle(title);
        Article article = articlePersistenceAdapter.findById(normalized);
        if (article != null) {
            throw new ArticleAlreadyExistsException("An entry with id '" + normalized + "' already exists");
        }
        return normalized;
    }

    private void saveNew(Article newEntry) throws BlogServiceException {
        try {
            articlePersistenceAdapter.save(newEntry);
        } catch (RuntimeException e) {
            throw new BlogServiceException(e);
        }
    }

    private Date now() {
        return new Date();
    }

    @Override
    public Article edit(String articleId, String editor, String title, String content) throws BlogServiceException {
        verifyNotEmpty(editor, title, content);
        Article published = read(articleId);
        Article updated = executeUpdate(published, editor, title, content);
        LOG.debug("Article updated: {}", updated);
        return updated;
    }

    private Article executeUpdate(Article published, String nickName, String title, String content)
            throws BlogServiceException {
        verifySameAuthor(published, nickName);
        Article updated = new Article(published, title, content);
        updateExisting(updated);
        notificationAdapter.edited(updated);
        return updated;
    }

    private void updateExisting(Article updated) throws BlogServiceException {
        try {
            articlePersistenceAdapter.update(updated);
        } catch (RuntimeException e) {
            throw new BlogServiceException(e);
        }
    }

    private void verifySameAuthor(Article published, String nickName) throws WrongAuthorException {
        String originalAuthor = published.getPublishedBy().getNickName();
        if (!originalAuthor.equals(nickName)) {
            throw new WrongAuthorException(nickName + " is not the author of " + published.getArticleId());
        }
    }

    @Override
    public Article read(String articleId) throws BlogServiceException {
        Article article = articlePersistenceAdapter.findById(articleId);
        if (article != null) {
            LOG.debug("Found article: {}", article);
            return article;
        } else {
            throw new ArticleNotFoundException("Article not found: " +  articleId);
        }
    }
}
