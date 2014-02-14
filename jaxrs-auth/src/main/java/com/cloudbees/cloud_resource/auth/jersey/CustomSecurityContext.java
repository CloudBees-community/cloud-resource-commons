package com.cloudbees.cloud_resource.auth.jersey;

import com.cloudbees.cloud_resource.auth.CloudbeesPrincipal;
import com.cloudbees.cloud_resource.auth.Principal;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Type;

/**
 * 
 * A JAX-RS Jersey Injectible provider to inject {@link CloudbeesPrincipal}
 * 
 * @author Vivek Pandey
 */
@Provider
public class CustomSecurityContext implements InjectableProvider<Principal, Type> {

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    @Override
    public Injectable getInjectable(ComponentContext ic, Principal Principal, Type type) {
        if (CloudbeesPrincipal.class.equals(type)) {
            return new AbstractHttpContextInjectable<CloudbeesPrincipal>() {
                @Context
                private SecurityContext securityContext;


                @Override
                public CloudbeesPrincipal getValue() {
                    return (CloudbeesPrincipal)securityContext.getUserPrincipal();
                }

                @Override
                public CloudbeesPrincipal getValue(HttpContext c) {
                    return (CloudbeesPrincipal) c.getRequest().getUserPrincipal();
                }
            };
        } else {
            return null;
        }
    }
}
