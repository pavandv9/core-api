/**
 * 
 */
package com.core.api.constants;

/**
 * @author Pavan.DV
 *
 */
public enum PropertyFile implements IConfig {

	CONFIG_FILE("config.properties"),
	MAIL_FILE("mail.properties");

	private String value;

	PropertyFile(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
