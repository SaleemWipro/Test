package com.fedexUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	
	Properties pro;
	
	public ReadConfig()
	{
		File src = new File("./Configuration//config.properties");
		try
		{
			FileInputStream fis = new FileInputStream(src);
			pro= new Properties();
			pro.load(fis);
		}
		catch (Exception e)
		{
			System.out.println("Exception is "+ e.getMessage());
		}
	}
	
	public String getApplicationURL()
	{
		String url=pro.getProperty("baseURL");
		return url;
	}
	
	public String getPRodApplicationURL()
	{
		String url=pro.getProperty("prodURL");
		return url;
	}
	
	public String getUsername()
	{
		String url=pro.getProperty("username");
		return url;
	}
	
	public String getHeader()
	{
		String url=pro.getProperty("header");
		return url;
	}
	
	public String getPassword()
	{
		String url=pro.getProperty("password");
		return url;
	}
	
	public String getChromePath()
	{
		String url=pro.getProperty("chromepath");
		return url;
	}
	
	public String getPath()
	{
		String path=pro.getProperty("path");
		return path;
	}
	
	public String getExtentReportPath()
	{
		String ERpath=pro.getProperty("ExtentReportPath");
		return ERpath;
	}
	
	
	
	

}
