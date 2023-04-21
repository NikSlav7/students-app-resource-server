package com.example.ResourceServer.mail;


import com.example.ResourceServer.domains.PasswordResetToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class PasswordResetMailSender {

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${frontend.domain}")
    private String frontendDomain;

    private final JavaMailSender javaMailSender;

    public PasswordResetMailSender(@Qualifier("sender") JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendPasswordResetEmail(String userEmail, String username, PasswordResetToken passwordResetToken) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setFrom(senderEmail);
        message.setTo(userEmail);
        message.setSubject(getSubject(username));
        message.setText(getBody(passwordResetToken), true);
        javaMailSender.send(mimeMessage);
    }

    private String getSubject(String username){
        return String.format("Password reset for %s", username);
    }

    private String getBody(PasswordResetToken passwordResetToken){
        return String.format("<p style='font-size: 27px'>Click <a href='%s'>here<a/> to reset your password. The link is valid for 15 minutes<p>", getLink(passwordResetToken.getToken()));
    }

    private String getLink(String token){
        return frontendDomain + "/password/reset?reset-password-token=" + token;
    }
}
