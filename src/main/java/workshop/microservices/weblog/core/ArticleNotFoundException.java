package workshop.microservices.weblog.core;

/**
 * Thrown if referenced article is missing.
 */
public class ArticleNotFoundException extends BlogServiceException {

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
