package com.TestNG;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 
public class TestNg06 {
	
	

 
 @DataProvider(name = "browserName")
 public Object[][] browserName() 
 {
 return new Object[][] { { "chrome,name,lastname,sdsd,sdsd" }, { "firefox,name,lastname,sdsd,sdsd" }  };
 }
 
 
 
 @Test(dataProvider = "browserName")
 public void crossBrowserTest(String browser, String name, String lastname, String sasa, String sdsd) 
 {
 //WebDriver driver = null;
 
 System.out.println("Launching browser : " + browser);
 
 switch(browser)
 {
 case "chrome":
	 System.out.println("chrome is launched");
 //System.setProperty("webdriver.chrome.driver", "<Path_to_your_chrome_driver>");
 //driver = new ChromeDriver();
 break;
 case "firefox":
	 System.out.println("fire is launched");
// System.setProperty("webdriver.firefox.driver", "<Path_to_your_firefox_driver>");
 //driver = new FirefoxDriver();
 break;
 default:
 System.out.println("Invalid browser name passed");
 }
 
 //driver.navigate().to("http://www.lambdatest.com/");
 System.out.println("Navigated Successfully to the website");
 }
 
}
