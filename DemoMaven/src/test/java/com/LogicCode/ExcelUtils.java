package com.LogicCode;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelUtils {

	public static Map<String, Object[][]> readExcel(String filePath, String sheetName) throws IOException {
	    FileInputStream fis = new FileInputStream(filePath);
	    Workbook workbook = new XSSFWorkbook(fis);
	    Sheet sheet = workbook.getSheet(sheetName);

	    int rowCount = sheet.getLastRowNum();
	    int colCount = sheet.getRow(0).getLastCellNum();

	    Object[][] data = new Object[rowCount][colCount];
	    Object[] headers = new Object[colCount];

	    for (int i = 0; i < rowCount; i++) {
	        Row row = sheet.getRow(i + 1); // Skip header row
	        for (int j = 0; j < colCount; j++) {
	            Cell cell = row.getCell(j);
	            data[i][j] = cell.getStringCellValue(); // Assuming all values are strings
	        }
	    }

	    Row headerRow = sheet.getRow(0);
	    for (int j = 0; j < colCount; j++) {
	        Cell headerCell = headerRow.getCell(j);
	        headers[j] = headerCell.getStringCellValue();
	    }

	    workbook.close();
	    fis.close();

	    Map<String, Object[][]> result = new HashMap<>();
	    result.put("data", data);
	    result.put("headers", new Object[][]{headers});
	    return result;
	}


    
 // Inside your ExcelUtils class

    public static void writeExcel(String filePath, String sheetName, List<Object[]> data) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getLastRowNum();
            int colCount = data.get(0).length;

            for (Object[] rowData : data) {
                Row row = sheet.createRow(++rowCount);

                for (int i = 0; i < colCount; i++) {
                    Cell cell = row.createCell(i);
                    if (rowData[i] instanceof String) {
                        cell.setCellValue((String) rowData[i]);
                    } else if (rowData[i] instanceof Double) {
                        cell.setCellValue((Double) rowData[i]);
                    } // Add more data type handlers as needed
                }
            }

            fis.close();

            // Open an OutputStream to save written data into Excel file
            FileOutputStream os = new FileOutputStream(filePath);
            workbook.write(os);
            os.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

