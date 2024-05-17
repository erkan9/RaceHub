package org.erkamber.services.interfaces;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(String racerFirstName, String racerLastName, String trackName, String racerEmail, String trackTime) throws MessagingException;
}
