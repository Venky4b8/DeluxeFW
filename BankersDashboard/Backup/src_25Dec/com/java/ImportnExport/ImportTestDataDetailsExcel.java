package com.java.ImportnExport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.java.Objects.TestDataDetails;
import com.java.SeleniumDriver;
import java.sql.*;

//import org.seleniumhq.jetty7.util.log.Log;

/**
* This java program is used to read the data from a Excel sheet using SQL query
*/
public class ImportTestDataDetailsExcel{
	
	private SeleniumDriver sd;

	public ImportTestDataDetailsExcel (SeleniumDriver sd){
		this.sd=sd;
	}


	/*'#########################################################################################################
	'Function name		:	displayFromExcel
	'Description		:	This function is to read the details from Excel sheet 
						
	'Parameters			:	TestcaseID
	'#########################################################################################################*/
	
	public HashMap<Integer,TestDataDetails> displayFromExcel (int TCID)
	{
			//HashMap to store each row as bean object along with the row number
			TreeMap<Integer,TestDataDetails> intermediateRows =  new TreeMap<Integer,TestDataDetails>();
			HashMap<Integer,TestDataDetails> Rows =  new HashMap<Integer,TestDataDetails>();
			HashMap<String, String> objectRepository = new HashMap<String, String>();
			String xlsPath ="";
			ResultSet rs, rs1;
			try
			{
				xlsPath = sd.ProjPath+"\\TestInputs\\"+sd.hMap.get("testDataFile");
				sd.log.info("test data details path===== "+xlsPath);
				//Connection to excel sheet as database
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+xlsPath+";DriverID=22;READONLY=false","","");
				String sql="Select * from [DataSheet$] Where \"Test Case ID\" = "+TCID+" Order by \"Test Priority\"";
		        Statement st = conn.createStatement();
		        rs1 = st.executeQuery("Select ObjectName,ObjectLocator from [ObjectRepository$]");
		        while (rs1.next()) {
		        	objectRepository.put(rs1.getString("ObjectName"), rs1.getString("ObjectLocator"));
		        }
		        rs = st.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();
		        int numberOfColumns = rsmd.getColumnCount(); //number of columns in a row	        
		        //Loop through the test steps resultset.
		        while (rs.next()) {		        	
		        	TestDataDetails tdd=new TestDataDetails();
		        	int priority = 0;
		        	int caseId = 0;
					int dataId = 0;
			        //Loop through the columns in each row.
		        	for (int c = 1; c <= numberOfColumns; c++) {		        	  
				      switch (rsmd.getColumnType(c))
						{
	                   		//Assigning each cell value in a row to a bean based on the type of value
	                   	    case Types.BOOLEAN :								                  	    	
	                   	    	break;
							case 8 : // cell type numeric.
								int str = rs.getInt(c);							  							
								if(c==1){
									caseId = str;
									tdd.setTestCaseID(str);
								}if(c==2) {
									dataId = str;
									tdd.setTestDataID(str);
								}if(c==5)
									tdd.setDataFields(str+"");	
								if(c==6){
									tdd.setDataValues(str+"");
								}								
								break;
							case 12 :	// cell type string.
							    String strValue = rs.getString(c);							  
								if(c==3)
									tdd.setTestCaseTitle(strValue);
								if(c==4)
									tdd.setWorkingPage(strValue);
								if(c==5) {
									try {
										if (strValue.startsWith("OR:")) {
											tdd.setDataFields(objectRepository.get(strValue.substring(3)));
										} else
											tdd.setDataFields(strValue);
									} catch (Exception e) {
										
									}
								}
								if(c==6)
									tdd.setDataValues(strValue);
								if(c==7)
									tdd.setActionType(strValue);
								if(c==8)
									tdd.setCondition(strValue);
								if(c==9) {								
									tdd.setBrowserType(strValue.toUpperCase());
								}
								if(c==10){
									tdd.setclientName(strValue.toUpperCase());
								}
								if(c==11) {
									tdd.setFieldName(strValue);
								}										
								break;
							case Types.NULL:
								if(c==9) {
									tdd.setBrowserType("COMMON");	
								}
								if(c==10) {
									tdd.setclientName("COMMON");	
								}
								if(c==11) {
									tdd.setFieldName("NONE");
								}																			
					            break;
					        case Types.OTHER:
					            break;					           		
							default :	
								break;
						}
	                }
		        	//Adding bean object i.e. a row along with number to HashMap
		            priority = Integer.parseInt(Integer.toString(caseId*10) + Integer.toString(dataId));
		            if(priority != 0)		            	
		            	intermediateRows.put(priority,tdd);
		        }		        
		        Set<Integer> prKeys = new TreeSet<Integer>();
				prKeys = intermediateRows.keySet();
				Iterator<Integer> iterKeys = prKeys.iterator();		
				int counter = 0;
				while(iterKeys.hasNext()) {
					counter++;
					int nextKey = iterKeys.next();					
					Rows.put(new Integer(counter), intermediateRows.get(nextKey));
				}
				//Push the Parameters in to hash map
				if (sd.parameterDetails.isEmpty()) {
					sql="Select * from [TestData$]";
			        Statement st1 = conn.createStatement();
			        rs1 = st1.executeQuery(sql);			        
			        String pName = "";		        
			        String pValue = "";
			        int pIndex = 0;
			        String pKey = "";			
			        ResultSetMetaData rsmd1 = rs1.getMetaData();
			        int numberOfColumns1 = rsmd1.getColumnCount(); //number of columns
			        while (rs1.next()) {
			        	for (int c = 1; c <= numberOfColumns1; c++) {
			        		switch (rsmd1.getColumnType(c))
							{
								case 8: //Numeric column
									if(c==3)
				                		pIndex = rs1.getInt(c);
									break;
								case 12 : //varchar column									
									if(c==1) 
				                		pName = rs1.getString(c);									
									if(c==2)										
										pValue = rs1.getString(c);									
									break;
							}	
			        	}
			        	pKey = pName.toLowerCase()+pIndex+"";
			            sd.parameterDetails.put(pKey, pValue);
			        }
				}
		        rs.close();		        
		        st.close();
		        conn.close();
	        
			} catch (Exception e){
				sd.log.debug("Unable to read the data from the TestData: "+e.getMessage());
				System.out.println("Unable to read the data from the TestData");
			}
			return Rows;
		}
}
