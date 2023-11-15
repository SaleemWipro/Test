package com.PageObjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class NextGenObjects {
	WebDriver ldriver;

	public NextGenObjects(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	@FindBy(name = "vfb-5")
	@CacheLookup
	WebElement firstname;

	public void EnterFirstName(String fName) {
		firstname.sendKeys(fName);
	}

	@FindBy(name = "vfb-7")
	@CacheLookup
	WebElement lastName;

	public void EnterLastName(String lName) {
		lastName.sendKeys(lName);
	}

	@FindBy(name = "vfb-31")
	@CacheLookup
	List <WebElement> gender;

	public void EnterGender(String gen) {
		
		int size = gender.size();
		for (int i = 1; i <= size; i++) {
			System.out.println("entered for loop");
			String gene = ldriver.findElement(By.xpath("//*[@id='vfb-31-" + i + "']")).getAttribute("value");
			System.out.println(gene);
			if (gene.equals(gen)) {
				ldriver.findElement(By.xpath("//*[@id='vfb-31-" + i + "']")).click();
			}
		}

	}

	@FindBy(id = "vfb-13-address")
	@CacheLookup
	WebElement address;

	public void EnterAddress(String add) {
		address.sendKeys(add);
	}

	@FindBy(xpath = "//*[@id='vfb-13-country']")
	@CacheLookup
	WebElement countrySelection;

	public void EnterCountry(String country) {
		Select dropDown = new Select(countrySelection);
		dropDown.selectByVisibleText(country);
	}

	@FindBy(id = "vfb-14")
	@CacheLookup
	WebElement email;

	public void EnterEmail(String mail) {

		email.sendKeys(mail);
	}

	@FindBy(xpath = "//*[@id='vfb-18']")
	@CacheLookup
	WebElement dates;

	public void EnterDate(String d) {
		dates.sendKeys(d);
	}

	@FindBy(id = "vfb-19")
	@CacheLookup
	WebElement mobile;

	public void EnterMobile(String mob) {
		mobile.sendKeys(mob);
	}

	
	@FindBy(name = "vfb-20[]")

	@CacheLookup

	WebElement course;

	public void EnterCourse(String Course) {

		List<WebElement> s2 = ldriver.findElements(By.xpath("//*[@name='vfb-20[]']"));

		int num = s2.size();

		System.out.println("num"+num);

		for (int k = 0; k < num; k++) {

			System.out.println("Entered course for loop ");

			String cou = ldriver.findElement(By.xpath("//*[@id='vfb-20-" + k + "']")).getAttribute("Value");

			System.out.println(cou);

			if (cou.equals(Course)) {

				ldriver.findElement(By.xpath("//*[@id='vfb-20-" + k + "']")).click();

			}
		}

	}

	@FindBy(xpath = "//*[@id='item-vfb-2']/ul/li[1]/span/label")
	@CacheLookup
	WebElement verification;

	public String Verify() {
		String veri = verification.getText();
		String v = veri.substring(9);
		return v;
	}

	@FindBy(xpath = "//*[@id='vfb-3']")
	@CacheLookup
	WebElement verificationEnter;

	public void Verification() {

		verificationEnter.sendKeys(Verify());
	}

	@FindBy(id = "vfb-4")
	@CacheLookup
	WebElement submit;

	public void EnterSubmit() {
		submit.click();
	}

	@FindBy(xpath = "//*[@class='elementor-widget-container']")
	@CacheLookup
	List<WebElement> result;

	public int VerifyResult() {
		return result.size();

	}

	public void TimeOut() {
		ldriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
	}

}
