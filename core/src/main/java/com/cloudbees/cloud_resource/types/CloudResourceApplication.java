package com.cloudbees.cloud_resource.types;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents  PaaS application CR
 *
 * @author Vivek Pandey
 */
@CloudResourceType("https://types.cloudbees.com/paas/application")
public interface CloudResourceApplication extends CloudResource{

    @JsonProperty("application")
    public Application application();

    public class Application{

        private String url;

        public Application(String url) {
            this.url = url;
        }

        public Application() {
        }

        @JsonProperty("url")
        public String getUrl(){
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
