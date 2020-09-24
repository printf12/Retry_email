package com.mhp.coding.challenges.retry.configuration;

import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;
import com.mhp.coding.challenges.retry.core.logic.NotificationService;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry // To enable Spring Retry in an application, we need to add the @EnableRetry annotation
public class GlobalBeanConfiguration {
	/*
	 * The RetryPolicy determines when an operation should be retried. A
	 * SimpleRetryPolicy is used to retry a fixed number of times. On the other
	 * hand, the BackOffPolicy is used to control backoff between retry attempts.
	 * Finally, a FixedBackOffPolicy pauses for a fixed period of time before
	 * continuing.
	 */
	@Bean
	public NotificationHandler notificationHandler(NotificationSender notificationSender) {
		return new NotificationService(notificationSender);
	}

	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(5000l);
		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(2);
		retryTemplate.setRetryPolicy(retryPolicy);

		return retryTemplate;
	}
}
