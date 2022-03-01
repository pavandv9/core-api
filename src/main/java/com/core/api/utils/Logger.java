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

import com.aventstack.extentreports.Status;
import com.core.api.Request;
import com.core.api.Response;
import com.core.api.config.ReportUtil;
import com.core.api.constants.DefProperty;
import com.core.api.constants.MailProperty;
import com.core.api.constants.ResourceFile;

import io.qameta.allure.Allure;

// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class Logger implements ILogger {

	/** The request. */
	private static Request request;
	
	/** The new line. */
	static String NEW_LINE = System.lineSeparator();
	
	/** The format. */
	private static String FORMAT = "%1$-15s%2$-20s%3$-50s";
	
	/** The format text. */
	private static String FORMAT_TEXT = FORMAT + NEW_LINE;
	
	/** The suffix. */
	static String suffix = "****************************************************************************************************";

	/**
	 * Log request.
	 *
	 * @param request the request
	 */
	public static void logRequest(Request request) {
		Logger.request = request;
		String prefix = NEW_LINE
				+ "********************************************* Request **********************************************"
				+ NEW_LINE;
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Http Method", ":", request.getHttpMethod()));
		builder.append(String.format(FORMAT_TEXT, "Base Url", ":", request.getBaseUrl()));
		builder.append(String.format(FORMAT_TEXT, "End Point", ":", formatEndPoint()));
		builder.append(String.format(FORMAT_TEXT, "Path Params", ":", prettyMap(request.getPathParams())));
		builder.append(String.format(FORMAT_TEXT, "Query Paramas", ":", prettyMap(request.getQueryParams())));
		builder.append(String.format(FORMAT_TEXT, "Headers", ":", prettyMap(request.getHeaders())));
		builder.append(String.format(FORMAT_TEXT, "Body", ":", NEW_LINE + getRequestBody()));
		String requestLog = prefix + builder.toString() + suffix;
		LOG.info(requestLog);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + suffix);
		Allure.addAttachment("Request", builder.toString());
		ReportUtil.logReqRes(Status.INFO, requestLog);
	}

	/**
	 * Log response.
	 *
	 * @param response the response
	 */
	public static void logResponse(Response response) {
		String prefix = NEW_LINE
				+ "********************************************* Response *********************************************"
				+ NEW_LINE;
		String suffix = "****************************************************************************************************";
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(FORMAT_TEXT, "Status code", ":", response.getStatusLine().getStatusCode()));
		builder.append(String.format(FORMAT_TEXT, "Status message", ":", response.getStatusLine().getStatusMessage()));
		builder.append(String.format(FORMAT_TEXT, "Headers", ":", prettyMap(response.getHeaders())));
		builder = appendBody(builder, response);
		builder.append(String.format(FORMAT_TEXT, "Find the report using command in project directory", ":", "allure serve"));
		String responseLog = prefix + builder.toString() + suffix;
		LOG.info(responseLog);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + suffix);
		Allure.addAttachment("Response", builder.toString());
		ReportUtil.logReqRes(Status.PASS, responseLog);
	}

	/**
	 * Pretty map.
	 *
	 * @param map the map
	 * @return the string
	 */
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
		return sb.toString().isEmpty() ? "<none>" : sb.toString();
	}

	/**
	 * Append body.
	 *
	 * @param builder the builder
	 * @param response the response
	 * @return the string builder
	 */
	private static StringBuilder appendBody(StringBuilder builder, Response response) {
		if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("5")) {
			try {
				builder.append(String.format(FORMAT_TEXT, "Response body", ":",
						NEW_LINE + JavaUtil.prettyJson(response.getBody().toString())));
			} catch (JSONException e) {
				if (e.getMessage().contains("JSONObject text must begin with")) {
					if (!request.getHeaders().equals(null)
							|| request.getHeaders().get("Accept").toString().contains("xml")) {
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

	/**
	 * Format end point.
	 *
	 * @return the string
	 */
	private static String formatEndPoint() {
		return (request.getEndPoint() == null || request.getEndPoint().isEmpty()) ? "<none>"
				: request.getEndPoint();
	}

	/**
	 * Gets the request body.
	 *
	 * @return the request body
	 */
	private static String getRequestBody() {
		return request.getContentType().contains("json") == true
				? JavaUtil.prettyJson(JavaUtil.toJson(request.getBody()))
				: JavaUtil.prettyXml(JavaUtil.toXml(request.getBody()));
	}

	/**
	 * Log mail properties.
	 */
	public static void logMailProperties() {
		String prefix = NEW_LINE
				+ "********************************************* Mail Properties **********************************************"
				+ NEW_LINE;
		String suffix = NEW_LINE
				+ "****************************************************************************************************";
		StringBuilder builder = new StringBuilder();
		builder.append(
				"To send mail fill the data in mail.properties available in src/main/resources, if not available refresh the folder. \nStill not visible create it in same folder and add below data to it");
		builder.append(NEW_LINE + MailProperty.SEND_MAIL.getValue() + "=true/false");
		builder.append(NEW_LINE + MailProperty.HOST.getValue() + "=gmail/outlook/office365");
		builder.append(NEW_LINE + MailProperty.FROM.getValue());
		builder.append(NEW_LINE + MailProperty.PASSWORD.getValue());
		builder.append(NEW_LINE + MailProperty.TO.getValue());
		builder.append(NEW_LINE + MailProperty.CC.getValue());
		builder.append(NEW_LINE + MailProperty.SUB.getValue());
		builder.append(NEW_LINE + MailProperty.TEXT.getValue());
		String mailProps = prefix + builder.toString() + suffix;
		LOG.info(mailProps);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + suffix);
	}

	/**
	 * Log mail request.
	 */
	public static void logMailRequest() {
		String prefix = NEW_LINE
				+ "********************************************* Mail Request **********************************************"
				+ NEW_LINE;
		String suffix = "****************************************************************************************************";
		StringBuilder builder = new StringBuilder();
		builder.append("Sending mail to below details..." + NEW_LINE);
		PropertyUtil.loadProperties(ResourceFile.MAIL_FILE);
		builder.append(String.format(FORMAT_TEXT, "Host", ":", PropertyUtil.get(MailProperty.HOST)));
		builder.append(String.format(FORMAT_TEXT, "Username", ":", PropertyUtil.get(MailProperty.FROM)));
		builder.append(String.format(FORMAT_TEXT, "To", ":", PropertyUtil.get(MailProperty.TO)));
		builder.append(String.format(FORMAT_TEXT, "Cc", ":", PropertyUtil.get(MailProperty.CC)));
		builder.append(String.format(FORMAT_TEXT, "Subject", ":",
				!PropertyUtil.get(MailProperty.SUB).isEmpty() ? PropertyUtil.get(MailProperty.SUB) : DefProperty.SUB));
		builder.append(String.format(FORMAT_TEXT, "Text", ":",
				!PropertyUtil.get(MailProperty.TEXT).isEmpty() ? PropertyUtil.get(MailProperty.TEXT)
						: DefProperty.TEXT));
		String mailProps = prefix + builder.toString() + suffix;
		LOG.info(mailProps);
		Reporter.log(JavaUtil.convertToHtml(prefix) + JavaUtil.convertToHtml(builder.toString()) + suffix);
	}

}
