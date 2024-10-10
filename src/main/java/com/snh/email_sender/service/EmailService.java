package com.snh.email_sender.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${linkedInUrl}")
    private String linkedInUrl;

    @Value("${githubUrl}")
    private String githubUrl;

    @Value("${filePath}")
    private String filePath;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(List<String> recipients, String body) throws MessagingException {

        body = body.replace("[Platform/Source]", "LinkedIn");
        body = body.replace("[LinkedIn]", linkedInUrl);
        body = body.replace("[GitHub]", githubUrl);

        for(int i = 0; i<recipients.size(); i++){
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            String[] data = recipients.get(i).split("#");
            String subject = "Application for "+ data[3];
            body = body.replace("[Recruiter’s Name]", data[0]);
            body = body.replace("[Job Title]", data[3]);
            body = body.replace("[Company Name]", data[2]);

            File file = new File(filePath);
            helper.setFrom(fromEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setTo(data[1]);
            helper.addAttachment(file.getName(), file);

            System.out.println("Sent Mail to " + data[1]);
            mailSender.send(mimeMessage);
        }
    }
}
