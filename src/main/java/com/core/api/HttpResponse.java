/**
 * 
 */
package com.core.api;

import java.util.HashMap;
import java.util.Map;

import com.core.api.exception.HttpException;
import com.core.api.model.StatusLine;
import com.core.api.utils.ILogger;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Pavan.DV
 *
 */
@ToString
public abstract class HttpResponse implements ILogger {

	/**
	 * Get status line
	 */
	@Getter
	protected StatusLine statusLine;

	/**
	 * Set status line
	 */
	protected abstract void setStatusLine();

	/**
	 * Get response body
	 */
	@Getter
	protected Object body;

	/**
	 * Set response body
	 */
	protected abstract void setBody();

	/**
	 * Get all headers
	 */
	@Getter
	protected Map<String, Object> headers = new HashMap<String, Object>();

	/**
	 * Set headers
	 */
	protected abstract void setHeaders();

	/**
	 * Parse response body.
	 * 
	 * @param jsonpath
	 * @return path of the value
	 */
	public String parse(String jsonpath) {
		String value = "";
		try {
			DocumentContext documentContext = JsonPath.parse(getBody().toString());
			value = documentContext.read(jsonpath).toString();
		} catch (PathNotFoundException e) {
			throw new HttpException("JsonPath [\"" + jsonpath + "\"] not found");
		}
		return value;
	}

//	@Ignore
//	private String readJSONFile(String jsonPath) {
//		String response = getBody().toString();
//		JSONObject jsonObject = new JSONObject(response);
//		Object obj = jsonObject;
//		for (String s : jsonPath.split("/"))
//			if (!s.isEmpty())
//				if (!(s.contains("[") || s.contains("]")))
//					try {
//						obj = ((JSONObject) obj).get(s);
//					} catch (ClassCastException e) {
//						throw new HttpException("JsonPath [\"" + jsonPath + "\"] not found");
//					}
//				else if (s.contains("[") || s.contains("]"))
//					obj = (((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
//							.get(Integer.parseInt(s.split("\\[")[1].replace("]", ""))));
//		return obj.toString();
//	}

}
