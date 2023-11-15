package com.DeliveryCount;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/java/com/TestData/input1.xlsx";

    public static List<DeliveryData> getDeliveryData() {
        List<DeliveryData> DeliveryDataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
        	Sheet sheet = workbook.getSheet("Sheet1");

        	 for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                 Row row = sheet.getRow(rowIndex);
                 
                 
                 String country = row.getCell(0).getStringCellValue();
                 String state = row.getCell(1).getStringCellValue();
                 String area = row.getCell(41).getStringCellValue();
                 String route = row.getCell(4).getStringCellValue();
                 String postalCode = row.getCell(32).getStringCellValue();
                 String customerName = row.getCell(34).getStringCellValue();
                 String address = row.getCell(30).getStringCellValue();
                 String scanTime = row.getCell(12).getStringCellValue();

                 DeliveryData DeliveryData = new DeliveryData(country, state, area, route, postalCode,
                         customerName, address, scanTime);
                 DeliveryDataList.add(DeliveryData);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }


        return DeliveryDataList;
    }
}


