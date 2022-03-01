
/**
 * 
 */
package com.core.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.core.api.constants.ConfigProperty;
import com.core.api.constants.HttpMethod;
import com.core.api.constants.IHeaders;
import com.core.api.exception.HttpException;
import com.core.api.utils.ILogger;
import com.core.api.utils.JavaUtil;
import com.core.api.utils.Logger;
import com.core.api.utils.PropertyUtil;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpConsumer.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class HttpConsumer implements HttpClient, ILogger, IHeaders {

	/** The request. */
	private Request request;

	static {
		clearFiles();
	}

	/**
	 * Process Request.
	 *
	 * @return the response
	 */
	private Response processRequest() {
		loadConfigFileAndValidateRequest();
		Logger.logRequest(request);
		CloseableHttpResponse closeableHttpResponse = null;
		Response httpResponse = null;
		try {
			closeableHttpResponse = getDefaultClient().execute(getHttpUriRequest(request.getHttpMethod()));
			httpResponse = new AbstractResponse(closeableHttpResponse);
			Logger.logResponse(httpResponse);
		} catch (IOException e) {
			LOG.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return httpResponse;
	}

	/* (non-Javadoc)
	 * @see com.core.api.HttpClient#getHttpRequest()
	 */
	@Override
	public Request getHttpRequest() {
		return request;
	}

	/* (non-Javadoc)
	 * @see com.core.api.HttpClient#execute(com.core.api.Request)
	 */
	@Override
	public Response execute(@NonNull Request request) {
		this.request = request;
		return processRequest();
	}

	/**
	 * Get URI by building url, path parameters and query parameters.
	 *
	 * @return the uri
	 */
	private URI URI() {
		URI uri = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(buildPathParams());
			for (Entry<String, Object> entrySet : request.getQueryParams().entrySet()) {
				uriBuilder.setParameter(entrySet.getKey(), entrySet.getValue().toString());
			}
			uri = uriBuilder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * Load config file and vlidate request.
	 */
	private void loadConfigFileAndValidateRequest() {
		loadConfig();
		if (request.getHttpMethod() == null)
			throw new HttpException("HttpMethod not found in the request");
	}

	/**
	 * Build path parameter to url.
	 *
	 * @return the string
	 */
	private String buildPathParams() {
		String endPointWithPathPrams = request.getEndPoint();
		String url = request.getBaseUrl();
		if (endPointWithPathPrams != null) {
			for (Entry<String, Object> pathParam : request.getPathParams().entrySet()) {
				String key = pathParam.getKey().replace("{", "").replace("}", "");
				endPointWithPathPrams = endPointWithPathPrams.replace("{" + key + "}", pathParam.getValue().toString());
			}
			url = String.format("%1$s%2$s", request.getBaseUrl(), endPointWithPathPrams);
		}
		return url;
	}

	/**
	 * Set default headers and get CloseableHttpClient.
	 * 
	 * @return CloseableHttpClient
	 */
	private CloseableHttpClient getDefaultClient() {
		CloseableHttpClient client = HttpClients.createDefault();
		HashSet<Header> defaultHeaders = new HashSet<Header>();
		defaultHeaders.add(new BasicHeader(HttpHeaders.PRAGMA, "no-cache"));
		defaultHeaders.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache"));
		defaultHeaders.add(new BasicHeader(ACCEPT, APPLICATION_JSON));
		defaultHeaders.add(new BasicHeader(CONTENT_TYPE, APPLICATION_JSON));
		HttpClientBuilder clientBuilder = HttpClients.custom().setDefaultHeaders(defaultHeaders).disableAuthCaching()
				.disableContentCompression();
		clientBuilder.build();
		return client;
	}

	/**
	 * Set headers to the request.
	 *
	 * @param httpUriRequest the new headers
	 */
	private void setHeaders(HttpUriRequest httpUriRequest) {
		for (Entry<String, Object> entry : request.getHeaders().entrySet())
			httpUriRequest.setHeader(entry.getKey(), entry.getValue().toString());
	}

	/**
	 * Gets the http uri request.
	 *
	 * @param method the method
	 * @return the http uri request
	 */
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
			httpPost.setEntity(getHttpEntityBody());
			setHeaders(httpPost);
			httpUriRequest = httpPost;
			break;
		case PUT:
			HttpPut httpPut = new HttpPut();
			httpPut.setURI(URI());
			setHeaders(httpPut);
			httpPut.setEntity(getHttpEntityBody());
			httpUriRequest = httpPut;
			break;
		case PATCH:
			HttpPatch httpPatch = new HttpPatch();
			httpPatch.setURI(URI());
			setHeaders(httpPatch);
			httpPatch.setEntity(getHttpEntityBody());
			httpUriRequest = httpPatch;
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
		default:
			break;
		}
		return httpUriRequest;
	}

	/**
	 * Load config.
	 */
	private void loadConfig() {
		loadConfigProperties();
		formatUrlAndEndPoint();
		loadHeaders();
	}

	/**
	 * Load config properties.
	 */
	private void loadConfigProperties() {
		String env = "";
		try {
			if (null != ConfigManager.props) {
				env = ConfigManager.get(ConfigProperty.ENV);
				try {
					PropertyUtil.loadProperties(
							String.format("%1$s%2$s%3$s", "src/main/resources/env-file/", env, ".properties"));
				} catch (HttpException e) {
					if (null != env)
						LOG.warn(env + ".properties not found, use it for better convince");
				}
			}
		} catch (ExceptionInInitializerError e) {
			LOG.warn("config.properties not found, use it for better convince");
		}
		String baseUrl = request.getBaseUrl();
		String authorization = request.getAuthorization();
		if (baseUrl == null || baseUrl.isEmpty()) {
			try {
				baseUrl = PropertyUtil.get(ConfigProperty.BASE_URL);
				request.addBaseUrl(baseUrl);
			} catch (NullPointerException e) {
				throw new HttpException("base_url is not found");
			} catch (ExceptionInInitializerError e) {
			}
		}
		if (request.getBaseUrl() == null || baseUrl.isEmpty())
			throw new HttpException("base_url is not found");
		if (baseUrl.contains("{") || baseUrl.contains("}"))
			throw new HttpException("base_url is not valid");
		if (authorization == null || authorization.isEmpty()) {
			try {
				authorization = PropertyUtil.get(ConfigProperty.AUTHORIZATION);
				if (authorization != null && !authorization.isEmpty())
					request.addAuthorization(authorization);
			} catch (ExceptionInInitializerError | NullPointerException e) {
			}
		}
	}

	/**
	 * Format url and end point.
	 */
	private void formatUrlAndEndPoint() {
		if (request.getEndPoint() == null || request.getEndPoint().isEmpty()) {
			request.addBaseUrl(request.getBaseUrl());
		} else if (request.getBaseUrl().endsWith("/") && request.getEndPoint().startsWith("/")) {
			request.addBaseUrl(request.getBaseUrl());
			request.addEndPoint(request.getEndPoint().substring(1));
		} else if (request.getBaseUrl().endsWith("/") && !request.getEndPoint().startsWith("/")) {
			request.addBaseUrl(request.getBaseUrl());
			request.addEndPoint(request.getEndPoint());
		} else if (!request.getBaseUrl().endsWith("/") && request.getEndPoint().startsWith("/")) {
			request.addBaseUrl(request.getBaseUrl() + "/");
			request.addEndPoint(request.getEndPoint().substring(1));
		} else {
			request.addBaseUrl(request.getBaseUrl() + "/");
			request.addEndPoint(request.getEndPoint());
		}
	}

	/**
	 * Load headers.
	 */
	private void loadHeaders() {
		if (request.getHeaders().isEmpty()) {
			request.addHeader(CONTENT_TYPE, APPLICATION_JSON);
			request.addHeader(ACCEPT, APPLICATION_JSON);
		}
		if (request.getContentType() == null || request.getContentType().isEmpty()) {
			request.addHeader(CONTENT_TYPE, APPLICATION_JSON);
			request.addHeader(ACCEPT, APPLICATION_JSON);
		}
	}

	/**
	 * Gets the http entity body.
	 *
	 * @return the http entity body
	 */
	private HttpEntity getHttpEntityBody() {
		HttpEntity entity = null;
		try {
			entity = new StringEntity(JavaUtil.toJson(request.getBody()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * Clear files.
	 */
	private static void clearFiles() {
		clearAllureResultsFiles();
		clearAllureReport();
	}

	/**
	 * Clear allure results files.
	 */
	private static void clearAllureResultsFiles() {
		try {
			Arrays.stream(new File("allure-results").listFiles()).forEach(File::delete);
			LOG.info("allure-results cleared from repository.");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Clear allure report.
	 */
	private static void clearAllureReport() {
		try {
			Arrays.stream(new File("allure-report").listFiles()).forEach(File::delete);
			LOG.info("allure-report cleared from repository.");
		} catch (NullPointerException e) {
		}
	}

}
