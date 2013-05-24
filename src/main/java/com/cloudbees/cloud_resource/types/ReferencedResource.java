package com.cloudbees.cloud_resource.types;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final List<String> types;

    public ReferencedResource(String url, List<CloudResourceType> types) {
        this.url = url;
        this.types = new ArrayList<String>();
        for (CloudResourceType t : types) {
            this.types.add(t.value());
        }
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("types")
    public List<String> getTypes() {
        return Collections.unmodifiableList(types);
    }

}
