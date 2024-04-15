package vn.melowyeti.spring.spring_ecommerce_project.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Properties;

@Service
public class HtmlEmailSender {
    private final JavaMailSender javaMailSender;
    @Autowired
    public HtmlEmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = createMimeMessage(toEmail, subject, body);
        javaMailSender.send(message);
    }
    private MimeMessage createMimeMessage(String toEmail, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("lequantan1974@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject(subject);
        message.setText(body);
        return message;
    }
}
