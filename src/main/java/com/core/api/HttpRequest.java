/**
 * 
 */
package com.core.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHeaders;

import com.core.api.constants.HttpMethod;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 * @apiNote HttpRequest which forms the request. Pass HttpRequest object to the
 *          execute method which is available in getHttpClient of ServiceHelper
 *          interface.
 */
public class HttpRequest {

	/**
	 * Get request body
	 */
	@Getter
	private Object body;

	/**
	 * Get request base url
	 */
	@Getter
	private String baseUrl;

	/**
	 * Get request endpoint
	 */
	@Getter
	private String endPoint;
	/**
	 * Get request httpMethod
	 */
	@Getter
	private HttpMethod httpMethod;
	/**
	 * Get request headers
	 */
	@Getter
	private Map<String, Object> headers = new HashMap<String, Object>();
	/**
	 * Get request path parameters
	 */
	@Getter
	private Map<String, Object> pathParams = new HashMap<String, Object>();
	/**
	 * Get request query parameters
	 */
	@Getter
	private Map<String, Object> queryParams = new HashMap<String, Object>();

	/**
	 * Add base url to the HttpRequest
	 * 
	 * @param url
	 * @return
	 */
	public HttpRequest addBaseUrl(@NonNull String url) {
		if (!url.isEmpty() || null != url) {
			baseUrl = url;
		}
		return this;
	}

	/**
	 * Add base url to the HttpRequest
	 * 
	 * @param url
	 * @return
	 */
	public HttpRequest addBaseUrl(@NonNull Object url) {
		if (!url.toString().isEmpty() || null != url) {
			baseUrl = url.toString();
		}
		return this;
	}

	/**
	 * Add path parameters to the HttpRequest
	 * 
	 * @param pathParams
	 * @return
	 */
	public HttpRequest addPathParamValues(@NonNull Map<String, Object> pathParams) {
		if (!pathParams.isEmpty() || null != pathParams) {
			pathParams.forEach((name, value) -> this.pathParams.put(name, value));
		}
		return this;
	}

	/**
	 * Add path parameters to the HttpRequest.
	 * 
	 * @Description Add path parameter name given in end point enclosed by {}.
	 * 
	 * @param name  with or without {}
	 * @param value of path parameter
	 * @example addPathParamValue("{pathParamName}", pathParamValue) Or <br>
	 *          addPathParamValue("pathParamName", pathParamValue)
	 * @return HttpRequest object
	 */
	public HttpRequest addPathParamValue(@NonNull String name, Object value) {
		this.pathParams.put(name, value);
		return this;
	}

	/**
	 * Add query parameters
	 * 
	 * @param queryParams to the HttpRequest
	 * @return
	 */
	public HttpRequest addQueryParams(@NonNull Map<String, Object> queryParams) {
		if (!queryParams.isEmpty() || null != queryParams) {
			queryParams.forEach((name, value) -> this.queryParams.put(name, value));
		}
		return this;
	}

	/**
	 * Add query parameters to the HttpRequest
	 * 
	 * @param name  of the query parameter
	 * @param value of the query parameter
	 * @return
	 */
	public HttpRequest addQueryParam(@NonNull String name, Object value) {
		this.queryParams.put(name, value);
		return this;
	}

	/**
	 * Add body to the HttpRequest
	 * 
	 * @param body
	 * @return
	 */
	public HttpRequest addBody(@NonNull Object body) {
		this.body = body;
		return this;
	}

	/**
	 * Add HttpMethod to the HttpRequest
	 * 
	 * @param httpMethod
	 * @return
	 */
	public HttpRequest addMethod(@NonNull HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		return this;
	}

	/**
	 * Add end point to the HttpRequest. <br>
	 * Add path parameters to end point if api request has it, by enclosing in
	 * flower braces {pathParam}
	 * 
	 * @param endpoint
	 * @return
	 * @example https://example.com/demo/{pathName}
	 */
	public HttpRequest addEndPoint(@NonNull String endpoint) {
		this.endPoint = endpoint;
		return this;
	}

	/**
	 * Add headers to the HttpRequest
	 * 
	 * @param headers
	 * @return
	 */
	public HttpRequest addHeader(@NonNull Map<String, Object> headers) {
		if (!headers.isEmpty() || null != headers) {
			headers.forEach((name, value) -> this.headers.put(name, value));
		}
		return this;
	}

	/**
	 * Add header to the HttpRequest
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpRequest addHeader(@NonNull String name, Object value) {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(name, value);
		addHeader(headers);
		return this;
	}

	/**
	 * Add header to the HttpRequest
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpRequest addHeader(@NonNull HttpHeaders name, Object value) {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(name.toString(), value);
		addHeader(headers);
		return this;
	}
}
