/**
 * 
 */
package com.core.api.utils;

import com.core.api.exception.HttpException;
import com.github.underscore.lodash.Json.JsonStringBuilder.Step;
import com.github.underscore.lodash.U;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 */
public class JavaUtil {

	public static String toJson(Object object) {
		String json = "";
		if (null == object) {
			return null;
		} else {
			if (isValidJson(object))
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

	public static boolean isValidJson(Object json) {
		Gson gson = new Gson();
		try {
			gson.fromJson(json.toString(), Object.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
