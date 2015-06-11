package workshop.microservices.weblog.persistence.internal;

import static org.mockito.Mockito.*;
import static workshop.microservices.weblog.TestData.*;

import java.util.Optional;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.microservices.weblog.core.Author;
import workshop.microservices.weblog.core.AuthorBuilder;
import workshop.microservices.weblog.core.AuthorPersistenceAdapter;
import workshop.microservices.weblog.persistence.UserEntity;
import workshop.microservices.weblog.persistence.UserEntityBuilder;
import workshop.microservices.weblog.persistence.UserEntityRepository;

@RunWith(MockitoJUnitRunner.class)
public class JpaAuthorPersistenceAdapterTest {

    @InjectMocks
    private AuthorPersistenceAdapter underTest = new JpaAuthorPersistenceAdapter();

    @Mock
    private UserEntityMapper userEntityMapperMock;

    @Mock
    private UserEntityRepository userEntityRepositoryMock;

    @Test
    public void findByIdReturnsTransformedUser() throws Exception {
        UserEntity userEntity = UserEntityBuilder.defaultUserEntity().build();
        Author author = AuthorBuilder.defaultAuthor().build();
        when(userEntityRepositoryMock.findByUserId(NICK_NAME)).thenReturn(userEntity);
        when(userEntityMapperMock.map(userEntity)).thenReturn(author);

        Optional<Author> result = underTest.findById(NICK_NAME);

        MatcherAssert.assertThat("Author", result.get(), Matchers.sameInstance(author));
    }
}