/**
 * 
 */
package com.core.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.core.api.constants.HttpMethod;
import com.core.api.constants.IHeaders;
import com.core.api.exception.HttpException;
import com.core.api.utils.ILogger;
import com.core.api.utils.Logger;

import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 */
public class HttpConsumer implements HttpClient, ILogger, IHeaders {

	private HttpRequest httpRequest;

	private HttpResponse processRequest(@NonNull HttpRequest httpRequest) {
		Logger.logRequest(httpRequest);
		validateRequest();
		CloseableHttpResponse closeableHttpResponse = null;
		HttpResponse httpResponse = null;
		try {
			closeableHttpResponse = getDefaultClient().execute(getHttpUriRequest(httpRequest.getHttpMethod()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpResponse = new AbstractResponse(closeableHttpResponse);
		Logger.logResponse(httpResponse);
		return httpResponse;
	}

	@Override
	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	@Override
	public HttpResponse execute(@NonNull HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
		return processRequest(httpRequest);
	}

	private URI URI() {
		URI uri = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(buildPathParams());
			for (Entry<String, Object> entrySet : httpRequest.getQueryParams().entrySet()) {
				uriBuilder.setParameter(entrySet.getKey(), entrySet.getValue().toString());
			}
			uri = uriBuilder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

	private void validateRequest() {
		try {
			httpRequest.getHttpMethod().toString();
		} catch (NullPointerException e) {
			throw new HttpException("HttpMethod not found in the request");
		}
	}

	private String buildPathParams() {
		String pathParams = "";
		for (Entry<String, Object> pathParam : httpRequest.getPathParams().entrySet()) {
			pathParams = pathParams + String.format("/%1$s/%2$s", pathParam.getKey(), pathParam.getValue());
		}
		String url = String.format("%1$s/%2$s%3$s", httpRequest.getBaseUrl(), httpRequest.getEndPoint(), pathParams);
		return url;
	}

	private CloseableHttpClient getDefaultClient() {
		CloseableHttpClient client = HttpClients.createDefault();
		HashSet<Header> defaultHeaders = new HashSet<Header>();
		defaultHeaders.add(new BasicHeader(HttpHeaders.PRAGMA, "no-cache"));
		defaultHeaders.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache"));
		defaultHeaders.add(new BasicHeader(ACCEPT, APPLICATION_JSON));
		HttpClientBuilder clientBuilder = HttpClients.custom().setDefaultHeaders(defaultHeaders).disableAuthCaching()
				.disableContentCompression();
		clientBuilder.build();
		return client;
	}

	private void setHeaders(HttpUriRequest httpUriRequest) {
		for (Entry<String, Object> entry : httpRequest.getHeaders().entrySet())
			httpUriRequest.setHeader(entry.getKey(), entry.getValue().toString());
	}

	private HttpUriRequest getHttpUriRequest(HttpMethod method) {
		HttpUriRequest httpUriRequest = null;
		switch (method) {
		case GET:
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(URI());
			setHeaders(httpGet);
			httpUriRequest = httpGet;
			break;
		case POST:
			HttpPost httpPost = new HttpPost();
			httpPost.setURI(URI());
			setHeaders(httpPost);
			httpUriRequest = httpPost;
			break;
		case PUT:
			HttpPut httpPut = new HttpPut();
			httpPut.setURI(URI());
			setHeaders(httpPut);
			httpUriRequest = httpPut;
			break;
		case DELETE:
			HttpDelete httpDelete = new HttpDelete();
			httpDelete.setURI(URI());
			setHeaders(httpDelete);
			httpUriRequest = httpDelete;
			break;
		case OPTIONS:
			HttpOptions httpOptions = new HttpOptions();
			httpOptions.setURI(URI());
			setHeaders(httpOptions);
			httpUriRequest = httpOptions;
			break;
		case HEAD:
			HttpHead httpHead = new HttpHead();
			httpHead.setURI(URI());
			setHeaders(httpHead);
			httpUriRequest = httpHead;
			break;
		case PATCH:
			HttpPatch httpPatch = new HttpPatch();
			httpPatch.setURI(URI());
			setHeaders(httpPatch);
			httpUriRequest = httpPatch;
			break;
		default:
			break;
		}
		return httpUriRequest;
	}

}
