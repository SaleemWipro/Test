package com.MavenDemo;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SelectMinAgeByLocationTest {

 
    String path = System.getProperty("user.dir") +"./src\\test\\java\\com\\TestData\\input.xlsx";


    @Test
    public void testSelectMinAgeByLocation() throws IOException {
        FileInputStream file = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Map<String, Integer> locationMinAgeMap = new HashMap<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String name = row.getCell(0).getStringCellValue();
            String location = row.getCell(1).getStringCellValue();
            int age = (int) row.getCell(2).getNumericCellValue();

            if (!locationMinAgeMap.containsKey(location) || age < locationMinAgeMap.get(location)) {
                locationMinAgeMap.put(location, age);
            }
        }

        workbook.close();

        // Now you have the map of locations and their corresponding minimum ages
        for (Map.Entry<String, Integer> entry : locationMinAgeMap.entrySet()) {
            String location = entry.getKey();
            int minAge = entry.getValue();

         
            System.out.println("Selected person for location " + location + ": Minimum Age - " + minAge);
        }
    }

}
