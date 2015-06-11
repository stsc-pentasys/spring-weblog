package workshop.microservices.weblog.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA enabled persistence API.
 *
 * No explicit implementation necessary, is implemented auto-magically at runtime by Spring Data JPA.
 */
@Repository
public interface BlogEntryRepository extends CrudRepository<BlogEntryEntity, Long> {

    /**
     * Retrieve a BlogEntry by its unique id.
     *
     * Corresponding query is generated automatically by Spring Data JPA.
     *
     * @param entryId the entry's id
     * @return the full entry or null, if not found
     */
    BlogEntryEntity findByEntryId(String entryId);

    /**
     * Retrieves all entries, sorted by publication date (descending).
     *
     * Uses a custom JPQL query.
     *
     * @return the entries as list or an empty list, if no entry exists.
     */
    @Query("select be from BlogEntryEntity be order by be.created desc")
    List<BlogEntryEntity> findAllByCustomQuery();

}
