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

    public static List<String> readEmailsFromExcel(String filePath) throws IOException {
        List<String> recipientsList = new LinkedList<>();
        ClassPathResource resource = new ClassPathResource(filePath);

        try (InputStream excelFile = resource.getInputStream()) {

            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if(row.getCell(0)!=null && "Name".equalsIgnoreCase(row.getCell(0).getStringCellValue())){
                    continue;
                }
                String recipientName = row.getCell(0)==null? "" : row.getCell(0).getStringCellValue();
                String recipientEmail = row.getCell(1)==null? "" : row.getCell(1).getStringCellValue();
                String companyName = row.getCell(2)==null? "" : row.getCell(2).getStringCellValue();
                String jobTitle = row.getCell(3)==null? "" : row.getCell(3).getStringCellValue();
                if(recipientEmail !="" && recipientName !="")
                    recipientsList.add(recipientName+"#"+recipientEmail+"#"+companyName+"#"+jobTitle);
            }

            workbook.close();
        }
        return recipientsList;
    }
}
