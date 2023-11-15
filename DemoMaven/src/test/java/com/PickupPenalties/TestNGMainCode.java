package com.PickupPenalties;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TestNGMainCode {

    String path = System.getProperty("user.dir") + "./src\\test\\java\\com\\TestData\\input.xlsx";

    @DataProvider(name = "inputData")
    public Object[][] provideInputData() {
        return new Object[][]{
            {path, "Sheet1"}
        };
    }

    @Test(dataProvider = "inputData")
    public void testEligibility(String inputFilePath, String sheetName) {
        try {
            Workbook workbook = ExcelUtility.loadWorkbook(inputFilePath);
            Sheet sheet = workbook.getSheet(sheetName);

            // Create a map to store groups based on unique Dispatch daily number
            Map<String, List<Row>> groups = new HashMap<>();

            // Iterate through data rows and group them based on Dispatch daily number
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                String dispatchDailyNumber = row.getCell(4).getStringCellValue();
                String groupKey = row.getCell(0).getStringCellValue() +
                                 row.getCell(1).getStringCellValue() +
                                 row.getCell(2).getStringCellValue() +
                                 dispatchDailyNumber;

                groups.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(row);
            }

            // Iterate through groups and apply conditions
            for (List<Row> groupRows : groups.values()) {
                boolean hasOnTimeRow = false;

                for (Row row : groupRows) {
                    String company = row.getCell(0).getStringCellValue();
                    String location = row.getCell(1).getStringCellValue();
                    String route = row.getCell(2).getStringCellValue();
                    String dispatchDailyNumber = row.getCell(4).getStringCellValue();

                    double scanTime = Double.parseDouble(row.getCell(9).getStringCellValue());
                    double latestPickupTime = Double.parseDouble(row.getCell(12).getStringCellValue());
                    String mpuEligible = row.getCell(25).getStringCellValue();
                    String codeEligible = row.getCell(26).getStringCellValue();
                    String marking = ""; // Default marking

                    if (scanTime < latestPickupTime) {
                        hasOnTimeRow = true;
                        marking = "On Time";
                    } else {
                        if ("Y".equals(mpuEligible) && scanTime >= latestPickupTime) {
                            marking = "MPU Eligible";
                        } else if ("N".equals(mpuEligible) && "Y".equals(codeEligible) && scanTime >= latestPickupTime) {
                            marking = "LPU Eligible";
                        } else if ("N".equals(mpuEligible) && "N".equals(codeEligible)) {
                            marking = "Not Eligible";
                        }
                    }

                    // Print details and marking
                    System.out.println("Company: " + company + ", Location: " + location +
                                       ", Route: " + route + ", Dispatch Daily Number: " + dispatchDailyNumber +
                                       ", Scan Time: " + scanTime + ", Latest Pickup Time: " + latestPickupTime +
                                       ", MPU Eligible: " + mpuEligible + ", Code Eligible: " + codeEligible +
                                       ", Marking: " + marking);
                }

                if (!hasOnTimeRow) {
                    // Print additional result here if needed for groups without on-time rows
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ExcelUtility {

    public static Workbook loadWorkbook(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new XSSFWorkbook(fileInputStream);
    }
}
