package com.PickUpCount;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/java/com/TestData/input.xlsx";

    public static List<PickupData> getPickupData() {
        List<PickupData> pickupDataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
        	Sheet sheet = workbook.getSheet("Sheet1");

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                String country = row.getCell(0).getStringCellValue();
                String state = row.getCell(1).getStringCellValue();
                String area = row.getCell(27).getStringCellValue();
                String route = row.getCell(2).getStringCellValue();
                String postalCode = row.getCell(19).getStringCellValue();
                String customerName = row.getCell(16).getStringCellValue();
                String address = row.getCell(17).getStringCellValue();
                String scanTime = row.getCell(9).getStringCellValue();

                PickupData pickupData = new PickupData(country, state, area, route, postalCode,
                                                       customerName, address, scanTime);
                pickupDataList.add(pickupData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pickupDataList;
    }
}


