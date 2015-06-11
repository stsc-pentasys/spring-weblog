package workshop.microservices.weblog.resource.internal;

import static javax.ws.rs.core.Response.*;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import workshop.microservices.weblog.resource.internal.ErrorMessage;

/**
 * Created by schulzst on 26.05.2015.
 */
public class BlogResourceException extends WebApplicationException {

    public BlogResourceException(Status status, ErrorMessage errorMessage) {
        super(Response.status(status).entity(errorMessage).build());
    }
}
