package io.elastic.api;


import javax.json.JsonObject;

/**
 * Interface to be implemented by components to verify user's
 * credentials before they are persisted.
 * <p>
 * Typically a credentials verification is implemented by sending
 * an arbitrary request to the given service and analyse the response.
 * For example if the response's status code is <i>HTTP 401 Unauthorized</i>
 * the verification failed.
 * </p>
 */
public interface CredentialsVerifier {

    /**
     * Performs the verification credentials in the given <i>configuration</i>.
     * The <i>configuration</i> parameter is a JSON object containing either
     * OAuth access token, API key or username/password combination provided
     * by a user during authentication process. These values are to be verified
     * by sending an arbitrary request to the desired service.
     *
     * @param configuration contains OAuth access token, API key,
     *                      username/password to be verified
     * @throws InvalidCredentialsException if the credentials are invalid
     */
    void verify(JsonObject configuration) throws InvalidCredentialsException;
}
