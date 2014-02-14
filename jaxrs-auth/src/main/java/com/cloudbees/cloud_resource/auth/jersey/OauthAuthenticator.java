package com.cloudbees.cloud_resource.auth.jersey;

import com.cloudbees.api.BeesClient;
import com.cloudbees.api.cr.Capability;
import com.cloudbees.api.oauth.OauthClient;
import com.cloudbees.api.oauth.OauthClientException;
import com.cloudbees.api.oauth.OauthToken;
import com.cloudbees.cloud_resource.auth.AuthException;
import com.cloudbees.cloud_resource.auth.CloudbeesPrincipal;
import com.cloudbees.cloud_resource.auth.CloudbeesPrincipalImpl;
import com.cloudbees.cloud_resource.auth.Secure;
import com.cloudbees.cloud_resource.auth.guice.InjectLogger;
import com.cloudbees.cloud_resource.auth.guice.OauthConfig;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.sun.jersey.spi.container.AdaptingContainerRequest;
import com.sun.jersey.spi.container.ContainerRequest;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vivek Pandey
 */
@Singleton
public class OauthAuthenticator implements Provider<Authenticator>, Authenticator {

    @InjectLogger
    private Logger logger;

    private final OauthConfig oauthConfig;

    @Inject
    public OauthAuthenticator(OauthConfig oauthConfig) {
        this.oauthConfig = oauthConfig;
    }

    @Override
    public Authenticator get() {
        return this;
    }


    @Override
    public ContainerRequest authenticate(ContainerRequest request, Secure secureAnnotation) {

        List<String> scopes = new ArrayList<String>(Arrays.asList(secureAnnotation.scopes()));
        String[] capabilities = secureAnnotation.capabilities();

        for(String cap:capabilities){
            try {
                scopes.add(new Capability(cap).to(request.getRequestUri().toURL()));
            } catch (MalformedURLException e) {
                throw new AuthException(500, "Invalid host name: "+ request.getRequestUri().toString());
            }
        }
        logger.debug("Expecting scopes: "+Arrays.toString(scopes.toArray()));

        // Create OAuth client
        OauthClient oauthClient = new BeesClient(oauthConfig.getClientId(), oauthConfig.getClientSecret()).getOauthClient();

        //parse Bearer token from Authorization header
        String token = oauthClient.parseAuthorizationHeader(request.getHeaderValue("Authorization"));

        // If not found get it from the access_token parameter
        if(token == null){
            token = request.getQueryParameters().getFirst("access_token");
        }

        if (token == null) {
            logger.error("No OAuth access_token found in the request.");
            throw new AuthException(401, "No OAuth access_token found in the request.");
        }

        OauthToken oauthToken;
        try {
            //Validate scopes
            if(secureAnnotation.validateAllScopes()){
                oauthToken = oauthClient.validateToken(token);
                if(oauthToken != null){
                    for(String scope:scopes){
                        if(!oauthToken.validateScope(scope)){
                            throw new AuthException(401, String.format("Expected scope: %s not found on the token", scope));
                        }
                    }
                }
            }else{
                oauthToken = oauthClient.validateToken(token,scopes.toArray(new String[scopes.size()]));
            }
        } catch (OauthClientException e) {
            logger.error(e.getMessage(),e);
            throw new AuthException(401, "Authentication failed, invalid token");
        }
        if (oauthToken == null || oauthToken.accessToken == null) {
            throw new AuthException(401, "Authentication failed, invalid token");
        }

        //Set principal to secu context in the request
        CloudbeesPrincipal principal = new CloudbeesPrincipalImpl(oauthToken);
        return new AuthContainerRequest(principal,request);
    }



    private class AuthContainerRequest extends AdaptingContainerRequest{

        private final CloudbeesPrincipal principal;
        protected AuthContainerRequest(CloudbeesPrincipal principal, ContainerRequest acr) {
            super(acr);
            this.principal = principal;
        }

        @Override
        public Principal getUserPrincipal() {
            return principal;
        }
    }
}
