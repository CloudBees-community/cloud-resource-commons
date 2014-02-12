package org.cloudbees.cloud_resource.jersey;

import org.codehaus.jackson.JsonParseException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

/**
 * Processes errors and returns it as {@link com.cloudbees.cloud_resource.types.CloudResourceError} JSON response
 *
 * @author Vivek Pandey
 */
@Provider
public class CloudResourceExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if(e instanceof WebApplicationException){
            return ((WebApplicationException)e).getResponse();
        }else if(e instanceof JsonParseException){
            return Response.status(400)
                    .entity(Collections.singletonMap("error", "Error parsing JSON: " + e.getMessage())).build();
        }
        return Response.status(500).entity(Collections.singletonMap("error", "Unexpected error" + e.getMessage())).build();
    }
}
