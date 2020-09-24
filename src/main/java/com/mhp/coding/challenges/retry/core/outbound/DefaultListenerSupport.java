package com.mhp.coding.challenges.retry.core.outbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class DefaultListenerSupport extends RetryListenerSupport {

	private static final Logger logger = LoggerFactory.getLogger(DefaultListenerSupport.class);

	/*
	 * The RetryCallback which is a parameter of the execute() is an interface that
	 * allows insertion of business logic that needs to be retried upon failure
	 */
	/*
	 * The open and close callbacks come before and after the entire retry, while
	 * onError applies to the individual RetryCallback calls.
	 */

	@Override
	public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		logger.info("onClose");
		super.close(context, callback, throwable);
	}

	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
			Throwable throwable) {
		logger.info("onError");
		super.onError(context, callback, throwable);
	}

	@Override
	public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
		logger.info("onOpen");
		return super.open(context, callback);
	}

}