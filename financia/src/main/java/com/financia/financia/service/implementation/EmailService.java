package com.financia.financia.service.implementation;


import com.financia.financia.service.abstraction.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;
    private String frontendUrl;
    private String fromEmail;

    @Value("${app.frontend-url}")
    public void setFrontendUrl(String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    @Value("${spring.mail.username}")
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendActivationLinkEmail(String email, String token, String username) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(email);
        helper.setSubject("Attiva il tuo account - Financia");

        String confirmationUrl = frontendUrl + "/" + token;

        String emailContent =
                "<h2>Attivazione account</h2>" +
                        "<p>Salve " + username + "</p>" +
                        "<p>Benvenuto su Financia!</p>" +
                        "<p>Clicca sul link qui sotto per attivare il tuo account:</p>" +
                        "<p>" + confirmationUrl + "</p>" +
                        "<p>Questo link scadrà tra 24 ore.</p>" +

                        "<p>Cordiali saluti,<br>Il Team Financia</p>" +
                        "<p>Non sei tu?</p>" +
                        "</body></html>";

        helper.setText(emailContent, true);

        mailSender.send(message);

    }
}
