/**
 * 
 */
package com.core.api.constants;

/**
 * @author Pavan.DV
 *
 */
public enum MailFile implements IConfig {

	SEND_MAIL("send_mail"), HOST("host"), USERNAME("username"), PASSWORD("password"), TO("to"), CC("cc"),
	SUB("subject"), TEXT("text");

	private String value;

	MailFile(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}
