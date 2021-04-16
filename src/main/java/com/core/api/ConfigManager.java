/**
 * 
 */
package com.core.api;

import java.util.Properties;
import java.util.Set;

import com.core.api.constants.IConfig;
import com.core.api.constants.ResourceFile;
import com.core.api.utils.PropertyUtil;

import lombok.NonNull;

/**
 * @author Pavan.DV
 *
 */
public class ConfigManager {

	static Properties props;

	/**
	 * Loading property file.
	 */
	static {
		props = PropertyUtil.loadProperties(ResourceFile.CONFIG_FILE);
	}

	/**
	 * Get value from config.properties file.
	 * 
	 * @param key
	 * @return value
	 */
	public static String get(@NonNull String key) {
		return (String) props.get(key);
	}

	/**
	 * Get value from config.properties file.
	 * 
	 * @param key
	 * @return value
	 */
	public static String get(@NonNull IConfig key) {
		return (String) props.get(key.getValue());
	}

	/**
	 * Get all config.properties names.
	 * 
	 * @return set of names
	 */
	public Set<String> getConfigProperyNames() {
		return props.stringPropertyNames();
	}
}
