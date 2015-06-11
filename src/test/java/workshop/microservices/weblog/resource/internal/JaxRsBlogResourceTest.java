package workshop.microservices.weblog.resource.internal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static workshop.microservices.weblog.TestData.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.microservices.weblog.core.ArticleNotFoundException;
import workshop.microservices.weblog.resource.ArticleListResponse;
import workshop.microservices.weblog.resource.ArticleRequest;
import workshop.microservices.weblog.resource.ArticleResponse;
import workshop.microservices.weblog.resource.BlogResource;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.core.ArticleAlreadyExistsException;
import workshop.microservices.weblog.core.ArticleBuilder;
import workshop.microservices.weblog.core.BlogService;
import workshop.microservices.weblog.core.BlogServiceException;
import workshop.microservices.weblog.core.UnknownAuthorException;

@RunWith(MockitoJUnitRunner.class)
public class JaxRsBlogResourceTest {

    public static final String NEW_TITLE = "New title";
    public static final String NEW_CONTENT = "New content";
    @InjectMocks
    private BlogResource underTest = new JaxRsBlogResource();
    
    @Mock
    private BlogService blogServiceMock;

    @Mock
    private UriInfo uriInfoMock;

    @Mock
    private UriBuilder uriBuilderMock;

    @Test
    public void getOneReturnsOk() throws Exception {
        Article article = ArticleBuilder.defaultArticle().build();
        when(blogServiceMock.read(ARTICLE_ID)).thenReturn(article);
        Response response = getOne();
        assertStatus(response, Status.OK);
        assertThat("Response body", ((ArticleResponse) response.getEntity()).getArticleId(), is(article.getArticleId()));
    }

    private Response getOne() {
        return underTest.getOne(ARTICLE_ID);
    }

    private void assertStatus(Response response, StatusType status) {
        assertThat("HTTP status code", response.getStatusInfo(), is(status));
    }

    @Test(expected = UnknownArticleIdException.class)
    public void getOneReturnsNotFound() throws Exception {
        onGetArticleThrow(new ArticleNotFoundException("Not found!"));
        getOne();
    }

    private void onGetArticleThrow(Exception exception) throws BlogServiceException {
        when(blogServiceMock.read(ARTICLE_ID)).thenThrow(exception);
    }

    @Test
    public void postNewReturnsCreated() throws Exception {
        when(blogServiceMock.publish(NICK_NAME, TITLE, CONTENT))
                .thenReturn(ArticleBuilder.defaultArticle().build());
        expectURIConstruction();
        Response result = postNew();
        assertStatus(result, Status.CREATED);
        assertThat("Location header", result.getLocation().toString(), is("/entries/for-testing-purposes-only"));
    }

    private void expectURIConstruction() throws URISyntaxException {
        when(uriInfoMock.getAbsolutePathBuilder()).thenReturn(uriBuilderMock);
        when(uriBuilderMock.path(ARTICLE_ID)).thenReturn(uriBuilderMock);
        when(uriBuilderMock.build()).thenReturn(new URI("/entries/for-testing-purposes-only"));
    }

    private Response postNew() {
        return underTest.postNew(createArticleRequest());
    }

    private ArticleRequest createArticleRequest() {
        return new ArticleRequest(NICK_NAME, TITLE, CONTENT);
    }

    @Test(expected = NoAccessAllowedException.class)
    public void postReturnsReturnsForbidden() throws Exception {
        onPostNewThrow(new UnknownAuthorException("Test"));
        postNew();
    }

    private void onPostNewThrow(Exception exception) throws BlogServiceException {
        when(blogServiceMock.publish(NICK_NAME, TITLE, CONTENT))
                .thenThrow(exception);
    }

    @Test(expected = ArticleIdAlreadyInUseException.class)
    public void postNewReturnsConflict() throws Exception {
        onPostNewThrow(new ArticleAlreadyExistsException("Test"));
        postNew();
    }

    @Test
    public void getAllReturnsOkWithList() throws Exception {
        List<Article> articles = Arrays.asList(ArticleBuilder.defaultArticle().build());
        when(blogServiceMock.index()).thenReturn(articles);
        expectURIConstruction();
        Response result = underTest.getAll();
        assertStatus(result, Status.OK );
        assertThat("List size", ((List< ArticleListResponse>) result.getEntity()).size(), is(1));
    }

    @Test
    public void getAllReturnsOkWithEmptyList() throws Exception {
        List<Article> articles = Collections.emptyList();
        when(blogServiceMock.index()).thenReturn(articles);
        Response result = underTest.getAll();
        assertStatus(result, Status.OK);
        assertThat("List size", ((List<ArticleListResponse>) result.getEntity()).size(), is(0));
        verifyZeroInteractions(uriInfoMock);
    }

    @Test
    public void putExistingReturnsOk() throws Exception {
        Article article = ArticleBuilder.defaultArticle().withTitle(NEW_TITLE).andContent(NEW_CONTENT).build();
        when(blogServiceMock.edit(article.getArticleId(), NICK_NAME, NEW_TITLE, NEW_CONTENT))
                .thenReturn(article);
        Response result = underTest.putExisting(ARTICLE_ID, new ArticleRequest(NICK_NAME, NEW_TITLE, NEW_CONTENT));
        assertStatus(result, Status.OK);
        ArticleResponse response = (ArticleResponse) result.getEntity();
        assertThat("Title", response.getTitle(), is(NEW_TITLE));
    }
}
