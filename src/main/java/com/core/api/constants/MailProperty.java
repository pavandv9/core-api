/**
 * 
 */
package com.core.api.constants;

/**
 * @author Pavan.DV
 *
 */
public enum MailProperty implements IConfig {

	SEND_MAIL("send_mail"), HOST("host"), FROM("from"), PASSWORD("password"), TO("to"), CC("cc"), SUB("subject"),
	TEXT("text");

	private String value;

	MailProperty(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}
