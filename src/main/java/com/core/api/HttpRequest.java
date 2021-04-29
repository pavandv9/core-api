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
 *         <p>
 *         <b>apiNote</b> HttpRequest which forms the request. Pass HttpRequest
 *         object to the execute method which is available in getHttpClient of
 *         ServiceHelper interface.
 * @since 1.0.0
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
	 * @return this
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
	 * @return this
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
	 * @return this
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
	 * <p>
	 * <b>Description</b> Add path parameter name given in end point enclosed by {}.
	 * 
	 * @param name  with or without {}
	 * @param value of path parameter
	 *              <p>
	 *              <b>example</b> addPathParamValue("{pathParamName}",
	 *              pathParamValue) Or <br>
	 *              addPathParamValue("pathParamName", pathParamValue)
	 * @return this
	 */
	public HttpRequest addPathParamValue(@NonNull String name, Object value) {
		this.pathParams.put(name, value);
		return this;
	}

	/**
	 * Add query parameters
	 * 
	 * @param queryParams to the HttpRequest
	 * @return this
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
	 * @return this
	 */
	public HttpRequest addQueryParam(@NonNull String name, Object value) {
		this.queryParams.put(name, value);
		return this;
	}

	/**
	 * Add body to the HttpRequest
	 * 
	 * @param body
	 * @return this
	 */
	public HttpRequest addBody(@NonNull Object body) {
		this.body = body;
		return this;
	}

	/**
	 * Add HttpMethod to the HttpRequest
	 * 
	 * @param httpMethod
	 * @return this
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
	 * @return this
	 *         <p>
	 *         <b>example</b> https://example.com/demo/{pathName}
	 */
	public HttpRequest addEndPoint(@NonNull String endpoint) {
		this.endPoint = endpoint;
		return this;
	}

	/**
	 * Add headers to the HttpRequest
	 * 
	 * @param headers
	 * @return this
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
	 * @return this
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
	 * @return this
	 */
	public HttpRequest addHeader(@NonNull HttpHeaders name, Object value) {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(name.toString(), value);
		addHeader(headers);
		return this;
	}

	public HttpRequest addContentType(@NonNull String value) {
		addHeader("Content-Type", value);
		return this;
	}

	public HttpRequest addAuthorization(@NonNull String value) {
		addHeader("Authorization", value);
		return this;
	}

	/**
	 * Get content type
	 */
	public String getContentType() {
		return RequestUtil.getContentType(headers);
	}

	/**
	 * Get authorization
	 */
	public String getAuthorization() {
		return RequestUtil.getAuthorization(headers);
	}

}
