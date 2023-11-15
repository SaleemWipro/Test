package com.MavenDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class examtest {
	 
	@Parameters("browser")
	@Test
	public static void main(String Browser) throws InterruptedException {
		WebDriver driver;
	    // Saving the expected title of the Webpage
	    String title = "ToolsQA - Demo Website For Automation";
	    
	  
	    	System.out.println("This is the starting point of the test");
	    	System.setProperty("webdriver.chrome.driver", "C:\\Automation_Training\\chromedriver.exe");
	    	 driver = new ChromeDriver();
	    	 driver.get("https://magento.softwaretestingboard.com/");
	    	 Thread.sleep(5000);
	    	 //driver.findElement(By.linkText("Create an Account")).click();
	    	 driver.findElement(By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/a")).click();
	    	 Thread.sleep(5000);
	    	 
	    }
}
