package workshop.microservices.weblog.resource.internal;

import javax.ws.rs.core.Response;

/**
 * Created by schulzst on 26.05.2015.
 */
public class UnknownArticleIdException extends BlogResourceException {
    public UnknownArticleIdException(String articleId) {
        super(Response.Status.NOT_FOUND,
                new ErrorMessage(ErrorCode.UNKNOWN_ARTICLE_ID, "No article with id " + articleId + " found"));
    }
}
