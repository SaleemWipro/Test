package com.PageObjects;

import java.sql.Driver;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class NextgenPageObjects {


	
		WebDriver ldriver;
		public NextgenPageObjects(WebDriver rdriver)
		{
			ldriver=rdriver;
			PageFactory.initElements(rdriver, this);
		}
		
		
		@FindBy(xpath="//*[@id='vfb-18']")
		@CacheLookup
		WebElement date;
		
		
		@FindBy(xpath="//*[@id='vfb-13-country']")
		@CacheLookup
		WebElement selectCountry;
		
		
		@FindBy(xpath="//*[@id='openMenuHeaderButton']")
        @CacheLookup
		WebElement navBar;
		
		@FindBy(xpath="//*[@id='openMenuHeaderButton']")
        @CacheLookup
        List<WebElement> navBarexist;
		
		
		public void EnterDate(String ID)
		{
			date.sendKeys(ID);
		}
		
		public void SelectCountry(String Country)
		{

	        Select dropDown = new Select(selectCountry);
	        //dropDown.selectByIndex(1);
	      dropDown.selectByVisibleText(Country);
		}

	}


