/**
 * 
 */
package com.core.api;

import com.core.api.utils.ILogger;

// TODO: Auto-generated Javadoc
/**
 * The Interface ServiceHelper.
 *
 * @author Pavan.DV
 * @since 1.0.0
 * @deprecated As of core-api version 1.1.0, replaced by
 *             <code>Request.execute(HttpRequest r)</code> use
 *             {@link com.core.api.Request}.
 */
@Deprecated
public interface ServiceHelper extends ILogger {

	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	default HttpClient getHttpClient() {
		return new HttpConsumer();
	};

}
