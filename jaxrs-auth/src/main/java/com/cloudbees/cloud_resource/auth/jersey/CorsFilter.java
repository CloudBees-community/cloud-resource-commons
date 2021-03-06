package com.cloudbees.cloud_resource.auth.jersey;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Vivek Pandey
 */
public class CorsFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    private final CorsConfig config;

    public CorsFilter(@Context ResourceConfig resourceConfig){
        this.config = new CorsConfig(resourceConfig.getProperties());
    }

    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        if(request.getHeaderValue(Headers.ORIGIN) != null) {
            MultivaluedMap<String, Object> headers = response.getHttpHeaders();

            if(!config.allowOrigin.isEmpty() && !config.allowOrigin.equals("*")) {
                String s = String.format("^https?://[^/@]*[.]?%s",extractWithoutWildCard(config.allowOrigin));
                if(request.getHeaderValue("Origin").matches(s)) {
                    headers.add(Headers.ALLOW_ORIGIN, request.getHeaderValue("Origin"));
                }else{
                    logger.error(String.format("Origin: %s did not match allowed origin pattern: %s", request.getHeaderValue("Origin"), config.allowOrigin));
                    return returnPreflightResponse(response);
                }
            }else{
                headers.add(Headers.ALLOW_ORIGIN, request.getHeaderValue("Origin"));
            }
            if (request.getHeaderValue("Access-Control-Request-Method") != null){
                if (config.isValidAllowRequestMethod(request.getHeaderValue("Access-Control-Request-Method"))) {
                    if(request.getHeaderValue("Access-Control-Request-Headers") != null) {
                        if (config.isValidAllowRequestHeaders(request.getHeaderValue("Access-Control-Request-Headers").split(","))) {
                            headers.add(Headers.ALLOW_HEADERS, request.getHeaderValue("Access-Control-Request-Headers"));
                        } else {
                            logger.error("Invalid Access-Control-Request-Headers: "+request.getHeaderValue("Access-Control-Request-Headers"));
                            return response;
                        }
                    }
                    headers.add(Headers.ALLOW_METHODS, config.allowedMethodsString);

                } else {
                    logger.error("Invalid Access-Control-Request-Method: "+request.getHeaderValue("Access-Control-Request-Method"));
                    return response;
                }
                if(config.maxAge > 0){
                    headers.add(Headers.MAX_AGE, config.maxAge);
                }

            }else{
                if(!config.exposeHeaders.isEmpty()){
                    headers.add(Headers.EXPOSE_HEADERS, config.exposeHeaders);
                }
            }

            if(config.allowCredentials){
                headers.add(Headers.ALLOW_CREDENTIALS, true);
            }
            return returnPreflightResponse(response);
        }
        return response;
    }

    private ContainerResponse returnPreflightResponse(ContainerResponse response){
        //Jersey returns 204 for OPTIONS call without target resource
        if(response.getStatus() == 204){
            response.setStatus(200);
        }
        return response;
    }

    private String extractWithoutWildCard(String x){
        if(x.startsWith("*.")){
            return x.substring(2,x.length());
        }
        return x;
    }

    final class Headers{
        public static final String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
        public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
        public static final String MAX_AGE = "Access-Control-Max-Age";
        public static final String ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
        public static final String ALLOW_METHODS = "Access-Control-Allow-Methods";
        public static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
        public static final String ORIGIN = "Origin";
    }

}
