package com.snh.email_sender.controller;

import com.snh.email_sender.helper.EmailBodyReader;
import com.snh.email_sender.helper.ExcelReader;
import com.snh.email_sender.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Value("${excelBodyPath}")
    private String emailBodyPath;
    @Value("${excelFilePath}")
    private String excelFilePath;
    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public String sendEmail() {

        try {
            List<String> recipients = ExcelReader.readEmailsFromExcel(excelFilePath);
            String emailBody = EmailBodyReader.getEmailBody(emailBodyPath);

            emailService.sendEmail(recipients, emailBody);
            return "Emails sent successfully!";
        } catch (IOException | MessagingException e) {
            return "Error while sending emails: " + e.getMessage();
        }
    }
}
