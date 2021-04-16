/**
 * 
 */
package com.core.api;

import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 */
public interface HttpClient {

	public HttpResponse execute(@NonNull HttpRequest httpRequest);

	public HttpRequest getHttpRequest();

}
