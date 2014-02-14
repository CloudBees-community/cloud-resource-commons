package com.cloudbees.cloud_resource.auth.jersey;

import com.cloudbees.cloud_resource.auth.AuthException;
import com.cloudbees.cloud_resource.auth.Secure;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 *
 * JAX-RS Jersey resource filter. This is where {@link Secure} annotation is processed and authentication is enforced.
 *
 * To include this in your Jersey resource filter chain do the following
 *
 * <pre>
 * {@code
 * public class Main extends GuiceServletContextListener {
 *      @Override
 *      protected Injector getInjector() {
 *          return Guice.createInjector(
 *              new JerseyServletModule() {
 *                  @Override
 *                  protected void configureServlets() {
 *                      Map<String, String> params = new HashMap<String, String>();
 *                      params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES, AuthResourceFilter.class.getName());
 *                      params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
 *                      serve("/*").with(GuiceContainer.class, params);
 *                  }
 *             }
 *         );
 *     }
 * }
 * }
 *
 * @author Vivek Pandey
 */
@Singleton
public class AuthResourceFilter implements ResourceFilterFactory {

    private static final Logger logger = LoggerFactory.getLogger(AuthResourceFilter.class);


    @Inject
    private OauthAuthenticator oauthAuthenticator;

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {
        Secure secureAnnotation = am.getResource().getAnnotation(Secure.class);

        // Secure annotation at the method takes precedence over the class
        if (am.isAnnotationPresent(Secure.class)) {
            secureAnnotation = am.getAnnotation(Secure.class);
        }

        return Collections.<ResourceFilter>singletonList(new Filter(secureAnnotation, am));
    }

    private class Filter implements ResourceFilter, ContainerRequestFilter {
        private final  Secure secureAnnotation;
        private  final AbstractMethod abstractMethod;


        private Filter(Secure secureAnnotation, AbstractMethod abstractMethod) {
            this.secureAnnotation = secureAnnotation;
            this.abstractMethod = abstractMethod;
        }

        @Override
        public ContainerRequest filter(ContainerRequest containerRequest) {
            Secure.Authenticator authenticator = secureAnnotation.with();
            if (authenticator == Secure.Authenticator.OAUTH) {
                return oauthAuthenticator.authenticate(containerRequest, secureAnnotation);
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


    }
}