/**
 * 
 */
package com.core.api.constants;

/**
 * @author Pavan.DV
 *
 */
public enum ConfigFile implements IConfig {

	BASE_URL("base_url"), AUTH_TOKEN("auth_token"), USERNAME("user_name"), PASSWORD("passsword");

	private String value;

	ConfigFile(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
