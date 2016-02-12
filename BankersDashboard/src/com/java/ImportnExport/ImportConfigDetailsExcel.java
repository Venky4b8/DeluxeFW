package com.java.ImportnExport;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.java.CreateClient;
import com.java.Objects.ConfigDetails;

import java.sql.*;

/**
 * This java program is used to read the data from a Excel sheet (Config.xls)
 */
public class ImportConfigDetailsExcel {
	public static String TestInputsPath;//=System.getProperty("user.dir");
	private static Logger log = Logger.getLogger(ImportConfigDetailsExcel.class.getName());
	public ImportConfigDetailsExcel (){}	

	
	/*'#########################################################################################################
	'Function name		:	displayFromExcel
	'Description		:	This function is to read the config sheet details from Excel
						
	'Parameters			:	TestcaseID
	'#########################################################################################################*/
	
	public HashMap<Integer,ConfigDetails> displayFromExcel ()
	{
		//HashMap to store each row as bean object along with the row number
		HashMap<Integer,ConfigDetails> Rows =  new HashMap<Integer,ConfigDetails>();
		ResultSet rs;
		String str;		

		try {
			String xlsPath="";
			ArrayList<Integer> TestCases = new ArrayList<Integer>();
			TestInputsPath = CreateClient.ProjPath+"\\TestInputs";
			System.out.println("TestInputsPath  : "+TestInputsPath);
			 xlsPath= TestInputsPath+"\\Config.xls";
			System.out.println("xlsPath : "+xlsPath);
	
			//Connection to excel sheet as database
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+xlsPath+";DriverID=22;READONLY=false","","");
			String sql = "Select  *  from [Sheet1$] where Execution =1";
			
			Statement st = conn.createStatement();
			rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			System.out.println("number of coulmns are-------" +numberOfColumns);//number of columns in a row
			
			//Loop through the test steps resultset.
			while (rs.next()) {
				System.out.println("asjkdhaskjdk++++++++++++++++====="+rs.getString(1));
				ConfigDetails confDtls=new ConfigDetails();
				TestCases = new ArrayList<Integer>();
				
				
				System.out.println("asjkdhaskjdk++++++++++++++++====="+rs.getString(1).toString());
				//Loop through the columns in each row.
				for (int c = 1; c <= numberOfColumns; c++) {
					 str = rs.getString(c);
					if(c==1){				
						System.out.println("test cases are----" +str);
						log.debug("String to split: "+str);				
						String[] tokens = str.split(",");
						for(int i=0;i<tokens.length;i++){
							System.out.println("Tokens: " + tokens[i]);									    	
							if(!tokens[i].contains("-"))
								TestCases.add(Integer.parseInt(tokens[i]));
							else{
								String[] range=tokens[i].split("-");
								String from = range[0];
								String to =  range[1];
								int f = Integer.parseInt(from);
								int t = Integer.parseInt(to);
								TestCases.add(f);
								while(f!=t){	
									f=f+1;
									int s = f;									    			
									TestCases.add(s);
								}
							}
						}
						log.debug("ranges of - : " + TestCases);
						for(int i=0;i<TestCases.size();i++){
							System.out.println("TEST CASES ARE HERE : ----- " +TestCases.get(i));
						}
						confDtls.setTestCasesToBeExecuted(TestCases);
					} else if(c==2) { 
						confDtls.setScriptPath(str);
					} else if(c==3) {
						confDtls.setBrowser(str);
					}
					else if(c==4)
					{
						confDtls.setClient(str);
					}
				}	        	
				//Adding bean object i.e. a row along with number to HashMap
				Rows.put(rs.getRow(),confDtls);
		}
		rs.close();
		st.close();
		conn.close();
		}catch (Exception e){
			log.debug("Exception e"+e.getMessage());
		}		
		return Rows;
	}
}
