package com.cloudbees.cloud_resource.auth.jersey;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements HSTS spec as defined in RFC-6797, http://tools.ietf.org/html/rfc6797
 *
 * @author: Vivek Pandey
 */
public class HstsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private final HstsConfig config;

    public HstsFilter(@Context ResourceConfig resourceConfig) {
        Map<String,Object> props = resourceConfig.getProperties();
        this.config = new HstsConfig(props);
    }

    @Override
    public ContainerRequest filter(ContainerRequest request) {
        /* un-secure http://... request */
        if(!request.isSecure()){
            URI location = request.getRequestUriBuilder().scheme("https").build();
            throw new WebApplicationException(Response.status(301).location(location).build());
        }
        return request;
    }

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        MultivaluedMap<String,Object> headers = response.getHttpHeaders();
        List<Object> hstsValues = new ArrayList<Object>();
        hstsValues.add(String.format("%s=%s",Headers.MAX_AGE, config.maxAge));
        if(config.includeSubDomains){
            hstsValues.add(Headers.INCLUDE_SUB_DOMAINS);
        }
        headers.put(Headers.HSTS, hstsValues);
        return response;
    }

    public static final class Headers{
        public static final String HSTS = "Strict-Transport-Security";
        public static final String MAX_AGE = "max-age";
        public static final String INCLUDE_SUB_DOMAINS = "includeSubDomains";

    }
}
