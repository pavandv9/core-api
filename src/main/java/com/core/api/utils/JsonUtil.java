/**
 * 
 */
package com.core.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class JsonUtil {

	/**
	 * Read json file.
	 *
	 * @param filePath the file path
	 * @return the JSON array
	 */
	public static JSONArray readJsonFile(String filePath) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			Object obj = jsonParser.parse(new FileReader(filePath));
			jsonArray = (JSONArray) obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	/**
	 * Read json file.
	 *
	 * @param file the file
	 * @return the JSON array
	 */
	public static JSONArray readJsonFile(File file) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			Object obj = jsonParser.parse(new FileReader(file));
			jsonArray = (JSONArray) obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
}
