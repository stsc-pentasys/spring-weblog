package workshop.microservices.weblog.persistence.internal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import workshop.microservices.weblog.core.Author;
import workshop.microservices.weblog.core.AuthorBuilder;
import workshop.microservices.weblog.persistence.UserEntity;
import workshop.microservices.weblog.persistence.UserEntityBuilder;

public class UserEntityMapperTest {

    private UserEntityMapper underTest = new UserEntityMapper();

    private UserEntity original = UserEntityBuilder.defaultUserEntity().build();

    private Author expected = AuthorBuilder.defaultAuthor().build();

    private Author actual = underTest.map(original);

    @Test
    public void nickNameMatches() throws Exception {
        assertThat("Nickname", actual.getNickName(), is(expected.getNickName()));
    }

    @Test
    public void fullNameMatches() throws Exception {
        assertThat("Full name", actual.getFullName(), is(expected.getFullName()));
    }

    @Test
    public void emailAddressMatches() throws Exception {
        assertThat("Email address", actual.getEmailAddress(), is(expected.getEmailAddress()));
    }
}
