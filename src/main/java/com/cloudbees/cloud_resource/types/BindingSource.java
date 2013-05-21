package com.cloudbees.cloud_resource.types;

/**
 *
 *
 * @author Vivek Pandey
 */
@CloudResourceType("https://types.cloudbees.com/binding/source")
public interface BindingSource extends CloudResource{
    String bindingCollectionUrl();
}
