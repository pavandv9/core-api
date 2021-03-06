/**
 * 
 */
package com.core.api.constants;

/**
 * @author Pavan.DV
 *
 */
public enum ConfigProperty implements IConfig {

	ENV("env"), BASE_URL("base_url"), AUTH_TOKEN("auth_token"), USERNAME("user_name"), PASSWORD("passsword"),
	AUTHORIZATION("authorization");

	private String value;

	ConfigProperty(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
