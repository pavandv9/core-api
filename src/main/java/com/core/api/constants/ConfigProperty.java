/**
 * 
 */
package com.core.api.constants;

// TODO: Auto-generated Javadoc
/**
 * The Enum ConfigProperty.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public enum ConfigProperty implements IConfig {

	/** The env. */
	ENV("env"), /** The base url. */
 BASE_URL("base_url"), /** The auth token. */
 AUTH_TOKEN("auth_token"), /** The username. */
 USERNAME("user_name"), /** The password. */
 PASSWORD("passsword"),
	
	/** The authorization. */
	AUTHORIZATION("authorization");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new config property.
	 *
	 * @param value the value
	 */
	ConfigProperty(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see com.core.api.constants.IConfig#getValue()
	 */
	@Override
	public String getValue() {
		return value;
	}
}
