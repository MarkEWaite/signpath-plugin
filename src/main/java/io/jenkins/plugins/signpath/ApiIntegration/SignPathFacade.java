package io.jenkins.plugins.signpath.ApiIntegration;

import io.jenkins.plugins.signpath.ApiIntegration.Model.SigningRequestModel;
import io.jenkins.plugins.signpath.ApiIntegration.Model.SubmitSigningRequestResult;
import io.jenkins.plugins.signpath.Common.TemporaryFile;
import io.jenkins.plugins.signpath.Exceptions.SignPathFacadeCallException;

import java.io.IOException;
import java.util.UUID;

/**
 * A facade for the SignPath API
 */
public interface SignPathFacade {

    /**
     * Submits a signing request to SignPath, waits until it completes and then retrieves the signed artifact
     *
     * @param submitModel all the (non-authentication) parameters needed for creating the signing request
     * @return the signed artifact in form of a TemporaryFile
     * @throws IOException                 occurs if any necessary intermediate file cannot be successfully created
     * @throws SignPathFacadeCallException occurs if any user error has been made (i.e. misconfiguration)
     */
    SubmitSigningRequestResult submitSigningRequest(SigningRequestModel submitModel) throws IOException, SignPathFacadeCallException;

    /**
     * Similar to the submitSigningRequest method, but does not wait for the signing request to complete
     *
     * @param submitModel all the (non-authentication) parameters needed for creating the signing request
     * @return the signing request ID that you need to specify for downloading the signed artifact
     * @throws SignPathFacadeCallException occurs if any user error has been made (i.e. misconfiguration)
     */
    UUID submitSigningRequestAsync(SigningRequestModel submitModel) throws SignPathFacadeCallException;

    /**
     * Downloads a signed artifact from SignPath
     *
     * @param organizationId   the organization ID where the signing request resides
     * @param signingRequestID the signing request ID as given as a result of the submitSigningRequestAsync method
     * @return the signed artifact in form of a TemporaryFile
     * @throws IOException                 occurs if any necessary intermediate file cannot be successfully created
     * @throws SignPathFacadeCallException occurs if any user error has been made (i.e. misconfiguration)
     */
    TemporaryFile getSignedArtifact(UUID organizationId, UUID signingRequestID) throws IOException, SignPathFacadeCallException;
}