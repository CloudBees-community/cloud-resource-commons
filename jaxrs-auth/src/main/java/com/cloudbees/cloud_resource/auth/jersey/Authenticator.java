package com.cloudbees.cloud_resource.auth.jersey;

import com.cloudbees.cloud_resource.auth.Secure;
import com.sun.jersey.spi.container.ContainerRequest;

/**
 *
 * @author Vivek Pandey
 */
public interface Authenticator {
    /**
     *  Authenticate incoming request.
     *
     *  @throws com.cloudbees.cloud_resource.auth.AuthException if authentication fails
     */
    ContainerRequest authenticate(ContainerRequest request, Secure secureAnnotation);
}
