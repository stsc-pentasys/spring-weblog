package workshop.microservices.weblog.persistence.internal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workshop.microservices.weblog.core.Author;
import workshop.microservices.weblog.core.AuthorPersistenceAdapter;
import workshop.microservices.weblog.persistence.UserEntity;
import workshop.microservices.weblog.persistence.UserEntityRepository;

/**
 * Adapter implementation as bridge between technology-independent API and Spring Data JPA specific implementation.
 */
@Component
@Transactional
public class JpaAuthorPersistenceAdapter implements AuthorPersistenceAdapter {

    private UserEntityRepository userEntityRepository;

    private UserEntityMapper userEntityMapper;

    @Autowired
    public JpaAuthorPersistenceAdapter(UserEntityRepository userEntityRepository, UserEntityMapper userEntityMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userEntityMapper = userEntityMapper;
    }

    /**
     * Test only.
     */
    JpaAuthorPersistenceAdapter() {
    }

    @Override
    public Optional<Author> findById(String nickName) {
        UserEntity userEntity = userEntityRepository.findByUserId(nickName);
        return Optional.ofNullable(userEntityMapper.map(userEntity));
    }
}
