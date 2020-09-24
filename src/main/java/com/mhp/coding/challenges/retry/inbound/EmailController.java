package com.mhp.coding.challenges.retry.inbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.constraints.Email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/emails")
public class EmailController {

	private NotificationHandler notificationHandler;

	public EmailController(NotificationHandler notificationHandler) {
		this.notificationHandler = notificationHandler;
	}

	@PostMapping
	public ResponseEntity<EmailNotification> createEmailNotification(@RequestBody EmailNotification emailNotification) {
		EmailNotification emailNotificationResult = notificationHandler.processEmailNotification(emailNotification);
		if (!isValidEmailAddress(emailNotificationResult.getRecipient())) {
			return (ResponseEntity<EmailNotification>) ResponseEntity.badRequest();
		}
		if (emailNotificationResult.getRecipient() == "") {
			return (ResponseEntity<EmailNotification>) ResponseEntity.badRequest();

		} else if (emailNotificationResult.getText() == "") {
			return (ResponseEntity<EmailNotification>) ResponseEntity.badRequest();

		} else if (emailNotificationResult.getSubject() == "") {
			return (ResponseEntity<EmailNotification>) ResponseEntity.badRequest();

		} else {
			return ResponseEntity.ok(emailNotificationResult);
		}
	}

	// check for valid email addresses
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
}
