/**
 * 
 */
package com.core.api.constants;

/**
 * @author Pavan.DV
 *
 */
public enum ResourceFile implements IConfig {

	CONFIG_FILE("config.properties"), MAIL_FILE("mail.properties"), DB_FILE("db.json");

	private String value;

	ResourceFile(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
