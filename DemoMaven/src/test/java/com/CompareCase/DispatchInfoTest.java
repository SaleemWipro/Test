package com.CompareCase;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DispatchInfoTest extends BaseTest {

    private Map<String, Integer> minScanTimes = new HashMap<>();
    String filePath =System.getProperty("user.dir") +"./src\\test\\java\\com\\TestData\\input.xlsx";
    @Test(dataProvider = "dispatchData", dataProviderClass = ExcelDataProvider.class)
    public void testDispatchInfo(String company, String workArea, String route,
                                 String dispatchId, String scanTimeStr, String actualScanTimeStr, String Result) {
        int scanTime = Integer.parseInt(scanTimeStr);
        int actualScanTime = Integer.parseInt(actualScanTimeStr);

        if (scanTime >= actualScanTime) {
            processDispatchInfo(filePath, dispatchId, route, workArea, company, scanTime);
        }
    }

    private void processDispatchInfo(String filePath, String dispatchId, String route, String workArea,
            String company, int scanTime) {
String dispatchKey = dispatchId + "_" + route + "_" + workArea; // Combine dispatch ID, route, and work area

if (!minScanTimes.containsKey(dispatchKey)) {
minScanTimes.put(dispatchKey, scanTime);
String result = "MPU or LPU " + scanTime;
ExcelUtils.updateResultColumnInSheet(filePath, "Sheet1", dispatchId, route, workArea, company, scanTime, result);
printDispatchInfo(dispatchId, route, workArea, company, scanTime);
} else {
int currentMinScanTime = minScanTimes.get(dispatchKey);
if (scanTime < currentMinScanTime) {
minScanTimes.put(dispatchKey, scanTime);
String result = "MPU or LPU " + scanTime;
ExcelUtils.updateResultColumnInSheet(filePath, "Sheet1", dispatchId, route, workArea, company, scanTime, result);
printDispatchInfo(dispatchId, route, workArea, company, scanTime);
}
}
}

    
    private void printDispatchInfo(String dispatchId, String route, String workArea,
            String company, int scanTime) {
System.out.println("Dispatch ID: " + dispatchId);
System.out.println("Route: " + route);
System.out.println("Work Area: " + workArea);
System.out.println("Company: " + company);
System.out.println("Minimum Scan Time: " + scanTime);
System.out.println();
}

 
}
