package com.cloudbees.cloud_resource.types;

import java.util.Map;

/**
 * Represents edge of the binding.
 *
 * @author Vivek Pandey
 */
public interface Edge {

    /**
     * URL of the Edge
     */
    public String url();

    /**
     * the source
     */
    public String source();

    /**
     * Sink of the binding
     */
    public String sink();

    /**
     * binding label
     */
    public String label();

    /**
     * Optional settings associated with the binding
     */
    public Map settings();
}
