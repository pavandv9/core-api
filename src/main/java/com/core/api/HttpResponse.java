/**
 * 
 */
package com.core.api;

import java.util.HashMap;
import java.util.Map;

import com.core.api.exception.HttpException;
import com.core.api.model.StatusLine;
import com.core.api.utils.ILogger;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
@ToString
public abstract class HttpResponse implements ILogger {

	/**
	 * Get status code of response
	 */
	@Getter
	protected int statusCode;

	/**
	 * Get status message of response
	 */
	@Getter
	protected String statusMessage;

	/**
	 * Get status line
	 */
	@Getter
	protected StatusLine statusLine;

	/**
	 * Set status line
	 */
	protected abstract void setStatusLine();

	/**
	 * Get response body
	 */
	@Getter
	protected Object body;

	/**
	 * Set response body
	 */
	protected abstract void setBody();

	/**
	 * Get all headers
	 */
	@Getter
	protected Map<String, Object> headers = new HashMap<String, Object>();

	/**
	 * Set headers
	 */
	protected abstract void setHeaders();

	/**
	 * Parse response body.
	 * 
	 * @param jsonpath
	 * @return path of the value
	 */
	public String parse(String jsonpath) {
		String value = "";
		try {
			DocumentContext documentContext = JsonPath.parse(getBody().toString());
			value = documentContext.read(jsonpath).toString();
		} catch (PathNotFoundException e) {
			throw new HttpException("JsonPath [\"" + jsonpath + "\"] not found");
		}
		return value;
	}
}
