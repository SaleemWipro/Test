package com.LogicCode;

import org.testng.annotations.Test;
import java.util.*;
import java.util.List;

public class PickupTest {

    String path = System.getProperty("user.dir") + "./src\\test\\java\\com\\TestData\\input.xlsx";

    @Test
    public void testPickupLogic() throws Exception {
        Map<String, Object[][]> inputDataMap = ExcelUtils.readExcel(path, "Sheet1");
        Object[][] inputData = inputDataMap.get("data");
        Object[] inputHeaders = inputDataMap.get("headers")[0];

        // Create a map to store the results
        Map<String, String> resultMap = new HashMap<>();
        Map<String, Long> dispatchTimeMap = new HashMap<>(); // Store the minimum scan time for each dispatch daily number

        // Group input data by dispatch daily number
        Map<String, List<Object[]>> groupedData = new HashMap<>();
        for (Object[] row : inputData) {
            String dispatchNumber = row[4].toString();
            groupedData.computeIfAbsent(dispatchNumber, k -> new ArrayList<>()).add(row);
        }

     // Iterate through the grouped data
        for (List<Object[]> dispatchGroup : groupedData.values()) {
            // Get dispatch details from the group
            Object[] firstRow = dispatchGroup.get(0);
            String dispatchNumber = firstRow[4].toString();
            
            // Initialize flag to track if any row in the group is eligible
            boolean isEligible = false;

            // Check if any row in the group is eligible for On Time
            boolean onTimeEligibleFound = false;
            long minOnTimeScanTime = Long.MAX_VALUE;

            for (Object[] row : dispatchGroup) {
                String scanTime = row[9].toString();
                if (scanTime.compareTo(row[12].toString()) <= 0) {
                    resultMap.put(dispatchNumber, "On Time");
                    System.out.println("Dispatch " + dispatchNumber + " is On Time");
                    isEligible = true;
                    break; // No need to check further for this group
                }
            }
            

            if (onTimeEligibleFound) {
                resultMap.put(dispatchNumber, "On Time");
                System.out.println("Dispatch " + dispatchNumber + " is On Time");
            } 
             // If no On Time eligible row, check for MPU and LPU eligibility
            if (!isEligible) {
                boolean mpuEligibleFound = false;
                boolean lpuEligibleFound = false;
                long minMpuScanTime = Long.MAX_VALUE;
                long minLpuScanTime = Long.MAX_VALUE;
                String mpuResult = "MPU eligible";
                String lpuResult = "LPU eligible";

                for (Object[] row : dispatchGroup) {
                    String scanTime = row[9].toString();
                    String mpuEligible = row[25].toString();
                    String codeEligible = row[26].toString();

                    if (mpuEligible.equalsIgnoreCase("Y")) {
                        mpuEligibleFound = true;
                        long currentScanTime = Long.parseLong(scanTime);
                        if (currentScanTime < minMpuScanTime) {
                            minMpuScanTime = currentScanTime;
                        }
                    } else if (mpuEligible.equalsIgnoreCase("N") && codeEligible.equalsIgnoreCase("Y")) {
                        lpuEligibleFound = true;
                        long currentScanTime = Long.parseLong(scanTime);
                        if (currentScanTime < minLpuScanTime) {
                            minLpuScanTime = currentScanTime;
                        }
                    }
                }

                    // Assign results based on scan times for MPU and LPU eligible rows
                    if (mpuEligibleFound && !lpuEligibleFound) {
                        // Assign "MPU eligible" only to the row with the lowest scan time among MPU eligible rows
                        for (Object[] row : dispatchGroup) {
                            if (row[25].toString().equalsIgnoreCase("Y")) { // MPU eligible
                                long currentScanTime = Long.parseLong(row[9].toString());
                                if (currentScanTime == minMpuScanTime) {
                                    resultMap.put(dispatchNumber, mpuResult);
                                    System.out.println("Dispatch " + dispatchNumber + " " +currentScanTime+ " is MPU eligible");
                                } else {
                                    resultMap.put(dispatchNumber, ""); // Leave other MPU eligible rows blank
                                }
                            }
                        }
                    } else if (!mpuEligibleFound && lpuEligibleFound) {
                        // Assign "LPU eligible" only to the row with the lowest scan time among LPU eligible rows
                        for (Object[] row : dispatchGroup) {
                            if (row[26].toString().equalsIgnoreCase("Y")) { // LPU eligible
                                long currentScanTime = Long.parseLong(row[9].toString());
                                if (currentScanTime == minLpuScanTime) {
                                    resultMap.put(dispatchNumber, lpuResult);
                                    System.out.println("Dispatch " + dispatchNumber + " " +currentScanTime+ " is LPU eligible");
                                } else {
                                    resultMap.put(dispatchNumber, ""); // Leave other LPU eligible rows blank
                                }
                            }
                        }
                    } else if  (mpuEligibleFound && lpuEligibleFound) {
                       resultMap.put(dispatchNumber, ""); // Leave blank for both MPU and LPU eligible rows
                        
                        // Additional code to find the row with the lowest scan time among MPU eligible rows
                        for (Object[] row : dispatchGroup) {
                            String scanTime = row[9].toString();
                            String mpuEligible = row[25].toString();
                            String result = resultMap.get(row[4].toString());
                            
                            if (mpuEligible.equalsIgnoreCase("Y")) {
                            	System.out.println(scanTime + minMpuScanTime);
                                if (Long.parseLong(scanTime) == minMpuScanTime) {
                                    resultMap.put(row[4].toString(), "MPU Eligible");
                                 
                                    System.out.println("Dispatch " + dispatchNumber + " "+ scanTime+ " is MPU eligible");
                                } else {
                                  resultMap.put(row[4].toString(), ""); // Leave other MPU eligible rows blank
                                }
                            }
                        }
                    }
                }
        }


        
        // Define the headers for the output sheet
        List<Object[]> resultData = new ArrayList<>();
        Object[] resultHeaders = Arrays.copyOf(inputHeaders, inputHeaders.length + 1);
        resultHeaders[inputHeaders.length] = "Result";

        // Create a list to store the result data
        resultData.add(resultHeaders); // Add the headers to the result data

        // Iterate through the input data to populate the result data
        for (Object[] row : inputData) {
            String dispatchNumber = row[4].toString();
            String result = resultMap.get(dispatchNumber);

            // Create a new row with the existing data and the calculated result
            Object[] resultRow = Arrays.copyOf(row, row.length + 1);
            resultRow[row.length] = result;

            // Add the result row to the result data
            resultData.add(resultRow);
        }

        // Print the final result data for debugging
        for (Object[] row : resultData) {
            for (Object value : row) {
                System.out.print(value + " | ");
            }
            System.out.println(); // Newline
        }

        // Create an Excel sheet with the result data
        // String outputPath = System.getProperty("user.dir") + "./src\\test\\java\\com\\TestData\\output.xlsx";
        ExcelUtils.writeExcel(path, "Sheet5", resultData);
    }
}