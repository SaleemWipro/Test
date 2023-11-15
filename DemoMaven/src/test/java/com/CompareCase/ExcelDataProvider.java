package com.CompareCase;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "dispatchData")
    public static Object[][] provideDispatchData() {
        String excelFilePath = System.getProperty("user.dir") +"./src\\test\\java\\com\\TestData\\input.xlsx";
        String sheetName = "Sheet1"; // Change to the actual sheet name

        return ExcelUtils.readExcelData(excelFilePath, sheetName);
    
    }
}

