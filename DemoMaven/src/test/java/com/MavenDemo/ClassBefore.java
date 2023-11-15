package com.MavenDemo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fedexUtilities.ReadConfig;

public class ClassBefore {
	
    ReadConfig readconfig = new ReadConfig();
	
	public String baseURL= readconfig.getApplicationURL();
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentHtmlReporter htmlReporter;
	public String chromePath =readconfig.getChromePath();
	
	
	
	@BeforeSuite
	public void setUp()
	{
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + File.separator
						+"DimenstionUAT_Parallel_1.html");//specify location of the report
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	
		extent.setSystemInfo("Host name","Fedex");
		extent.setSystemInfo("Environemnt","UAT");
		extent.setSystemInfo("user","Saleem Sarfani");
		
		htmlReporter.config().setDocumentTitle("Fedex Test Project"); // Tile of report
		htmlReporter.config().setReportName("Functional Test Automation Report"); // name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
		htmlReporter.config().setTheme(Theme.DARK);
	
	}
	
	 @BeforeClass
     public void beforeClass() {
	
		test= extent.createTest("Dimension Test");
		
	}
	
	

	@BeforeMethod
	public void beforeMeth() {
		
		
		test= extent.createTest("Dimension Test");
		
		System.out.println("BeforeClass entered");

		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+chromePath);
		driver = new ChromeDriver();
		driver.get(baseURL);
		
		test.pass("entered the URL");
		
	}
	
	
	
	public static String captureScreen(WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srouce = ts.getScreenshotAs(OutputType.FILE);
		String Spath = System.getProperty("user.dir") + "/screenshots/"+ "Test_Parallel_1_UAT_"+ System.currentTimeMillis()  + ".png";
		File target = new File(Spath);
		FileUtils.copyFile(srouce, target);
		System.out.println("screenshot taken");
		return Spath;
			
	}
	
	@AfterMethod
	public void getResult(ITestResult result)
	{
		if(result.getStatus() == ITestResult.FAILURE)
		{
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+ "Test case failed due to beow", ExtentColor.RED));
		}
		else if(result.getStatus() == ITestResult.SUCCESS)
		{
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+ "Test case Passed", ExtentColor.GREEN));
		}
		
		System.out.println("Afterclass entered");
		driver.quit();
		
	}
	
	@AfterSuite
	public void tearDown()
	{
	
		extent.flush();
	}
	
	

}
