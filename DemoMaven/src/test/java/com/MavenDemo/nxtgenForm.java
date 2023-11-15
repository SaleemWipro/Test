package com.MavenDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.PageObjects.NextgenPageObjects;

public class nxtgenForm {

	@Test
	@Parameters("browser")
	
	public static void main(String browser) throws IOException, InterruptedException {
	
		
		System.setProperty("webdriver.chrome.driver", "C:\\Automation_Training\\chromedriver.exe");
	
      
        //Creating an object of ChromeDriver
        WebDriver driver = new ChromeDriver();
        
        //Navigate to the URL
        driver.get("https://nxtgenaiacademy.com/demo-site");
        
        
       String gender = "Female";
       
        List<WebElement> S1= driver.findElements(By.xpath("//*[@name ='vfb-31']"));
        System.out.println(S1.size());
        int size= S1.size();
        for(int i=1;i<=size;i++)
        {
        	System.out.println("//*[@id ='vfb-31-"+i+"']");
        	String GenderP= driver.findElement(By.xpath("//*[@id ='vfb-31-"+i+"']")).getAttribute("value");
        	if(GenderP.equals(gender))
        	{
        		driver.findElement(By.xpath("//*[@id ='vfb-31-"+i+"']")).click();
        	}
        
 
        }
   
        
		Thread.sleep(5000);
		
		
       

   
        

	}

}
