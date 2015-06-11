package workshop.microservices.weblog.core;

/**
 * Thrown on an unspecified service failure.
 */
public class BlogServiceException extends Exception {

    public BlogServiceException(String message) {
        super(message);
    }

}
