package workshop.microservices.weblog.persistence;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import workshop.microservices.weblog.IntegrationBase;

public class ReadUserRepositoryIT extends IntegrationBase {

    public static final String USER_ID = "martin";

    @Autowired
    private UserEntityRepository underTest;

    @Test
    public void readByUserIdSuccess() throws Exception {
        UserEntity result = underTest.findByUserId(USER_ID);
        assertThat("User id", result.getUserId(), is(USER_ID));
    }
}
