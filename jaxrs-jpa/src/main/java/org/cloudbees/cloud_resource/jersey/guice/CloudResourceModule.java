package org.cloudbees.cloud_resource.jersey.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.cloudbees.cloud_resource.jersey.CloudResourceExceptionMapper;

/**
 * @author Vivek Pandey
 */
public class CloudResourceModule extends AbstractModule{
    @Override
    protected void configure() {
        bindListener(Matchers.any(), new Slf4jTypeListener());
        bind(CloudResourceExceptionMapper.class).asEagerSingleton();

    }
}
