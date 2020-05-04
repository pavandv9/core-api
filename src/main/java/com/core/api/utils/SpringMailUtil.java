/**
 * 
 */
package com.core.api.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;

import com.core.api.constants.MailFile;
import com.core.api.constants.PropertyFile;

/**
 * @author Pavan.DV
 *
 */
public class SpringMailUtil implements ILogger {

	static Properties properties;

	public static void sendMail(StringBuilder testcases) {
		createFileIfNotExist();
		PropertyUtil.loadProperties(
				System.getProperty("user.dir") + "/src/main/resources/" + PropertyFile.MAIL_FILE.getValue());
		if (PropertyUtil.get(MailFile.SEND_MAIL).toLowerCase().equalsIgnoreCase("true")) {
			EmailConfig emailConfig = new EmailConfig();
			JavaMailSender mailSender = emailConfig.getJavaMailSender();
			mailSender.send(emailConfig.emailTemplate(testcases));
			Logger.logMailRequest();
		} else {
			Logger.logMailProperties();
		}
	}

	/**
	 * Create file {@link mail.properties} if doesn't exist in main/resources
	 * folder.
	 * 
	 * @return true if file created.
	 * @throws IOException
	 */
	private static void createFileIfNotExist() {
		String filePath = System.getProperty("user.dir") + "/src/main/resources/" + PropertyFile.MAIL_FILE.getValue();
		File file;
		try {
			file = new File(filePath);
			if (file.createNewFile()) {
				Properties props = PropertyUtil.loadProperties(filePath);
				properties = props;
				setDefaultProps();
				properties.store(new FileOutputStream(filePath), null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setDefaultProps() {
		properties.put(MailFile.SEND_MAIL.getValue(), "false");
		properties.put(MailFile.HOST.getValue(), "gmail/outlook/office365");
		properties.put(MailFile.USERNAME.getValue(), "");
		properties.put(MailFile.PASSWORD.getValue(), "");
		properties.put(MailFile.TO.getValue(), "");
		properties.put(MailFile.CC.getValue(), "");
		properties.put(MailFile.SUB.getValue(), "");
		properties.put(MailFile.TEXT.getValue(), "");
	}
}
