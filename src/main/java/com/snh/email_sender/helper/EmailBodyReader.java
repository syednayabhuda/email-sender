package com.snh.email_sender.helper;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EmailBodyReader {

    public static String getEmailBody(String file) throws IOException {
        ClassPathResource resource = new ClassPathResource(file);
        Path path = resource.getFile().toPath();
        return new String(Files.readAllBytes(path));
    }
}
