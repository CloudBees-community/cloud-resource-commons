package org.cloudbees.cloud_resource.jersey.guice;

/**
 *
 * Guice injectable Configuration object for Cloud Resource Provider
 *
 * A CRP implementation should bind it to actual implementation class.
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
 *                  bind(OauthConfig.class).to(GithubOauthConfig.class);
 *              }
 *          }
 *      }
 * }
 * }
 *
 * @author Vivek Pandey
 */
public abstract class OauthConfig {
    /**
     * OAuth application clientId
     */
    public abstract String getClientId();

    /**
     * OAuth application clientSecret
     */
    public abstract String getClientSecret();
}