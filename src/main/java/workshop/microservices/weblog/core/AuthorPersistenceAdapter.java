package workshop.microservices.weblog.core;

import java.util.Optional;

/**
 * Persistence operations required by the 'business logic'.
 */
public interface AuthorPersistenceAdapter {

    /**
     * Retrieve an author by his/her login name.
     *
     * @param nickName the author's login name
     * @return a valid Author or null, if none found
     */
    Optional<Author> findById(String nickName);
}
