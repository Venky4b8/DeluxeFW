package com.java;




import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.common.collect.Multimap;
import com.java.ImportnExport.ExportTestResultsExcel;
import com.java.Objects.TestExecutionDetails;

public class TemplateGenerator {

	int total;
	int passedCount;
	int failedCount ;
	int skipped;

	public static  HashMap<String,String> passedMap=new HashMap<String,String>();
	public static String passeddummy = "";
	public static String faileddummy = "";

	public static String totalPassed = new String();
	public static String totalFailed = new String();
	Calendar cal = Calendar.getInstance();
	public static final String dateTime = "MMddyy_HHmmss";
	SimpleDateFormat dateFormat = new SimpleDateFormat(dateTime);	
	
	
	/*'#########################################################################################################
	'Function name		:	HtmlReport_ClientLevel
	'Description		:	This function is to generate the client level execution report in html format
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void HtmlReport_ClientLevel(ArrayList<String[]> browserNames,List<String> clientName,ArrayList<Integer> testcases){

		try {
			System.out.println("Clients are-------"+clientName);
			Connection conn = null;
			Statement st;
			ResultSet rs;
			for(int c=0;c<clientName.size();c++){

				HashMap<String,String> clientExecutionDetails=new HashMap<String,String>();
				Collection<HashMap<String, String>> clientList=CreateClient.clientLevelDetails.get(clientName.get(c).toUpperCase());
				for(HashMap<String,String> clientDetails:clientList){
					for(String key:clientDetails.keySet()){
						clientExecutionDetails.put(key, clientDetails.get(key));
					}
				}

				CreateClient.clientReports_URLs.put(clientName.get(c).toUpperCase().toUpperCase(), clientExecutionDetails);

				String totalTCs = "";
				String passedTCs ="";
				String failedTCs = "";
				String skippedTCs="";
				String browserName;

				List<HashMap<String,String>> listOfMaps = new ArrayList<HashMap<String,String>>();
				String graphVerticalLabelNames="";

				String file=CreateClient.clientReports_URLs.get(clientName.get(c).toUpperCase()).get("excelReportPath");

				String htmlFilename="Test Results_" +CreateClient.executionlog+"_"+dateFormat.format(cal.getTime())+"_"+ clientName.get(c)+ ".html";
				System.out.println("the file is------" +file);
				FileInputStream resutlFileReader=new FileInputStream(file);
				BufferedWriter out = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"\\TestReports\\"+htmlFilename));
				ExportTestResultsExcel.resultsPaths.put("HtmlReport"+clientName.get(c),System.getProperty("user.dir")+"\\TestReports\\"+htmlFilename);
				HashMap<String,String> htmlReportName=new HashMap<String,String>();
				htmlReportName.put("htmlReportPath", System.getProperty("user.dir")+"\\TestReports\\"+htmlFilename);
				htmlReportName.put("htmlSharedPath", CreateClient.sharedPath+"\\TestReports\\"+htmlFilename);
				CreateClient.clientLevelDetails.put(clientName.get(c).toUpperCase(), htmlReportName);

				HSSFWorkbook wb =new HSSFWorkbook(resutlFileReader);
				int workSheetCount= wb.getNumberOfSheets();
			
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" +file+ ";DriverID=22;READONLY=false","","");

				String imageSrc = "https://clients.xuat.bankersdashboard.com/assets/images/header/bdlogo.jpg";
				String color = "3399FF";		
				out.write("<html>");
				out.write("<head>");
				out.write("\n <script type=\"text/javascript\">");			
				out.write("function checkError(ele){document.getElementById('ErrorDiv').style.Visibility='Visible';document.getElementById('ErrorDiv').style.display='';document.getElementById(\"ErrorDiv\").innerText=ele;}");
				out.write("</script>");
				out.write("\n </head>");
				out.write("\n <body leftmargin=\"0\" marginwidth=\"0\"topmargin=\"0\" marginheight=\"0\" offset=\"0\" bgcolor='#FFFFFF'>");
				out.write("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor='#FFFFFF'> \n <tr> \n <td valign=\"top\" align=\"left\">");
				out.write("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
				out.write("<td align=\"left\" valign=\"middle\"style=\"background-color:#" + color + ";border-top:0px solid #333333;border-bottom:1px dashed #000000;\"><left><a href=\"\"><IMG id=editableImg1 SRC="+imageSrc+" height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\" ></a></left></td>");
				out.write("<td align=\"left\" valign=\"middle\" style=\"background-color:#" + color + ";border-top:0px solid #333333;border-bottom:1px dashed #000000;\"><span style=\"font-size:14px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;\">" + "AUTOMATION TEST RESULTS - "+clientName.get(c).toUpperCase() + "</span></td>");
				out.write("<td align=\"left\" valign=\"middle\"style=\"background-color:#" + color + ";border-top:0px solid #333333;border-bottom:1px dashed #000000;\"><left><a href=\"\"><IMG id=editableImg1 SRC="+CreateClient.clientLOGO.get(clientName.get(c).toUpperCase())+"  height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\"></a></left></td>");
				out.write("<td align=\"\" valign=\"middle\" style=\"background-color:#"+ color + ";border-top:0px solid #000000;border-bottom:1px dashed #000000;\"><span style=\"font-size:15px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none;\"></span></td>");
				out.write("</tr> \n </table> \n <table width=\"100%\" cellpadding=\"3\"cellspacing=\"0\"> \n <tr>");
				out.write("<h3>Test Result</h3>");
				out.write("</table>");
				out.write("<div>");
				out.write("<TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=55%>");
				//To generate the HTML table headers
				out.write("<TR>");
				out.write("<TD><CENTER><B>");
				out.write("<FONT SIZE=3>TCID</FONT>");
				out.write("</B></CENTER></TD>");
				out.write("<TD><CENTER><B>");
				out.write("<FONT SIZE=3>TC Title</FONT>");
				out.write("</B></CENTER></TD>");

				for(int sheetNumber=0;sheetNumber<workSheetCount;sheetNumber++){				
					browserName= wb.getSheetName(sheetNumber);
					out.write("<TD><CENTEr>");
					out.write("<B><FONT SIZE=3>"+browserName.toUpperCase()+"</FONT></B>");
					out.write("</CENTER></TD>");
					graphVerticalLabelNames = graphVerticalLabelNames+browserName.toUpperCase();
					if(sheetNumber!=workSheetCount-1){
						graphVerticalLabelNames = graphVerticalLabelNames+"|";
					}
				}
				System.out.println(graphVerticalLabelNames);
				out.write("</tr> \n ");
				ArrayList<String> testCaseIDs = new ArrayList<String>();

				// To read the test result from each sheet in the Test result excel
				for(int sheetNumber=0;sheetNumber<workSheetCount;sheetNumber++){
					browserName= wb.getSheetName(sheetNumber);
					String SqlQuery = "select [Test Case ID],[Test Case Title],[Result(P/F)],[Error Message] from ["+browserName+"$]";
					HashMap<String,String> browserDetails = new HashMap<String,String>();
					System.out.println(SqlQuery);
					st = conn.createStatement();
					rs = st.executeQuery(SqlQuery);
					System.out.println("resultset obtained");
					while(rs!=null && rs.next()){
						String testcaseID= rs.getString(1);
						String errorMessage= rs.getString(4);
						if(testcaseID!=null){
							if(!testCaseIDs.contains(testcaseID.trim())){							
								testCaseIDs.add(testcaseID);
							}
							if(errorMessage!= null){
								String value=rs.getString(2)+","+rs.getString(3)+","+errorMessage;
								browserDetails.put(testcaseID, value);							
							}else{
								String value=rs.getString(2)+","+rs.getString(3)+","+"";
								browserDetails.put(testcaseID, value);						

							}
						}				
					}
					listOfMaps.add(browserDetails);
					rs.close();st.close();
				}			
				// To construct the testcaseID list in ascending order.
				for(int i=0;i<testCaseIDs.size();i++){
					for(int j=i+1;j<testCaseIDs.size();j++){
						if(Integer.parseInt(testCaseIDs.get(i))>Integer.parseInt(testCaseIDs.get(j))){
							String temp2=testCaseIDs.get(i);
							testCaseIDs.set(i, testCaseIDs.get(j));
							testCaseIDs.set(j,temp2);
						}
					}
				} 
				//To get the Total test cases and pass,fail count in each work sheet

				for(int i = 0; i < listOfMaps.size(); i++){
					int failTC_Count=0;
					int passTC_Count=0;
					int skippedTC_Count=0;
					totalTCs= totalTCs+listOfMaps.get(i).size();
					if(i!=listOfMaps.size()-1){
						totalTCs = totalTCs+",";
					}
					Object[] tcIDArray= listOfMaps.get(i).keySet().toArray();
					for(int j=0;j<tcIDArray.length;j++){

						String str[]=listOfMaps.get(i).get(tcIDArray[j].toString()).split(",");
						if(str[1].trim().equalsIgnoreCase("Fail")){
							failTC_Count=failTC_Count+1;
						}
						else if(str[1].trim().equalsIgnoreCase("Skipped")){
							skippedTC_Count=skippedTC_Count+1;
						}else{
							passTC_Count=passTC_Count+1;
						}
					}
					passedTCs = passedTCs+passTC_Count;
					failedTCs = failedTCs+failTC_Count;
					skippedTCs=skippedTCs+skippedTC_Count;
					if(i!=(listOfMaps.size()-1)){
						passedTCs = passedTCs+",";
						failedTCs = failedTCs+",";
						skippedTCs=skippedTCs+",";
					}

				}
				String total="";
				String pass="";
				String fail="";
				String skipped="";
				for(int pos=0;pos<graphVerticalLabelNames.split("\\|").length;pos++){
					if(pos==0){
						total=graphVerticalLabelNames.split("\\|")[pos]+"="+totalTCs.split(",")[pos];
						pass=graphVerticalLabelNames.split("\\|")[pos]+"="+passedTCs.split(",")[pos];
						fail=graphVerticalLabelNames.split("\\|")[pos]+"="+failedTCs.split(",")[pos];
						skipped=graphVerticalLabelNames.split("\\|")[pos]+"="+skippedTCs.split(",")[pos];

					}else{
						total=total+":"+graphVerticalLabelNames.split("\\|")[pos]+"="+totalTCs.split(",")[pos];
						pass=pass+":"+graphVerticalLabelNames.split("\\|")[pos]+"="+passedTCs.split(",")[pos];
						fail=fail+":"+graphVerticalLabelNames.split("\\|")[pos]+"="+failedTCs.split(",")[pos];
						skipped=skipped+":"+graphVerticalLabelNames.split("\\|")[pos]+"="+skippedTCs.split(",")[pos];
					}
				}

				// To generate the html with each test case result in respective browsers
				for (int j = 0;j<testCaseIDs.size();j++) {
					out.write("<tr> \n ");
					out.write("<TD><CENTEr>");
					out.write("<FONT SIZE=2>"+testCaseIDs.get(j)+"</FONT>");
					out.write("</CENTER></TD>");
					for(int i = 0; i < listOfMaps.size(); i++){
						if(listOfMaps.get(i).keySet().contains(testCaseIDs.get(j))){
							String str[]= listOfMaps.get(i).get(testCaseIDs.get(j)).split(",");
							out.write("<TD><CENTEr>");
							out.write("<FONT SIZE=2>"+str[0]+"</FONT>");
							out.write("</CENTER></TD>");
							break;
						}
					}
					for(int i = 0; i < listOfMaps.size(); i++){
						out.write("<TD><CENTEr>");
						if(listOfMaps.get(i).get(testCaseIDs.get(j))!=null){
							String str[]= listOfMaps.get(i).get(testCaseIDs.get(j)).split(",");
							if(str[1].trim().equalsIgnoreCase("Fail")){
								out.write("<a href=\"javascript:checkError('"+str[2]+"');\">"+str[1]+"</a>");
							}else{
								out.write("<FONT SIZE=2>"+str[1]+"</FONT>");
							}
						}else{
							out.write("<FONT SIZE=2>"+"N/A"+"</FONT>");
						}
						out.write("</CENTER></TD>");
					}
					out.write("</tr> \n ");
				}		
				out.write("</table");
				out.write("\n </div>");
				out.write("<div style=\"width: 100px; height: 10px;\"></div>");
				out.write("\n <div id='ErrorDiv' style=\"color:#FF0000\" align=\"left\">");
				out.write("\n</div>");
				// To generate Test results Graph	
				out.write("\n<div>");out.write("\n<div>");
				out.write("\n<div>");out.write("<h3>Result Graph</h3>");out.write("\n<div>");
				out.write("<div>");
				out.write("<IMG align=\"left\" src=\"https://chart.googleapis.com/chart?chs=600x250&chd=t:"+totalTCs+"|"+passedTCs+"|"+failedTCs+"|"+skippedTCs+"&chco=0000FF,00FF00,FF0000,696969&cht=bvg&chxt=x,y&chds=a&chl="+graphVerticalLabelNames+"&chbh=19,0,15&&chdl=Total|Pass|Fail|skip\">");
				out.write("</div>");
				out.write("\n </BODY> \n <html>");			
				out.close();
			}
		}catch(Exception e) {
			CreateClient.log.debug("Exception: "+e.getMessage());
		}
	}


	/*'#########################################################################################################
	'Function name		:	buildTemplate_BrowserLevel
	'Description		:	This function is to generate the browser level template  in html format
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void buildTemplate_BrowserLevel(SeleniumDriver sd,int Total, int Passed, int Failed, HashMap<Integer, String> testCaseExecutionDetails) {

		sd.log.info("Executed:"+Total);
		sd.log.info("Passed:"+Passed);
		sd.log.info("Failed:"+Failed);
		sd.log.info("Skipeed:"+(sd.TestCases.size()-Total));
		sd.log.info("Application URL:"+sd.appurl);
		sd.log.info("Start Time:"+sd.hMap.get("StartTime"));
		sd.log.info(" Creating HTML Report ");

		sd.TED.setURL(sd.appurl);
		if(sd.isFailedCaseExecuted)
			sd.TED.setTotalTestCases(sd.totalTestCases);
		else
			sd.TED.setTotalTestCases(sd.TestCases.size());//setting total number of test cases to be executed
		sd.TED.setTotalExecuted(Total);
		sd.TED.setPassed(Passed);
		sd.TED.setFailed(Failed);
		if(sd.isFailedCaseExecuted)
			sd.TED.setSkipped(sd.totalTestCases-Total);
		else
			sd.TED.setSkipped(sd.TestCases.size()-Total);
		sd.TED.setEndTime(sd.hMap.get("EndTime"));

		String testType = "General";

		if(sd.isFailedCaseExecuted)
			total=sd.totalTestCases;
		else
			total = sd.TestCases.size();



		passedCount = Passed;
		failedCount = Failed;
		skipped = total - Total;


		String resultsType = "Smoke Test Results";
		String strChart = passedCount + "," + failedCount + "," + skipped;

		String strBrowser = sd.hMap.get("Browser");
		String strURL = sd.hMap.get("URL");
		String detailFileName = "";
		String parentFolder = CreateClient.ProjPath+ "//TestReports//" ;
		String chartDimensions = "";
		String chartMaxHeight = "";

		if (total < 10) {
			chartDimensions = "0|5|10";
			chartMaxHeight = "10";
		} else if ((total >= 10) && (total < 20)) {
			chartDimensions = "0|5|10|15|20";
			chartMaxHeight = "20";
		} else if ((total >= 20) && (total < 50)) {
			chartDimensions = "0|10|20|30|40|50";
			chartMaxHeight = "50";
		} else if ((total >= 50) && (total < 100)) {
			chartDimensions = "0|20|40|60|80|100";
			chartMaxHeight = "100";
		} else if ((total >= 100) && (total < 200)) {
			chartDimensions = "0|40|80|120|160|200";
			chartMaxHeight = "200";
		} else if ((total >= 200) && (total < 300)) {
			chartDimensions = "0|50|100|150|200|250|300";
			chartMaxHeight = "300";
		} else if ((total >= 300) && (total < 400)) {
			chartDimensions = "0|80|160|240|320|400";
			chartMaxHeight = "400";
		} else if ((total >= 400) && (total < 500)) {
			chartDimensions = "0|100|200|300|400|500";
			chartMaxHeight = "500";
		} else if ((total >= 500) && (total < 800)) {
			chartDimensions = "0|160|320|480|640|800";
			chartMaxHeight = "800";
		} else if ((total >= 800) && (total < 1000)) {
			chartDimensions = "0|200|400|600|800|1000";
			chartMaxHeight = "1000";
		} else {
			System.out.println("Error: Invalid Chart Scale");
		}

		try {
			detailFileName = parentFolder + sd.hMap.get("htmlFile");
			sd.TED.setHTMLReportPath(detailFileName);
			sd.TED.setHTMLSharedPath(sd.sharedPath+"//TestReports//"+sd.hMap.get("htmlFile"));
			writeDetailFile(sd,detailFileName, testType, resultsType,strBrowser, strURL,
					strChart, chartDimensions,chartMaxHeight, testCaseExecutionDetails);
		}catch(Exception e) {
			sd.log.debug("Exception: "+e.getMessage());
		}
	}
	
	
	/*'#########################################################################################################
	'Function name		:	writeDetailFile
	'Description		:	This function is to generate the browser level detailed execution report in html format
							
	'Parameters			:	N/A
	'#########################################################################################################*/

	public void writeDetailFile(SeleniumDriver sd,String name, String testType,String resultsType,
		String strBrowser,String strURL, String strChart, String chartDimensions,
		String chartMaxHeight,HashMap<Integer, String> testCaseExecutionDetails) {
		String file = name;
		Date d = new Date();
		try {
			d = new SimpleDateFormat("MMddyy_HHmmss").parse(sd.hMap.get("TimeStamp"));
		}catch(Exception e) {
			sd.log.error(e.getMessage());
		}
		String ln = "\n";
		String onFailed=sd.onFailedCaseExecution?"- Failed Cases Execution":"";
		String headContent = "<html> "
				+ ln
				+ " <head>"
				+ ln
				+ " <style>"
				+ ln
				+ "	td.header {"
				+ ln
				+ " background-color:#3399FF;border-top:0px solid #333333;border-bottom:1px dashed #000000;"
				+ ln
				+ "	}"
				+ " td.testDetails { "
				+ ln
				+ " background-color:#3399FF;border-top:5px solid #3399FF;border-bottom:1px dashed #000000;"
				+ ln
				+ "	}"
				+ ln
				+ " span.testDetails {"
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;"
				+ ln
				+ "}"
				+ ln
				+ "td.execDetails { "
				+ ln
				+ " background-color:#3399FF;border-top:5px solid #3399FF;border-bottom:0px dashed #000000;"
				+ ln
				+ "}"
				+ ln
				+ " span.execDetails {"
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;"
				+ ln
				+ "}"
				+ ln
				+ "span.pass { "
				+ ln
				+ " font-size: 14px;font-weight:bold;line-height:100%;color:#00FF00;font-family:arial; "
				+ ln
				+ "	}"
				+ ln
				+ " span.fail { "
				+ ln
				+ " font-size: 14px;font-weight:bold;color:#FF0000;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " span.skip { "
				+ ln
				+ " font-size: 14px;font-weight:bold;color:#0000FF;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " span.title { "
				+ " font-size: 14px;font-weight:normal;color:#000000;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " td.reqDetails { "
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none; "
				+ ln
				+ " } "
				+ ln
				+ " td.reqData {  "
				+ ln
				+ " font-size:12px;color:#000000;line-height:100%;font-family:verdana;text-decoration:none; "
				+ ln
				+ " } "
				+ ln
				+ " </style> "
				+ ln
				+ " </head> "
				+ ln
				+ "<body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" bgcolor='#FFFFFF'>";

		String header = "<div id=\"header\"> "
				+ ln
				+ " <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">"
				+ ln
				+ " <tr> "
				+ ln
				+ "<td align=\"left\" valign=\"middle\" class=\"header\"> "
				+ ln
				+ "<img id=\"editableImg1\""
				+ " src=\""
				+ CreateClient.bankersDashboardLOGO
				+ "\" height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\" />"
				+ ln
				+ "</td>"
				+ ln
				+ "<td align=\"left\""
				+ " valign=\"middle\" class=\"header\">"
				+ ln
				+ "<span style=\"font-size:14px;font-weight:bold;color:#000000;line-"
				+ "height:200%;font-family:verdana;text-decoration:none;\">"
				+ ln
				+ "AUTOMATION TEST RESULTS - "+sd.client.toUpperCase() +" - "+strBrowser.toUpperCase()+" "+onFailed
				+ ln
				+ "</span>"
				+ ln
				+ "</td>"
				+ ln

				+ "<td align=\"left\" valign=\"middle\"style=\"background-color:#3399FF;border-top:0px solid #333333;border-bottom:1px dashed #000000;\"><left><a href=\"\"><IMG id=editableImg1 SRC="+CreateClient.clientLOGO.get(sd.client.toUpperCase())+" height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\"></a></left></td>"+ln

				+ "<td align=\"\" valign=\"middle\" style=\"background-color:#3399FF;border-top:0px solid #000000;border-bottom:"
				+ "1px dashed #000000;\">"
				+ ln
				+ " <span style=\"font-size:15px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;"
				+ "text-decoration:none;\">" + ln + "</span>" + ln + "</td>"
				+ ln + " </tr>" + ln + "</table>" + ln + "</div>";

		String testDetails = "<div id=\"testDetails\">"
				+ ln
				+ "<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\"> "
				+ ln
				+ "<tr> "
				+ ln
				+ " <td align=\"left\" valign=\"middle\" class=\"testDetails\"> "
				+ ln + "<span  class=\"testDetails\">" + ln + " Date &amp; Time : "
				+ d.toString() + ln + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"testDetails\">"
				+ ln + "<span  class=\"testDetails\">" + ln + "Test Type : "
				+ testType + ln + " </span> " + ln + " </td> " + ln
				+ "<td align=\"left\" "
				+ "valign=\"middle\" class=\"testDetails\" colspan=\"2\"> " + ln
				+ "<span  class=\"testDetails\"> " + ln
				+ "ClientName : <font color=\"#FFFFFF\">" + sd.client
				+ " </font> " + ln + " </span>" + ln + " </td> " + ln
				+ " </tr>" + ln + " </table> " + ln + "</div>";

		String execDetails = "<div id=\"execDetails\"> "
				+ ln
				+ "<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\"> "
				+ ln
				+ "  <tr> "
				+ ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln
				+ "<span class=\"execDetails\">"
				+ ln
				+ "Test Cases Executed : "
				+ (sd.isFailedCaseExecuted?sd.totalTestCases:sd.TestCases.size())
				+ "</span>"
				+ ln
				+ "</td>"
				+ ln
				+ "	<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Passed : "
				+ passedCount + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Failed :"
				+ failedCount + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Skipped : "
				+ skipped + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Browser: "
				+ strBrowser + "</span>" + ln + "</td>" + ln + "</tr>" + ln
				+ "</table>" + ln + "</div> <br/>";

		String graph = "<div id=\"graph\"  style=\"padding-left:10px\" > "
				+ ln
				+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor='#FFFFFF'> "
				+ ln
				+ "<tr> "
				+ ln
				+ "<td bgcolor=\"#FFFFFF\" valign=\"top\" width=\"99%\">"
				+ ln
				+ "<img id=\"graph\" src=\"http://chart.apis.google.com/chart?"
				+ "cht=bvg&amp;chs=350x175&amp;chd=t:"
				+ strChart
				+ "&amp;chds=0,"
				+ chartMaxHeight
				+ "&amp;chxt=x,y&amp;chxs=0,000000,12|1,000000,12&amp;chco=00FF00|FF0000|0000FF|FFFF00&amp;chbh=50,0,20&amp;"
				+ "chxl=0:|Passed|Failed|Skipped|1:|" + chartDimensions
				+ "&amp;chg=25,16.667,2,5&amp;chtt=Total+Test+Cases+=+" + total
				+ "&amp;chts=000000,15\" BORDER=\"0\" align=\"left\" />" + ln+ "</td>" + ln + "</tr>" + ln + "</table>" + ln + "</div>" + ln
				+ "<br/>";

		String genDetails = "<div id=\"genDetails\"  style=\"padding-left:10px\" >"
				+ ln
				+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor='#FFFFFF'>"
				+ "<tr>"
				+ ln
				+ "<td>"
				+ ln
				+ "<span style=\"font-size:20px;font-weight:bold;color:#000000;font-family:arial;line-height:110%;\">"
				+ ln
				+ "General Details"
				+ ln
				+ "</span>"
				+ ln
				+ "</td>"
				+ ln
				+ " </tr>"
				+ ln
				+ "<tr>"
				+ ln
				+ "<td>"
				+ ln
				+ "<span style=\"font-size:12px;font-weight:bold;color:#000000;font-family:arial;"
				+ "line-height:110%;\">"
				+ ln
				+ "URL : "
				+ ln
				+ "</span>"
				+ ln
				+ "<a href=\""
				+ strURL
				+ "\" style=\"font-size:12px;color:#0000FF;line-height:150%;font-family:trebuchet ms;\">"
				+ ln
				+ strURL
				+ "</a> "
				+ ln
				+ "</td>"
				+ ln
				+ " </tr>"
				+ ln
				+ "</table>" + ln + "</div>";

		String testCaseDetails = "<div id=\"testcaseDetails\" style=\"padding-left:15px\">"
				+ ln
				+ " <p> "
				+ "<span style=\"font-size: 15px;font-weight:bold;color:#000000;font-family:arial;\">Items Tested:</span> </p>"
				+ ln;
		try {
			Set TCset=testCaseExecutionDetails.keySet();
			Iterator TCiter = TCset.iterator();

			int a[] = new int[TCset.size()];
			int count = 0;
			while (TCiter.hasNext()) {
				a[count] = Integer.parseInt(TCiter.next().toString());
				count++;
			}
			Arrays.sort(a);

			for(int Key : a){

				sd.log.info(" Exporting case :: " + Key);
				String	style = "";
				String Value = sd.TestCaseDetails.get(Key);
				System.out.println("ID " + Key + " : " + Value);

				if (testCaseExecutionDetails.get(Key) == "PASS") {
					style = "pass";
					Value = Value + " - Passed";
				}else if (testCaseExecutionDetails.get(Key).startsWith("FAIL")) {
					style = "fail";
					Value = Value + " - Failed : "	+ testCaseExecutionDetails.get(Key).substring(4);
				}else if (testCaseExecutionDetails.get(Key) == ("SKIPPED")) {
					style = "skip";
					Value = Value + " -  No Test Steps Available - Skipped";
				}
				if (testCaseExecutionDetails.get(Key).startsWith("FAIL"))
				{
					testCaseDetails = testCaseDetails + "<p> <span class=\""+ style + "\">" +Key + ": </span>" + ln;
					testCaseDetails = testCaseDetails + "<span class=\"title\">"+ Value.replace("&", "&amp;") + ": </span>" + ln ;

					System.out.println(sd.FailedCaseScreenShot.get(Key).toString().trim());
					if(!sd.FailedCaseScreenShot.get(Key).toString().trim().equalsIgnoreCase("Browser Crashed")){
						if(sd.FailedCaseScreenShot.get(Key).toString().contains("\\..\\"))
						{
							String path=sd.FailedCaseScreenShot.get(String.valueOf(Key)).replace("\\..\\", "/../");
							testCaseDetails = testCaseDetails + "<a href=file:///"+path.replace("\\", "//")+" target=\"_blank\">ScreenShot</a>" + ln + "</p>";
						}else{
							testCaseDetails = testCaseDetails + "<a href=file:///"+sd.FailedCaseScreenShot.get(Key).replace("\\", "//")+" target=\"_blank\">ScreenShot</a>" + ln + "</p>";
						}
					}else{
						testCaseDetails = testCaseDetails + "<strong style=\"color: red;\">Browser Crashed</strong>" + ln + "</p>";

					}
				}else {
					testCaseDetails = testCaseDetails + "<p> <span class=\""+ style + "\">"+Key + ": </span>" + ln;
					testCaseDetails = testCaseDetails + "<span class=\"title\">"+ Value.replace("&", "&amp;") + "</span>" + ln + "</p>";
				}
			}
		}catch(Exception e) {
			sd.log.error("  Exception while exporting Cases to HTML");
			sd.log.error(e.getMessage());
			System.out.println(e.getMessage());
		}

		testCaseDetails = testCaseDetails + "</div>" + ln + "<br/>";
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(headContent);
			out.write(header);
			out.write(testDetails);
			out.write(execDetails);
			out.write(graph);
			out.write(genDetails);
			out.write(testCaseDetails);
			out.write("");
			out.write("</body>" + ln + "</html>");
		} catch (Exception e) {
			sd.log.error("  Exception while generating html report ");
			sd.log.error(e.getMessage());
			System.err.println(" Exception while generating html report");
			System.out.println(e.getMessage());
		}finally{
			try {
				out.close();
			}catch(Exception e) {
				sd.log.error(e.getMessage());
			}
		}
	}
	
	
	/*'#########################################################################################################
	'Function name		:	HtmlReport_Consolidated
	'Description		:	This function is to generate the consolidated execution report in html format
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void HtmlReport_Consolidated(
		Multimap<String, HashMap<String, TestExecutionDetails>> executionStatusForAllClients,
		List<String> browserNames, List<String> clientNames) throws IOException {

		
			String htmlFilename="Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+ ".html";
			String ln = "\n";
			String head="<head>        <title>Consolidated Report</title>    <link rel=\"stylesheet\" href=\"testng.css\" type=\"text/css\">"+ ln
					+ "   <style> body{background-color:#696969;}h1{background-color:#00ff00;}p{background-color:rgb(255,0,255);}</style>      <link type=\"text/css\" rel=\"stylesheet\" href=\"http://beust.com/beust.css\">"+ ln
					+ "    <script type=\"text/javascript\" src=\"banner.js\"></script>"+ ln
					+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shCore.js\"></script>"+ ln
					+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushJava.js\"></script>"+ ln
					+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushXml.js\"></script>"+ ln
					+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushBash.js\"></script>"+ ln
					+ "     <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushPlain.js\"></script>"+ ln
					+ "       <link type=\"text/css\" rel=\"stylesheet\" href=\"http://beust.com/styles/shCore.css\">"+ ln
					+ "       <link type=\"text/css\" rel=\"stylesheet\" href=\"http://beust.com/styles/shThemeCedric.css\">"+ ln
					+ "      <script type=\"text/javascript\">"+ ln
					+ "    SyntaxHighlighter.config.clipboardSwf = 'scripts/clipboard.swf';"+ ln
					+ "     SyntaxHighlighter.defaults['gutter'] = false;"+ ln
					+ "    SyntaxHighlighter.all();      </script>"+ ln
					+ "   <script type=\"text/javascript\" src=\"http://beust.com/toc.js\"></script>"+ ln
					+ "        <style type=\"text/css\">"+ ln
					+ "   /* Set the command-line table option column width. */            #command-line colgroup.option {                 width: 7em;            }        </style>    </head>";
			
			String header = "<div id=\"header\"> "
					+ ln
					+ " <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">"
					+ ln
					+ " <tr> "
					+ ln
					+ "<td align=\"left\" valign=\"middle\" class=\"header\"> "
					+ ln
					+ "<img id=\"editableImg1\""
					+ " src=\""
					+ CreateClient.bankersDashboardLOGO
					+ "\" height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\" />"
					+ ln
					+ "</td>"
					+ ln
					+ "<td align=\"left\""
					+ " valign=\"middle\" class=\"header\">"
					+ ln
					+ "<span style=\"font-size:14px;font-weight:bold;color:#000000;line-"
					+ "height:200%;font-family:verdana;text-decoration:none;\">"
					+ ln
					+ "AUTOMATION TEST RESULTS - Consolidated Report"
					+ ln
					+ "</span>"
					+ ln
					+ "</td>"
					+ ln

					+ "<td align=\"\" valign=\"middle\" style=\"background-color:#3399FF;border-top:0px solid #000000;border-bottom:"
					+ "1px dashed #000000;\">"
					+ ln
					+ " <span style=\"font-size:15px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;"
					+ "text-decoration:none;\">" + ln + "</span>" + ln + "</td>"
					+ ln + " </tr>" + ln + "</table>" + ln + "</div>";
		
			writeToHTML( executionStatusForAllClients,browserNames,clientNames,htmlFilename,head,header);
	}
	
	
	public void browserLevelReport_Consolidated(
			Multimap<String, HashMap<String, TestExecutionDetails>> executionStatusForAllClients,
			List<String> browserNames, List<String> clientNames) throws IOException {
		
		
		CreateClient.log.info("Generating browser level report for:"+browserNames.get(0).toUpperCase());
		String htmlFilename="Test Results_" +CreateClient.executionlog+"_"+ dateFormat.format(cal.getTime())+"_"+browserNames.get(0).toUpperCase()+ ".html";
		
		String ln = "\n"; 
		String head="<head>        <title>"+browserNames.get(0).toUpperCase()+" Browser Consolidated Report for All Clients</title>    <link rel=\"stylesheet\" href=\"testng.css\" type=\"text/css\">"+ ln
				+ "   <style> body{background-color:#696969;}h1{background-color:#00ff00;}p{background-color:rgb(255,0,255);}</style>      <link type=\"text/css\" rel=\"stylesheet\" href=\"http://beust.com/beust.css\">"+ ln
				+ "    <script type=\"text/javascript\" src=\"banner.js\"></script>"+ ln
				+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shCore.js\"></script>"+ ln
				+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushJava.js\"></script>"+ ln
				+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushXml.js\"></script>"+ ln
				+ "    <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushBash.js\"></script>"+ ln
				+ "     <script type=\"text/javascript\" src=\"http://beust.com/scripts/shBrushPlain.js\"></script>"+ ln
				+ "       <link type=\"text/css\" rel=\"stylesheet\" href=\"http://beust.com/styles/shCore.css\">"+ ln
				+ "       <link type=\"text/css\" rel=\"stylesheet\" href=\"http://beust.com/styles/shThemeCedric.css\">"+ ln
				+ "      <script type=\"text/javascript\">"+ ln
				+ "    SyntaxHighlighter.config.clipboardSwf = 'scripts/clipboard.swf';"+ ln
				+ "     SyntaxHighlighter.defaults['gutter'] = false;"+ ln
				+ "    SyntaxHighlighter.all();      </script>"+ ln
				+ "   <script type=\"text/javascript\" src=\"http://beust.com/toc.js\"></script>"+ ln
				+ "        <style type=\"text/css\">"+ ln
				+ "   /* Set the command-line table option column width. */            #command-line colgroup.option {                 width: 7em;            }        </style>    </head>";

		
		String header = "<div id=\"header\"> "
				+ ln
				+ " <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">"
				+ ln
				+ " <tr> "
				+ ln
				+ "<td align=\"left\" valign=\"middle\" class=\"header\"> "
				+ ln
				+ "<img id=\"editableImg1\""
				+ " src=\""
				+ CreateClient.bankersDashboardLOGO
				+ "\" height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\" />"
				+ ln
				+ "</td>"
				+ ln
				+ "<td align=\"left\""
				+ " valign=\"middle\" class=\"header\">"
				+ ln
				+ "<span style=\"font-size:14px;font-weight:bold;color:#000000;line-"
				+ "height:200%;font-family:verdana;text-decoration:none;\">"
				+ ln
				+ "AUTOMATION TEST RESULTS - "+browserNames.get(0).toUpperCase()+ " Consolidated Report"
				+ ln
				+ "</span>"
				+ ln
				+ "</td>"
				+ ln

				+ "<td align=\"\" valign=\"middle\" style=\"background-color:#3399FF;border-top:0px solid #000000;border-bottom:"
				+ "1px dashed #000000;\">"
				+ ln
				+ " <span style=\"font-size:15px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;"
				+ "text-decoration:none;\">" + ln + "</span>" + ln + "</td>"
				+ ln + " </tr>" + ln + "</table>" + ln + "</div>";
		
		writeToHTML( executionStatusForAllClients,browserNames,clientNames,htmlFilename,head,header);
		
		
	}
	
	
	public void writeToHTML(Multimap<String, HashMap<String, TestExecutionDetails>> executionStatusForAllClients,
			List<String> browserNames, List<String> clientNames,String htmlFilename,String head,String header){
	
		String browers_Tag="";
		String totalPassFail_Tag="";
		String ClientWiseTestStatus_Tag="";
		String browserWiseTestStaus_Tag="";
		String URL="";
		String ln = "\n";
		Date d = new Date();


try{
		HashSet duplicateRemove = new HashSet();
		Collections.sort(browserNames);
		duplicateRemove.addAll(browserNames);


		browserNames.clear();
		browserNames.addAll(duplicateRemove);
		Collections.sort(browserNames);
		
		duplicateRemove = new HashSet();
        Collections.sort(clientNames);
        duplicateRemove.addAll(clientNames);
        clientNames.clear();
        clientNames.addAll(duplicateRemove);
        Collections.sort(clientNames);

		CreateClient.log.debug("Generating consolidated HTML file for all clients");
		
		CreateClient.log.info("Browser names:"+browserNames);
		CreateClient.log.info("Client names:"+clientNames);
		
		BufferedWriter out = new BufferedWriter(new FileWriter(CreateClient.ProjPath+"\\TestReports\\"+htmlFilename));
		CreateClient.consolidatedHTMLReport=CreateClient.ProjPath+"\\TestReports\\"+htmlFilename;
	

			String headContent = "<html> "
				+ ln
				+ " <head>"
				+ ln
				+ " <style>"
				+ ln
				+ "	td.header {"
				+ ln
				+ " background-color:#3399FF;border-top:0px solid #333333;border-bottom:1px dashed #000000;"
				+ ln
				+ "	}"
				+ " td.testDetails { "
				+ ln
				+ " background-color:#3399FF;border-top:5px solid #3399FF;border-bottom:1px dashed #000000;"
				+ ln
				+ "	}"
				+ ln
				+ " span.testDetails {"
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;"
				+ ln
				+ "}"
				+ ln
				+ "td.execDetails { "
				+ ln
				+ " background-color:#3399FF;border-top:5px solid #3399FF;border-bottom:0px dashed #000000;"
				+ ln
				+ "}"
				+ ln
				+ " span.execDetails {"
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;"
				+ ln
				+ "}"
				+ ln
				+ "span.pass { "
				+ ln
				+ " font-size: 14px;font-weight:bold;line-height:100%;color:#00FF00;font-family:arial; "
				+ ln
				+ "	}"
				+ ln
				+ " span.fail { "
				+ ln
				+ " font-size: 14px;font-weight:bold;color:#FF0000;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " span.skip { "
				+ ln
				+ " font-size: 14px;font-weight:bold;color:#0000FF;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " span.title { "
				+ " font-size: 14px;font-weight:normal;color:#000000;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " td.reqDetails { "
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none; "
				+ ln
				+ " } "
				+ ln
				+ " td.reqData {  "
				+ ln
				+ " font-size:12px;color:#000000;line-height:100%;font-family:verdana;text-decoration:none; "
				+ ln
				+ " } "
				+ ln
				+ " </style> "
				+ ln
				+ " </head> "
				+ ln
				+ "<body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" bgcolor='#FFFFFF'>";

	

		String testDetails = "<div id=\"testDetails\">"
				+ ln
				+ "<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\"> "
				+ ln
				+ "<tr> "
				+ ln
				+ " <td align=\"left\" valign=\"middle\" class=\"testDetails\"> "
				+ ln + "<span  class=\"testDetails\">" + ln + " Date &amp; Time : "
				+ d.toString() + ln + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"testDetails\">"
				+ ln + "<span  class=\"testDetails\">" + ln + "Test Type : "
				+ "General" + ln + " </span> " + ln + " </td> " + ln
				+ "<td align=\"left\" "
				+ "valign=\"middle\" class=\"testDetails\" colspan=\"2\"> " + ln
				+ "<span  class=\"testDetails\"> " + ln
				+ "ClientName : <font color=\"#FFFFFF\">" + "All"
				+ " </font> " + ln + " </span>" + ln + " </td> " + ln
				+ " </tr>" + ln + " </table> " + ln + "</div>";

		List<String> browserList= new ArrayList<String>();
		List<String> browserDiff= new ArrayList<String>();

		for(int i=0;i<browserNames.size();i++){
			String browser=browserNames.get(i);
			browers_Tag=browers_Tag+"<th colspan=\"5\" style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >"+browser.toUpperCase()+"</font></b></th>";
			totalPassFail_Tag=totalPassFail_Tag+"<th style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Total</font></b></th>	<th style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Pass</font></b></th>	<th style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Fail</font></b></th> <th style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Skipped</font></b> </th>	<th style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Report</font></b></th>";
		}

		for(String clientName:clientNames){
			CreateClient.log.info("---------------------------");
			CreateClient.log.info("clientName: "+clientName);

			browserDiff.clear();
			System.out.println("=========================================");
			browserWiseTestStaus_Tag="";
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
			CreateClient.log.info("Browser execution details missing:"+browserDiff);
			URL="";
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
			CreateClient.log.info("Browser execution details exist for client: "+clientName+ " are: "+browserNamesInClient);
			String browser;
			String repoSharePath = "";
			for(int i=0;i<browserNames.size();i++){
				browser=browserNames.get(i);	
				if((browserNamesInClient.contains(browser.toUpperCase()))){

					treeMap = new TreeMap<String, TestExecutionDetails>(clientnames);
					for(Entry<String, TestExecutionDetails> entry : treeMap.entrySet()) {
						if(entry.getKey().equals(browser.toUpperCase())){
							TestExecutionDetails TED=entry.getValue();		
							if(URL==null || URL.equals(""))
								URL=TED.getURL();		
							if(!(TED.getHTMLSharedPath()==null)){
								browserWiseTestStaus_Tag=browserWiseTestStaus_Tag+"<td>"+TED.getTotalTestCases()+"</td>"+"<td>"+TED.getPassed()+"</td>"+"<td>"+TED.getFailed()+"</td>"+"<td>"+(TED.getTotalTestCases()-TED.getTotalExecuted())+"</td>"+"<td><a href=\"file:///"+TED.getHTMLSharedPath()+"\" target=\"_blank\">html</a> | <a href=\"file:///"+TED.getLogFile()+"\" target=\"_blank\"> log </a></td>";
								repoSharePath = TED.getHTMLSharedPath();
							}
							else
								browserWiseTestStaus_Tag=browserWiseTestStaus_Tag+"<td>"+TED.getTotalTestCases()+"</td>"+"<td>"+TED.getPassed()+"</td>"+"<td>"+TED.getFailed()+"</td>"+"<td>"+(TED.getTotalTestCases()-TED.getTotalExecuted())+"</td>"+"<td>Execution terminated unexpectedly</td>";
							break;
						}							
					}
				}else {						
					CreateClient.log.warn("Browser execution details are missing for browser: "+browser+" making all fiels for this exeuction as \"Execution terminated unexpectedly\"");
					browserWiseTestStaus_Tag=browserWiseTestStaus_Tag+"<td>"+"Execution terminated unexpectedly"+"</td>"+"<td>"+"Execution terminated unexpectedly"+"</td>"+"<td>"+"Execution terminated unexpectedly"+"</td>"+"<td>"+"Execution terminated unexpectedly"+"</td>"+"<td>"+"Execution terminated unexpectedly"+"</td>";
				}	
			}
			HashMap<String,String> clientExecutionDetails=new HashMap<String,String>();
			Collection<HashMap<String, String>> clientList=CreateClient.clientLevelDetails.get(clientName.toUpperCase());
			for(HashMap<String,String> clientDetails:clientList){
				for(String key:clientDetails.keySet()){
					clientExecutionDetails.put(key, clientDetails.get(key));
				} 
			}
			CreateClient.clientReports_URLs.put(clientName.toUpperCase().toUpperCase(), clientExecutionDetails);
			ClientWiseTestStatus_Tag=ClientWiseTestStatus_Tag+"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >"+clientName.toUpperCase()+"</font></b></td>  <td>"+URL+"</td>  <td><a href=\"file:///"+CreateClient.clientReports_URLs.get(clientName.toUpperCase()).get("htmlSharedPath")+"\" target=\"_blank\">html</a></td>"+browserWiseTestStaus_Tag+"</tr>";
//			ClientWiseTestStatus_Tag=ClientWiseTestStatus_Tag+"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >"+clientName.toUpperCase()+"</font></b></td>  <td>"+URL+"</td>  <td><a href=\"file:///"+repoSharePath+"\" target=\"_blank\">html</a></td>"+browserWiseTestStaus_Tag+"</tr>";
		}
		String countersText = "<br></br><table   style=\"background-color:#FFFFE0;\" align: center; BORDER=5 BORDERCOLOR=BLACK cellpadding=8>"+

					"<tr> <th  rowspan=\"2\" style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Client</font></b></th>	" +
					"	  <th rowspan=\"2\" style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >URL</font></b></th>"	+
					"	  <th rowspan=\"2\" style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Integrated Report</font></b></th>"	
					+browers_Tag+"</tr>"+"<tr>"+totalPassFail_Tag+" </tr>"+ClientWiseTestStatus_Tag+

					"</table> ";
		out.write(head);
		out.write(headContent);
		out.write(header);
		out.write(testDetails);
		out.write(countersText);
		out.write("</div>");
		out.write("\n </BODY> \n <html>");			
		out.close();
}catch(Exception e){
	e.printStackTrace();
	System.out.println("Error while writing data in to html file");
	CreateClient.log.error("Error while writing data in to html file");
	CreateClient.log.error("Error:",e);
}
	}

	
	
	/*'#########################################################################################################
	'Function name		:	buildTemplate_BrowserLevel_ForMissedExecutionDetails
	'Description		:	This function is to generate the missed threads execution details
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	public void buildTemplate_BrowserLevel_ForMissedExecutionDetails(String clientName,String browser, int totalTestCases,int Total, int Passed, int Failed, HashMap<Integer, String> testCaseExecutionDetails, TestExecutionDetails TED,LinkedHashMap<Integer , String> testCaseTitles) {

		String testType = "General";
		total = totalTestCases;
		passedCount = Passed;
		failedCount = Failed;
		skipped = total - Total;

		String resultsType = "Smoke Test Results";
		String strChart = passedCount + "," + failedCount + "," + skipped;

		String strBrowser = browser;
		String strURL = TED.getURL();
		String detailFileName = "";

		String chartDimensions = "";
		String chartMaxHeight = "";

		if (total < 10) {
			chartDimensions = "0|5|10";
			chartMaxHeight = "10";
		} else if ((total >= 10) && (total < 20)) {
			chartDimensions = "0|5|10|15|20";
			chartMaxHeight = "20";
		} else if ((total >= 20) && (total < 50)) {
			chartDimensions = "0|10|20|30|40|50";
			chartMaxHeight = "50";
		} else if ((total >= 50) && (total < 100)) {
			chartDimensions = "0|20|40|60|80|100";
			chartMaxHeight = "100";
		} else if ((total >= 100) && (total < 200)) {
			chartDimensions = "0|40|80|120|160|200";
			chartMaxHeight = "200";
		} else if ((total >= 200) && (total < 300)) {
			chartDimensions = "0|50|100|150|200|250|300";
			chartMaxHeight = "300";
		} else if ((total >= 300) && (total < 400)) {
			chartDimensions = "0|80|160|240|320|400";
			chartMaxHeight = "400";
		} else if ((total >= 400) && (total < 500)) {
			chartDimensions = "0|100|200|300|400|500";
			chartMaxHeight = "500";
		} else if ((total >= 500) && (total < 800)) {
			chartDimensions = "0|160|320|480|640|800";
			chartMaxHeight = "800";
		} else if ((total >= 800) && (total < 1000)) {
			chartDimensions = "0|200|400|600|800|1000";
			chartMaxHeight = "1000";
		} else {
			System.out.println("Error: Invalid Chart Scale");
		}

		try {
			detailFileName = TED.getHTMLReportPath();
			
			writeDetailFile_ForMissedExecutionDetails(detailFileName, testType, resultsType,strBrowser, strURL,
					strChart, chartDimensions,chartMaxHeight, testCaseExecutionDetails,clientName,browser,TED, testCaseTitles);
		}catch(Exception e) {
			CreateClient.log.error("Exception while generating the html reports for missed execution details:");
			CreateClient.log.error("Error:"+e.getMessage());
		}
	}


	/*'#########################################################################################################
	'Function name		:	writeDetailFile_ForMissedExecutionDetails
	'Description		:	This function is to generate data for missed threads execution details
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void writeDetailFile_ForMissedExecutionDetails(String name, String testType,String resultsType,
			String strBrowser,String strURL, String strChart, String chartDimensions,
			String chartMaxHeight,HashMap<Integer, String> testCaseExecutionDetails,String clientName,String browser,TestExecutionDetails TED,LinkedHashMap<Integer , String> testCaseTitles) {

		String file = name;
		Date d = new Date();
		try {
			d = new SimpleDateFormat("MMddyy_HHmmss").parse(TED.getStartTime());
		}catch(Exception e) {
			CreateClient.log.debug("Exception: "+e.getMessage());
		}

		String ln = "\n";
		String headContent = "<html> "
				+ ln
				+ " <head>"
				+ ln
				+ " <style>"
				+ ln
				+ "	td.header {"
				+ ln
				+ " background-color:#3399FF;border-top:0px solid #333333;border-bottom:1px dashed #000000;"
				+ ln
				+ "	}"
				+ " td.testDetails { "
				+ ln
				+ " background-color:#3399FF;border-top:5px solid #3399FF;border-bottom:1px dashed #000000;"
				+ ln
				+ "	}"
				+ ln
				+ " span.testDetails {"
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;"
				+ ln
				+ "}"
				+ ln
				+ "td.execDetails { "
				+ ln
				+ " background-color:#3399FF;border-top:5px solid #3399FF;border-bottom:0px dashed #000000;"
				+ ln
				+ "}"
				+ ln
				+ " span.execDetails {"
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:200%;font-family:verdana;text-decoration:none;"
				+ ln
				+ "}"
				+ ln
				+ "span.pass { "
				+ ln
				+ " font-size: 14px;font-weight:bold;line-height:100%;color:#00FF00;font-family:arial; "
				+ ln
				+ "	}"
				+ ln
				+ " span.fail { "
				+ ln
				+ " font-size: 14px;font-weight:bold;color:#FF0000;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " span.skip { "
				+ ln
				+ " font-size: 14px;font-weight:bold;color:#0000FF;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " span.title { "
				+ " font-size: 14px;font-weight:normal;color:#000000;line-height:100%;font-family:arial; "
				+ ln
				+ " } "
				+ ln
				+ " td.reqDetails { "
				+ ln
				+ " font-size:12px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;text-decoration:none; "
				+ ln
				+ " } "
				+ ln
				+ " td.reqData {  "
				+ ln
				+ " font-size:12px;color:#000000;line-height:100%;font-family:verdana;text-decoration:none; "
				+ ln
				+ " } "
				+ ln
				+ " </style> "
				+ ln
				+ " </head> "
				+ ln
				+ "<body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" bgcolor='#FFFFFF'>";

		String header = "<div id=\"header\"> "
				+ ln
				+ " <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">"
				+ ln
				+ " <tr> "
				+ ln
				+ "<td align=\"left\" valign=\"middle\" class=\"header\"> "
				+ ln
				+ "<img id=\"editableImg1\""
				+ " src=\""
				+ CreateClient.bankersDashboardLOGO
				+ "\" height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\" />"
				+ ln
				+ "</td>"
				+ ln
				+ "<td align=\"left\""
				+ " valign=\"middle\" class=\"header\">"
				+ ln
				+ "<span style=\"font-size:14px;font-weight:bold;color:#000000;line-"
				+ "height:200%;font-family:verdana;text-decoration:none;\">"
				+ ln
				+ "AUTOMATION TEST RESULTS - "+clientName.toUpperCase() +" - "+strBrowser.toUpperCase()
				
				+ ln
				+ "</span>"
				+ ln
				+ "</td>"
				+ ln

				+ "<td align=\"left\" valign=\"middle\"style=\"background-color:#3399FF;border-top:0px solid #333333;border-bottom:1px dashed #000000;\"><left><a href=\"\"><IMG id=editableImg1 SRC="+CreateClient.clientLOGO.get(clientName.toUpperCase())+" height=\"60px\" width=\"250px\" BORDER=\"0\" align=\"center\"></a></left></td>"+ln

				+ "<td align=\"\" valign=\"middle\" style=\"background-color:#3399FF;border-top:0px solid #000000;border-bottom:"
				+ "1px dashed #000000;\">"
				+ ln
				+ " <span style=\"font-size:15px;font-weight:bold;color:#000000;line-height:100%;font-family:verdana;"
				+ "text-decoration:none;\">" + ln + "</span>" + ln + "</td>"
				+ ln + " </tr>" + ln + "</table>" + ln + "</div>";

		String testDetails = "<div id=\"testDetails\">"
				+ ln
				+ "<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\"> "
				+ ln
				+ "<tr> "
				+ ln
				+ " <td align=\"left\" valign=\"middle\" class=\"testDetails\"> "
				+ ln + "<span  class=\"testDetails\">" + ln + " Date &amp; Time : "
				+ d.toString() + ln + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"testDetails\">"
				+ ln + "<span  class=\"testDetails\">" + ln + "Test Type : "
				+ testType + ln + " </span> " + ln + " </td> " + ln
				+ "<td align=\"left\" "
				+ "valign=\"middle\" class=\"testDetails\" colspan=\"2\"> " + ln
				+ "<span  class=\"testDetails\"> " + ln
				+ "ClientName : <font color=\"#FFFFFF\">" + clientName
				+ " </font> " + ln + " </span>" + ln + " </td> " + ln
				+ " </tr>" + ln + " </table> " + ln + "</div>";

		String execDetails = "<div id=\"execDetails\"> "
				+ ln
				+ "<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\"> "
				+ ln
				+ "  <tr> "
				+ ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln
				+ "<span class=\"execDetails\">"
				+ ln
				+ "Test Cases Executed : "
				+ TED.getTotalTestCases()
				+ "</span>"
				+ ln
				+ "</td>"
				+ ln
				+ "	<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Passed : "
				+ passedCount + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Failed :"
				+ failedCount + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Skipped : "
				+ skipped + "</span>" + ln + "</td>" + ln
				+ "<td align=\"left\" valign=\"middle\" class=\"execDetails\">"
				+ ln + "<span class=\"execDetails\">" + ln + "Browser: "
				+ strBrowser + "</span>" + ln + "</td>" + ln + "</tr>" + ln
				+ "</table>" + ln + "</div> <br/>";

		String graph = "<div id=\"graph\"  style=\"padding-left:10px\" > "
				+ ln
				+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor='#FFFFFF'> "
				+ ln
				+ "<tr> "
				+ ln
				+ "<td bgcolor=\"#FFFFFF\" valign=\"top\" width=\"99%\">"
				+ ln
				+ "<img id=\"graph\" src=\"http://chart.apis.google.com/chart?"
				+ "cht=bvg&amp;chs=350x175&amp;chd=t:"
				+ strChart
				+ "&amp;chds=0,"
				+ chartMaxHeight
				+ "&amp;chxt=x,y&amp;chxs=0,000000,12|1,000000,12&amp;chco=00FF00|FF0000|0000FF|FFFF00&amp;chbh=50,0,20&amp;"
				+ "chxl=0:|Passed|Failed|Skipped|1:|" + chartDimensions
				+ "&amp;chg=25,16.667,2,5&amp;chtt=Total+Test+Cases+=+" + total
				+ "&amp;chts=000000,15\" BORDER=\"0\" align=\"left\" />" + ln+ "</td>" + ln + "</tr>" + ln + "</table>" + ln + "</div>" + ln
				+ "<br/>";

		String genDetails = "<div id=\"genDetails\"  style=\"padding-left:10px\" >"
				+ ln
				+ "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor='#FFFFFF'>"
				+ "<tr>"
				+ ln
				+ "<td>"
				+ ln
				+ "<span style=\"font-size:20px;font-weight:bold;color:#000000;font-family:arial;line-height:110%;\">"
				+ ln
				+ "General Details"
				+ ln
				+ "</span>"
				+ ln
				+ "</td>"
				+ ln
				+ " </tr>"
				+ ln
				+ "<tr>"
				+ ln
				+ "<td>"
				+ ln
				+ "<span style=\"font-size:12px;font-weight:bold;color:#000000;font-family:arial;"
				+ "line-height:110%;\">"
				+ ln
				+ "URL : "
				+ ln
				+ "</span>"
				+ ln
				+ "<a href=\""
				+ strURL
				+ "\" style=\"font-size:12px;color:#0000FF;line-height:150%;font-family:trebuchet ms;\">"
				+ ln
				+ strURL
				+ "</a> "
				+ ln
				+ "</td>"
				+ ln
				+ " </tr>"
				+ ln
				+ "</table>" + ln + "</div>";

		String testCaseDetails = "<div id=\"testcaseDetails\" style=\"padding-left:15px\">"
				+ ln
				+ " <p> "
				+ "<span style=\"font-size: 15px;font-weight:bold;color:#000000;font-family:arial;\">Items Tested:</span> </p>"
				+ ln;

		try {
			Set TCset=testCaseExecutionDetails.keySet();
			Iterator TCiter = TCset.iterator();

			int a[] = new int[TCset.size()];
			int count = 0;
			while (TCiter.hasNext()) {
				a[count] = Integer.parseInt(TCiter.next().toString());
				count++;
			}
			Arrays.sort(a);
			for(int Key : a){
				String	style = "";
				String Value = testCaseTitles.get(Key);
				System.out.println("ID " + Key + " : " + Value);

				if (testCaseExecutionDetails.get(Key).startsWith("PASS")) {
					style = "pass";
					Value = Value + " - Passed";
				} else if (testCaseExecutionDetails.get(Key).startsWith("FAIL")) {
					style = "fail";
					Value = Value + " - Failed : "	+ testCaseExecutionDetails.get(Key).substring(4);
				} else if (testCaseExecutionDetails.get(Key).startsWith("SKIPPED")) {
					style = "skip";
					Value = Value + " -  No Test Steps Available - Skipped";
				}

				if (testCaseExecutionDetails.get(Key).startsWith("FAIL"))
				{
					testCaseDetails = testCaseDetails + "<p> <span class=\""+ style + "\">" +Key + ": </span>" + ln;
					testCaseDetails = testCaseDetails + "<span class=\"title\">"+ Value.replace("&", "&amp;") + ": </span>" + ln ;
				
					if(!testCaseExecutionDetails.get(Key).toString().trim().contains("Browser Crashed")){
							testCaseDetails = testCaseDetails + "<a href=file:///"+CreateClient.sharedPath+testCaseExecutionDetails.get(Key).toString().substring(testCaseExecutionDetails.get(Key).toString().indexOf("\\TestReports\\"))+" target=\"_blank\">ScreenShot</a>" + ln + "</p>";
					}else{
						testCaseDetails = testCaseDetails + "<strong style=\"color: red;\">Browser Crashed</strong>" + ln + "</p>";
					}
				}else {
					testCaseDetails = testCaseDetails + "<p> <span class=\""+ style + "\">"+Key + ": </span>" + ln;
					testCaseDetails = testCaseDetails + "<span class=\"title\">"+ Value.replace("&", "&amp;") + "</span>" + ln + "</p>";
				}
			}
		} catch (Exception e) {
			CreateClient.log.error("Exception: "+e.getMessage());
			System.out.println(e.getMessage());
		}

		testCaseDetails = testCaseDetails + "</div>" + ln + "<br/>";

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(headContent);
			out.write(header);
			out.write(testDetails);
			out.write(execDetails);
			out.write(graph);
			out.write(genDetails);
			out.write(testCaseDetails);
			out.write("");
			out.write("</body>" + ln + "</html>");
		}catch(Exception e) {
			CreateClient.log.error("Exception while generating the html reports for missed execution details:");
			CreateClient.log.error("Error:"+e.getMessage());
			System.err.println(" Exception while generating html report");
			System.out.println(e.getMessage());
		}finally{
			try {
				out.close();
			}catch(Exception e){
				CreateClient.log.error("Debug: "+e.getMessage());
			}
		}
	}
}

