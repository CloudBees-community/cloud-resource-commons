This is CloudBees JAX-RS Jersey based OAuth authentication Guice Module and Cors response filter to automatically add
support for Cors header in the response.

## OAuth support

### Annotate your JAX-RS resources with @Secure annotation

```java
@Path("{repo}")
@Secure(capabilities={"https://types.cloudbees.com/resource/read"}, scopes={...})
public GitHubRepository getRepository(@PathParam("repo") String repo) throws IOException {
    ...
}
```

### Add CloudbeesAuthModule to Guice Injector

> You must provide implementation of **OauthConfig** as injectible object to provide OAuth clientId and clientSecret.


```java

        public class GuiceConfig extends GuiceServletContextListener {
            @Override
            protected Injector getInjector() {
                return Guice.createInjector(
                        new CloudbeesAuthModule(),
                        new JerseyServletModule() {
                            @Override
                            protected void configureServlets() {

                                /** Allows injection of config object in to {@link CloudbeesAuthModule} module **/
                                bind(OauthConfig.class).to(GithubOauthConfig.class);

                                Map<String, String> params = new HashMap<String, String>();

                                /**
                                 * Enable security via {@link AuthResourceFilter}
                                 */
                                params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                                        AuthResourceFilter.class.getName());


                                /**
                                 * Enable CORS by adding CorsFilter to PROPERTY_CONTAINER_RESPONSE_FILTERS,
                                 */
                                params.put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
                                        CorsFilter.class.getName());

                                /** restrict Cors for *.cloudbees.com domain */
                                params.put(CorsConfig.ALLOW_ORIGIN, "*.cloudbees.com");

                                /** Disable WADL to avoid wadl in the response */
                                params.put(ResourceConfig.FEATURE_DISABLE_WADL, "true");

                                params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");

                                serve("/*").with(GuiceContainer.class, params);
                            }
                        }
                );
            }
        }
```
## Cors support

CORS support is enabled by Jersey ResourceResponseFilter implementation. To enable it:

```java
    params.put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
                                    CorsFilter.class.getName());
```

By default it allows all origins. To restrict your app to specific origins you can set **CorsConfig.ALLOW_ORIGIN**
to "*.example.com" or "example.com" or "*".

```java
    params.put(CorsConfig.ALLOW_ORIGIN, "*.cloudbees.com");
```
