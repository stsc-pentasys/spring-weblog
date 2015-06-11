package workshop.microservices.weblog.resource.internal;

import static javax.ws.rs.core.Response.*;

/**
 * Created by schulzst on 26.05.2015.
 */
public class ArticleIdAlreadyInUseException extends BlogResourceException {
    public ArticleIdAlreadyInUseException(String articleId) {
        super(Status.CONFLICT,
                new ErrorMessage(ErrorCode.ARTICLE_ID_ALREADY_IN_USE, "Conflicting article id " + articleId));
    }
}
