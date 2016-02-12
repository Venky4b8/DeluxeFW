package com.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperations {
	
	static Connection conn;
	
	public void dbConnection(String connURL, String user, String pswd)
	{
		try {
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	         conn = DriverManager.getConnection(connURL,user, pswd);
	         System.out.println("connected");
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public ResultSet getDataFromDb(String query)
	{
		ResultSet rs = null;
		Statement statement = null;
		try{
			dbConnection("jdbc:sqlserver://<hostname>", "<user>", "<pswd>");
			statement = conn.createStatement();
	         String queryString = "select * from table1 where type='a'";
	         rs = statement.executeQuery(queryString);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
				 if (rs != null) try { rs.close(); } catch(Exception e) {}
		         if (statement != null) try { statement.close(); } catch(Exception e) {}
		         if (conn != null) try { conn.close(); } catch(Exception e) {}
			
		}
		return rs;
	}
	

}
