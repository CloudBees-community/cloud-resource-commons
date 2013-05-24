package com.cloudbees.cloud_resource.types;

/**
 * @author Vivek Pandey
 */
public enum Capability {
    READ("https://types.cloudbees.com/resource/read"),
    BIND("https://types.cloudbees.com/binding/bind");

    private final String capability;
    private Capability(String value) {
        this.capability = value;
    }

    /**
     * Gives the Cloud Resource Oauth scope for the given destination domain.
     *
     * @param destination domain of the target, for example: acme.example.com
     *
     * @return String representation of crs URI, for example, crs://acme.example.com!https://types.cloudbees.com/resource/read
     */
    public String oauthScope(String destination){
        return "crs://"+destination+"!"+capability;
    }
}
