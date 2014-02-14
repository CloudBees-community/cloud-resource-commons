package org.cloudbees.cloud_resource.jersey.guice;

import com.cloudbees.cloud_resource.auth.guice.CloudbeesAuthModule;
import com.google.inject.AbstractModule;
import org.cloudbees.cloud_resource.jersey.CloudResourceExceptionMapper;

/**
 * @author Vivek Pandey
 */
public class CloudResourceModule extends AbstractModule{
    private final CloudbeesAuthModule authModule;


    public CloudResourceModule(CloudbeesAuthModule authModule) {
        this.authModule = authModule;
    }

    public CloudResourceModule() {
        this.authModule = new CloudbeesAuthModule();
    }

    @Override
    protected void configure() {
        install(authModule);
        bind(CloudResourceExceptionMapper.class).asEagerSingleton();
    }
}
