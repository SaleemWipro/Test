package com.TestNG;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
 
public class TestNG04 {
 
 @Test(enabled = false, description = "This test will be skipped as enabled is set to false")
 public void disabledTest() {
 System.out.println("This method will be skipped from the test run using the attribute enabled=false");
 }
 
 @Test(invocationCount = 5, invocationTimeOut = 20, groups = "testngAttributes")
 public void invocationCountTest() {
 System.out.println("This method will be executed by 5 times");
 }
 
 @Test(timeOut = 500, groups = "testngAttributes")
 public void baseTest() {
 System.out.println("This is base test method");
 }
 
 @Test(dependsOnMethods = "baseTest", groups = "testngAttributes")
 public void firstDependentTest() {
 System.out.println("This is dependent method 1 and will only execute if baseTest passes");
 }
 
 @Test(alwaysRun = true, dependsOnMethods = "baseTest", groups = "testngAttributes")
 public void secondDependentTest() {
 System.out.println(
 "This is dependent method 2 and will execute irrespective of baseTest result because of alwaysRun");
 }
 
 @BeforeGroups("testngAttributes")
 public void beforeGroupMethod() {
 System.out.println("This method is executed before testngAttributes group test cases");
 }
 
 @AfterGroups("testngAttributes")
 public void afterGroupMethod() {
 System.out.println("This method is executed after testngAttributes group test cases");
 }
}