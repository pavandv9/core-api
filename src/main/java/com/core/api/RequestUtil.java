/**
 * 
 */
package com.core.api;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Pavan.DV
 *
 */
public class RequestUtil {

	public static String getContentType(Map<String, Object> headers) {
		String contentType = "";
		for (Entry<String, Object> entry : headers.entrySet()) {
			String key = entry.getKey().trim();
			if (key.equalsIgnoreCase("Content-Type")) {
				contentType = (String) entry.getValue();
			}
		}
		return contentType;
	}

	public static String getAuthorization(Map<String, Object> headers) {
		String authorization = "";
		for (Entry<String, Object> entry : headers.entrySet()) {
			String key = entry.getKey().trim();
			if (key.equalsIgnoreCase("Authorization")) {
				authorization = (String) entry.getValue();
			}
		}
		return authorization;
	}
}
