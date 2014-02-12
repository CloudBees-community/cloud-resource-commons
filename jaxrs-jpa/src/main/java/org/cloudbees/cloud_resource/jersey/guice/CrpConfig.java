package org.cloudbees.cloud_resource.jersey.guice;

/**
 *
 * Guice injectable Configuration object for Cloud Resource Provider
 *
 * A CRP implementation should bind it to actual implementation class.
 *
 * This example shows how you can bind a Github CRP config object to CrpConfig with JerseyServletModule
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
 *                  bind(CrpConfig.class).to(GithubConfig.class);
 *              }
 *          }
 *      }
 * }
 * }
 *
 * @author Vivek Pandey
 */
public abstract class CrpConfig {
    /**
     * OAuth application clientId
     */
    public abstract String getClientId();

    /**
     * OAuth application clientSecret
     */
    public abstract String getClientSecret();

    /**
     * Get Host URL where the CRP is running name, in the format host[:port], for example example.com or example.com:443
     *
     * This method must return non-null value if it enforces Cloud Resource capability check in during authentication
     */
    public abstract String getHostURL();
}