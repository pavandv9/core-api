/**
 * 
 */
package com.core.api.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.core.api.constants.DefProperty;
import com.core.api.constants.MailProperty;
import com.core.api.constants.ResourceFile;
import com.core.api.exception.MailException;
import com.core.api.utils.ILogger;
import com.core.api.utils.PropertyUtil;

/**
 * @author Pavan.DV
 *
 */
@Configuration
public class EmailConfig implements ILogger {

	public EmailConfig() {
		PropertyUtil.loadProperties(ResourceFile.MAIL_FILE);
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		switch (PropertyUtil.get(MailProperty.HOST).toLowerCase()) {
		case "gmail":
			mailSender.setHost("smtp.gmail.com");
			break;
		case "outlook":
			mailSender.setHost("smtp-mail.outlook.com");
			break;
		case "office365":
			mailSender.setHost("smtp.office365.com");
			break;
		default:
			LOG.error("not a valid host, host should be either gmail or outlook or office365");
			throw new MailException("invalid host");
		}
		mailSender.setPort(587);
		String user = PropertyUtil.get(MailProperty.FROM);
		if (user.length() == 0) {
			LOG.error("enter valid username in mail.properties");
			throw new MailException("invalid username");
		}
		mailSender.setUsername(user);
		String password = PropertyUtil.get(MailProperty.PASSWORD);
		if (password.length() == 0) {
			LOG.error("enter valid password in mail.properties");
			throw new MailException("enter valid password");
		}
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "false");

		return mailSender;
	}

	@Bean
	public SimpleMailMessage emailTemplate(StringBuilder testcases) {
		SimpleMailMessage message = new SimpleMailMessage();
		String to = PropertyUtil.get(MailProperty.TO);
		if (to.length() == 0)
			throw new MailException("enter valid 'to' address(comma seperated values for multiple emails)");
		message.setTo(splitEmailId(to));
		String cc = PropertyUtil.get(MailProperty.CC);
		if (cc.length() == 0)
			message.setCc(cc.length() != 0 ? splitEmailId(cc) : null);
		message.setFrom(PropertyUtil.get(MailProperty.FROM));
		message.setSubject(
				!PropertyUtil.get(MailProperty.SUB).isEmpty() ? PropertyUtil.get(MailProperty.SUB) : DefProperty.SUB);
		message.setText(!PropertyUtil.get(MailProperty.TEXT).isEmpty() ? PropertyUtil.get(MailProperty.TEXT)
				: DefProperty.TEXT + "\n\n" + testcases);
		return message;
	}

	public String[] splitEmailId(String ids) {
		return ids.split(",");
	}
}
