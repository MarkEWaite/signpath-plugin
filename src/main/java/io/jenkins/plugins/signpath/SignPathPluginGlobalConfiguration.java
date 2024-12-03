package io.jenkins.plugins.signpath;

import com.cloudbees.plugins.credentials.CredentialsScope;
import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import org.kohsuke.stapler.DataBoundSetter;
import hudson.util.FormValidation;
import io.jenkins.plugins.signpath.Common.PluginConstants;
import io.jenkins.plugins.signpath.Exceptions.SecretNotFoundException;
import io.jenkins.plugins.signpath.SecretRetrieval.CredentialBasedSecretRetriever;
import io.jenkins.plugins.signpath.SecretRetrieval.SecretRetriever;
import java.util.UUID;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.QueryParameter;

@Extension
public class SignPathPluginGlobalConfiguration extends GlobalConfiguration {

    private String defaultApiURL = PluginConstants.DEFAULT_API_URL;
    private String defaultTrustedBuildSystemCredentialId = PluginConstants.DEFAULT_TBS_CREDENTIAL_ID;
    private String defaultOrganizationId;

    public SignPathPluginGlobalConfiguration() {
        load();
    }

    // default DefaultApiURL
    
    public String getDefaultApiURL() {
        return defaultApiURL;
    }

    @DataBoundSetter
    public void setDefaultApiURL(String url) {
        this.defaultApiURL = url;
        save();
    }
    
    // DefaultTrustedBuildSystemCredential
    
    public String getDefaultTrustedBuildSystemCredentialId() {
        return defaultTrustedBuildSystemCredentialId;
    }

    @DataBoundSetter
    public void setDefaultTrustedBuildSystemCredentialId(String tbsCredentialId) {
        this.defaultTrustedBuildSystemCredentialId = tbsCredentialId;
        save();
    }
    
    public FormValidation doCheckDefaultTrustedBuildSystemCredentialId(@QueryParameter String value) {
        if (value == null || value.trim().isEmpty()) {
            return FormValidation.ok(); // empty value is allowed
        }
        
        Jenkins jenkins = Jenkins.get();
        SecretRetriever secretRetriever = new CredentialBasedSecretRetriever(jenkins);
        
        try {
            // let's see if such secret exists
            // SYSTEM scope is required for TBS token
            secretRetriever.retrieveSecret(value, new CredentialsScope[] {CredentialsScope.SYSTEM});
            return FormValidation.ok();
        }
        catch(SecretNotFoundException ex)
        {
            return FormValidation.error(ex.getMessage());
        }
    }
    
    // DefaultOrganizationId

    public String getDefaultOrganizationId() {
        return defaultOrganizationId;
    }

    @DataBoundSetter
    public void setDefaultOrganizationId(String organizationId) {
        this.defaultOrganizationId = organizationId;
        save();
    }
    
    public FormValidation doCheckDefaultOrganizationId(@QueryParameter String value) {
        if (value == null || value.trim().isEmpty()) {
            return FormValidation.ok(); // empty value is allowed
        }
        
        if (!isValidUUID(value)) {
            return FormValidation.error("Default Organization ID must be a valid uuid.");
        }
        
        return FormValidation.ok();
    }
    
    protected boolean isValidUUID(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}