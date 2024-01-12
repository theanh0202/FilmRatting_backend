package com.example.filmrating.service.iml;


import com.example.filmrating.service.IMailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService implements IMailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendMessageWithHtml(String to, String subject, String text) {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = null;
            try {
                helper = new MimeMessageHelper(message, true);
                helper.setFrom("noreply@baeldung.com");
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(text, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        // text có thể để dạng html = true
//        FileSystemResource file
//                = new FileSystemResource(new File(pathToAttachment));
//        helper.addAttachment("Invoice", file);// File có thể là hình ảnh hoặc pdf,...
        emailSender.send(message);
    }
}
