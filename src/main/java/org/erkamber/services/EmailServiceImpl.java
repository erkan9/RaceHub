package org.erkamber.services;

import lombok.extern.slf4j.Slf4j;
import org.erkamber.services.interfaces.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.thymeleaf.context.Context;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final ITemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, ITemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    @Override
    public void sendEmail(String racerFirstName, String racerLastName, String trackName, String racerEmail, String trackTime) throws MessagingException {

        Context context = new Context();
        context.setVariable("racerFirstName", racerFirstName);
        context.setVariable("racerLastName", racerLastName);
        context.setVariable("trackName", trackName);
        context.setVariable("racerEmail", racerEmail);
        context.setVariable("trackTime", trackTime);

        String process = templateEngine.process("fastest", context);

        // Create and send the email message
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(racerEmail);
        helper.setSubject("Congratulations on Your Best Lap Time!");
        helper.setText(process, true);

        javaMailSender.send(message);
    }
}
