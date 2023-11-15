package com.fedexUtilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtils {
	
	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	String path;
	
	public XLUtils(String path)
	{
		this.path=path;
	}

	public static int gettotalSheetNum(String xlfile) throws IOException 
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		
		int sheetcount=wb.getNumberOfSheets();
		wb.close();
		fi.close();
		return sheetcount;		
	}
	
	public static int getRowCount(String xlfile,String xlsheet) throws IOException 
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		int rowcount=ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;		
	}
	
	
	public static int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}
	
	
	public static String getCellData(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		/*Cell cell = row.getCell(colnum);
		DataFormatter formatter = new DataFormatter();
        String cellData = formatter.formatCellValue(cell);*/
        
			//cell=row.getCell(colnum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);			

		String data;
		try 
		{
			Cell cell = row.getCell(colnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			//Cell cell = row.getCell(colnum);
			DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
		}
		catch (Exception e) 
		{
			data=" ";
		} 
		wb.close();
		fi.close();
		return data;
		
	}
	
	public void setCellData(String xlfile,String xlsheet,int rownum,int colnum,String data) throws IOException
	{
		File xlfile1 = new File(xlfile);
		if(!xlfile1.exists())
		{
			wb = new XSSFWorkbook();
			fo= new FileOutputStream(xlfile);
			wb.write(fo);
		}
		
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		if(wb.getSheetIndex(xlsheet)==-1)
			wb.createSheet(xlsheet);
		
		ws=wb.getSheet(xlsheet);
		
		if(ws.getRow(rownum)==null)
			ws.createRow(rownum);
		
		row=ws.getRow(rownum);
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);		
		wb.close();
		fi.close();
		fo.close();
	}
	
	
}
