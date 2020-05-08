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

/**
 * @author Pavan.DV
 *
 */
public class JsonUtil {

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
