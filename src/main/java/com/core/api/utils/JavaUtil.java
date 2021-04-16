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

/**
 * @author Pavan.DV
 *
 */
public class JavaUtil implements ILogger {

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

	public static String convertToHtml(@NonNull String string) {
		return string.replaceAll("\\r?\\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}

	public static String prettyJson(Object json) {
		if (null == json) {
			return "<nil>";
		} else {
			try {
				String jsonStr = U.formatJson(json.toString(), Step.TWO_SPACES);
				return jsonStr;
			} catch (ClassCastException e) {
				throw new HttpException("Invalid json body " + toJson(json));
			}
		}
	}

	public static String prettyXml(Object xml) {
		return xml == null ? "<nil>" : U.formatXml(xml.toString());
	}

	public static boolean isJsonValid(Object object) {
		try {
			new ObjectMapper().readTree(object.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void executeShellCommand(String... shellCmd) {
		try {
			ProcessBuilder builder = new ProcessBuilder(shellCmd);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				LOG.info(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(
					String.format("Unable to execute shell command: %1s. %2s", shellCmd, e.getLocalizedMessage()));
		}
	}

	public static String getCurrentDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

}
