package automation1807;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LaunchBrowser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "C:\\Automation_Training\\Fedex_Automation\\Automation_1807\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https:www.google.com");
		String S= driver.getTitle();
		System.out.println(S);
	}

}
