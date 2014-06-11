package com.cloudbees.cloud_resource.auth.jersey;

import com.cloudbees.cloud_resource.auth.AuthException;
import com.cloudbees.cloud_resource.auth.Secure;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Vivek Pandey
 */
public class OauthFilter implements ResourceFilter, ContainerRequestFilter {
    private final Secure secureAnnotation;
    private  final AbstractMethod abstractMethod;
    private final Authenticator authenticator;


    public OauthFilter(Secure secureAnnotation, Authenticator authenticator, AbstractMethod abstractMethod) {
        this.secureAnnotation = secureAnnotation;
        this.abstractMethod = abstractMethod;
        this.authenticator = authenticator;
    }

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) {
        Secure.Authenticator secureAuthenticator = secureAnnotation.with();
        if (secureAuthenticator == Secure.Authenticator.OAUTH) {
            return authenticator.authenticate(containerRequest, secureAnnotation);
        } else {
            logger.error(String.format("Invalid authenticator  %s for method %s", authenticator, containerRequest.getPath()));
            throw new AuthException(500, "Invalid authenticator: " + authenticator);
        }
    }

    @Override
    public ContainerRequestFilter getRequestFilter() {
        return this;
    }

    @Override
    public ContainerResponseFilter getResponseFilter() {
        return null;
    }

    private static final Logger logger = LoggerFactory.getLogger(OauthFilter.class);
}