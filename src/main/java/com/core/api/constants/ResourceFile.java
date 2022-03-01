/**
 * 
 */
package com.core.api.constants;

// TODO: Auto-generated Javadoc
/**
 * The Enum ResourceFile.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public enum ResourceFile implements IConfig {

	/** The config file. */
	CONFIG_FILE("config.properties"), /** The mail file. */
 MAIL_FILE("mail.properties"), /** The db file. */
 DB_FILE("db.json"), /** The db nosql file. */
 DB_NOSQL_FILE("db.nosql.json");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new resource file.
	 *
	 * @param value the value
	 */
	ResourceFile(String value) {
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
