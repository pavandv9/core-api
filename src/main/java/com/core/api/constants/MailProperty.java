/**
 * 
 */
package com.core.api.constants;

// TODO: Auto-generated Javadoc
/**
 * The Enum MailProperty.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public enum MailProperty implements IConfig {

	/** The send mail. */
	SEND_MAIL("send_mail"), /** The host. */
 HOST("host"), /** The from. */
 FROM("from"), /** The password. */
 PASSWORD("password"), /** The to. */
 TO("to"), /** The cc. */
 CC("cc"), /** The sub. */
 SUB("subject"),
	
	/** The text. */
	TEXT("text");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new mail property.
	 *
	 * @param value the value
	 */
	MailProperty(String value) {
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
