/**
 * 
 */
package com.core.api.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Reporter;

import com.core.api.HttpRequest;
import com.core.api.HttpResponse;

/**
 * @author Pavan.DV
 *
 */
public class Logger implements ILogger {

	private static HttpRequest httpRequest;
	private static String NEW_LINE = System.lineSeparator();
	private static String FORMAT = "%1$-15s%2$-20s%3$-50s";
	private static String FORMAT_TEXT = FORMAT + NEW_LINE;

	public static void logRequest(HttpRequest httpRequest) {
		Logger.httpRequest = httpRequest;
		String prefix = NEW_LINE
				+ "********************************************* Request **********************************************"
				+ NEW_LINE;
		String suffix = "****************************************************************************************************";
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Http Method", ":", httpRequest.getHttpMethod()));
		builder.append(String.format(FORMAT_TEXT, "Base Url", ":", httpRequest.getBaseUrl()));
		builder.append(String.format(FORMAT_TEXT, "End Point", ":", formatEndPoint()));
		builder.append(String.format(FORMAT_TEXT, "Path Params", ":", prettyMap(httpRequest.getPathParams())));
		builder.append(String.format(FORMAT_TEXT, "Query Paramas", ":", prettyMap(httpRequest.getQueryParams())));
		builder.append(String.format(FORMAT_TEXT, "Headers", ":", prettyMap(httpRequest.getHeaders())));
		builder.append(String.format(FORMAT_TEXT, "Body", ":", NEW_LINE + getJsonBody()));

		String requestLog = prefix + builder.toString() + suffix;
		LOG.info(requestLog);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + suffix);
	}

	public static void logResponse(HttpResponse response) {
		String prefix = NEW_LINE
				+ "********************************************* Response *********************************************"
				+ NEW_LINE;
		String suffix = "****************************************************************************************************";
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Status code", ":", response.getStatusLine().getStatusCode()));
		builder.append(String.format(FORMAT_TEXT, "Status message", ":", response.getStatusLine().getStatusMessage()));
		builder.append(String.format(FORMAT_TEXT, "Headers", ":", prettyMap(response.getHeaders())));
		builder = appendBody(builder, response);
		String responseLog = prefix + builder.toString() + suffix;
		LOG.info(responseLog);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + suffix);
	}

	private static String prettyMap(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			sb.append(entry.getKey());
			sb.append('=').append('"');
			sb.append(entry.getValue());
			sb.append('"');
			if (iter.hasNext()) {
				sb.append(',').append(String.format(NEW_LINE + "%35s", ""));
			}
		}
		return sb.toString().isEmpty() ? "<nil>" : sb.toString();
	}

	private static StringBuilder appendBody(StringBuilder builder, HttpResponse response) {
		if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("5")) {
			try {
				builder.append(String.format(FORMAT_TEXT, "Response body", ":",
						NEW_LINE + JavaUtil.prettyJson(response.getBody().toString())));
			} catch (JSONException e) {
				if (e.getMessage().contains("JSONObject text must begin with")) {
					if (!httpRequest.getHeaders().equals(null)
							|| httpRequest.getHeaders().get("Accept").toString().contains("xml")) {
						builder.append(String.format(FORMAT_TEXT, "Response body", ":",
								NEW_LINE + JavaUtil.prettyXml(response.getBody().toString())));
					} else {
						JSONObject xmlJsonObject = XML.toJSONObject(response.getBody().toString());
						String jsonBody = xmlJsonObject.toString(4);
						builder.append(String.format(FORMAT_TEXT, "Response body", ":",
								NEW_LINE + JavaUtil.prettyJson(jsonBody)));
					}
				} else
					throw new JSONException(e);
			}
		} else
			builder.append(String.format(FORMAT_TEXT, "Response body", ":", NEW_LINE + response.getBody()));
		return builder;
	}

	private static String formatEndPoint() {
		return (httpRequest.getEndPoint() == null || httpRequest.getEndPoint().isEmpty()) ? "<nil>"
				: httpRequest.getEndPoint();
	}

	private static String getJsonBody() {
		String body = JavaUtil.toJson(httpRequest.getBody());
		body = JavaUtil.prettyJson(body);
		return body;
	}
}
