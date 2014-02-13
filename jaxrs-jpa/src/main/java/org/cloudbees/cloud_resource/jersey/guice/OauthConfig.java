package org.cloudbees.cloud_resource.jersey.guice;

import com.google.inject.Singleton;

/**
 *
 * Guice injectable OAuth Configuration object
 *
 * A CRP implementation should bind instance of it
 *
 * This example shows how you can bind a Github CRP config object to OauthConfig with JerseyServletModule
 *
 * <pre>
 * {@code
 * public class GuiceServletConfig extends GuiceServletContextListener {
 *
 *      @Override
 *      protected Injector getInjector() {
 *          return Guice.createInjector(new JerseyServletModule() {
 *
 *              @Override
 *              protected void configureServlets() {
 *                  bind(new OauthConfig(clientId,clientSecret));
 *              }
 *          }
 *      }
 * }
 * }
 *
 * @author Vivek Pandey
 */
@Singleton
public  class OauthConfig {

    private final String clientId;

    private final String clientSecret;

    public OauthConfig(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * OAuth application clientId
     */
    public  String getClientId(){
        return clientId;
    }

    /**
     * OAuth application clientSecret
     */
    public String getClientSecret(){
        return clientSecret;
    }
}