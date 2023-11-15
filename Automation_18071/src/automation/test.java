package automation;

import java.lang.reflect.Array;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class test {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chromdriver.driver", "C:\\Automation_Training\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
	/*	driver.get("https:www.google.com");
		String S = driver.getTitle();
		String D = driver.getCurrentUrl();
System.out.println(S +" " + D);
driver.close();*/




driver.get("https://www.saucedemo.com/");
driver.findElement(By.id("user-name")).sendKeys("standard_user");
driver.findElement(By.id("password")).sendKeys("secret_sauce");
driver.findElement(By.id("login-button")).click();
driver.manage().window().maximize();

String URL= "https://www.saucedemo.com/inventory.html";
String D = driver.getCurrentUrl();

String input= "Sauce Labs Bolt T-Shirt";

if(D.equals(URL))
{
	System.out.println("pass:-Login is succesful");
	
	//driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
	//driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
	
    List<WebElement> S1=driver.findElements(By.className("inventory_item_name"));
    int size= S1.size();
    System.out.println(size);
    
   for(int i=0;i<size;i++)
   {
	   String S3= S1.get(i).getText();
	   System.out.println(S3);
	   if(S3.equals(input))
	   {
		   S1.get(i).click();
		   Thread.sleep(3000);
		   driver.findElement(By.xpath("//*[text() = 'Add to cart']")).click();
		   driver.findElement(By.id("back-to-products")).click();
		   break;
	   }
   }
    
}
else
{
	System.out.println("Fail:- Login isnt succesful");
	System.out.println(D);
}

driver.quit();

	
	
	}

	}

