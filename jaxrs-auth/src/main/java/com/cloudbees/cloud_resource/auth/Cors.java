package com.cloudbees.cloud_resource.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables CORS handling.
 * <br/>
 *
 * For more information see http://tools.ietf.org/html/rfc6454
 *
 * <br/>
 *
 * To enable CORS handling on existing JAX-RS resource, simply annotate it with @Cors. Two things happen:
 *
 * <ul>HTTP Option method handling</ul>
 * <ul>Response to carry CORS headers</ul>
 *
 * @author Vivek Pandey
 *
 * @see com.cloudbees.cloud_resource.auth.jersey.CorsFilter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Cors {
    /**
     * Enable CORS handling
     */
    boolean enable() default true;

    /**
     * Allowed Origins. Comma separated list of allowed origins:
     *
     * <pre>
     * {@code
     *
     *  public class Resource{
     *    @literal@Cors({"https://example.com", "https://acme.com"})
     *    @literal@Secure
     *    @literal@GET
     *    public User getUser(){
     *      ...
     *    }
     * }
     * }
     *
     *  Default is "*" - any origin is allowed to access the resource
     */
    String allowOrigin() default "*";



    String exposeHeaders() default "";



}
