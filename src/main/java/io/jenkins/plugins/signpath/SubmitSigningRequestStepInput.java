package io.jenkins.plugins.signpath;

import java.io.Serializable;
import java.util.UUID;

/**
 * Holds all input specific to the
 *
 * @see io.jenkins.plugins.signpath.SubmitSigningRequestStep
 */
public class SubmitSigningRequestStepInput implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID organizationId;
    private final String trustedBuildSystemTokenCredentialId;
    private final String apiTokenCredentialId;
    private final String projectSlug;
    private final String artifactConfigurationSlug;
    private final String signingPolicySlug;
    private final String inputArtifactPath;
    private final String description;
    private final String outputArtifactPath;
    private final boolean waitForCompletion;

    public SubmitSigningRequestStepInput(UUID organizationId,
                                         String trustedBuildSystemTokenCredentialId,
                                         String apiTokenCredentialId,
                                         String projectSlug,
                                         String artifactConfigurationSlug,
                                         String signingPolicySlug,
                                         String inputArtifactPath,
                                         String description,
                                         String outputArtifactPath,
                                         boolean waitForCompletion) {
        this.organizationId = organizationId;
        this.trustedBuildSystemTokenCredentialId = trustedBuildSystemTokenCredentialId;
        this.apiTokenCredentialId = apiTokenCredentialId;
        this.projectSlug = projectSlug;
        this.artifactConfigurationSlug = artifactConfigurationSlug;
        this.signingPolicySlug = signingPolicySlug;
        this.inputArtifactPath = inputArtifactPath;
        this.description = description;
        this.outputArtifactPath = outputArtifactPath;
        this.waitForCompletion = waitForCompletion;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public boolean getWaitForCompletion() {
        return waitForCompletion;
    }

    public String getTrustedBuildSystemTokenCredentialId() {
        return trustedBuildSystemTokenCredentialId;
    }

    public String getApiTokenCredentialId() {
        return apiTokenCredentialId;
    }

    public String getInputArtifactPath() {
        return inputArtifactPath;
    }

    public String getProjectSlug() {
        return projectSlug;
    }

    public String getArtifactConfigurationSlug() {
        return artifactConfigurationSlug;
    }

    public String getSigningPolicySlug() {
        return signingPolicySlug;
    }

    public String getDescription() {
        return description;
    }

    public String getOutputArtifactPath() {
        return outputArtifactPath;
    }
}
