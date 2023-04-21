package com.example.ResourceServer.mail;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class JavaMailSenderConfig {

    @Bean
    @Qualifier("sender")
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername("nikslav07@zohomail.eu");
        javaMailSender.setPassword("Kolbasa1289");
        javaMailSender.setHost("smtp.zoho.eu");
        javaMailSender.setPort(465);

        Properties senderProps = javaMailSender.getJavaMailProperties();
        senderProps.setProperty("mail.smtp.host", "smtp.zoho.eu");
        senderProps.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        senderProps.setProperty("mail.smtp.port", "465");
        senderProps.setProperty("mail.smtp.socketFactory.port", "465");
        senderProps.setProperty("mail.smtp.ssl.trust", "smtp.zoho.eu");
        senderProps.put("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }
}
