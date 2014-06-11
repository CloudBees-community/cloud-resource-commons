package com.cloudbees.cloud_resource.auth.jersey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Vivek Pandey
 */
public class CorsConfig {

    private static final String PREFIX = "com.cloudbees.cloud_resource.auth.cors.";

    /**
     * Expiration of pre-flight response caching in seconds as java.lang.Integer. Default is 24 hours.
     */
    public static final String MAX_AGE = PREFIX + "maxAge";

    /**
     * Comma-separated list of HTTP methods. The default is "GET, POST,PUT,DELETE,OPTIONS".
     */
    public static final String ALLOW_METHODS = PREFIX + "allowMethods";

    /**
     * Comma-separated list of HTTP headers. The default is ""
     */
    public static final String ALLOW_HEADERS = PREFIX + "allowHeaders";

    /**
     * A boolean, the default is false.
     */
    public static final String ALLOW_CREDENTIALS = PREFIX + "allowCredentials";

    /**
     * String '*', 'null', or an origin URI. The default is '*'.
     */
    public static final String ALLOW_ORIGIN = PREFIX + "allowOrigin";

    /**
     * Comma-separated list of HTTP headers. The default is ""
     */
    public static final String EXPOSE_HEADERS = PREFIX + "exposeHeaders";


    public final List<String> allowedMethods = new ArrayList<String>();
    public final String allowedMethodsString;

    public final String allowOrigin;

    public final int maxAge;

    public final List<String> allowedHeaders = new ArrayList<String>();

    public final String exposeHeaders;

    public final boolean allowCredentials;

    public CorsConfig(Map<String, Object> props) {
        this.allowOrigin = props.containsKey(ALLOW_ORIGIN) ? (String)props.get(ALLOW_ORIGIN) : "*";
        this.exposeHeaders = props.containsKey(EXPOSE_HEADERS) ? (String)props.get(EXPOSE_HEADERS) : "";
        this.maxAge = props.containsKey(MAX_AGE) ? (Integer)props.get(MAX_AGE) : 24*3600; //24 hrous default
        if(props.containsKey(ALLOW_HEADERS) && !((String)props.get(ALLOW_HEADERS)).isEmpty()){
            for(String h:((String)props.get(ALLOW_HEADERS)).split(",")){
                allowedHeaders.add(h.trim());
            }
        }

        if(props.containsKey(ALLOW_METHODS) && !((String)props.get(ALLOW_METHODS)).isEmpty()){
            this.allowedMethodsString = (String)props.get(ALLOW_METHODS);
            for(String h:((String)props.get(ALLOW_METHODS)).split(",")){
                allowedMethods.add(h.trim());
            }
        }else{
            this.allowedMethodsString = "GET,POST,PUT,DELETE,OPTIONS";
            Collections.addAll(allowedMethods, allowedMethodsString.split(","));
            Arrays.toString(allowedMethodsString.split(","));
        }
        this.allowCredentials = props.containsKey(ALLOW_CREDENTIALS) ? (Boolean)props.get(ALLOW_CREDENTIALS) : false;
    }



    public boolean isValidAllowRequestHeaders(String[] headers){
        if(allowedHeaders.isEmpty()){
            return true;
        }
        for(String header: headers){
            if(!allowedHeaders.contains(header.toUpperCase())){
                return false;
            }
        }
        return true;
    }

    public boolean isValidAllowRequestMethod(String header){
        return allowedMethods.contains(header);
    }
}
