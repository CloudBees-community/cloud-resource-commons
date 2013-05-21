package com.cloudbees.cloud_resource.types;

/**
 * @author Vivek Pandey
 */

@CloudResourceType("https://types.cloudbees.com/binding/edge")
public interface BindingEdge extends CloudResource{
    Edge edge();
}
