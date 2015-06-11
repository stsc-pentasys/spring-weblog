package workshop.microservices.weblog.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data specific persistence API.
 *
 * Implementation not necessary, will be created at runtime by Spring Data JPA.
 */
@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {

    /**
     * Retrieve an user by its unique id.
     *
     * @param userId the user's Id
     * @return the user  or null, if not found
     */
    UserEntity findByUserId(String userId);

}
