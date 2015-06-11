package workshop.microservices.weblog.persistence.internal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.core.ArticleBuilder;
import workshop.microservices.weblog.core.Author;
import workshop.microservices.weblog.core.AuthorBuilder;
import workshop.microservices.weblog.persistence.BlogEntryEntity;
import workshop.microservices.weblog.persistence.BlogEntryEntityBuilder;
import workshop.microservices.weblog.persistence.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class BlogEntryEntityMapperTest {

    @InjectMocks
    private BlogEntryEntityMapper underTest = new BlogEntryEntityMapper();

    @Mock
    private UserEntityMapper userEntityMapper;

    private BlogEntryEntity original = BlogEntryEntityBuilder.defaultBlogEntryEntity().build();

    private Article actual;

    private Article expected = ArticleBuilder.defaultArticle().build();

    private Author author = AuthorBuilder.defaultAuthor().build();

    @Before
    public void setUp() throws Exception {
        Mockito.when(userEntityMapper.map(Mockito.isA(UserEntity.class))).thenReturn(author);
        actual = underTest.map(original);
    }

    @Test
    public void entryIdMatches() throws Exception {
        assertThat("entryId", actual.getArticleId(), is(expected.getArticleId()));
    }

    @Test
    public void titleMatches() throws Exception {
        assertThat("title", actual.getTitle(), is(expected.getTitle()));
    }

    @Test
    public void contentMatches() throws Exception {
        assertThat("content", actual.getContent(), is(expected.getContent()));
    }
    @Test
    public void userConverted() throws Exception {
        assertThat("Author", actual.getPublishedBy(), sameInstance(author));
    }

    @Test
    public void createdMatches() throws Exception {
        assertThat("Date created", actual.getCreated().toString(), is(expected.getCreated().toString()));
    }

}