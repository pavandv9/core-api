/**
 * 
 */
package com.core.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.core.api.constants.IConfig;
import com.core.api.exception.HttpException;

import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 */
public class PropertyUtil {

	static Properties properties;

	public static Properties readProperty(@NonNull File file) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (IOException e) {
			throw new HttpException("Unable load property file");
		}
		properties = prop;
		return prop;
	}

	public static Properties loadProperties(@NonNull String fileName) {
		return readProperty(new File(fileName));
	}

	public static Properties loadProperties(@NonNull IConfig fileName) {
		return readProperty(new PropertyUtil().getFile(fileName));
	}

	public File getFile(IConfig configFileName) {
		return new File(getClass().getClassLoader().getResource(configFileName.getValue()).getFile());
	}

	public File getFile(String configFileName) {
		return new File(getClass().getClassLoader().getResource(configFileName).getFile());
	}

	public static String get(String key) {
		return properties.get(key).toString();
	}

	public static String get(IConfig key) {
		return properties.get(key.getValue()).toString();
	}

}
