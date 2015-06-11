package workshop.microservices.weblog.persistence;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import workshop.microservices.weblog.IntegrationBase;

public class WriteBlogEntryRepositoryIT extends IntegrationBase {

    @Autowired
    private BlogEntryRepository underTest;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    public void saveSuccess() throws Exception {
        UserEntity user = userEntityRepository.findByUserId("martin");
        BlogEntryEntity entity = new BlogEntryEntityBuilder()
                .withEntryId("for-testing-purposes-only")
                .withTitle("For testing purposes only!")
                .withContent("Some strange bla bla")
                .createdOn(new Date())
                .build();
        entity.setAuthor(user);
        underTest.save(entity);
        BlogEntryEntity result = underTest.findByEntryId("for-testing-purposes-only");

        assertThat("Title", result.getTitle(), is(entity.getTitle()));
    }

    @Test
    public void updateSuccess() throws Exception {

    }
}
