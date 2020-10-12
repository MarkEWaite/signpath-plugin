package io.jenkins.plugins.SignPath;

import io.jenkins.plugins.SignPath.ApiIntegration.SignPathCredentials;
import io.jenkins.plugins.SignPath.ApiIntegration.SignPathFacade;
import io.jenkins.plugins.SignPath.ApiIntegration.SignPathFacadeFactory;
import io.jenkins.plugins.SignPath.Artifacts.ArtifactFileManager;
import io.jenkins.plugins.SignPath.Common.TemporaryFile;
import io.jenkins.plugins.SignPath.Exceptions.SecretNotFoundException;
import io.jenkins.plugins.SignPath.Exceptions.SignPathFacadeCallException;
import io.jenkins.plugins.SignPath.Exceptions.SignPathStepFailedException;
import io.jenkins.plugins.SignPath.SecretRetrieval.SecretRetriever;
import io.jenkins.plugins.SignPath.StepShared.GetSignedArtifactStepInput;
import io.jenkins.plugins.SignPath.StepShared.SignPathContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousStepExecution;

import java.io.IOException;
import java.io.PrintStream;

/**
 * The step-execution for the
 *
 * @see GetSignedArtifactStep
 */
public class GetSignedArtifactStepExecution extends SynchronousStepExecution<String> {
    private final PrintStream logger;
    private final SecretRetriever secretRetriever;
    private final ArtifactFileManager artifactFileManager;
    private final SignPathFacadeFactory signPathFacadeFactory;
    private final GetSignedArtifactStepInput input;

    protected GetSignedArtifactStepExecution(GetSignedArtifactStepInput input,
                                             SignPathContext signPathContext) {
        super(signPathContext.getStepContext());
        this.input = input;
        this.logger = signPathContext.getLogger();
        this.secretRetriever = signPathContext.getSecretRetriever();
        this.artifactFileManager = signPathContext.getArtifactFileManager();
        this.signPathFacadeFactory = signPathContext.getSignPathFacadeFactory();
    }

    @Override
    protected String run() throws SignPathStepFailedException {
        logger.printf("GetSignedArtifactStepExecution organizationId: %s signingRequestId: %s\n", input.getOrganizationId(), input.getSigningRequestId());

        try {
            String trustedBuildSystemToken = secretRetriever.retrieveSecret(Constants.TrustedBuildSystemTokenCredentialId);
            SignPathCredentials credentials = new SignPathCredentials(input.getCiUserToken(), trustedBuildSystemToken);
            SignPathFacade signPathFacade = signPathFacadeFactory.create(credentials);
            TemporaryFile signedArtifact = signPathFacade.getSignedArtifact(input.getOrganizationId(), input.getSigningRequestId());

            artifactFileManager.storeArtifact(signedArtifact, input.getOutputArtifactPath());
            logger.print("\nSigning step succeeded\n");
            return "";
        } catch (SecretNotFoundException | SignPathFacadeCallException | IOException | InterruptedException ex) {
            logger.print("\nSigning step failed: " + ex.getMessage() + "\n");
            throw new SignPathStepFailedException("Signing step failed: " + ex.getMessage(), ex);
        }
    }
}
