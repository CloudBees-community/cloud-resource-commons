This is CloudBees JAX-RS Jersey based OAuth authentication Guice Module.

## Use

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

                        params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");

                        serve("/*").with(GuiceContainer.class, params);
                    }
                }
        );
    }
}
```
