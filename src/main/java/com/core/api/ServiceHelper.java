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

	default HttpClient getHttpClient() {
		return new HttpConsumer();
	};

}
