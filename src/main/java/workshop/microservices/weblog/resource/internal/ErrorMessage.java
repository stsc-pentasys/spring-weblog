package workshop.microservices.weblog.resource.internal;

/**
 * Wrapper for error messages to prevent stack traces on REST API calls.
 */
public class ErrorMessage {

    private final String message;

    private final ErrorCode errorCode;

    public ErrorMessage(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
