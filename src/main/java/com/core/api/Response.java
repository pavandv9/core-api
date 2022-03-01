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

// TODO: Auto-generated Javadoc
/**
 * The Class Response.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public abstract class Response implements ILogger {
	
	/** The status code. */
	protected int statusCode;

	/** The status message. */
	protected String statusMessage;

	/** The status line. */
	protected StatusLine statusLine;

	/** The body. */
	protected Object body;
	
	/** The headers. */
	protected Map<String, Object> headers = new HashMap<String, Object>();
	
	/**
	 * Set status line.
	 */
	protected abstract void setStatusLine();

	/**
	 * Set response body.
	 */
	protected abstract void setBody();

	/**
	 * Set headers.
	 */
	protected abstract void setHeaders();

	
	/**
	 * Get status code of response.
	 *
	 * @return status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Get status message of response.
	 *
	 * @return status message
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * Get status line.
	 *
	 * @return the status line
	 */
	public StatusLine getStatusLine() {
		return statusLine;
	}

	/**
	 * Get response body.
	 *
	 * @return body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * Get all headers.
	 *
	 * @return Get all the headers
	 */
	public Map<String, Object> getHeaders() {
		return headers;
	}


	/**
	 * Parse response body.
	 *
	 * @param jsonpath the jsonpath
	 * @return path of the value
	 * 
	 * <p><b>Example:</b> To get id inside body object "body.id"
	 */
	public String path(String jsonpath) {
		String value = "";
		try {
			DocumentContext documentContext = JsonPath.parse(getBody().toString());
			value = documentContext.read(jsonpath).toString();
		} catch (PathNotFoundException e) {
			throw new HttpException("JsonPath [\"" + jsonpath + "\"] not found");
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", statusLine="
				+ statusLine + ", body=" + body + ", headers=" + headers + "]";
	}
	
}
