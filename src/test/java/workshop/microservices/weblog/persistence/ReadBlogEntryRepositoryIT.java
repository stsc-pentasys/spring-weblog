package workshop.microservices.weblog.persistence;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import workshop.microservices.weblog.IntegrationBase;

public class ReadBlogEntryRepositoryIT extends IntegrationBase {

    @Autowired
    private BlogEntryRepository underTest;

    @Test
    public void getEntryReturnsExisting() throws Exception {
        BlogEntryEntity result = underTest.findByEntryId("is-tdd-dead");
        assertThat("Title", result.getTitle(), is("Is TDD Dead?"));
    }

    @Test
    public void getEntryReturnsNullOnMissingEntry() throws Exception {
        BlogEntryEntity result = underTest.findByEntryId("non-existing-id");
        assertThat("Result", result, nullValue());
    }

    @Test
    public void findAllByCustomQueryReturnsEntriesInProperOrder() throws Exception {
        List<BlogEntryEntity> result = underTest.findAllByCustomQuery();
        int size = result.size();
        assertThat("Number of entries", size, is(10));
        assertThat("First entry", result.get(0).getEntryId(), is("is-tdd-dead"));
        assertThat("Last entry", result.get(size - 1).getEntryId(), is("explore-capabilities-not-features"));
    }
}
