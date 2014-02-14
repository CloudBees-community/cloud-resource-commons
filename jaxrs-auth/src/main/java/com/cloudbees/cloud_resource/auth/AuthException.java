package com.cloudbees.cloud_resource.auth;

import com.cloudbees.cloud_resource.types.CloudResourceError;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Vivek Pandey
 */
public class AuthException extends WebApplicationException {

    public AuthException(int httpStatus, String message) {
        super(Response.status(httpStatus).type(MediaType.APPLICATION_JSON_TYPE).entity(new CloudResourceError(message)).build());
    }
}