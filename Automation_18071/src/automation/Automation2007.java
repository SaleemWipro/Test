package automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Automation2007 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
System.setProperty("webdriver.chromdriver.driver", "C:\\Automation_Training\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://cosmocode.io/automation-practice-webtable");
		driver.manage().window().maximize();
		
		
		
		List<WebElement> rowNum = driver.findElements(By.xpath("//*[@id='countries']/tbody/tr"));
		int rowCount = rowNum.size();
		System.out.println(rowCount);
		
		List<WebElement> colNum = driver.findElements(By.xpath("//*[@id='countries']/tbody/tr[1]/td"));
		int colCont = colNum.size();
		System.out.println(colCont);
		
		for(int i=1;i<=colCont;i++)
		{
			
				String S =driver.findElement(By.xpath("//*[@id='countries']/tbody/tr[1]/td["+i+"]/*/*")).getText();
		 System.out.print(S + " ");
					
	
			}
		
		
		
		for(int i=2;i<=2;i++)
		{
			for(int j=1; j<=colCont;j++)
				
			{
				
				if(j==1)
				{
					driver.findElement(By.xpath("//*[@id='countries']/tbody/tr["+i+"]/td["+j+"]/*")).click();	
				}
				
				
				else if(j==2)
				{
					String S =driver.findElement(By.xpath("//*[@id='countries']/tbody/tr["+i+"]/td["+j+"]/*")).getText();
					 System.out.print(S + " ");
				}
				
				
				String S =driver.findElement(By.xpath("//*[@id='countries']/tbody/tr["+i+"]/td["+j+"]")).getText();
		 System.out.print(S + " ");
					
			}
			}
		
		
		}
	
	
	
		

	}

