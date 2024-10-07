package com.snh.email_sender.helper;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public static List<Map<String, String>> readEmailsFromExcel(String filePath) throws IOException {
        List<Map<String, String>> recipientsList = new LinkedList<>();
        ClassPathResource resource = new ClassPathResource(filePath);

        try (InputStream excelFile = resource.getInputStream()) {

            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);  // Assuming recipientsList are in the first sheet

            for (Row row : sheet) {
                String recipientName = row.getCell(0).getStringCellValue();
                String recipientEmail = row.getCell(1).getStringCellValue();
                recipientsList.add(Map.of(recipientName, recipientEmail));
            }

            workbook.close();
        }
        return recipientsList;
    }
}
