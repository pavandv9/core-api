/**
 * 
 */
package com.core.api;

import com.core.api.utils.ILogger;

/**
 * @author Pavan.DV
 *
 */
public interface ServiceHelper extends ILogger {

	/**
	 * 
	 * @deprecated As of core-api version 1.1.0, replaced by
	 *             <code>Request.execute(HttpRequest r)</code> use
	 *             {@link com.core.api.Request}.
	 * @return
	 */
	@Deprecated
	default HttpClient getHttpClient() {
		return new HttpConsumer();
	};

}
