package com.CompareCase;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public static Object[][] readExcelData(String excelFilePath, String sheetName) {
        Object[][] data = null;

        try (FileInputStream inputStream = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum();
            int columnCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][columnCount];

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < columnCount; j++) {
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = cellToString(cell);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static String cellToString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }
    public static void updateResultColumnInSheet(String filePath, String sheetName, String dispatchID, String route, String workArea, String company, int scanTime, String result) {
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            int dispatchIDColumnIndex = 3;   // Assuming "Dispatch ID" column index
            int routeColumnIndex = 2;        // Assuming "Route" column index
            int workAreaColumnIndex = 1;     // Assuming "WorkAreaID" column index
            int companyColumnIndex = 0;      // Assuming "Company" column index
            int resultColumnIndex = 7; 
            int scantimeIndex=5;// Assuming "Result" column index (after "Actual ScanTime")

            // Print some information for debugging
            System.out.println("Updating Result column for Dispatch ID: " + dispatchID);

            // Iterate through rows (starting from the second row since the first row has headers)
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                Cell dispatchIDCell = row.getCell(dispatchIDColumnIndex);
                Cell routeCell = row.getCell(routeColumnIndex);
                Cell workAreaCell = row.getCell(workAreaColumnIndex);
                Cell companyCell = row.getCell(companyColumnIndex);
                Cell ScanTimeCell = row.getCell(scantimeIndex);

                if (dispatchIDCell != null && dispatchIDCell.getStringCellValue().equals(dispatchID) &&
                    routeCell != null && routeCell.getStringCellValue().equals(route) &&
                    workAreaCell != null && workAreaCell.getStringCellValue().equals(workArea) &&
                    companyCell != null && companyCell.getStringCellValue().equals(company) &&
                    ScanTimeCell != null && ScanTimeCell.getStringCellValue().equals(Integer.toString(scanTime)))
                 {

                    // Create the "Result" cell
                    Cell resultCell = row.createCell(resultColumnIndex);
                    resultCell.setCellValue(result);
                    System.out.println("Result added: " + result); // Print the result value
                    break; // No need to continue checking other rows
                }
            }

            inputStream.close();

            // Write the updated workbook back to the Excel file
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            outputStream.close();

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}