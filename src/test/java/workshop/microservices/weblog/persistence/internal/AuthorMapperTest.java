package workshop.microservices.weblog.persistence.internal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.microservices.weblog.core.Author;
import workshop.microservices.weblog.core.AuthorBuilder;
import workshop.microservices.weblog.persistence.UserEntity;
import workshop.microservices.weblog.persistence.UserEntityBuilder;
import workshop.microservices.weblog.persistence.UserEntityRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuthorMapperTest {

    @InjectMocks
    private AuthorMapper underTest = new AuthorMapper();

    @Mock
    private UserEntityRepository userEntityRepositoryMock;

    @Test
    public void mapConvertsAuthorToUserEntity() throws Exception {
        Author author = AuthorBuilder.defaultAuthor().build();
        UserEntity userEntity = UserEntityBuilder.defaultUserEntity().build();
        when(userEntityRepositoryMock.findByUserId(author.getNickName())).thenReturn(userEntity);

        UserEntity result = underTest.map(author);

        assertThat("User entity", result, sameInstance(userEntity));
    }
}