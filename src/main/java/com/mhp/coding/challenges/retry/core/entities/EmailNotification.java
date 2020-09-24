package com.mhp.coding.challenges.retry.core.entities;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailNotification {

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotBlank
    @Email(message = "Email should be valid")
    private String recipient;

    @NotBlank
    private String subject;

    @NotBlank
    private String text;
}
