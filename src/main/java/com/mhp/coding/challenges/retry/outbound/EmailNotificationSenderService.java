package com.mhp.coding.challenges.retry.outbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;

import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;

import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;

import java.sql.SQLException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Validated
public class EmailNotificationSenderService implements NotificationSender {

	private static final String SENDER_ADDRESS = "info@mhp.com";

	private JavaMailSender mailSender;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationSenderService.class);
	private static int COUNTER = 0;

	public EmailNotificationSenderService(JavaMailSender mailSender) {
		this.mailSender = mailSender;

	}

	@Async
	@Override
	public void sendEmail(@Valid @NotNull EmailNotification emailNotification) {

		COUNTER++;
		LOGGER.info("COUNTER = " + COUNTER);
		sendMessage(emailNotification);
		try {
			Thread.sleep(3);
		} catch (InterruptedException ie) {
			System.out.println("Scanning...");

    	}
	}
	private void sendMessage(EmailNotification emailNotification) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(SENDER_ADDRESS);
			mailMessage.setTo(emailNotification.getRecipient());
			mailMessage.setSubject(emailNotification.getSubject());
			mailMessage.setText(emailNotification.getText());

			mailSender.send(mailMessage);

		} catch (Exception e) {
			throw new RuntimeException(
					String.format("Failed to send email to recipient: %s", emailNotification.getRecipient()));

		}
	}

	
	@Override
	public void recover(SQLException e) {
		LOGGER.info("In recover method");
	}

	
}
