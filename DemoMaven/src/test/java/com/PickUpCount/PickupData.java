package com.PickUpCount;

public class PickupData {
    private String company;
    private String state;
    private String area;
    private String route;
    private String postalCode;
    private String customerName;
    private String address;
    private String scanTime;

    public PickupData(String company, String state, String area, String route,
                      String postalCode, String customerName, String address, String scanTime) {
        this.company = company;
        this.state = state;
        this.area = area;
        this.route = route;
        this.postalCode = postalCode;
        this.customerName = customerName;
        this.address = address;
        this.scanTime = scanTime;
    }

    public String getCompany() {
        return company;
    }

    public String getState() {
        return state;
    }

    public String getArea() {
        return area;
    }

    public String getRoute() {
        return route;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return address;
    }

    public String getScanTime() {
        return scanTime;
    }
}
