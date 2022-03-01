/**
 * 
 */
package com.core.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.core.api.exception.HttpException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.underscore.lodash.Json.JsonStringBuilder.Step;
import com.github.underscore.lodash.U;
import com.google.gson.GsonBuilder;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class JavaUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class JavaUtil implements ILogger {

	/**
	 * To json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toJson(Object object) {
		String json = "";
		if (null == object) {
			return null;
		} else {
			if (isJsonValid(object))
				json = object.toString();
			else
				json = new GsonBuilder().serializeNulls().create().toJson(object);
		}
		return json;
	}

	/**
	 * Convert to html.
	 *
	 * @param string the string
	 * @return the string
	 */
	public static String convertToHtml(@NonNull String string) {
		return string.replaceAll("\\r?\\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}

	/**
	 * Pretty json.
	 *
	 * @param json the json
	 * @return the string
	 */
	public static String prettyJson(Object json) {
		if (null == json) {
			return "<none>";
		} else {
			try {
				String jsonStr = U.formatJson(json.toString(), Step.TWO_SPACES);
				return jsonStr;
			} catch (ClassCastException e) {
				throw new HttpException("Invalid json body " + toJson(json));
			}
		}
	}

	/**
	 * Pretty xml.
	 *
	 * @param xml the xml
	 * @return the string
	 */
	public static String prettyXml(Object xml) {
		return xml == null ? "<none>" : U.formatXml(xml.toString());
	}

	/**
	 * Checks if is json valid.
	 *
	 * @param object the object
	 * @return true, if is json valid
	 */
	public static boolean isJsonValid(Object object) {
		try {
			new ObjectMapper().readTree(object.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Execute shell command.
	 *
	 * @param shellCmd the shell cmd
	 * @return the string
	 */
	public static String executeShellCommand(String... shellCmd) {
		try {
			ProcessBuilder builder = new ProcessBuilder(shellCmd);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			LOG.info("Execution of shell command in progress...");
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					LOG.info("Executing of shell command is completed.");
					return line;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(
					String.format("Unable to execute shell command: %1s. %2s", shellCmd, e.getLocalizedMessage()));
		}
	}

	/**
	 * Gets the current date.
	 *
	 * @param pattern the pattern
	 * @return the current date
	 */
	public static String getCurrentDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * To xml.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static String toXml(Object obj) {
		return U.jsonToXml(toJson(obj));
	}

}
