package com.MavenDemo;

import java.util.Map;


public class BaseDataProcessor {
    protected void processExtractedData(Map<String, Map<String, Map<String, Map<String, String>>>> dataMap) {
        // Process the data to find the minimum scan time for each dispatch number
        for (Map.Entry<String, Map<String, Map<String, Map<String, String>>>> companyEntry : dataMap.entrySet()) {
            String company = companyEntry.getKey();
            Map<String, Map<String, Map<String, String>>> workAreaMap = companyEntry.getValue();
                        for (Map.Entry<String, Map<String, Map<String, String>>> workAreaEntry : workAreaMap.entrySet()) {
                            String workArea = workAreaEntry.getKey();
                            Map<String, Map<String, String>> routeMap = workAreaEntry.getValue();

                            for (Map.Entry<String, Map<String, String>> routeEntry : routeMap.entrySet()) {
                                String routeNumber = routeEntry.getKey();
                                Map<String, String> dispatchMap = routeEntry.getValue();

                                for (Map.Entry<String, String> dispatchEntry : dispatchMap.entrySet()) {
                                    String dispatchNumber = dispatchEntry.getKey();
                                    String scanTime = dispatchEntry.getValue();

                                    // Process scanTime (e.g., convert to milliseconds, etc.)
                                    // Perform comparison to find the minimum scan time for each dispatch number

                                    // Example: Print the minimum scan time for each dispatch number
                                    System.out.println("Company: " + company +
                                            " | Work Area: " + workArea +
                                            " | Route Number: " + routeNumber +
                                            " | Dispatch Number: " + dispatchNumber +
                                            " | Minimum Scan Time: " + scanTime);
                                }
                            }
                        }
                    }
                }
            }
