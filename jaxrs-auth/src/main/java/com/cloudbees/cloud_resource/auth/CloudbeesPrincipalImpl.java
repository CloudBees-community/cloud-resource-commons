package com.cloudbees.cloud_resource.auth;

import com.cloudbees.api.oauth.OauthToken;

/**
 * @author Vivek Pandey
 */
public class CloudbeesPrincipalImpl implements CloudbeesPrincipal {
    private  String email;
    private  String[] scopes=new String[0];
    private  String[] accounts=new String[0];
    private  String clientId;
    private   String accessToken;
    private  String userAccountRole;

    private  Secure.Authenticator authenticationMethod;

    private boolean secured;

    public CloudbeesPrincipalImpl(OauthToken token) {
        this.email = token.email;
        this.scopes = token.getScopes().toArray(new String[token.getScopes().size()]);
        this.accounts = token.listAccounts().toArray(new String[token.listAccounts().size()]);
        this.clientId = token.clientId;
        this.accessToken = token.accessToken;
        this.userAccountRole = token.userAccountRole;
        this.authenticationMethod = Secure.Authenticator.OAUTH;
        this.secured = true;
    }

    @Override
    public boolean isSecured() {
        return secured;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String[] getAccounts(){
        return accounts;
    }

    @Override
    public String[] getScopes(){
        return scopes;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getUserAccountRole() {
        return userAccountRole;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean hasScope(String scope){
        for(String s:scopes){
            if(scope.equals(s)){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean includesAccount(String expectedAccount){
        for(String account:accounts){
            if(account.equals(expectedAccount)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void authorizeAccount(String expectedAccount){
        authorizeAccount(expectedAccount, true);
    }

    @Override
    public boolean isInAdminRole(){
        return authenticationMethod != Secure.Authenticator.OAUTH || userAccountRole.equals("admin");
    }

    @Override
    public void authorizeAccount(String expectedAccount, boolean checkAdminRole){
        if(!secured){
            throw new AuthException(401, "Authentication failed");
        }
        if(authenticationMethod != Secure.Authenticator.OAUTH){
            return;
        }

        if(!includesAccount(expectedAccount)){
            throw new AuthException(403, "Unauthorized access to account: "+expectedAccount);
        }

        if(checkAdminRole && !userAccountRole.equals("admin")){
            throw new AuthException(403, "Unauthorized access to account: "+expectedAccount+". User represented by this token must be admin of this account.");
        }
    }

}
