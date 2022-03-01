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

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyUtil.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class PropertyUtil {

	/** The properties. */
	static Properties properties;

	/**
	 * Gets the single instance of PropertyUtil.
	 *
	 * @return single instance of PropertyUtil
	 */
	private static PropertyUtil getInstance(){
		return new PropertyUtil();
	}

	/**
	 * Load properties.
	 *
	 * @param file the file
	 * @return the properties
	 */
	public static Properties loadProperties(@NonNull File file) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (IOException e) {
			throw new HttpException("Unable load property file");
		}
		properties = prop;
		return prop;
	}

	/**
	 * Load properties.
	 *
	 * @param fileName the file name
	 * @return the properties
	 */
	public static Properties loadProperties(@NonNull String fileName) {
		return loadProperties(new File(fileName));
	}

	/**
	 * Load properties.
	 *
	 * @param fileName the file name
	 * @return the properties
	 */
	public static Properties loadProperties(@NonNull IConfig fileName) {
		return loadProperties(getInstance().getResourceFile(fileName));
	}

	/**
	 * Gets the resource file.
	 *
	 * @param configFileName the config file name
	 * @return the resource file
	 */
	public File getResourceFile(IConfig configFileName){
		return new File(getClass().getClassLoader().getResource(configFileName.getValue()).getFile());
	}

	/**
	 * Gets the resource file.
	 *
	 * @param configFileName the config file name
	 * @return the resource file
	 */
	public File getResourceFile(String configFileName) {
		return new File(getClass().getClassLoader().getResource(configFileName).getFile());
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String get(String key) {
		return properties.get(key).toString();
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String get(IConfig key) {
		return properties.get(key.getValue()).toString();
	}

}
