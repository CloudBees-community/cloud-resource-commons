package com.cloudbees.cloud_resource.types;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Kohsuke Kawaguchi
 * @author Vivek Pandey
 */
public class ReferencedResource {
    /**
     * URL of Cloud Resource
     */
    private final String url;

    /**
     * Resource types implemented by the cloud resource represented by the url
     */
    private final Collection<String> types;

    public ReferencedResource(String url, Collection<String> types) {
        this.url = url;
        this.types = new ArrayList<String>(types);
    }

    public ReferencedResource(String url, Class<?> type) {
        this.url = url;
        this.types = CloudResourceTypes.of(type);
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("types")
    public Collection<String> getTypes() {
        return Collections.unmodifiableCollection(types);
    }

}
