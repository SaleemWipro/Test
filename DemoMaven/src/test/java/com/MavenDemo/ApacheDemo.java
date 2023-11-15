package com.MavenDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ApacheDemo {
	@Parameters("browser")
    @Test
	public static void main(String chrome) throws IOException, InterruptedException {
		
		        //set the ChromeDriver path
		System.setProperty("webdriver.chrome.driver", "C:\\Automation_Training\\chromedriver.exe");

		        //Create an object of File class to open xls file
		        File file =    new File("C:\\Automation\\Fedex_Automation\\automationTraining\\TestData.xlsx");
		        
		        //Create an object of FileInputStream class to read excel file
		        FileInputStream inputStream = new FileInputStream(file);
		        
		        //creating workbook instance that refers to .xls file
		        XSSFWorkbook wb=new XSSFWorkbook(inputStream);
		        
		        //creating a Sheet object
		        XSSFSheet sheet=wb.getSheet("STUDENT_DATA");
		        
		        //get all rows in the sheet
		        int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
		        
		        for(int i=1;i<=rowCount;i++) {
		        
		       //Creating an object of ChromeDriver
		        WebDriver driver = new ChromeDriver();
		        
		        //Navigate to the URL
		        driver.get("https://nxtgenaiacademy.com/demo-site");
		        driver.manage().window().maximize();
		        
		        Thread.sleep(6000);


		        //Identify the WebElements for the student registration form
		        WebElement firstName=driver.findElement(By.name("vfb-5"));
		        WebElement lastName=driver.findElement(By.name("vfb-7"));
		        
		        
		        WebElement Male=driver.findElement(By.id("vfb-31-1"));
		        WebElement Female=driver.findElement(By.id("vfb-31-2"));
		        WebElement other=driver.findElement(By.id("vfb-31-3"));
		       
		       
		        
		        WebElement address=driver.findElement(By.id("vfb-13-address"));
		        
		        WebElement email= driver.findElement(By.id("vfb-14"));
		        
		        WebElement courseSelenium= driver.findElement(By.id("vfb-20-0"));
		        
		        WebElement Verification = driver.findElement(By.xpath("//*[@id=\"item-vfb-2\"]/ul/li[1]/span/label"));
		        WebElement VerificationEnter = driver.findElement(By.xpath("//*[@id=\"vfb-3\"]"));
		        
		        WebElement submitBtn=driver.findElement(By.id("vfb-4"));


		        //iterate over all the rows in Excel and put data in the form.
		       
		            //Enter the values read from Excel in firstname,lastname,mobile,email,address
		        	
		        	
		        	
		            firstName.sendKeys(sheet.getRow(i).getCell(0).getStringCellValue());
		            
		            Thread.sleep(3000);
		            lastName.sendKeys(sheet.getRow(i).getCell(1).getStringCellValue());
		            Thread.sleep(3000);
		            email.sendKeys(sheet.getRow(i).getCell(4).getStringCellValue());
		            Thread.sleep(3000);
		            Male.click();
		            Thread.sleep(3000);
		            address.sendKeys(sheet.getRow(i).getCell(3).getStringCellValue());
		            Thread.sleep(3000);
		           
		            VerificationEnter.sendKeys("99");
		            Thread.sleep(3000);
		            courseSelenium.click();
		            Thread.sleep(3000);
		            submitBtn.click();
		            Thread.sleep(6000);
		            
		          
		           int size = driver.findElements(By.xpath("//*[@class='elementor-widget-container']")).size();
		           System.out.println(size);
		            
		        
		            //create a new cell in the row at index 6
		            XSSFCell cell = sheet.getRow(i).createCell(6);
		            
		            //check if confirmation message is displayed
		            if (size>0) {
		                // if the message is displayed , write PASS in the excel sheet
		                cell.setCellValue("PASS");
		                
		            } else {
		                //if the message is not displayed , write FAIL in the excel sheet
		                cell.setCellValue("FAIL");
		            }
		            
		            // Write the data back in the Excel file
		            FileOutputStream outputStream = new FileOutputStream("C:\\\\Automation\\\\Fedex_Automation\\\\automationTraining\\\\TestData.xlsx");
		            wb.write(outputStream);
		            
		           
		        //Quit the driver
		        driver.close();
		        }
		        
		       // Close the workbook
			        wb.close();
	}
		
    @BeforeTest
	public static void main( )
	{
		System.out.println("before testm this must be executed");
	}
	
	
	}


