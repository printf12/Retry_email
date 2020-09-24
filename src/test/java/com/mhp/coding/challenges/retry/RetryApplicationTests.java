package com.mhp.coding.challenges.retry;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;

@SpringBootTest
class RetryApplicationTests {

	
	@Autowired
    private NotificationSender myService;

    @Autowired
    private RetryTemplate retryTemplate;
    @Test
    public void sendEmail() {
    	EmailNotification emailNotification = null ;
    	emailNotification.setRecipient("test@test.com");
    	emailNotification.setSubject("test");
    	emailNotification.setText("test");
        myService.sendEmail(emailNotification);
    }

    @Test
    public void givenRetryService_whenCallWithException_thenRetry() {
        myService.retryService();
    }

    @Test
    public void givenRetryServiceWithRecovery_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithRecovery(null);
    }

   

    @Test
    public void givenRetryServiceWithExternalConfiguration_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithExternalConfiguration(null);
    }

    @Test
    public void givenTemplateRetryService_whenCallWithException_thenRetry() {
        retryTemplate.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
    }
}
