/**
 * 
 */
package com.core.api;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Interface HttpClient.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public interface HttpClient {

	/**
	 * Execute.
	 *
	 * @param httpRequest the http request
	 * @return the response
	 */
	public Response execute(@NonNull Request httpRequest);

	/**
	 * Gets the http request.
	 *
	 * @return the http request
	 */
	public Request getHttpRequest();

}
