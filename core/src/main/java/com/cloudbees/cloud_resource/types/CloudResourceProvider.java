package com.cloudbees.cloud_resource.types;

import java.util.List;

/**
 * @author Vivek Pandey
 */
@CloudResourceType("https://types.cloudbees.com/resource/provider")
public interface CloudResourceProvider extends CloudResource{
    public List<ReferencedResource> resources();

}
