
package com.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.java.ImportnExport.ImportConfigDetailsExcel;


/**
 * This java program is used to read the data from a Excel sheet (TestData.xls)
 */
public class ExecuteFailedCases {
	private static Logger log = Logger.getLogger(ExecuteFailedCases.class.getName());
	public ExecuteFailedCases (){}	


	/*'#########################################################################################################
	'Function name		:	updateConfig
	'Description		:	This is to read and update the failed test case details in config Excel sheet which is in the path given as argument
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void updateConfig (List<String> clientName,ArrayList<String[]> arry,HashMap<String,String> prgmIdMap) throws IOException
	{
		String blnExecuteFailedCases = "";
		int failedExecution = 0;
		Properties miscProps=new Properties();
		LinkedList<String> result = new LinkedList<String>();
		String values = null;
		try {
			FileInputStream inFile = new FileInputStream(ImportConfigDetailsExcel.TestInputsPath+"/properties/Misc.properties");
			miscProps.load(inFile);
			blnExecuteFailedCases = miscProps.getProperty("executefailedcases");
			failedExecution = Integer.parseInt(miscProps.getProperty("launchExecution"));
			miscProps.setProperty("launchExecution", "0");
			System.out.println("the launch property is-------" +miscProps.getProperty("launchExecution"));
			FileOutputStream propsOut = new FileOutputStream(ImportConfigDetailsExcel.TestInputsPath+"/properties/Misc.properties");			
			miscProps.store(propsOut,"Updated Props File");			
			inFile.close();
		}catch(IOException io) {
			log.error("Unable to read Misc Properties File: "+io.getMessage());
			System.out.println("Unable to read Misc Properties File.");
		}
		
		if ((blnExecuteFailedCases.equalsIgnoreCase("true")) && (failedExecution == 1)){
			InputStream inputStream = null;
			try {	
				String xlsPath = CreateClient.ProjPath+"//TestInputs//"+SeleniumDriver.configFile;
				inputStream = new FileInputStream (xlsPath);
			}catch (FileNotFoundException e) {
				log.error("File not found in the specified path: "+e.getMessage());
			}	
			try {

				for(int i=0;i<clientName.size();i++)
				{			
					for(int k=0;k<arry.size();k++)
					{
						String[] browsers=arry.get(k);
						for(int b=0;b<browsers.length;b++){
							System.out.println("The hashmap values are-----" +SeleniumDriver.failMap);
							System.out.println("The key value is------"+"failedCases"+clientName.get(i)+""+browsers[b]);
							if(browsers[b].equalsIgnoreCase("ff"))
							{	
								values=SeleniumDriver.failMap.get("failedCases_ff"+clientName.get(i)+""+browsers[b]);	
							}

							if(browsers[b].equalsIgnoreCase("IE8"))
							{	
								values=SeleniumDriver.failMap.get("failedCases_ie8"+clientName.get(i)+""+browsers[b]);	
							}
							if(browsers[b].equalsIgnoreCase("IE9"))
							{	
								values=SeleniumDriver.failMap.get("failedCases_ie9"+clientName.get(i)+""+browsers[b]);	
							}
							if(browsers[b].equalsIgnoreCase("IE10"))
							{	
								values=SeleniumDriver.failMap.get("failedCases_ie10"+clientName.get(i)+""+browsers[b]);	
							}
							if(browsers[b].equalsIgnoreCase("IE11"))
							{	
								values=SeleniumDriver.failMap.get("failedCases_ie11"+clientName.get(i)+""+browsers[b]);	
							}
							if(browsers[b].equalsIgnoreCase("gchrome"))
							{	
								values=SeleniumDriver.failMap.get("failedCases_gchrome"+clientName.get(i)+""+browsers[b]);	
							}
							if(browsers[b].equalsIgnoreCase("safari"))
							{	
								values=SeleniumDriver.failMap.get("failedCases_safari"+clientName.get(i)+""+browsers[b]);	
							}


							System.out.println("The failed test cases values are" +values);
							String failedTC=values.split("_")[0];
							String Client=values.split("_")[1];
							String failedClient=Client+"-"+prgmIdMap.get(Client);
							String failedBrowser=values.split("_")[2];
							result.add(failedTC);
							result.add(failedBrowser);
							result.add(failedClient);
							System.out.println("The failed test casesis-------------"+failedTC+"-----------failed client is------------" +failedClient+"-----------failed browser is----------" +failedBrowser);	    			    			    		
						}
					}
				}
				System.out.println("list values are-------" +result);
				String xlsPath = CreateClient.ProjPath+"//TestInputs//"+SeleniumDriver.configFile;
				FileInputStream inputStream2 = new FileInputStream (xlsPath);
				System.out.println("inputStream: " + inputStream2);
				POIFSFileSystem fs = new POIFSFileSystem(inputStream2);
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				System.out.println(fs);
				HSSFSheet sheet = wb.getSheetAt(0);			
				sheet.setAutobreaks(false);
				System.out.println(sheet);
				int rows; // No of rows
				for(int x=0; x<3;x++){ 
					sheet.setColumnWidth(2,  (short)(256*13));
					sheet.setColumnWidth(1,  (short)(256*25));
					sheet.setColumnWidth(2,  (short)(256*14));
					sheet.setColumnWidth(3,  (short)(256*30));
					sheet.setColumnWidth(4,  (short)(256*28));
					HSSFRow roww=sheet.getRow(1);			    
					HSSFCell cell = roww.getCell(x);
					cell.setCellValue(result.get(x).toString());
					inputStream2.close();
				}
				rows = sheet.getPhysicalNumberOfRows();
				System.out.println("No of rows in sheet="+rows);
				int count=0;		    
				for(int y=3;y<result.size();)
				{				
					System.out.println("Row to be created is" +(rows+count));
					HSSFRow row=sheet.createRow(rows+count);				
					HSSFCell cell1=row.createCell(0);
					HSSFCell cell2=row.createCell(1);
					HSSFCell cell3=row.createCell(2);
					System.out.println("result value is----"+result.get(y).toString());
					System.out.println("result value is----"+result.get(y+1).toString());
					System.out.println("result value is----"+result.get(y+2).toString());
					cell1.setCellValue(result.get(y).toString());
					cell2.setCellValue(result.get(y+1).toString());
					cell3.setCellValue(result.get(y+2).toString());
					y=y+3;
					count++;
					inputStream2.close();
				}           
				FileOutputStream outFile =new FileOutputStream(xlsPath);
				wb.write(outFile);
				outFile.close();
				System.out.println("Config file is updated with the failed test cases");
				Runtime.getRuntime().exec("cmd /c start C:\\DestinationRewards_Update2.1\\DestinationRewards\\InvokeBatch.bat");
				System.out.println("execution of failed cases have been started");
			}catch(Exception e) {
				log.debug("Exception: "+e.getMessage());
			}
		}
		else {				
			System.out.println("Unable to execute failed test case..........");
		}
	}
}
