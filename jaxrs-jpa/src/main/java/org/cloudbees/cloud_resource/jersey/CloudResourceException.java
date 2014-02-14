package org.cloudbees.cloud_resource.jersey;

import com.cloudbees.cloud_resource.types.CloudResourceError;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Vivek Pandey
 */
public class CloudResourceException extends WebApplicationException {

    public CloudResourceException(int httpStatus, String message) {
        super(Response.status(httpStatus).type(MediaType.APPLICATION_JSON_TYPE).header("Cloud-Resource-Type", "https://types.cloudbees.com/error").entity(new CloudResourceError(message)).build());
    }

    public CloudResourceException(int httpStatus, CloudResourceError error) {
        super(Response.status(httpStatus).type(MediaType.APPLICATION_JSON_TYPE).header("Cloud-Resource-Type","https://types.cloudbees.com/error").entity(error).build());
    }
}
