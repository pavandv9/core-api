/**
 * 
 */
package com.core.api;

import java.util.Properties;
import java.util.Set;

import com.core.api.constants.IConfig;
import com.core.api.constants.ResourceFile;
import com.core.api.utils.ILogger;
import com.core.api.utils.PropertyUtil;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigManager.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class ConfigManager implements ILogger {

	/** The props. */
	static Properties props = null;

	/**
	 * Loading property file.
	 */
	static {
		try {
			props = PropertyUtil.loadProperties(ResourceFile.CONFIG_FILE);
		}catch (NullPointerException e){
			LOG.debug("config.properties not found or No data found, proceeding with given parameters");
		}
	}

	/**
	 * Get value from config.properties file.
	 *
	 * @param key the key
	 * @return value
	 */
	public static String get(@NonNull String key) {
		return (String) props.get(key);
	}

	/**
	 * Get value from config.properties file.
	 *
	 * @param key the key
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
