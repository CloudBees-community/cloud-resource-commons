package org.cloudbees.cloud_resource.jersey;

import com.cloudbees.cloud_resource.types.CloudResourceError;
import org.codehaus.jackson.JsonParseException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Processes errors and returns it as {@link com.cloudbees.cloud_resource.types.CloudResourceError} JSON response
 *
 * @author Vivek Pandey
 */
@Provider
public class CloudResourceExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof CloudResourceException) {
            return ((CloudResourceException) e).getResponse();
        } else if (e instanceof JsonParseException) {
            return Response.status(400)
                    .entity(new CloudResourceError("Error parsing JSON: " + e.getMessage())).build();
        } else if (e instanceof WebApplicationException) {
            if(((WebApplicationException) e).getResponse().getEntity() != null && ((WebApplicationException) e).getResponse().getEntity() instanceof CloudResourceError){
                return ((WebApplicationException) e).getResponse();
            }else{
                return Response.status(((WebApplicationException) e).getResponse().getStatus())
                        .entity(((WebApplicationException) e).getResponse().getEntity()).build();
            }
        }
        return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).header("Cloud-Resource-Type","https://types.cloudbees.com/error").entity(new CloudResourceError("Unexpected error" + e.getMessage())).build();
    }
}
