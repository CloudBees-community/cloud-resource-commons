package com.cloudbees.cloud_resource.auth.guice;

import com.cloudbees.cloud_resource.auth.jersey.Authenticator;
import com.cloudbees.cloud_resource.auth.jersey.OauthAuthenticator;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author Vivek Pandey
 */
public class CloudbeesAuthModule extends AbstractModule {

    private boolean enable;

    public CloudbeesAuthModule(boolean enable) {
        this.enable = enable;
    }

    public CloudbeesAuthModule() {
        this.enable = true;
    }

    @Override
    protected void configure() {
        bindListener(Matchers.any(), new Slf4jTypeListener());
        bind(Authenticator.class).toProvider(OauthAuthenticator.class);
    }
}
