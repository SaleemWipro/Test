package com.TestNG;

import org.testng.annotations.Test;

public class TestNg05 {
 @Test(priority = 1)
 public void aTest() {
 System.out.println("This is test a.");
 }
 
 @Test(priority = 2)
 public void bTest() {
 System.out.println("This is test b.");
 }
 
 @Test
 public void cTest() {
 System.out.println("This is test c.");
 }
 
 @Test(priority = 1)
 public void dTest() {
 System.out.println("This is test d.");
 }
 
 @Test
 public void eTest() {
 System.out.println("This is test e.");
 }
 
}
