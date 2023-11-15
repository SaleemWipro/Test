package com.PickupPenalties;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TestNgMainCode1 {

    String path = System.getProperty("user.dir") + "/src/test/java/com/TestData/input.xlsx";

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

                // Collect rows based on different markings
                List<Row> onTimeRows = new ArrayList<>();
                List<Row> mpuEligibleRows = new ArrayList<>();
                List<Row> lpuEligibleRows = new ArrayList<>();
                List<Row> NotEligibleRows = new ArrayList<>();

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
                /*    System.out.println("Company: " + company + ", Location: " + location +
                                       ", Route: " + route + ", Dispatch Daily Number: " + dispatchDailyNumber +
                                       ", Scan Time: " + scanTime + ", Latest Pickup Time: " + latestPickupTime +
                                       ", MPU Eligible: " + mpuEligible + ", Code Eligible: " + codeEligible +
                                       ", Marking: " + marking);
*/
                    // Determine eligibility and collect rows
                   // System.out.println(marking);
                    if ("On Time".equals(marking)) {
                    	//System.out.println(1);
                        onTimeRows.add(row);
                    } else if ("MPU Eligible".equals(marking)) {
                        mpuEligibleRows.add(row);
                       // System.out.println(2);
                    } else if ("LPU Eligible".equals(marking)) {
                        lpuEligibleRows.add(row);
                       // System.out.println(3);
                    }else if ("Not Eligible".equals(marking)) {
                        NotEligibleRows.add(row);
                       // System.out.println(3);
                    }
                    
                }

                // Apply additional conditions
                if (!onTimeRows.isEmpty()) {
                    for (Row row : onTimeRows) {
                        printRowDetails(row, "On Time", null); // No lowestScanTime for On Time rows
                    }
                        for (Row row : mpuEligibleRows) {
                            printRowDetails(row, "MPU Eligible", null);
                        }
                        for (Row row : lpuEligibleRows) {
                            printRowDetails(row, "LPU Eligible", null);
                        }
                        for (Row row : NotEligibleRows) {
                            printRowDetails(row, "Not Eligible", null);
                        }
                    
                } else if (!mpuEligibleRows.isEmpty() && !lpuEligibleRows.isEmpty()) {
                    Row lowestScanTimeMpu = findRowWithLowestScanTime(mpuEligibleRows);
                   // System.out.println("Marking: MPU Eligible (Lowest Scan Time)");

                  //  lpuEligibleRows.remove(lowestScanTimeMpu);
                    for (Row row : mpuEligibleRows) {
                        printRowDetails(row, "MPU Eligible", lowestScanTimeMpu);
                    }
                    for (Row row : lpuEligibleRows) {
                        printRowDetails(row, "LPU Eligible", null);
                    }
                    for (Row row : NotEligibleRows) {
                        printRowDetails(row, "Not Eligible", null); // No lowestScanTime for On Time rows
                    }
                } else if (!lpuEligibleRows.isEmpty()) {
                    Row lowestScanTimeLpu = findRowWithLowestScanTime(lpuEligibleRows);
                  //  System.out.println("Marking: LPU Eligible (Lowest Scan Time)");

                    for (Row row : lpuEligibleRows) {
                        printRowDetails(row, "LPU Eligible", lowestScanTimeLpu);
                    }
                } else if (!mpuEligibleRows.isEmpty()) {
                    Row lowestScanTimeMpu = findRowWithLowestScanTime(mpuEligibleRows);
                   // System.out.println("Marking: MPU Eligible (Lowest Scan Time)");

                    for (Row row : mpuEligibleRows) {
                        printRowDetails(row, "MPU Eligible", lowestScanTimeMpu);
                    }
                }
                else if (!NotEligibleRows.isEmpty()) {
                	 for (Row row : NotEligibleRows) {
                         printRowDetails(row, "Not Eligible", null); // No lowestScanTime for On Time rows
                     }
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ... ExcelUtility class remains the same ...

    // Helper method to find the row with the lowest scan time
    private Row findRowWithLowestScanTime(List<Row> rows) {
        Row lowestRow = rows.get(0);
        double lowestScanTime = Double.parseDouble(lowestRow.getCell(9).getStringCellValue());

        for (Row row : rows) {
            double scanTime = Double.parseDouble(row.getCell(9).getStringCellValue());
            if (scanTime < lowestScanTime) {
                lowestRow = row;
                lowestScanTime = scanTime;
            }
        }

        return lowestRow;
    }
    // Helper method to print row details
    private void printRowDetails(Row row, String marking, Row lowestScanTimeRow) {
        String company = row.getCell(0).getStringCellValue();
        String location = row.getCell(1).getStringCellValue();
        String route = row.getCell(2).getStringCellValue();
        String dispatchDailyNumber = row.getCell(4).getStringCellValue();
        double scanTime = Double.parseDouble(row.getCell(9).getStringCellValue());
        double latestPickupTime = Double.parseDouble(row.getCell(12).getStringCellValue());
        String mpuEligible = row.getCell(25).getStringCellValue();
        String codeEligible = row.getCell(26).getStringCellValue();
        double lowestScanTime = (lowestScanTimeRow != null) ?
                                Double.parseDouble(lowestScanTimeRow.getCell(9).getStringCellValue()) :
                                -1; // Indicate no lowestScanTime for this row

        System.out.println("Company: " + company + ", Location: " + location +
                           ", Route: " + route + ", Dispatch Daily Number: " + dispatchDailyNumber +
                           ", Scan Time: " + scanTime + ", Latest Pickup Time: " + latestPickupTime +
                           ", MPU Eligible: " + mpuEligible + ", Code Eligible: " + codeEligible +
                           ", Marking: " + marking + ", Lowest Scan Time: " + (lowestScanTime == -1 ? "" : lowestScanTime));
    }
}


