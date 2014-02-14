package com.cloudbees.cloud_resource.auth;

import java.security.Principal;

/**
 * @author Vivek Pandey
 */
public interface CloudbeesPrincipal extends Principal {
    boolean isSecured();

    String[] getAccounts();

    String[] getScopes();

    String getClientId();

    String getAccessToken();

    String getUserAccountRole();

    String getEmail();

    boolean hasScope(String scope);

    boolean includesAccount(String expectedAccount);

    void authorizeAccount(String expectedAccount);

    boolean isInAdminRole();

    void authorizeAccount(String expectedAccount, boolean checkAdminRole);
}
