package com.mhp.coding.challenges.retry.core.outbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;

import java.sql.SQLException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface NotificationSender {
	/*
	 * the retry is attempted when an SQLException is thrown.
	 * The @Recover annotation defines a separate recovery method when a @Retryable
	 * method fails with a specified exception.
	 * 
	 * Consequently, if the retryServiceWithRecovery method keeps throwing a
	 * SqlException after 5 attempts, the recover() method will be called.
	 * 
	 * The recovery handler should have the first parameter of type Throwable
	 * (optional) and the same return type. The following arguments are populated
	 * from the argument list of the failed method in the same order.
	 */
	@Retryable(value = { RuntimeException.class }, maxAttempts = 5, backoff = @Backoff(5000))
	void sendEmail(@Valid @NotNull EmailNotification emailNotification);

	@Recover
	void recover(SQLException e);


}
