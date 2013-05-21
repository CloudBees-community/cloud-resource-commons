package com.cloudbees.cloud_resource.types;

import java.util.Collections;
import java.util.List;

/**
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

    public ReferencedResource(String url, List<String> types) {
        this.url = url;
        this.types = types;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getTypes() {
        return Collections.unmodifiableList(types);
    }

}
