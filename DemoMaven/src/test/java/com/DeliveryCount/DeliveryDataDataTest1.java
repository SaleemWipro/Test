package com.DeliveryCount;

import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeliveryDataDataTest1 {


	@Test
	public void testPickupDataSortingAndCounting() {
	    List<DeliveryData> DeliveryDataList = ExcelReader.getDeliveryData();

	    List<DeliveryData> sortedDeliveryData = DeliveryDataList.stream()
	            .sorted(Comparator
	                    .comparing(DeliveryData::getCompany)
	                    .thenComparing(DeliveryData::getState)
	                    .thenComparing(DeliveryData::getArea)
	                    .thenComparing(DeliveryData::getRoute)
	                    .thenComparing(DeliveryData::getPostalCode)
	                    .thenComparing(customComparator)
	                    .thenComparing(DeliveryData::getScanTime))
	            .collect(Collectors.toList());

	    
	 // Print sorted data
	    System.out.println("Sorted By Location, Area, Route, Postal Code, Custom comparator, Scan Time (Ascending):");
	    for (DeliveryData data : sortedDeliveryData) {
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

	    // Initialize variables for tracking pickups
	    String currentArea = null;
	    String currentCompany = null;
	    String currentWorkAreaID = null;
	    String previousCustomerName = null;
	    String previousScanTime = null;
	    int pickupCount = 0;
	    int areaTotalPickups = 0;

	    // Iterate through the sorted pickup data
	    for (DeliveryData data : sortedDeliveryData) {
	        String area = data.getArea();
	        String company = data.getCompany();
	        String workAreaID = data.getState();
	        String customerName = data.getCustomerName();
	        String scanTime = data.getScanTime();

	        if (currentArea == null) {
	            currentArea = area;
	            currentCompany = company;
	            currentWorkAreaID = workAreaID;
	        }

	        if (!area.equals(currentArea) || !company.equals(currentCompany) || !workAreaID.equals(currentWorkAreaID)) {
	            // Print pickup count for the previous customer within the area
	            if (previousCustomerName != null) {
	                System.out.println("   " + previousCustomerName + ": " + pickupCount + " pickups");
	                areaTotalPickups += pickupCount;
	            }

	            // Print total pickups for the area
	            System.out.println("Area: " + currentArea + " (Mapped to " + currentCompany + ", Work Area ID: " + currentWorkAreaID + ") - Total Deliveries: " + areaTotalPickups);

	            // Reset pickup count and area-related variables for the new area
	            pickupCount = 0;
	            areaTotalPickups = 0;
	            currentArea = area;
	            currentCompany = company;
	            currentWorkAreaID = workAreaID;
	            previousCustomerName = null; // Reset the customer name for the new area
	        }

	        if (!customerName.equals(previousCustomerName)) {
	            // Print pickup count for the previous customer
	            if (previousCustomerName != null) {
	                System.out.println("   " + previousCustomerName + ": " + pickupCount + " pickups");
	                areaTotalPickups += pickupCount;
	            }

	            // Reset pickup count for new customer
	            pickupCount = 1;  // First row for a new customer is always considered as 1 pickup
	            previousCustomerName = customerName; // Update the customer name
	        } else {
	            // Calculate time difference in minutes
	            int timeDifference = calculateTimeDifference(scanTime, previousScanTime);

	            // Check if time difference is greater than or equal to 10 minutes
	            if (timeDifference >= 2) {
	                pickupCount++;
	            }
	        }

	        // Update previous values for comparison in the next iteration
	        previousScanTime = scanTime;
	    }

	    // Print pickup count for the last customer within the last area
	    if (previousCustomerName != null) {
	        System.out.println("   " + previousCustomerName + ": " + pickupCount + " pickups");
	        areaTotalPickups += pickupCount;
	    }

	    // Print total pickups for the last area
	    System.out.println("Area: " + currentArea + " (Mapped to " + currentCompany + ", Work Area ID: " + currentWorkAreaID + ") - Total Deliveries: " + areaTotalPickups);
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

    // Define a custom comparator for first three characters of Customer Name and Address
    Comparator<DeliveryData> customComparator = (data1, data2) -> {
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
}
