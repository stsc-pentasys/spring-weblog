package workshop.microservices.weblog.persistence.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import workshop.microservices.weblog.core.Author;
import workshop.microservices.weblog.persistence.Mapper;
import workshop.microservices.weblog.persistence.UserEntity;
import workshop.microservices.weblog.persistence.UserEntityRepository;

/**
 * Transforms Author to UserEntity.
 */
@Component
public class AuthorMapper implements Mapper<Author, UserEntity> {

    private UserEntityRepository userEntityRepository;

    @Autowired
    public AuthorMapper(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    /**
     * Test only.
     */
    AuthorMapper() {
    }

    @Override
    public UserEntity map(Author t) {
        return userEntityRepository.findByUserId(t.getNickName());
    }
}
