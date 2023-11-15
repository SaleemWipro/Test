package com.MavenDemo;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.PageObjects.NextGenObjects;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.fedexUtilities.XLUtils;


public class TestNgAnnotations {
	
	String path = System.getProperty("user.dir") +"./src\\test\\java\\com\\TestData\\input.xlsx";
	int k=0,p=0;;
	public int inc;
	public int incr;
int Row,Col;
int Scan_time,Latest_Pick_Up_code;
String PickUpScanCode,ExceptionCode,CheckBox,CheckBoxValue,Value,Xpath;
int rc=1;
	
		@Test(dataProvider= "LoginData")
		public static void Test(String Incrementor01, String Incrementor02, String Country,String Location,String Age) throws FileNotFoundException, IOException
		{
				System.out.println(Incrementor01+" "+ Incrementor02+" "+Country +" "+ Location +" " + Age);

		}		
	
		@DataProvider(name = "LoginData")
		String[][] getData() throws IOException
		{
			System.out.println(path);
			
			int rownum= XLUtils.getRowCount(path, "Sheet1");
			int colcont= XLUtils.getCellCount(path, "Sheet1", 0);
			int rownum1=rownum+1;

			int colcont1=colcont+2;

			String logindata[][] = new String[rownum][colcont1];
			for(int i=1;i<=rownum;i++)
			{
		
				for(int j=0;j<colcont;j++)
				{
					logindata[i-1][j+2]= XLUtils.getCellData(path, "Sheet1", i, j); //1 0
				}
			}
			logindata[0][0]="1";
			logindata[0][1]="0";
			
		
			for(int i=1;i<rownum;i++)
				
			{
		
				if((logindata[i][2].equals(logindata[i-1][2]))&&(logindata[i][3].equals(logindata[i-1][3])))
				{
					k=k+1;
					
					logindata[i][1]=Integer.toString(k);

				}
				
				else
				{
					k=0;
					logindata[i][1]="0";
				}
			
			}
			
	
			for(int i=1;i<rownum;i++)
			{
				
					if(logindata[i-1][1]=="0")
					{
						p=p+1;
					}
					else
					{
						p=p;
					}
					logindata[i-1][0] = String.valueOf(p);
					
				
				if(i==(rownum-1))
				{
					
					if(logindata[i][1]=="0")
					{
						p=p+1;
					}
					else
					{
						p=p;
					}
					logindata[i][0] = String.valueOf(p);
					}
					
				}
	
			
        for(int i=1;i<rownum;i++)
				
			{
        	
        	int ScanTime1=Integer.valueOf(logindata[i][4]);
        	
        	  for(int k=1;k<rownum;i++)
  				
  			{
        	int ScanTime2=Integer.valueOf(logindata[k][4]);
        	
        	
			if(ScanTime1>ScanTime2)
			{
				inc=1;
			}
			else
			{
				inc=0;
				break;
			}
        	
        	
			}
        	  if(inc==1)
  			{
  				System.out.println(logindata[i][4]);			
  				inc=0;
  			}
			}
	
			return logindata;
		}

	}	



