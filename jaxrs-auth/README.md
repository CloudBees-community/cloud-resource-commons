This is CloudBees JAX-RS Jersey and Guice based library. It provides support for **OAuth**, **CORS** and **HSTS** features.

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

    //Restrict only to requests from *.cloudbees.com
    params.put(CorsConfig.ALLOW_ORIGIN, "*.cloudbees.com");

    //only GET and POST methods support CORS
    params.put(CorsConfig.ALLOW_METHODS, "GET POST");

    //allow customer header X-FOO and X-BAR on the post-flight request
    params.put(CorsConfig.ALLOW_HEADERS, "X-FOO,X-BAR");

    //Tells UA how long it can cache the preflight response
    params.put(CorsConfig.MAX_AGE, 3600);

    //White listed headers that browser can see
    params.put(CorsConfig.EXPOSE_HEADERS, "X-My-Custom-Header1 X-My-Custom-Header2");
```

There are other configurable CORS parameters, please see CorsConfig class for more details.

## HSTS support

To enable HSTS support add HstsFilter to Jersey request and response filter chain.

```java

    params.put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                                    HstsFilter.class.getName());
    params.put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
                                    HstsFilter.class.getName());
```

You can configure max-age and includeSubDomains argument values. Default value for max-age is 1 year and
includeSubDomains is false.

```java
     params.put(HstsConfig.MAX_AGE, 31536000); //1year
     params.put(HstsConfig.INCLUDE_SUB_DOMAINS, false);
```