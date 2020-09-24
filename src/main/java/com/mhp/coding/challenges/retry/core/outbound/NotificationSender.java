package com.mhp.coding.challenges.retry.core.outbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;

import java.sql.SQLException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface NotificationSender {

	@Retryable(value = { RuntimeException.class }, maxAttempts = 5, backoff = @Backoff(5000))
	void sendEmail(@Valid @NotNull EmailNotification emailNotification);

	@Recover
	void recover(SQLException e, String sql);

	void templateRetryService();

	void retryService();

	void retryServiceWithRecovery(String sql) throws SQLException;

	

	void retryServiceWithExternalConfiguration(String sql) throws SQLException;
}
