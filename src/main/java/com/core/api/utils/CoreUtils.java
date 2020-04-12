/**
 * 
 */
package com.core.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Pavan.DV
 *
 */
public class CoreUtils {

	public Properties readProperty(File file) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		return properties;
	}
}
