package workshop.microservices.weblog.resource.internal;

import javax.ws.rs.core.Response;

/**
 * Created by schulzst on 26.05.2015.
 */
public class NoAccessAllowedException extends BlogResourceException {
    public NoAccessAllowedException(String userId) {
        super(Response.Status.FORBIDDEN,
                new ErrorMessage(ErrorCode.NO_ACCESS_ALLOWED, "No access for userId: " + userId));
    }
}
