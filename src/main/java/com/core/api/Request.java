/**
 * 
 */
package com.core.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHeaders;

import com.core.api.constants.HttpMethod;

import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 *         <p>
 *         <b>apiNote</b> Request which forms the request. Pass Request
 *         object to the execute method which is available in getHttpClient of
 *         ServiceHelper interface.
 * @since 1.0.0
 */
public class Request {

	private Object body;

	private String baseUrl;

	private String endPoint;

	private HttpMethod httpMethod;

	private Map<String, Object> headers = new HashMap<String, Object>();

	private Map<String, Object> pathParams = new HashMap<String, Object>();

	private Map<String, Object> queryParams = new HashMap<String, Object>();

	/**
	 * Get request body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * Get request base url
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Get request endpoint
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * Get request httpMethod
	 */
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	/**
	 * Get request headers
	 */
	public Map<String, Object> getHeaders() {
		return headers;
	}

	/**
	 * Get request path parameters
	 */
	public Map<String, Object> getPathParams() {
		return pathParams;
	}

	/**
	 * Get request query parameters
	 */
	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	/**
	 * Add base url to the Request
	 * 
	 * @param url
	 * @return this
	 */
	public Request addBaseUrl(@NonNull String url) {
		if (!url.isEmpty() || null != url) {
			baseUrl = url;
		}
		return this;
	}

	/**
	 * Add base url to the Request
	 * 
	 * @param url
	 * @return this
	 */
	public Request addBaseUrl(@NonNull Object url) {
		if (!url.toString().isEmpty() || null != url) {
			baseUrl = url.toString();
		}
		return this;
	}

	/**
	 * Add path parameters to the Request
	 * 
	 * @param pathParams
	 * @return this
	 */
	public Request addPathParamValues(@NonNull Map<String, Object> pathParams) {
		if (!pathParams.isEmpty() || null != pathParams) {
			pathParams.forEach((name, value) -> this.pathParams.put(name, value));
		}
		return this;
	}

	/**
	 * Add path parameters to the Request.
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
	public Request addPathParamValue(@NonNull String name, Object value) {
		this.pathParams.put(name, value);
		return this;
	}

	/**
	 * Add query parameters
	 * 
	 * @param queryParams to the Request
	 * @return this
	 */
	public Request addQueryParams(@NonNull Map<String, Object> queryParams) {
		if (!queryParams.isEmpty() || null != queryParams) {
			queryParams.forEach((name, value) -> this.queryParams.put(name, value));
		}
		return this;
	}

	/**
	 * Add query parameters to the Request
	 * 
	 * @param name  of the query parameter
	 * @param value of the query parameter
	 * @return this
	 */
	public Request addQueryParam(@NonNull String name, Object value) {
		this.queryParams.put(name, value);
		return this;
	}

	/**
	 * Add body to the Request
	 * 
	 * @param body
	 * @return this
	 */
	public Request addBody(@NonNull Object body) {
		this.body = body;
		return this;
	}

	/**
	 * Add HttpMethod to the Request
	 * 
	 * @param httpMethod
	 * @return this
	 */
	public Request addMethod(@NonNull HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		return this;
	}

	/**
	 * Add end point to the Request. <br>
	 * Add path parameters to end point if api request has it, by enclosing in
	 * flower braces {pathParam}
	 * 
	 * @param endpoint
	 * @return this
	 *         <p>
	 *         <b>example</b> https://example.com/demo/{pathName}
	 */
	public Request addEndPoint(@NonNull String endpoint) {
		this.endPoint = endpoint;
		return this;
	}

	/**
	 * Add headers to the Request
	 * 
	 * @param headers
	 * @return this
	 */
	public Request addHeader(@NonNull Map<String, Object> headers) {
		if (!headers.isEmpty() || null != headers) {
			headers.forEach((name, value) -> this.headers.put(name, value));
		}
		return this;
	}

	/**
	 * Add header to the Request
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public Request addHeader(@NonNull String name, Object value) {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(name, value);
		addHeader(headers);
		return this;
	}

	/**
	 * Add header to the Request
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public Request addHeader(@NonNull HttpHeaders name, Object value) {
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(name.toString(), value);
		addHeader(headers);
		return this;
	}

	public Request addContentType(@NonNull String value) {
		addHeader("Content-Type", value);
		return this;
	}

	public Request addAuthorization(@NonNull String value) {
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

	/**
	 * Send request and get response
	 * 
	 * @return Response
	 */
	public Response send() {
		return new HttpConsumer().execute(buildRequest());
	}

	private Request buildRequest() {
		return new Request().addBaseUrl(baseUrl).addEndPoint(endPoint).addMethod(httpMethod).addBody(body).addHeader(headers)
				.addPathParamValues(pathParams).addQueryParams(queryParams);
	}
}
