/**
 * 
 */
package com.core.api;

import com.core.api.utils.ILogger;

/**
 * @author Pavan.DV
 *
 * @deprecated As of core-api version 1.1.0, replaced by
 *             <code>Request.execute(HttpRequest r)</code> use
 *             {@link com.core.api.Request}.
 * @since 1.0.0
 */
@Deprecated
public interface ServiceHelper extends ILogger {

	default HttpClient getHttpClient() {
		return new HttpConsumer();
	};

}
