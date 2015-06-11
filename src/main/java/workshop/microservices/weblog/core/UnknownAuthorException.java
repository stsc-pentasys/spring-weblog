package workshop.microservices.weblog.core;

/**
 * Represents missing or invalid login "credentials".
 */
public class UnknownAuthorException extends BlogServiceException {
    public UnknownAuthorException(String message) {
        super(message);
    }
}
