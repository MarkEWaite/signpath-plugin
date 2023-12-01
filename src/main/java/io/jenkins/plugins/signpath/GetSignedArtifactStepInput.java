package io.jenkins.plugins.signpath;

import java.io.Serializable;
import java.util.UUID;

/**
 * Holds all input data specific to the
 *
 * @see io.jenkins.plugins.signpath.GetSignedArtifactStep
 */
public class GetSignedArtifactStepInput implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID organizationId;
    private final UUID signingRequestId;
    private final String trustedBuildSystemTokenCredentialId;
    private final String apiTokenCredentialId;
    private final String outputArtifactPath;

    public GetSignedArtifactStepInput(UUID organizationId, UUID signingRequestId, String trustedBuildSystemTokenCredentialId, String apiTokenCredentialId, String outputArtifactPath) {
        this.organizationId = organizationId;
        this.signingRequestId = signingRequestId;
        this.trustedBuildSystemTokenCredentialId = trustedBuildSystemTokenCredentialId;
        this.apiTokenCredentialId = apiTokenCredentialId;
        this.outputArtifactPath = outputArtifactPath;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public UUID getSigningRequestId() {
        return signingRequestId;
    }

    public String getTrustedBuildSystemTokenCredentialId() {
        return trustedBuildSystemTokenCredentialId;
    }

    public String getApiTokenCredentialId() {
        return apiTokenCredentialId;
    }

    public String getOutputArtifactPath() {
        return outputArtifactPath;
    }
}