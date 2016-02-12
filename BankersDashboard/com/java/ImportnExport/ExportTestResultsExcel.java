package com.java.ImportnExport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import com.google.common.collect.Multimap;
import com.java.CreateClient;
import com.java.SeleniumDriver;
import com.java.TemplateGenerator;
import com.java.Objects.TestExecutionDetails;
import com.java.Objects.TestResults;

/**
 * This is used to write the data into an Excel sheet 
 */
public class ExportTestResultsExcel{
	private SeleniumDriver sd;
	private HashMap<Integer,TestResults> testresults = new HashMap<Integer,TestResults>();
	private ArrayList<Integer> testcases = new ArrayList<Integer>();

	public ExportTestResultsExcel (SeleniumDriver sd){
		this.sd=sd;
	}

	public ExportTestResultsExcel() {
	}

	private static Logger log = Logger.getLogger(ExportTestResultsExcel.class.getName());
	//creating a workbook;
	HSSFWorkbook wb = new HSSFWorkbook();

	String fileName = null;
	String htmlFileName = null;
	FileOutputStream fileOut = null;
	InputStream inputStream = null;
	POIFSFileSystem fs;

	Calendar cal = Calendar.getInstance();
	public static final String dateTime = "MMddyy_HHmmss";
	public static final String dateTime1 = "EEE MMM dd hh:mm:ss z yyyy";
	SimpleDateFormat dateFormat = new SimpleDateFormat(dateTime);	
	Properties props = new Properties();
	public  String testResultPath;
	public  String testResultPath_IE ;
	public  String testResultPath_FF ;
	public  String testResultPath_Chrome ;
	public  String testResultPath_Safari ;
	public String dirpath = System.getProperty("user.dir");
	public String ProjPath =dirpath.substring(0, dirpath.lastIndexOf("\\")) ;
	public static HashMap<String,String> resultsPaths= new HashMap<String, String>();


	public  String testHTMLResultPath ;
	int headings = 0;


	/*'#########################################################################################################
	'Function name		:	EXPORTEXCELHEADER
	'Description		:	This function is to write the data into Excel Sheet(Test Results Header)
						
	'Parameters			:	browser name, client name need to be passed as parameters
	'#########################################################################################################*/
	
	public void exportExcelHeader(String browser,String client) throws IOException 
	{
		try
		{		
			if (headings == 0)
			{		    		
				fileName = "Test Results_" +CreateClient.executionlog+"_"+dateFormat.format(cal.getTime())+"_" +browser.toUpperCase()+"_" + client+".xls";
				htmlFileName = "Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" +browser.toUpperCase()+"_" + client+ ".html";
				if(sd.onFailedCaseExecution)
				{
					fileName ="Failed Cases Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" +browser.toUpperCase()+"_" + client+".xls";
					htmlFileName = "Failed Cases Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" +browser+"_" + client+ ".html";
				}

				sd.hMap.put("htmlFile", htmlFileName);
				sd.excelReports.add(ProjPath+"/DestinationRewards/TestReports/"+fileName);
				CreateClient.excelReportsPaths.put(sd.client.toUpperCase()+"-"+sd.prgmID+"_" +browser.toUpperCase(), sd.excelReports);
				SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateTime1);
				String strTimeStamp = dateFormat1.format(cal.getTime());
				sd.hMap.put("TimeStamp", strTimeStamp);
				sd.log.info("Time Stamp "+strTimeStamp);
				sd.TED.setExcelReportPath(ProjPath+"/DestinationRewards/TestReports/"+fileName);
			/*	if(browser.equalsIgnoreCase("IE8"))
				{						
					testResultPath = ProjPath+"/DestinationRewards/TestReports/"+fileName;
					testHTMLResultPath = ProjPath+"/DestinationRewards/TestReports/"+htmlFileName;
					resultsPaths.put("TestResultsPath_IE8"+client,testResultPath );
					resultsPaths.put("TestHtmlResultsPath_IE8"+client,testHTMLResultPath );
				}
				else*/ if(browser.equalsIgnoreCase("IE9"))
				{						
					testResultPath = ProjPath+"/DestinationRewards/TestReports/"+fileName;
					testHTMLResultPath = ProjPath+"/DestinationRewards/TestReports/"+htmlFileName;
					resultsPaths.put("TestResultsPath_IE9"+client,testResultPath );
					resultsPaths.put("TestHtmlResultsPath_IE9"+client,testHTMLResultPath );
				}
				else if(browser.equalsIgnoreCase("IE10"))
				{						
					testResultPath = ProjPath+"/DestinationRewards/TestReports/"+fileName;
					testHTMLResultPath = ProjPath+"/DestinationRewards/TestReports/"+htmlFileName;
					resultsPaths.put("TestResultsPath_IE10"+client,testResultPath );
					resultsPaths.put("TestHtmlResultsPath_IE10"+client,testHTMLResultPath );
				}
				else if(browser.equalsIgnoreCase("IE11"))
				{						
					testResultPath = ProjPath+"/DestinationRewards/TestReports/"+fileName;
					testHTMLResultPath = ProjPath+"/DestinationRewards/TestReports/"+htmlFileName;
					resultsPaths.put("TestResultsPath_IE11"+client,testResultPath );
					resultsPaths.put("TestHtmlResultsPath_IE11"+client,testHTMLResultPath );
				}
		/*		else if(browser.equalsIgnoreCase("GCHROME"))
				{						
					testResultPath = ProjPath+"/DestinationRewards/TestReports/"+fileName;
					testHTMLResultPath = ProjPath+"/DestinationRewards/TestReports/"+htmlFileName;
					resultsPaths.put("TestResultsPath_Chrome"+client,testResultPath );
					resultsPaths.put("TestHtmlResultsPath_Chrome"+client,testHTMLResultPath );
				}
				else if(browser.equalsIgnoreCase("safari"))
				{						
					testResultPath = ProjPath+"/DestinationRewards/TestReports/"+fileName;
					testHTMLResultPath = ProjPath+"/DestinationRewards/TestReports/"+htmlFileName;
					resultsPaths.put("TestResultsPath_Safari"+client,testResultPath );
					resultsPaths.put("TestHtmlResultsPath_Safari"+client,testHTMLResultPath );
				}*/else
				{						
					testResultPath = ProjPath+"/DestinationRewards/TestReports/"+fileName;
					testHTMLResultPath =ProjPath+"/DestinationRewards/TestReports/"+htmlFileName;
					resultsPaths.put("TestResultsPath_FF"+client,testResultPath );
					resultsPaths.put("TestHtmlResultsPath_FF"+client,testHTMLResultPath);

				}

				sd.hMap.put("TestResultsPath", testResultPath);
				sd.hMap.put("TestHtmlResultsPath",testHTMLResultPath );
				log.info("TestResultsPath:"+testResultPath);
				log.info("testHTMLResultPath"+testHTMLResultPath);
				props.put("TestResultsPath", testResultPath);
				props.put("testHTMLResultPath", testHTMLResultPath);

				sd.log.info("OUT FILE : "+testResultPath);

				wb = new HSSFWorkbook();
				wb.createSheet("Test Result");
				fileOut = new FileOutputStream(testResultPath);
				sd.log.info("Test Result file is created");
				HSSFSheet sheet = wb.getSheetAt(0);
				sheet.setAutobreaks(false);

				HSSFRow row = sheet.createRow((short)0);            
				HSSFFont font = wb.createFont();			
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);		
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);

				// Setting Headings in Test Results file
				row.createCell(0).setCellStyle(cellStyle);
				row.createCell(1).setCellStyle(cellStyle);
				row.createCell(2).setCellStyle(cellStyle);
				row.createCell(3).setCellStyle(cellStyle);

				row.createCell(0).setCellValue("Test Case ID");
				row.createCell(1).setCellValue("Test Case Title");
				row.createCell(2).setCellValue("Result(P/F)"); 
				row.createCell(3).setCellValue("Error Message"); 
				row.createCell(4).setCellValue("Time Stamp");          
				headings = 1;
			}
			wb.write(fileOut);
			fileOut.close();		
		}catch (Exception e) {
			log.error("Error while creating Test Report Excel file.."+e.getMessage());
		}
	}

	
	/*'#########################################################################################################
	'Function name		:	EXPORTEXCELROWS
	'Description		:	This function is to write the test case results into an Excel sheet after completing the each test case execution
						
	'Parameters			:	browser name, client name need to be passed as parameters
	'#########################################################################################################*/
	
	public void exportExcelRows(List<String> result,String browser,String client) throws IOException 
	{		
		try{
			System.out.println("testResultPath = " +testResultPath);
			sd.log.debug("testResultPath = " +testResultPath);
			sd.log.debug("browser is------------" +browser);
		/*	if(browser.equalsIgnoreCase("ff")&&testResultPath.contains("FF")&&testResultPath.contains(client))
			{
				inputStream = new FileInputStream (testResultPath);
				fs = new POIFSFileSystem(inputStream);
				fileOut = new FileOutputStream(testResultPath);
			}
			else if(browser.equalsIgnoreCase("IE8")&&testResultPath.contains("IE8")&&testResultPath.contains(client))
			{
				inputStream = new FileInputStream (testResultPath);
				fs = new POIFSFileSystem(inputStream);
				fileOut = new FileOutputStream(testResultPath);
			}
			else*/ if(browser.equalsIgnoreCase("IE9")&&testResultPath.contains("IE9")&&testResultPath.contains(client))
			{
				inputStream = new FileInputStream (testResultPath);
				fs = new POIFSFileSystem(inputStream);
				fileOut = new FileOutputStream(testResultPath);
			}
			else if(browser.equalsIgnoreCase("IE10")&&testResultPath.contains("IE10")&&testResultPath.contains(client))
			{
				inputStream = new FileInputStream (testResultPath);
				fs = new POIFSFileSystem(inputStream);
				fileOut = new FileOutputStream(testResultPath);
			}
			else if(browser.equalsIgnoreCase("IE11")&&testResultPath.contains("IE11")&&testResultPath.contains(client))
			{
				inputStream = new FileInputStream (testResultPath);
				fs = new POIFSFileSystem(inputStream);
				fileOut = new FileOutputStream(testResultPath);
			}
		/*	else if(browser.equalsIgnoreCase("GCHROME")&&testResultPath.contains("GCHROME")&&testResultPath.contains(client))
			{
				inputStream = new FileInputStream (testResultPath);
				fs = new POIFSFileSystem(inputStream);
				fileOut = new FileOutputStream(testResultPath);
			}
			else if(browser.equalsIgnoreCase("safari")&&testResultPath.contains("SAFARI")&&testResultPath.contains(client))
			{
				inputStream = new FileInputStream (testResultPath);
				fs = new POIFSFileSystem(inputStream);
				fileOut = new FileOutputStream(testResultPath);
			}*/
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);			
			sheet.setAutobreaks(false);

			sd.log.debug("result.size() = "+result.size());
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			sd.log.debug("No of rows in sheet="+rows);

			HSSFRow row=sheet.createRow(rows);
			//Adding the cell values in each row from bean
			result.remove(1); // to skip test data value in the report sheet
			for(int i=0; i<5; i++){ 
				sheet.setColumnWidth(2,  (short)(256*13));
				sheet.setColumnWidth(1,  (short)(256*25));
				sheet.setColumnWidth(2,  (short)(256*14));
				sheet.setColumnWidth(3,  (short)(256*30));
				sheet.setColumnWidth(4,  (short)(256*28));

				HSSFCell cell=row.createCell(i);
				HSSFRichTextString str = null;
				String temp = "";
				if (i!=3) {
					str= new HSSFRichTextString(result.get(i).toString());
				} else {
					if (sd.hMap.get("strWarningMessage")!=null){
						temp = temp + sd.hMap.get("strWarningMessage");
						sd.hMap.put("strWarningMessage",null);
					}
					if (result.get(i).toString()!=null)
						temp = result.get(i).toString();
					str = new HSSFRichTextString(temp);
				}

				sd.log.debug("..in export.."+str);
				log.debug("str---"+str);
				cell.setCellValue(str);
			}
			wb.write(fileOut);
			fileOut.close();			
		}catch(Exception e) {
			log.error("Error writing the Excel file");
			log.debug(e.getMessage());
		}finally{
			fileOut.close();
		}
	}

	
	/*'#########################################################################################################
	'Function name		:	exportTestSummary
	'Description		:	This function is to write the Test Summary into an Test Results Excel Sheet after completion of total test cases execution.
						
	'Parameters			:	browser name, client name need to be passed as parameters
	'#########################################################################################################*/
	
	public void exportTestSummary(List<String> result) throws IOException 
	{		
		try{
			if(sd.isFailedCaseExecuted)
				inputStream = new FileInputStream (sd.hMap.get("TestResultsPath"));
			else
				inputStream = new FileInputStream (testResultPath);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			fileOut = new FileOutputStream(sd.hMap.get("TestResultsPath"));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			sheet.setAutobreaks(false);
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			for(int ii=0;ii<result.size(); ii++)
			{

				HSSFRow row=sheet.createRow(rows+ii+1);
				HSSFCell cell;
				HSSFRichTextString str;
				//Adding the cell values in each row from bean
				switch (ii) {
				case 0:
					cell=row.createCell(1);
					cell.setCellValue("Browser Tested");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(result.get(ii).toString());
					cell.setCellValue(str);
					break;
				case 1:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Executed");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(result.get(ii).toString());
					cell.setCellValue(str);
					break;					
				case 2:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Passed");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(result.get(ii).toString());
					cell.setCellValue(str);
					break;		
				case 3:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Failed");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(result.get(ii).toString());
					cell.setCellValue(str);
					break;	
				case 4:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Skipped");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(result.get(ii).toString());
					cell.setCellValue(str);
					break;	
				}
			}
			wb.write(fileOut);
			fileOut.close();			
		}catch(Exception e) {
			log.error("Error writing the Excel file");
			log.debug(e.getMessage());
		}finally {
			fileOut.close();
			headings=0;
		}
	}

	/*'#########################################################################################################
	'Function name		:	exportExcelTestReport
	'Description		:	This function is to write the client level consolidated excel report
						
	'Parameters			:	N/A
	'#########################################################################################################*/
	public void exportExcelTestReport(ArrayList<String[]> arry,String client) throws IOException 
	{		
		try{
			String[] browsers = null;
			System.out.println(CreateClient.executionlog);
			String testreportname =   "Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" + client+".xls";
			try{
				String files[] = {resultsPaths.get("TestResultsPath_FF"+client),resultsPaths.get("TestResultsPath_Safari"+client),resultsPaths.get("TestResultsPath_IE8"+client),resultsPaths.get("TestResultsPath_IE9"+client),resultsPaths.get("TestResultsPath_IE10"+client),resultsPaths.get("TestResultsPath_IE11"+client),resultsPaths.get("TestResultsPath_Chrome"+client)};		
				HSSFWorkbook workbook=new HSSFWorkbook();
				FileInputStream fis = null;
				for(int b=0;b<arry.size();b++){
					try{
						browsers=arry.get(b);
					}catch(Exception e){
						log.error("Exception: "+e.getMessage());
					}
					for(int a=0;a<browsers.length;a++){					
						for(int f=0;f<files.length;f++){					
							if(files[f]==null)
							{
								continue;
							}
							try{
								if(files[f].contains(browsers[a].toUpperCase())){
									fis = new FileInputStream(files[f]);
									HSSFWorkbook workbook2 = new HSSFWorkbook(fis);
									HSSFSheet sheet2 = workbook2.getSheet("Test Result");
									HSSFSheet sheet =  workbook.createSheet(browsers[a]); 

									HSSFRow newRow = sheet.getRow(1);
									HSSFRow sourceRow =  sheet2.getRow(0);
									if (newRow != null) {
										sheet2.shiftRows(1, sheet2.getLastRowNum(), 1);
									} else {
										newRow = sheet.createRow(1);
									}
									try{
										for(int j=0;j<=sheet2.getPhysicalNumberOfRows();j++){
											sourceRow =  sheet2.getRow(j);
											newRow =sheet.createRow(j);
											sheet.setColumnWidth(2,  (short)(256*13));
											sheet.setColumnWidth(1,  (short)(256*25));
											sheet.setColumnWidth(2,  (short)(256*14));
											sheet.setColumnWidth(3,  (short)(256*30));
											sheet.setColumnWidth(4,  (short)(256*28));
											if ( sourceRow== null) {
												newRow  = null;
												continue;
											}
											for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
												// Grab a copy of the old/new cell
												HSSFCell oldCell = sourceRow.getCell(i);
												HSSFCell newCell = newRow.createCell(i);
												// If the old cell is null jump to next cell
												if (oldCell == null) {
													newCell = null;
													continue;
												}			        	    			        	    
												// Set the cell data type
												newCell.setCellType(oldCell.getCellType());

												// Set the cell data value
												switch (oldCell.getCellType()) {
												case Cell.CELL_TYPE_BLANK:
													newCell.setCellValue(oldCell.getStringCellValue());
													break;	        	        
												case Cell.CELL_TYPE_NUMERIC:
													newCell.setCellValue(oldCell.getNumericCellValue());
													break;
												case Cell.CELL_TYPE_STRING:
													newCell.setCellValue(oldCell.getRichStringCellValue());
													break;
												}
											}
										}
									}catch(Exception e)	{
										log.debug("Exception+ "+e.getMessage());
									}
									fis.close();  
									FileOutputStream fileOut = new FileOutputStream(ProjPath+"/DestinationRewards/TestReports/"+testreportname);


									HashMap<String,String> excelFileName=new HashMap<String,String>();
									excelFileName.put("excelReportPath", ProjPath+"/DestinationRewards/TestReports/"+testreportname);
									CreateClient.clientLevelDetails.put(client.toUpperCase(), excelFileName);
									resultsPaths.put("TestResultsPath"+"_"+client,ProjPath+"/DestinationRewards/TestReports/"+testreportname);

									workbook.write(fileOut);
									fileOut.flush();
									fileOut.close();
									fis.close();
								}
							}catch(Exception e) {
								log.error("Exception: "+e.getMessage());
							}
						}
					}
				}
			}catch ( Exception ex ) {
				log.error("Exception: "+ex.getMessage());
			}
		}catch(Exception e) {
			log.error("Exception: "+e.getMessage());
		}
	}



	/*'#########################################################################################################
	'Function name		:	readExcelReports
	'Description		:	This function is to read the test results of Test Execution reports(1st iteration & Failed iteration reports)
						
	'Parameters			:	N/A
	'#########################################################################################################*/
	public void readExcelReports(){
		try{
			sd.log.info("Reading the execel reports:");
			sd.log.info("Excel report files:"+sd.excelReports);
			htmlFileName = "Consolidated Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" +sd.Browser+"_" + sd.client+"-"+ sd.prgmID + ".html";
			sd.hMap.put("htmlFile", htmlFileName);
			for(String file:sd.excelReports)
			{
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				HSSFCell cell = sheet.getRow(0).getCell(7);
				int i = 1;
				while(true){
					TestResults ts = new TestResults();
					HSSFRow row = sheet.getRow(i);
					if(row == null)
						break;
					cell = row.getCell(0);
					if(cell == null )
						break;
					String str = cell.getStringCellValue();
					int tcid  = Integer.parseInt(str.replace(".0", ""));
					ts.setTCID(tcid);
					ts.setTCTitle(row.getCell(1).getStringCellValue());
					ts.setResult(row.getCell(2).getStringCellValue());
					cell = row.getCell(3);
					if(cell == null )
						str = "";
					else
						str = cell.getStringCellValue();
					ts.setErrorMsg(str);
					SimpleDateFormat sd = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
					ts.setTime_Stamp(sd.parse(row.getCell(4).getStringCellValue()));
					cell = row.getCell(6);
					if(cell == null )
						str = "";
					else
						str = cell.getStringCellValue();
					if(testresults.containsKey(tcid)){

						TestResults temp = testresults.get(tcid);
						int res = temp.getTime_Stamp().compareTo(ts.getTime_Stamp());
						if(res != -1){
							ts = temp;
						}
						testresults.remove(tcid);
					}
					testresults.put(tcid,ts);
					i++;
				}
			}
			}catch(Exception e){
				log.error("Exception while querying for testresults: "+e.getMessage());
			}
		Iterator<Integer> tcid = testresults.keySet().iterator();
		while(tcid.hasNext()){
			int testcaseid = tcid.next();
			testcases.add(testcaseid);
		}
		Collections.sort(testcases);
	}

	/*'#########################################################################################################
	'Function name		:	CONSOLIDATEDEXCELREPORT
	'Description		:	This function is to generate the test results of Test Execution reports(1st iteration & Failed iteration reports)
						
	'Parameters			:	N/A
	'#########################################################################################################*/
	public void consolidateExcelReport() {

		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			FileOutputStream fileOut = null;
			fileName =ProjPath+"/DestinationRewards/TestReports/"+ "Consolidated Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" +sd.Browser.toUpperCase()+"_" + sd.client+"-"+sd.prgmID+".xls";
			sd.log.info("Consolidating the excel reports:"+fileName);
			fileOut = new FileOutputStream(fileName);
			if(sd.Browser.equalsIgnoreCase("IE8"))
			{						
				resultsPaths.put("TestResultsPath_IE8"+sd.client+"-"+sd.prgmID,fileName );
			}else if(sd.Browser.equalsIgnoreCase("IE9")) {						
				resultsPaths.put("TestResultsPath_IE9"+sd.client+"-"+sd.prgmID,fileName );
			}else if(sd.Browser.equalsIgnoreCase("IE10")) {
				resultsPaths.put("TestResultsPath_IE10"+sd.client+"-"+sd.prgmID,fileName );
			}else if(sd.Browser.equalsIgnoreCase("IE11")) {
				resultsPaths.put("TestResultsPath_IE11"+sd.client+"-"+sd.prgmID,fileName );
			}else if(sd.Browser.equalsIgnoreCase("GCHROME")) {						
				resultsPaths.put("TestResultsPath_Chrome"+sd.client+"-"+sd.prgmID,fileName );
			}else if(sd.Browser.equalsIgnoreCase("safari"))	{						
				resultsPaths.put("TestResultsPath_Safari"+sd.client+"-"+sd.prgmID,fileName );
			}else {						
				resultsPaths.put("TestResultsPath_FF"+sd.client+"-"+sd.prgmID,fileName );
			}
			wb.createSheet("Test Result");
			HSSFSheet sheet = wb.getSheetAt(0);
			sheet.setAutobreaks(false);
			HSSFRow row = sheet.createRow((short) 0);
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);

			row.createCell(0).setCellStyle(cellStyle);
			row.createCell(1).setCellStyle(cellStyle);
			row.createCell(2).setCellStyle(cellStyle);
			row.createCell(3).setCellStyle(cellStyle);
			row.createCell(4).setCellStyle(cellStyle);

			row.createCell(0).setCellValue("Test Case ID");
			row.createCell(1).setCellValue("Test Case Title");
			row.createCell(2).setCellValue("Result(P/F)");
			row.createCell(3).setCellValue("Error Message");
			row.createCell(4).setCellValue("Time Stamp");

			int rows = 1;
			for (int i = 0; i < testcases.size(); i++){

				row = sheet.createRow(rows);
				rows++;
				TestResults ts = testresults.get(testcases.get(i));

				sheet.setColumnWidth(2,(256 * 13));
				sheet.setColumnWidth(1, (256 * 25));
				sheet.setColumnWidth(2, (256 * 14));
				sheet.setColumnWidth(3, (256 * 30));
				sheet.setColumnWidth(4, (256 * 28));

				sd.log.info("TestCaseID:"+ts.getTCID());
				sd.log.info("TestCase Title:"+ts.getTCTitle());
				sd.log.info("TestCase Result:"+ts.getResult());
				sd.log.info("TestCase Error Msg:"+ts.getErrorMsg());
				sd.log.info("TestCase TimeStamp:"+ts.getTime_Stamp().toString());

				row.createCell(0).setCellValue(String.valueOf(ts.getTCID()));
				row.createCell(1).setCellValue(ts.getTCTitle());
				row.createCell(2).setCellValue(ts.getResult());
				row.createCell(3).setCellValue(ts.getErrorMsg());
				row.createCell(4).setCellValue(ts.getTime_Stamp().toString());
			}
			System.out.println();
			row = sheet.createRow(rows+1);
			wb.write(fileOut);
			fileOut.close();
			sd.hMap.put("TestResultsPath",fileName);
			sd.log.info("TestResultsPath"+fileName);
			sd.TED.setExcelReportPath(fileName);
		} catch (Exception e) {
			sd.log.error("Exception: "+e.getMessage());
		}
	}

	
	/*'#########################################################################################################
	'Function name		:	GATHERMISSEDEXECUTIONDETAILS
	'Description		:	This function is to generate the test MissedExecutionDetails 
						
	'Parameters			:	N/A
	'#########################################################################################################*/
	public void gatherMissedExecutionDetails(Multimap<String, HashMap<String, TestExecutionDetails>> executionStatusForAllClients, List<String> browserNames, List<String> clientNames) {

		List<String> browserDiff= new ArrayList<String>();
		List<String> browserList= new ArrayList<String>();
		HashSet duplicateRemove = new HashSet();
		Collections.sort(CreateClient.browserNames);
		duplicateRemove.addAll(CreateClient.browserNames);
		CreateClient.browserNames.clear();
		CreateClient.browserNames.addAll(duplicateRemove);
		Collections.sort(CreateClient.browserNames);

		for(String clientName:clientNames){
			browserDiff.clear();
			Collection<HashMap<String, TestExecutionDetails>> clientWiseStatus=executionStatusForAllClients.get(clientName.toUpperCase());
			if(clientWiseStatus.size()!=browserNames.size()){
				for(HashMap<String, TestExecutionDetails> client:clientWiseStatus){
					browserList.addAll(client.keySet());
				}
				browserDiff.addAll(browserNames);
				Collections.sort(browserList);
				Collections.sort(browserDiff);
				browserDiff.removeAll(browserList);
			}

			HashMap<String, TestExecutionDetails> clientnames=new HashMap<String,TestExecutionDetails>();
			for(HashMap<String, TestExecutionDetails> client:clientWiseStatus){
				System.out.println(client.keySet().iterator().next());
				clientnames.put(client.keySet().iterator().next(), client.get(client.keySet().iterator().next()));
			}

			Map<String, TestExecutionDetails> treeMap = new TreeMap<String, TestExecutionDetails>(clientnames);

			ArrayList<Integer> browserIndex=new ArrayList<Integer>();

			ArrayList<String> browserNamesInClient=new ArrayList<String>();
			for(Entry<String, TestExecutionDetails> entry : treeMap.entrySet()){
				browserNamesInClient.add(entry.getKey());
				browserIndex.add(browserNames.indexOf(entry.getKey()));
			}
			String browser;
			for(int i=0;i<browserNames.size();i++){
				browser=browserNames.get(i);
				if(!(browserNamesInClient.contains(browser.toUpperCase()))){
					List<String> excelReportPaths=CreateClient.excelReportsPaths.get(clientName.toUpperCase()+"_" +browser.toUpperCase());
					try{
						CreateClient.log.warn("Excution Detials for Client: "+clientName.toUpperCase()+" Browser:"+browser.toUpperCase()+" missing");
						CreateClient.log.warn("Calling generateMissedReport method to generate reports");
						generateMissedReport(excelReportPaths,clientName,browser);
					}catch(Exception e){
						CreateClient.log.error("Unable to generate reports for Client: "+clientName.toUpperCase()+" Browser:"+browser.toUpperCase()+" missing");
						System.out.println("Error: "+e.getMessage());
					}
				}
			}

		}
	}
	
	/*'#########################################################################################################
	'Function name		:	GENERATEMISSEDREPORT
	'Description		:	This function is to generate the test MissedExecutionDetails 
						
	'Parameters			:	N/A
	'#########################################################################################################*/

	private void generateMissedReport(List<String> excelReportPaths, String clientName, String browser) throws FileNotFoundException, IOException, ParseException {
		HashMap<Integer,TestResults> testresults = new HashMap<Integer,TestResults>();
		ArrayList<Integer> testcases = new ArrayList<Integer>();
		HashMap<Integer , String> testCaseExecutionDetails= new HashMap<Integer, String>();
		TestExecutionDetails TED= new TestExecutionDetails();
		HashMap<String,TestExecutionDetails> executionStatus= new HashMap<String,TestExecutionDetails>();
		LinkedHashMap<Integer , String> testCaseTitles=new LinkedHashMap<Integer , String>();
		String endTime=dateFormat.format(new Date());
		htmlFileName = "Consolidated Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" +browser+"_" + clientName+ ".html";
		CreateClient.log.info("HtmlFileName:"+htmlFileName);
		for(String file:excelReportPaths)
		{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFCell cell = sheet.getRow(0).getCell(7);
			int i = 1;
			while(true){
				TestResults ts = new TestResults();
				HSSFRow row = sheet.getRow(i);
				if(row == null)
					break;
				cell = row.getCell(0);
				if(cell == null )
					break;
				String str = cell.getStringCellValue();
				int tcid  = Integer.parseInt(str.replace(".0", ""));
				ts.setTCID(tcid);
				ts.setTCTitle(row.getCell(1).getStringCellValue());
				ts.setResult(row.getCell(2).getStringCellValue());
				cell = row.getCell(3);
				if(cell == null )
					str = "";
				else
					str = cell.getStringCellValue();
				ts.setErrorMsg(str);
				SimpleDateFormat sd = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
				ts.setTime_Stamp(sd.parse(row.getCell(4).getStringCellValue()));

				cell = row.getCell(6);
				if(cell == null )
					str = "";
				else
					str = cell.getStringCellValue();
				if(testresults.containsKey(tcid)){
					TestResults temp = testresults.get(tcid);
					int res = temp.getTime_Stamp().compareTo(ts.getTime_Stamp());
					if(res != -1){
						ts = temp;
					}
					testresults.remove(tcid);
				}
				testresults.put(tcid,ts);
				i++;
			}
		}
		Iterator<Integer> tcid = testresults.keySet().iterator();
		while(tcid.hasNext()){
			int testcaseid = tcid.next();
			testcases.add(testcaseid);
		}
		Collections.sort(testcases);
		HSSFWorkbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = null;
		fileName =ProjPath+"/DestinationRewards/TestReports/"+ "Consolidated Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_" +browser.toUpperCase()+"_" + clientName+".xls";
		CreateClient.log.info("Excel file name: "+fileName);
		fileOut = new FileOutputStream(fileName);
		if(browser.equalsIgnoreCase("IE8"))
		{					
			resultsPaths.put("TestResultsPath_IE8"+clientName,fileName );
		}else if(browser.equalsIgnoreCase("IE9")) {						
			resultsPaths.put("TestResultsPath_IE9"+clientName,fileName );
		}else if(browser.equalsIgnoreCase("IE10")) {						
			resultsPaths.put("TestResultsPath_IE10"+clientName,fileName );
		}else if(browser.equalsIgnoreCase("IE11")) {						
			resultsPaths.put("TestResultsPath_IE11"+clientName,fileName );
		}else if(browser.equalsIgnoreCase("GCHROME"))
		{						
			resultsPaths.put("TestResultsPath_Chrome"+clientName,fileName );
		}else if(browser.equalsIgnoreCase("safari")) {						
			resultsPaths.put("TestResultsPath_Safari"+clientName,fileName );
		}else {						
			resultsPaths.put("TestResultsPath_FF"+clientName,fileName );
		}

		wb.createSheet("Test Result");
		HSSFSheet sheet = wb.getSheetAt(0);
		sheet.setAutobreaks(false);
		HSSFRow row = sheet.createRow((short) 0);
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);

		row.createCell(0).setCellStyle(cellStyle);
		row.createCell(1).setCellStyle(cellStyle);
		row.createCell(2).setCellStyle(cellStyle);
		row.createCell(3).setCellStyle(cellStyle);
		row.createCell(4).setCellStyle(cellStyle);

		row.createCell(0).setCellValue("Test Case ID");
		row.createCell(1).setCellValue("Test Case Title");
		row.createCell(2).setCellValue("Result(P/F)");
		row.createCell(3).setCellValue("Error Message");
		row.createCell(4).setCellValue("Time Stamp");


		int rows = 1;
		for (int i = 0; i < testcases.size(); i++){
			row = sheet.createRow(rows);
			rows++;
			TestResults ts = testresults.get(testcases.get(i));

			sheet.setColumnWidth(2,(256 * 13));
			sheet.setColumnWidth(1, (256 * 25));
			sheet.setColumnWidth(2, (256 * 14));
			sheet.setColumnWidth(3, (256 * 30));
			sheet.setColumnWidth(4, (256 * 28));

			row.createCell(0).setCellValue(String.valueOf(ts.getTCID()));
			row.createCell(1).setCellValue(ts.getTCTitle());
			row.createCell(2).setCellValue(ts.getResult());
			row.createCell(3).setCellValue(ts.getErrorMsg());
			row.createCell(4).setCellValue(ts.getTime_Stamp().toString());

			testCaseTitles.put(ts.getTCID(), ts.getTCTitle());

			if(ts.getResult().equalsIgnoreCase("fail")){
				testCaseExecutionDetails.put(ts.getTCID(), "FAIL"+ts.getErrorMsg());
			}else{
				testCaseExecutionDetails.put(ts.getTCID(),ts.getResult().toUpperCase());
			}
		}
		System.out.println();
		row = sheet.createRow(rows+2);
		wb.write(fileOut);
		fileOut.close();

		Set TCset=testCaseExecutionDetails.keySet();
		Iterator TCiter = TCset.iterator();

		int a[] = new int[TCset.size()];
		int count = 0;
		while (TCiter.hasNext()) {
			a[count] = Integer.parseInt(TCiter.next().toString());
			count++;
		}

		Arrays.sort(a);
		int skipped=0;
		int[] finalReportCounter= new int[3]; 
		for(int Key : a){
			if (testCaseExecutionDetails.get(Key).startsWith("PASS")){
				finalReportCounter[1]=finalReportCounter[1]+1;
				finalReportCounter[0]=finalReportCounter[0]+1;
			} else if (testCaseExecutionDetails.get(Key).startsWith("FAIL")) {
				finalReportCounter[2]=finalReportCounter[2]+1;
				finalReportCounter[0]=finalReportCounter[0]+1;

			} else if (testCaseExecutionDetails.get(Key).startsWith("SKIPPED")) {
				skipped=skipped+1;
			}
		}

		List<String> resultSummary = new ArrayList<String>();
		resultSummary.add(browser.toUpperCase());
		resultSummary.add(Integer.toString(finalReportCounter[0]));
		resultSummary.add(Integer.toString(finalReportCounter[1]));
		resultSummary.add(Integer.toString(finalReportCounter[2]));
		resultSummary.add(Integer.toString(CreateClient.testCasesCounts.get(clientName.toUpperCase()+"_"+browser.toUpperCase())-finalReportCounter[0]));

		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream (fileName));
		fileOut = new FileOutputStream(fileName);
		wb = new HSSFWorkbook(fs);
		sheet = wb.getSheetAt(0);
		sheet.setAutobreaks(false);

		rows = sheet.getPhysicalNumberOfRows();
		wb = new HSSFWorkbook(fs);
		sheet = wb.getSheetAt(0);
		sheet.setAutobreaks(false);

		rows = sheet.getPhysicalNumberOfRows();
		for(int ii=0;ii<resultSummary.size(); ii++)
		{
			row=sheet.createRow(rows+ii+1);
			HSSFCell cell;
			HSSFRichTextString str;
			//Adding the cell values in each row from bean
			switch (ii) {
				case 0:
					cell=row.createCell(1);
					cell.setCellValue("Browser Tested");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(resultSummary.get(ii).toString());
					cell.setCellValue(str);
					break;
				case 1:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Executed");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(resultSummary.get(ii).toString());
					cell.setCellValue(str);
					break;					
				case 2:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Passed");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(resultSummary.get(ii).toString());
					cell.setCellValue(str);
					break;		
				case 3:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Failed");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(resultSummary.get(ii).toString());
					cell.setCellValue(str);
					break;	
				case 4:
					cell=row.createCell(1);
					cell.setCellValue("Total Cases Skipped");
					cell=row.createCell(2);						
					str=new HSSFRichTextString(resultSummary.get(ii).toString());
					cell.setCellValue(str);
					break;	
			}
		}

		wb.write(fileOut);
		fileOut.close();	

		CreateClient.log.info("Client: "+clientName.toUpperCase());
		CreateClient.log.info("ProgramID: "+sd.prgmID.toUpperCase());
		CreateClient.log.info("Browser: "+browser.toUpperCase());
		CreateClient.log.info("TotalTestCase: " +CreateClient.testCasesCounts.get(clientName.toUpperCase()+"_"+browser.toUpperCase()));
		CreateClient.log.info("TotalExecuted: " +finalReportCounter[0]);
		CreateClient.log.info("Passed: " +finalReportCounter[1]);
		CreateClient.log.info("Failed: " +finalReportCounter[2]);
		CreateClient.log.info("Skipped: "+(CreateClient.testCasesCounts.get(clientName.toUpperCase()+"_"+browser.toUpperCase())-finalReportCounter[0]));
		CreateClient.log.info("URL: "+CreateClient.applicationURLs.get(clientName.toUpperCase()));
		CreateClient.log.info("HTMLReportPath: " +ProjPath+"/DestinationRewards/TestReports/"+htmlFileName);
		CreateClient.log.info("HTMLSharedPath: " +CreateClient.sharedPath+"/TestReports/"+htmlFileName);
		CreateClient.log.info("StartTime: " +CreateClient.executionStartTime.get(clientName.toUpperCase()+"_"+browser.toUpperCase()));
		CreateClient.log.info("EndTime: " +endTime);
		CreateClient.log.info("LogFile: " +CreateClient.logFiles.get(clientName.toUpperCase()+"_"+browser.toUpperCase()));
		CreateClient.log.info("ExcelReportPath: " +fileName);

		TED.setTotalTestCases(CreateClient.testCasesCounts.get(clientName.toUpperCase()+"_"+browser.toUpperCase()));
		TED.setHTMLReportPath(ProjPath+"/DestinationRewards/TestReports/"+htmlFileName);
		TED.setHTMLSharedPath(CreateClient.sharedPath+"/TestReports/"+htmlFileName);
		TED.setStartTime(CreateClient.executionStartTime.get(clientName.toUpperCase()+"_"+browser.toUpperCase()));
		TED.setEndTime(endTime);
		TED.setLogFile(CreateClient.logFiles.get(clientName.toUpperCase()+"_"+browser.toUpperCase()));
		TED.setExcelReportPath(fileName);
		TED.setTotalExecuted(finalReportCounter[0]);
		TED.setPassed(finalReportCounter[1]);
		TED.setFailed(finalReportCounter[2]);
		TED.setSkipped(CreateClient.testCasesCounts.get(clientName.toUpperCase()+"_"+browser.toUpperCase())-finalReportCounter[0]);
		TED.setURL(CreateClient.applicationURLs.get(clientName.toUpperCase()));

		executionStatus.put(browser.toUpperCase(), TED);
		CreateClient.executionStatusForAllClients.put(clientName.toUpperCase(), executionStatus);

		TemplateGenerator htmlTemplate = new TemplateGenerator();
		htmlTemplate.buildTemplate_BrowserLevel_ForMissedExecutionDetails(clientName.toUpperCase(),browser.toUpperCase(),CreateClient.testCasesCounts.get(clientName.toUpperCase()+"_"+browser.toUpperCase()),finalReportCounter[0],finalReportCounter[1],finalReportCounter[2],testCaseExecutionDetails,TED,testCaseTitles);
	}

}