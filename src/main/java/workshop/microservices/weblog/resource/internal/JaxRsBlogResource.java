package workshop.microservices.weblog.resource.internal;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import workshop.microservices.weblog.core.ArticleNotFoundException;
import workshop.microservices.weblog.resource.ArticleListResponse;
import workshop.microservices.weblog.resource.ArticleRequest;
import workshop.microservices.weblog.resource.ArticleResponse;
import workshop.microservices.weblog.resource.BlogResource;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.core.ArticleAlreadyExistsException;
import workshop.microservices.weblog.core.BlogService;
import workshop.microservices.weblog.core.BlogServiceException;
import workshop.microservices.weblog.core.UnknownAuthorException;
import workshop.microservices.weblog.core.WrongAuthorException;

/**
 * REST API implementation.
 */
@Component
public class JaxRsBlogResource implements BlogResource {

    // NOT on setter Method - not supported by Jersey/Spring integration
    @Autowired
    private BlogService blogService;

    @Context
    private UriInfo uriInfo;

    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @Override
    public Response getAll() {
        try {
            List<Article> articles = blogService.index();
            List<ArticleListResponse> views = createViewList(articles);
            return Response.ok(views).build();
        } catch (BlogServiceException e) {
            throw new TechnicalException();
        }
    }

    private List<ArticleListResponse> createViewList(List<Article> articles) {
        return articles.stream()
                .map(this::createArticleListResponse)
                .collect(Collectors.toList());
    }

    private ArticleListResponse createArticleListResponse(Article a) {
        return new ArticleListResponse(
                a.getTitle(),
                a.getCreated(),
                createUri(a.getArticleId()).toASCIIString());
    }

    private URI createUri(String articleId) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        return builder.path(articleId).build();
    }

    @Override
    public Response getOne(String entryId) {
        try {
            Article entry = blogService.read(entryId);
            return Response.ok(createBlogEntryView(entry)).build();
        } catch (ArticleNotFoundException e) {
            throw new UnknownArticleIdException(entryId);
        } catch (BlogServiceException e) {
            throw new TechnicalException();
        }
    }

    private ArticleResponse createBlogEntryView(Article entry) {
        return new ArticleResponse(
                entry.getArticleId(),
                entry.getTitle(),
                entry.getContent(),
                entry.getPublishedBy().getFullName(),
                entry.getPublishedBy().getEmailAddress(),
                entry.getCreated()
        );
    }

    @Override
    public Response postNew(ArticleRequest request) {
        try {
            ResponseBuilder response = postArticleResponse(request);
            return response.build();
        } catch (UnknownAuthorException e) {
            throw new NoAccessAllowedException(request.getNickName());
        } catch (ArticleAlreadyExistsException e) {
            throw new ArticleIdAlreadyInUseException(request.getTitle());
        } catch (BlogServiceException e) {
            throw new TechnicalException();
        }
    }

    private ResponseBuilder postArticleResponse(ArticleRequest request) throws BlogServiceException {
        Article entry = blogService.publish(
                request.getNickName(),
                request.getTitle(),
                request.getContent()
        );
        return Response.created(createUri(entry.getArticleId()));
    }

    @Override
    public Response putExisting(String entryId, ArticleRequest request) {
        try {
            ResponseBuilder response = Response.ok(putArticleResponse(entryId, request));
            return response.build();
        } catch (ArticleNotFoundException e) {
            throw new UnknownArticleIdException(entryId);
        } catch (WrongAuthorException e) {
            throw new NoAccessAllowedException(request.getNickName());
        } catch (BlogServiceException e) {
            throw new TechnicalException();
        }
    }

    private ArticleResponse putArticleResponse(String entryId, ArticleRequest request) throws BlogServiceException {
        Article article = blogService.edit(
                entryId,
                request.getNickName(),
                request.getTitle(),
                request.getContent()
        );
        return createBlogEntryView(article);
    }

}
