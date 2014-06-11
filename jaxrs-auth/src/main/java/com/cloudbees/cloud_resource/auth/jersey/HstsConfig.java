package com.cloudbees.cloud_resource.auth.jersey;

import java.util.Map;

/**
 * @author: Vivek Pandey
 */
public class HstsConfig {
    private static final String PREFIX = "com.cloudbees.cloud_resource.auth.hsts.";

    public static final String MAX_AGE = PREFIX + "maxAge";
    public static final String INCLUDE_SUB_DOMAINS = PREFIX + "includeSubDomains";

    public final int maxAge;
    public final boolean includeSubDomains;

    public HstsConfig(Map<String,Object> props) {
        if(props.containsKey(MAX_AGE) && (Integer)props.get(MAX_AGE) >=0 ){
            this.maxAge=(Integer)props.get(MAX_AGE);
        }else{
            this.maxAge = 31536000;
        }

        if(props.containsKey(INCLUDE_SUB_DOMAINS) && (Boolean)props.get(INCLUDE_SUB_DOMAINS)){
            this.includeSubDomains = (Boolean)props.get(INCLUDE_SUB_DOMAINS);
        }else{
            this.includeSubDomains = false;
        }
    }
}
