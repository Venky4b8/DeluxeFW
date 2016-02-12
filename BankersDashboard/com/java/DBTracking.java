package com.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;


public class DBTracking {

	SeleniumDriver sd;
	public DBTracking(SeleniumDriver sd) {
		this.sd=sd;
	}
	
	/*'#########################################################################################################
	'Function name		:	TestResultTracking
	'Description		:	This function is to record each test case result in the database
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void TestResultTracking(List<String> exportResult,String browser,String client,HashMap<Integer, Integer> attributeID2,String prgmID,String executionlog,int stepNo,String stepDescription,String strTestStepExecutionTimeDuration,String NodeIp,String build_ID)
	{
		try{
			int testcaseID=Integer.parseInt(exportResult.get(0));
			String testcaseTitle=exportResult.get(1);
			String result=exportResult.get(2);
			String error= exportResult.get(3);
			java.util.Date date = new java.util.Date();
		    long t = date.getTime();
		    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
			System.out.println("the client name is-----------" +client+"broowser value is--------" +browser+"tc are----------------"+testcaseID+"title is------------"+testcaseTitle+"result is--------------"+result+"error------------"+error+"timestamp is-----------"+sqlTimestamp);
			System.out.println("the program id is---------"+prgmID+"the attribute id is-------------"+attributeID2);
			int attributeID=attributeID2.get(testcaseID);
			System.out.println("The attribute value is------"+attributeID);
			System.out.println("The execution log value is-----------"+executionlog);
			String url="jdbc:sqlserver://10.120.100.52:61446;databaseName=automation_tests";
			String password = "p*fR3bCE!5Hz3H_90dNvi"; 
			String userName = "auto_app"; 
			String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
			Connection conn = null;
			PreparedStatement preparedStatement = null,preparedStatement1 = null;
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(url,userName,password);
			System.out.println("connected successfuly");
			String sql="INSERT INTO TestResult (ClientName,AttributeId,ProgramId,TCID ,TCTitle ,TestResult,ErrorReason,ExecutionTimeStamp,ExecutionTimeDuration,Browser,ExecutionNodeIp,NodeOperatingSystem,ExecutionLog,Build_ID) VALUES"
		+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = conn.prepareStatement(sql);		
			preparedStatement.setString(1,client);
			preparedStatement.setInt(2,attributeID);
			preparedStatement.setInt(3,Integer.parseInt(prgmID));
			preparedStatement.setInt(4,testcaseID);
			preparedStatement.setString(5,testcaseTitle);
			preparedStatement.setString(6,result);
			preparedStatement.setString(7,error);
			preparedStatement.setTimestamp(8,sqlTimestamp);
			preparedStatement.setString(9,strTestStepExecutionTimeDuration);
			preparedStatement.setString(10,browser);
			preparedStatement.setString(11,NodeIp);
			preparedStatement.setString(12,System.getProperty("os.name"));			
			//int tcMaxLog = Integer.parseInt(executionlog);
//			preparedStatement.setString(13,Integer.toString(tcMaxLog));			
			preparedStatement.setString(13,executionlog);
			preparedStatement.setString(14,build_ID);
			int rowcount =preparedStatement.executeUpdate();
			System.out.println("number of rows effected in TestResult are+++++++++++++++++++++++++++++++++++++++++++"+rowcount);
			sd.log.info("number of rows effected in TestResult are+++++++++++++++++++++++++++++++++++++++++++"+rowcount);
			String sql2="INSERT INTO TestStepLog"
		+ "(TCID ,TCStepId,TCStepDescription,Result,ErrorReason,ExecutionTimeStamp,ExecutionTimeDuration,ExecutionLog) VALUES"
		+ "(?,?,?,?,?,?,?,?)";
			preparedStatement1=conn.prepareStatement(sql2);			
			preparedStatement1.setInt(1,testcaseID);
			preparedStatement1.setInt(2,stepNo);
			preparedStatement1.setString(3,stepDescription);
			preparedStatement1.setString(4,result);
			preparedStatement1.setString(5,error);
			preparedStatement1.setTimestamp(6,sqlTimestamp);
			preparedStatement1.setString(7,strTestStepExecutionTimeDuration);		
			
			preparedStatement1.setInt(8,Integer.parseInt(executionlog));		
			
			
			int rowcount1 = preparedStatement1.executeUpdate();
			System.out.println("number of rows effected in TestStepLog are+++++++++++++++++++++++++++++++++++++++++++"+rowcount1);
			sd.log.info("number of rows effected in TestStepLog are+++++++++++++++++++++++++++++++++++++++++++"+rowcount1);		
		}catch(Exception e) {
			sd.log.warn("Eror occured while updating the DB" +e.getLocalizedMessage());
			sd.log.debug("Exception: "+e.getMessage());
		}		
	}
	
}
