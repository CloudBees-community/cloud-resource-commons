package org.cloudbees.cloud_resource.jersey;

import com.cloudbees.cloud_resource.types.CloudResourceProvider;
import com.cloudbees.cloud_resource.types.ReferencedResource;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Partial default implementation of {@link CloudResourceProvider}.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class CloudResourceProviderSupport extends CloudResourceSupport implements CloudResourceProvider {
    @JsonProperty
    public abstract List<ReferencedResource> edges();
}
