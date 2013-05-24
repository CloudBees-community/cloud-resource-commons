package com.cloudbees.cloud_resource.types;

/**
 * @author Vivek Pandey
 */
@CloudResourceType("https://types.cloudbees.com/resource")
public interface CloudResource {

    public static final String CONTENT_TYPE = "application/vnd.cloudbees.resource+json";
}
