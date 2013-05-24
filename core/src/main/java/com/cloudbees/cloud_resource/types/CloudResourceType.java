package com.cloudbees.cloud_resource.types;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Type this cloud resource represents
 *
 * @author Vivek Pandey
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CloudResourceType {

    /**
     * Gives string representation of URL
     */
    String value();
}

