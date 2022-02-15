/**
 * 
 */
package com.core.api;

import lombok.NonNull;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public interface HttpClient {

	public Response execute(@NonNull Request httpRequest);

	public Request getHttpRequest();

}
