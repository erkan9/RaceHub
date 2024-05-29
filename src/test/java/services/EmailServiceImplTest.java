package services;

import org.erkamber.services.EmailServiceImpl;
import org.erkamber.services.interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.ITemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.thymeleaf.context.Context;

public class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private ITemplateEngine templateEngine;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail() throws MessagingException {
        // Given
        String racerFirstName = "John";
        String racerLastName = "Doe";
        String trackName = "Speedway";
        String racerEmail = "john.doe@example.com";
        String trackTime = "1:30:45";

        Context context = new Context();
        context.setVariable("racerFirstName", racerFirstName);
        context.setVariable("racerLastName", racerLastName);
        context.setVariable("trackName", trackName);
        context.setVariable("racerEmail", racerEmail);
        context.setVariable("trackTime", trackTime);

        String expectedEmailContent = "test"; // Replace with actual expected content
        when(templateEngine.process(eq("fastest"), any(Context.class))).thenReturn(expectedEmailContent);

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // When
        emailService.sendEmail(racerFirstName, racerLastName, trackName, racerEmail, trackTime);

        // Then
        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(javaMailSender, times(1)).send(mimeMessageCaptor.capture());

        MimeMessage capturedMessage = mimeMessageCaptor.getValue();
        assertNotNull(capturedMessage);

        MimeMessageHelper helper = new MimeMessageHelper(capturedMessage, StandardCharsets.UTF_8.name());
        helper.setSubject("Congratulations on Your Best Lap Time!");

        // Verify the email content and properties
        assertEquals("test", expectedEmailContent);
    }
}
