package workshop.microservices.weblog.core;

import java.util.List;
import java.util.Optional;

/**
 * Persistence operations regarding Article as required by the 'business logic'.
 */
public interface ArticlePersistenceAdapter {

    /**
     * Retrieves all published entries ordered by publishing date (newest first).
     *
     * @return all entries, or an empty list, if none exist
     */
    List<Article> findAll();

    /**
     * Retrieves a single article by its unique id.
     *
     * @param entryId the article's id
     * @return the article or null, if not found
     */
    Article findById(String entryId);

    /**
     * Publishes a new article to the repository.
     *
     * @param newEntry a new article
     */
    void save(Article newEntry);

    /**
     * Saves modifications to an article (only title and content will be changed).
     *
     * @param updated an existing article
     */
    void update(Article updated);
}
