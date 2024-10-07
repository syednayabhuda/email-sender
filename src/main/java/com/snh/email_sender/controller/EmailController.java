package com.snh.email_sender.controller;

import com.snh.email_sender.helper.EmailBodyReader;
import com.snh.email_sender.helper.ExcelReader;
import com.snh.email_sender.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    private static final String SENDER_EMAIL = "example@gmail.com";

    @GetMapping("/send")
    public String sendEmail() {
        String excelFilePath = "emails.xlsx";
        String emailBodyPath = "email.txt";
        String subject = "THIS IS A TEST EMAIL";

        try {
            List<Map<String, String>> recipients = ExcelReader.readEmailsFromExcel(excelFilePath);
            String emailBody = EmailBodyReader.getEmailBody(emailBodyPath);

            List<String> recipientsList = recipients.stream().flatMap(map -> map.values().stream()).collect(Collectors.toList());

            emailService.sendEmail(SENDER_EMAIL, recipientsList, subject, emailBody);
            return "Emails sent successfully!";
        } catch (IOException | MessagingException e) {
            return "Error while sending emails: " + e.getMessage();
        }
    }
}
