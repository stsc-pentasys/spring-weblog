package workshop.microservices.weblog.core;

/**
 * Represents forbidden access by another author.
 */
public class WrongAuthorException extends BlogServiceException {

    public WrongAuthorException(String message) {
        super(message);
    }

}
