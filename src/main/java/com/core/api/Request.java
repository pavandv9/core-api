package com.core.api;

import lombok.NonNull;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public interface Request {

	/**
	 * Execute's the given request
	 * 
	 * @param httpRequest
	 * @return HttpResonse
	 */
	static HttpResponse execute(@NonNull HttpRequest httpRequest) {
		return new HttpConsumer().execute(httpRequest);
	}
}
