package org.cloudbees.cloud_resource.jersey;

import java.lang.String;import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define security policy on any JAX-RS resource
 *
 * @author Vivek Pandey
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Secure {
    /**
     * Comma separated authenticators telling what authentication methods to use.
     */
    Authenticator with() default Authenticator.OAUTH;

    /**
     * Controls whether a resource is secured. Default true.
     */
    boolean enable() default true;

    /**
     * OAuth scopes. At least one of the scope must be present with the OAuth token
     */
    String[] scopes() default {};

    /**
     * If true all scopes (including the ones derived from capabilities) will be validated.
     * If false, oauth token will be inspected to carry at least one of the scope
     */
    boolean validateAllScopes() default true;

    /**
     * Cloud Resource capabilities. One of the capability must be present
     */
    String[] capabilities() default {};


    public enum Authenticator {OAUTH}
}