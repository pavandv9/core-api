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

	public static Properties readProperty(@NonNull File file) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (IOException e) {
			throw new HttpException("Unable load properties");
		}
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
}
