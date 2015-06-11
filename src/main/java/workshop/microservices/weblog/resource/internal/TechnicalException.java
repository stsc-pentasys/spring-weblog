package workshop.microservices.weblog.resource.internal;

import javax.ws.rs.core.Response;

/**
 * Created by schulzst on 26.05.2015.
 */
public class TechnicalException extends BlogResourceException {

    public TechnicalException() {
        super(Response.Status.INTERNAL_SERVER_ERROR,
                new ErrorMessage(ErrorCode.TECHNICAL_ERROR, "Internal error, inform admin!"));
    }
}
