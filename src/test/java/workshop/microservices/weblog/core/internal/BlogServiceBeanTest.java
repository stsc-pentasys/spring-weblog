package workshop.microservices.weblog.core.internal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static workshop.microservices.weblog.TestData.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.microservices.weblog.core.*;

@RunWith(MockitoJUnitRunner.class)
public class BlogServiceBeanTest {

    public static final String NEW_TITLE = "New title";
    public static final String NEW_CONTENT = "New content";

    @InjectMocks
    private BlogService underTest = new BlogServiceBean();

    @Mock
    private ArticlePersistenceAdapter articlePersistenceAdapterMock;

    @Mock
    private AuthorPersistenceAdapter authorPersistenceAdapterMock;

    @Mock
    private IdNormalizer idNormalizerMock;

    @Mock
    private NotificationAdapter notificationAdapterMock;

    @Captor
    private ArgumentCaptor<Article> articleCaptor;

    @Test
    public void readPassesFoundArticle() throws Exception {
        Article entry = ArticleBuilder.defaultArticle().build();
        when(articlePersistenceAdapterMock.findById(ARTICLE_ID)).thenReturn(entry);
        Article result = underTest.read(ARTICLE_ID);
        assertThat("Blog entry", result, sameInstance(entry));
    }

    @Test(expected = ArticleNotFoundException.class)
    public void readThrowsExceptionOnMissingEntry() throws Exception {
        when(articlePersistenceAdapterMock.findById(ARTICLE_ID)).thenReturn(null);
        Article result = underTest.read(ARTICLE_ID);
    }

    @Test
    public void indexReturnsUnmodifiedList() throws Exception {
        List<Article> articles = Collections.emptyList();
        when(articlePersistenceAdapterMock.findAll()).thenReturn(articles);
        List<Article> result = underTest.index();
        assertThat("Article list", result, sameInstance(articles));
    }

    @Test
    public void publishSuccess() throws Exception {
        when(authorPersistenceAdapterMock.findById(NICK_NAME))
                .thenReturn(Optional.of(AuthorBuilder.defaultAuthor().build()));
        when(idNormalizerMock.normalizeTitle(TITLE)).thenReturn(ARTICLE_ID);
        when(articlePersistenceAdapterMock.findById(ARTICLE_ID)).thenReturn(null);

        Article result = underTest.publish(NICK_NAME, TITLE, CONTENT);

        assertThat("Article id", result.getArticleId(), is(ARTICLE_ID));
        verify(notificationAdapterMock).created(result);
    }

    @Test(expected = UnknownAuthorException.class)
    public void publishFailsForUnknownAuthor() throws Exception {
        when(authorPersistenceAdapterMock.findById(NICK_NAME)).thenReturn(Optional.empty());
        underTest.publish(NICK_NAME, TITLE, CONTENT);
    }

    @Test(expected = ArticleAlreadyExistsException.class)
    public void publishFailsForDuplicateId() throws Exception {
        when(authorPersistenceAdapterMock.findById(NICK_NAME))
                .thenReturn(Optional.of(AuthorBuilder.defaultAuthor().build()));
        when(idNormalizerMock.normalizeTitle(TITLE)).thenReturn(ARTICLE_ID);
        when(articlePersistenceAdapterMock.findById(ARTICLE_ID))
                .thenReturn(ArticleBuilder.defaultArticle().build());
        underTest.publish(NICK_NAME, TITLE, CONTENT);
    }

    @Test
    public void editSucceeds() throws Exception {
        Article article = ArticleBuilder.defaultArticle().build();
        when(articlePersistenceAdapterMock.findById(ARTICLE_ID)).thenReturn(article);

        Article result = underTest.edit(ARTICLE_ID, NICK_NAME, NEW_TITLE, NEW_CONTENT);

        verify(articlePersistenceAdapterMock).update(articleCaptor.capture());
        Article updated = articleCaptor.getValue();
        assertThat("Title", updated.getTitle(), is(NEW_TITLE));
        assertThat("Content", updated.getContent(), is(NEW_CONTENT));
        assertThat("Article", result, sameInstance(updated));
    }

    @Test(expected = WrongAuthorException.class)
    public void editFailsForWrongNickName() throws Exception {
        Article article = ArticleBuilder.defaultArticle().build();
        when(articlePersistenceAdapterMock.findById(ARTICLE_ID)).thenReturn(article);
        underTest.edit(ARTICLE_ID, "intruder", NEW_TITLE, NEW_CONTENT);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void editFailsForUnknownId() throws Exception {
        when(articlePersistenceAdapterMock.findById(ARTICLE_ID)).thenReturn(null);
        underTest.edit(ARTICLE_ID, NICK_NAME, NEW_TITLE, NEW_CONTENT);
    }
}