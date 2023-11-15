package com.PickUpCount;

import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PickupDataTest {

    @Test
    public void testPickupDataSorting() {
        List<PickupData> pickupDataList = ExcelReader.getPickupData();

        // Sort by Company (Ascending)
        List<PickupData> sortedByCompanyAsc = pickupDataList.stream()
                .sorted(Comparator.comparing(PickupData::getCompany))
                .collect(Collectors.toList());

        // Print sorted data
        System.out.println("Sorted By Company (Ascending):");
        for (PickupData data : sortedByCompanyAsc) {
            System.out.println("Company: " + data.getCompany());
            // Print other data as needed
        }

     // Define a custom comparator that compares the first three characters of Customer Name and Address
        Comparator<PickupData> customComparator = (data1, data2) -> {
            String name1 = data1.getCustomerName().substring(0, Math.min(3, data1.getCustomerName().length()));
            String name2 = data2.getCustomerName().substring(0, Math.min(3, data2.getCustomerName().length()));

            String address1 = data1.getCustomerAddress().substring(0, Math.min(3, data1.getCustomerAddress().length()));
            String address2 = data2.getCustomerAddress().substring(0, Math.min(3, data2.getCustomerAddress().length()));

            int nameComparison = name1.compareTo(name2);
            if (nameComparison != 0) {
                return nameComparison;
            }

            return address1.compareTo(address2);
        };

        // Sort by Location, Area, Route, Postal Code, Custom comparator, Scan Time (Ascending)
        List<PickupData> sortedByLocationAreaRoutePostalFirst3NameAddressNameAsc = pickupDataList.stream()
                .sorted(Comparator
                        .comparing(PickupData::getCompany)
                        .thenComparing(PickupData::getState)
                        .thenComparing(PickupData::getArea)
                        .thenComparing(PickupData::getRoute)
                        .thenComparing(PickupData::getPostalCode)
                        .thenComparing(customComparator)
                        .thenComparing(PickupData::getScanTime))
                .collect(Collectors.toList());

        // Print sorted data
        System.out.println("Sorted By Location, Area, Route, Postal Code, Custom comparator, Scan Time (Ascending):");
        for (PickupData data : sortedByLocationAreaRoutePostalFirst3NameAddressNameAsc) {
            System.out.print("Company: " + data.getCompany());
            System.out.print(" Location: " + data.getState());
            System.out.print(" Area: " + data.getArea());
            System.out.print(" Route: " + data.getRoute());
            System.out.print(" Postal Code: " + data.getPostalCode());
            System.out.print(" Customer Name: " + data.getCustomerName());
            System.out.print(" Customer Address: " + data.getCustomerAddress());
            System.out.print(" Scan Time: " + data.getScanTime());
            System.out.println("-------------------------------------");
        }


     // Iterate through the sorted data
        String currentWorkAreaId = null;
        String previousCustomerName = null;
        String previousScanTime = null;
        int pickupCount = 0;

 for (PickupData data : sortedByLocationAreaRoutePostalFirst3NameAddressNameAsc) {
     String Area = data.getArea();
     String route = data.getRoute();
     String postalCode = data.getPostalCode();
     String customerAddress = data.getCustomerAddress();
     String customerName = data.getCustomerName();
     String scanTime = data.getScanTime();

     if (currentWorkAreaId == null) {
         currentWorkAreaId = Area;
     }

     if (!Area.equals(currentWorkAreaId)) {
         // Print total count for previous work area
         System.out.println("Work Area ID: " + currentWorkAreaId + " - Total Pickups: " + pickupCount);
         
         // Reset pickup count and currentWorkAreaId for the new work area
         pickupCount = 0;
         currentWorkAreaId = Area;
     }

     if (!customerName.equals(previousCustomerName)) {
         // Reset pickup count for new customer
         pickupCount = 0;
     }

     // Check if it's the first row in the combination
     if (pickupCount == 0) {
         // Retrieve the previously stored pick-up count for the customer and add 1
    	 pickupCount = getStoredPickupCountForCustomer(Area, customerName, scanTime, previousScanTime) + 1;

     } else {
         // Calculate time difference in minutes
         int timeDifference = calculateTimeDifference(scanTime, previousScanTime);

         // Check if time difference is greater than or equal to 10 minutes
         if (timeDifference >= 10) {
             pickupCount++;
         }
     }

     // Update previous values for comparison in the next iteration
     previousCustomerName = customerName;
     previousScanTime = scanTime;
 }

 // Print total count for the last work area
 System.out.println("Work Area ID: " + currentWorkAreaId + " - Total Pickups: " + pickupCount);

}
    
 // Helper method to calculate time difference in minutes
    private int calculateTimeDifference(String currentTime, String previousTime) {
        int currentHours = Integer.parseInt(currentTime.substring(0, 2));
        int currentMinutes = Integer.parseInt(currentTime.substring(2, 4));
        
        int previousHours = Integer.parseInt(previousTime.substring(0, 2));
        int previousMinutes = Integer.parseInt(previousTime.substring(2, 4));
        
        // Convert time to minutes
        int currentTotalMinutes = currentHours * 60 + currentMinutes;
        int previousTotalMinutes = previousHours * 60 + previousMinutes;
        
        // Calculate the time difference in minutes
        int timeDifference = currentTotalMinutes - previousTotalMinutes;
        
        return timeDifference;
    }
    
 // Define a map to store pickup counts for each customer in the format: Area -> Customer Name -> Pickup Count
    private Map<String, Map<String, Integer>> pickupCountMap = new HashMap<>();

 // Method to get the stored pickup count for a customer
    private int getStoredPickupCountForCustomer(String area, String customerName, String scanTime, String previousScanTime) {
        // Retrieve the map for the current Area
        Map<String, Integer> areaMap = pickupCountMap.get(area);

        // If the map doesn't exist, return 0 (no previous pickup count)
        if (areaMap == null) {
            return 0;
        }

        // Retrieve the stored pickup count for the given customer
        Integer pickupCount = areaMap.get(customerName);

        // If the pickup count doesn't exist, return 0 (no previous pickup count)
        if (pickupCount == null) {
            return 0;
        }

        // Calculate time difference in minutes
        int timeDifference = calculateTimeDifference(scanTime, previousScanTime);

        // Check if time difference is greater than or equal to 10 minutes
        if (timeDifference >= 10) {
            // Increment the pickup count
            pickupCount++;
        }

        return pickupCount;
    }

}
