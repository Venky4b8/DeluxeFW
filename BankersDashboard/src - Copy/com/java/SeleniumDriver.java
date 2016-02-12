package com.java; 												

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.json.JSONException;
import org.json.JSONObject;
//import org.codehaus.jettison.json.JSONObject;

import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.java.ImportnExport.ExportTestResultsExcel;
import com.java.ImportnExport.ImportTestDataDetailsExcel;
import com.java.Objects.ConfigDetails;
import com.java.Objects.ResultDetails;
import com.java.Objects.TestDataDetails;
import com.java.Objects.TestExecutionDetails;

public class SeleniumDriver{ 

	public  Logger log=Logger.getLogger(SeleniumDriver.class.getName());
	private  RemoteWebDriver webdriver;
	public RemoteWebDriver auctionWebdriver;
	private RemoteWebDriver webdriverCopy;  //To maintain webdriver & webdriver1 as one object
	public  String ProjPath="";//ProjPathLibpath="";
	private SimpleDateFormat scrShot = new SimpleDateFormat("MMddyy_HHmmss");

	//This Hash map object is used to store the UI values , it is used in Store Value method in TestType class
	public   HashMap<String , String> hMap= new HashMap<String, String>();
	public static  HashMap<String , String> failMap= new HashMap<String, String>();
	public static String configFile="";
	public   HashMap<String , String> parameterDetails= new HashMap<String, String>();; // HashMap to store Parameter Test Data
	public   LinkedHashMap<Integer , String> TestCaseDetails=new LinkedHashMap<Integer , String>(); // HashMap to store list of test case numbers and titles
	public   HashMap<Integer , String> TestCaseExecutionDetails= new HashMap<Integer, String>(); // HashMap to store list of test cases and their test result
	public   HashMap<Integer , String> failedExecutionDetails= new HashMap<Integer, String>();; // HashMap to store list of test cases and their test result
	public TestExecutionDetails TED= new TestExecutionDetails();
	public HashMap<String,TestExecutionDetails> executionStatus= new HashMap<String,TestExecutionDetails>();

	//HashMaps to read the Configuration details and Test Data details from Excel Sheet
	public HashMap<Integer,ConfigDetails> ConfigDtls = new HashMap<Integer,ConfigDetails>();
	HashMap<Integer,TestDataDetails> TestData = new HashMap<Integer,TestDataDetails>();	
	public ConfigDetails confDtls;
	public HashMap<Integer, String> FailedCaseScreenShot = new HashMap<Integer, String>();
	List<String> exportResult = new ArrayList<String>();
	private ArrayList<Integer> failed = new ArrayList<Integer>();
	public String htmlReportsPath;
	public String sharedPath;

	int browserCrashCount=0;
	private int testCase=0;
	public int currentTestCase=0;
	public String hostAddress;

	String browserType = null;
	String strError = "";
	String strScreenshotName = "";	
	public int[] ReportCounters = new int[3];

	public int[] failedReportCounter= new int[3];
	int compVar=0;
	public  String Browser;
	DesiredCapabilities capability = null;
//	TestType test;
	
	public String bankProfitCenter;
	public String bankConsolidate;

	public  ArrayList<Integer> testCaseID = new ArrayList<Integer>();
	public  ArrayList<Integer> testcases = new ArrayList<Integer>();
	//An ArrayList to store the test cases to be executed
	ArrayList<Integer> TestCases = new ArrayList<Integer>();

	//Object To Read Test Data Details Excel Sheet. 
	ImportTestDataDetailsExcel impxl = new ImportTestDataDetailsExcel (this);
	public  String client;
	public  String browsername;
	public String appurl;
	public String user1;
	public String pwd1;

	public String nodeName;

	public String executionlog; //prgmID, Build ID
	Long startTime;
	int totalTestCases;
	String logFileName;


	boolean sequenceNumber = false;
	int previousFailedTCID = 0;		
	String failedCases = "";
	//Object To Write Test Results into an Excel Sheet
	ExportTestResultsExcel expxl = new ExportTestResultsExcel (this);
	TemplateGenerator report = new TemplateGenerator ();
	TestDataDetails tdd=new TestDataDetails();
	ArrayList<String> result;
	public String hostFound = null;
	boolean executeFailedCases=false;
	boolean isGridExecution=true;
	public boolean isFailedCaseExecuted=false;
	public boolean onFailedCaseExecution=false;
	public ArrayList<String> excelReports=new ArrayList<String>();
	public boolean isExecutionStarted=false;


	SeleniumDriver(){}
	SeleniumDriver(CreateThread obj){
		Browser = obj.browsername;
		client = obj.client;
		appurl=obj.appurl;
		for(int testCaseID:obj.testcases)
		{
			testcases.add(testCaseID);
			TestCases.add(testCaseID);
		}
		this.user1 =obj.user1;
		pwd1= obj.pwd1;
		this.executionlog=obj.executionlog;
		this.startTime=obj.startTime;
	}
	
	SeleniumDriver(String browser,String URL,String Clientname,ArrayList<Integer> Tc,String user1,String password1,String executionlog,Long startTime2){
	Browser = browser;
	client = Clientname;
	appurl=URL;
	client=Clientname;
	for(int testCaseID:Tc)
	{
		testcases.add(testCaseID);
		TestCases.add(testCaseID);
	}

	this.user1 = user1;
	pwd1= password1;
	this.executionlog=executionlog;
	this.startTime=startTime2;
	}

	public void setup() throws IOException
	{
		CreateClient.browserNames.add(Browser.toUpperCase());
		CreateClient.applicationURLs.put(client.toUpperCase(), appurl);
		executeFailedCases=CreateClient.executeFailedCases;
		isGridExecution=CreateClient.isGridExecution;
		totalTestCases=testcases.size();
		CreateClient.testCasesCounts.put(client.toUpperCase()+"-"+Browser.toUpperCase(), totalTestCases);
		TED.setTotalTestCases(testcases.size());//setting total number of test cases to be executed
		hMap.put("TimeStamp", scrShot.format(new Date()));
		hMap.put("StartTime", scrShot.format(new Date()));
		hMap.put("ExecutionStartTime", scrShot.format(new Date()));
		TED.setStartTime(hMap.get("StartTime"));
		CreateClient.executionStartTime.put(client.toUpperCase()+"-"+Browser.toUpperCase(), hMap.get("StartTime"));
		hostAddress="//"+InetAddress.getLocalHost().getHostAddress();
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		String name = CreateClient.ProjPath+"\\logs\\Log"+"_"+executionlog+"_"+Browser+"_"+client+"-"+hMap.get("TimeStamp") + ".log";
		FileAppender fa = new FileAppender(new PatternLayout("[%-5p][%9d][%C] [%5L] - %m%n"),name,false);
		fa.activateOptions();
		log = Logger.getLogger(name.replace("-",""));
		log.addAppender(fa);
		log.info("Execute Failed Cases"+executeFailedCases);
		log.info("Browser: "+Browser);
		log.info(testcases);
		log.info("Total TestCases:"+totalTestCases);
		log.info(" Reading Properties from Property File ");
		if(System.getProperty("user.dir").contains("bin"))
		{
			String dirpath = System.getProperty("user.dir");
			ProjPath = dirpath.substring(0, dirpath.lastIndexOf("\\"));
			log.info("ProjPath:"+ProjPath);
		}else {
			ProjPath = System.getProperty("user.dir"); //D:\Projects\Deluxe\Framework_BD\BankersDashboard
		}
		System.out.println("ProjPath: "+ProjPath);
		sharedPath=hostAddress+"//"+ProjPath;
		System.out.println("sharedPath: "+sharedPath);
//		 sharedPath=hostAddress+"//"+ProjPath.substring(ProjPathLibpath.indexOf("\\")+1); ////10.101.101.158//Projects\Deluxe\Framework_BD\BankersDashboard
		//sharedPath=hostAddress+"//"+ProjPath.substring(ProjPathLibpath.lastIndexOf("\\")+1);
		CreateClient.sharedPath=sharedPath;

		logFileName=sharedPath+"\\logs\\Log"+"_"+executionlog+"_"+Browser+"_"+client+"-"+hMap.get("TimeStamp") + ".log";
		CreateClient.logFiles.put(client.toUpperCase()+"-"+Browser.toUpperCase(), logFileName);

		TED.setLogFile(logFileName);
		getDataSource();
		log.debug("url : "+appurl);
		hMap.put("Browser",Browser);
		hMap.put("URL", appurl);
		
		expxl.exportExcelHeader(Browser,client);
		//Read BankEntities sheet from TestData.xls 
		bankEntityDtls();
		
		invokeBrowser();

		log.info(Browser + "is opened");
		try {
			hMap.put("ClientName",client);
			hMap.put("browser",Browser);
	        hMap.put("Executionlog",executionlog);
			webdriver.get(appurl);
			webdriver.manage().window().maximize();
			testInitiation(Browser,appurl,client,user1,pwd1,executionlog,startTime,hostFound);
			tearDown();
			if(isFailedCaseExecuted)
			{
				generateConsolidatedReport();
			}
			executionStatus.put(Browser.toUpperCase(), TED);
			CreateClient.executionStatusForAllClients.put(client.toUpperCase(), executionStatus);
			log.info("Execution Completed Stopping the thread");
			CreateClient.updateExecutionStatus(Browser.toUpperCase(),client.toUpperCase()); 
			Thread.currentThread().stop();
		} catch (Exception e) {
			try {
				e.printStackTrace();
				//browser invoking on existing browser crash  chrome not reachable
				log.error("Browser crashed. Opening new browser with same capabilities");
				log.error("Error:",e);
				try {
					webdriver.quit();
				}catch(Exception ee) {
					log.error("Exception: "+ee.getMessage());
				}
				testCase=currentTestCase+1;
				invokeBrowser();
				testInitiation(Browser,appurl,client,user1,pwd1,executionlog,startTime,hostFound);
				tearDown();
				if(isFailedCaseExecuted) {
					generateConsolidatedReport();
				}
				executionStatus.put(Browser.toUpperCase(), TED);
				CreateClient.executionStatusForAllClients.put(client.toUpperCase(), executionStatus);
				log.info("Execution Completed Stopping the thread, (Additional info Browser got crashed in the the total execution)");
				CreateClient.updateExecutionStatus(Browser.toUpperCase(),client.toUpperCase()); 
				Thread.currentThread().stop();
			}catch(Exception ee) {
				log.error("Exception: "+e.getMessage());
			}
			log.error(e.getLocalizedMessage());
		}
	}
	
	public void bankEntityDtls()
	{
		ResultSet ts = null;
		Connection conn = null;
		Statement st = null;
		try{
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" +ProjPath+"\\TestInputs\\TestData.xls"+ ";DriverID=22;READONLY=false","","");
			st = conn.createStatement();
			Statement ts1 = conn.createStatement();
			ts = ts1.executeQuery("Select * from [BankEntities$] where \"Client Name\" = '"+client+"'");
			while(ts.next()){
				bankProfitCenter = ts.getString("Profit Center");
				bankConsolidate = ts.getString("Consolidated");
				System.out.println("bankProfitCenter: "+bankProfitCenter);
				System.out.println("bankConsolidate: "+bankConsolidate);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			finally{
					try{if(conn!=null){conn.close();}}catch(Exception e){}
					try{if(st!=null){st.close();}}catch(Exception e){}
					try{if(ts!=null){ts.close();}}catch(Exception e){}
				}
	}
	

	public void generateConsolidatedReport() {
		try {
			log.info("Merging the reports");
			Set TCset=TestCaseExecutionDetails.keySet();
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
				if (TestCaseExecutionDetails.get(Key) == "PASS") {
					finalReportCounter[1]=finalReportCounter[1]+1;
					finalReportCounter[0]=finalReportCounter[0]+1;
				} else if (TestCaseExecutionDetails.get(Key).startsWith("FAIL")) {
					finalReportCounter[2]=finalReportCounter[2]+1;
					finalReportCounter[0]=finalReportCounter[0]+1;
				} else if (TestCaseExecutionDetails.get(Key) == ("SKIPPED")) {
					skipped=skipped+1;
				}
			}
			if(finalReportCounter[1]!= (finalReportCounter[0] - finalReportCounter[2])){
				log.warn("WARNNING:: Final results mismatched");
				log.info("WARNNING:: Actual data in finalReportCounter: Total:"+finalReportCounter[0]+" :: PASS: "+finalReportCounter[1]+" ::FAIL: "+finalReportCounter[2]);
			}
			if(totalTestCases!=finalReportCounter[0]+skipped){
				log.warn("WARNNING:: Final results mismatched for skipped count");
				log.info("WARNNING:: Actual data totalTestCases:"+totalTestCases+" finalReportCounter: Total:"+finalReportCounter[0]+" :: skipped: "+skipped);
			}
			log.info("TotalTestCases:"+totalTestCases);
			log.info("finalReportCounter:Total:"+finalReportCounter[0]+" :: PASS: "+finalReportCounter[1]+" ::FAIL: "+finalReportCounter[2]);
			hMap.put("EndTime", scrShot.format(new Date()));
			ExportTestResultsExcel expxl = new ExportTestResultsExcel (this);
			expxl.readExcelReports();

			//Export the Test summary report and Build HTML report

			TemplateGenerator htmlTemplate = new TemplateGenerator();
			htmlTemplate.buildTemplate_BrowserLevel(this,finalReportCounter[0], finalReportCounter[1],finalReportCounter[2],TestCaseExecutionDetails);

			log.info("Total Number of Cases:"+totalTestCases);
			log.info("Executed:"+finalReportCounter[0]);
			log.info("Passed:"+finalReportCounter[1]);
			log.info("Failed:"+finalReportCounter[2]);
			log.info("Skipeed:"+(totalTestCases-finalReportCounter[0]));
			log.info("Application URL:"+appurl);
			log.info("Start Time:"+hMap.get("StartTime"));

			TED.setStartTime(hMap.get("ExecutionStartTime"));
			TED.setTotalTestCases(totalTestCases);
			TED.setTotalExecuted(finalReportCounter[0]);
			TED.setPassed(finalReportCounter[1]);
			TED.setFailed(finalReportCounter[2]);
			TED.setSkipped(totalTestCases-finalReportCounter[0]);
			TED.setURL(appurl);
			expxl.consolidateExcelReport();
			List<String> resultSummary = new ArrayList<String>();
			resultSummary.add(Browser);
			resultSummary.add(Integer.toString(totalTestCases));
			resultSummary.add(Integer.toString(finalReportCounter[1]));
			resultSummary.add(Integer.toString(finalReportCounter[2]));
			resultSummary.add(Integer.toString(totalTestCases-finalReportCounter[0]));
			//Exporting the summary

			expxl.exportTestSummary(resultSummary);

			log.info("End Time:"+hMap.get("EndTime"));
			TED.setEndTime(hMap.get("EndTime"));
			EmailTestReport emp=new EmailTestReport();

			emp.postMail_BrowserLevel(this,TED,Browser.toUpperCase(),client);
		}catch(Exception e) {
			System.out.println("Unable to generate consolidated report");
			System.out.println("Error:"+e.getMessage());
			log.error("Unable to generate consolidated report");
			log.error("Error: "+e.getMessage());
		}
	}

	/*'#########################################################################################################
	'Function name		:	invokeBrowser
	'Description		:	This function is for Browser invocation

	'Parameters			:	N/A
	'#########################################################################################################*/

	public void invokeBrowser() {
		FirefoxProfile profile=new FirefoxProfile();
		try {
			nodeName=getNode(Browser.toLowerCase());			
			String hub=java.net.InetAddress.getLocalHost().getHostAddress().toString().trim();			
			String HubPort = "4444";
			URL grid_url = new URL("http://" +hub+ ":" + HubPort + "/wd/hub");
			System.out.println("Hub IP: " +hub);
			log.debug("Hub ip address is-------" +hub);
			if("GCHROME".equalsIgnoreCase(Browser))	{
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--test-type");
				capability=DesiredCapabilities.chrome();			
				capability.setCapability(ChromeOptions.CAPABILITY, options);
				capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			}else if("IE9".equalsIgnoreCase(Browser)||"IE10".equalsIgnoreCase(Browser)||"IE11".equalsIgnoreCase(Browser)) {
				capability=DesiredCapabilities.internetExplorer();		
				capability.setCapability("ignoreZoomSetting", true);
				capability.setCapability("browserstack.ie.noFlash", "true");
				capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			}else {
				if(!("ff".equalsIgnoreCase(Browser))){
					log.warn("Invalid Browser Name:"+Browser+" Executing the test cases in default browser FireFox");
					System.out.println(("Invalid Browser Name:"+Browser+" Executing the test cases in default browser FireFox"));
				}
				profile.setAssumeUntrustedCertificateIssuer(false);
				capability=DesiredCapabilities.firefox();
				capability.setCapability(FirefoxDriver.PROFILE,profile);						
			}
			capability.setPlatform(Platform.ANY);
			capability.setVersion(nodeName);
			log.info("Grid URL:"+grid_url);
			log.info("Capability:"+capability);
			
			//Start webdriver
			webdriver=new RemoteWebDriver(grid_url, capability);
			Thread.sleep(1000);
			webdriver.manage().window().maximize();
			Thread.sleep(1000);
			//Launch URL
			try {
				webdriver.get(appurl);
			}catch(Exception e) {
				System.out.println("Error: Unable to load URL");
				log.warn("Unable to load URL:"+appurl);
				log.warn("Error:",e);
			}
			
			log.info("Executing on  GRID");
			System.out.println("The node to be executed is------"+nodeName);
			log.info("Executing in Grid");
			log.info("The node to be executed is------"+nodeName);
			hostFound = null;			
			HttpCommandExecutor ce = (HttpCommandExecutor) webdriver.getCommandExecutor();
			String hostName = ce.getAddressOfRemoteServer().getHost();
			System.out.println("Hub IP: " +hostName);
			log.info("Hub IPAddress is---------" +hostName);
			int port = ce.getAddressOfRemoteServer().getPort();
			log.info("PortNumber is---------" +hostName);
			HttpHost host = new HttpHost(hostName, port);
			log.info("HOST---------" +host);
			DefaultHttpClient client = new DefaultHttpClient();
			URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session=" + webdriver.getSessionId());
			System.out.println("The session url is---------" +sessionURL);
			log.info("The session url is---------" +sessionURL);
			BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest( "POST", sessionURL.toExternalForm());
			HttpResponse response = client.execute(host, r);
			JSONObject object = extractObject(response);
			URL myURL = new URL(object.getString("proxyId"));
			if ((myURL.getHost() != null) && (myURL.getPort() != -1)) {
				hostFound = myURL.getHost();
				System.out.println("Node IP: " +hostFound);
				log.info("Node ip address" +hostFound);
			}					
		}catch(Exception e) {
			try {
				log.error("Unable invoke browser in grid:"+e.getMessage());
				System.out.println("Browser launching Locally: "+Browser.toUpperCase());
				log.info("Executing on local machine");
				if("IE9".equalsIgnoreCase(Browser)||"IE10".equalsIgnoreCase(Browser)||"IE11".equalsIgnoreCase(Browser)){
					System.setProperty("webdriver.ie.driver",ProjPath+"/drivers/IEDriverServer.exe");
					capability=DesiredCapabilities.internetExplorer();
					capability.setCapability("browserstack.ie.noFlash", "true");
					capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					webdriver = new InternetExplorerDriver(capability);				
				}else if("GCHROME".equalsIgnoreCase(Browser)) {
					System.setProperty("webdriver.chrome.driver",ProjPath+"/drivers/chromedriver.exe");
					capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--disable-extensions");
					options.addArguments("--disable-popup-blocking");
					options.addArguments("--test-type");
					capability.setCapability(ChromeOptions.CAPABILITY, options);
					webdriver = new ChromeDriver(capability);
				}else {
					if(!("ff".equalsIgnoreCase(Browser))) {
						log.warn("Invalid Browser Name:"+Browser+" Executing the test cases in default browser FireFox");
						System.out.println(("Invalid Browser Name:"+Browser+" Executing the test cases in default browser FireFox"));
					}
					profile.setAssumeUntrustedCertificateIssuer(false);
					capability=DesiredCapabilities.firefox();
					capability.setCapability(FirefoxDriver.PROFILE,profile);
					webdriver = new FirefoxDriver(capability);
				}
				Thread.sleep(1000);
				webdriver.manage().window().maximize();
				try {
					webdriver.get(appurl);
				}catch(Exception loadURL) {
					System.out.println("Error: Unable to load URL");
					log.warn("Unable to load URL:"+appurl);
					log.warn("Error:",loadURL);
				}
			}catch(Exception onLocalBrowserFail) {				
				hMap.put("EndTime", scrShot.format(new Date()));
				log.error("Unable to invoke the browser:"+Browser);
				log.error("Error:"+onLocalBrowserFail.getMessage());
				if(!onFailedCaseExecution){
					Set TCset=TestCaseExecutionDetails.keySet();
					Iterator TCiter = TCset.iterator();
					int a[] = new int[TCset.size()];
					int count = 0;
					while (TCiter.hasNext()) {
						a[count] = Integer.parseInt(TCiter.next().toString());
						count++;
					}
					Arrays.sort(a);
					int skipped=0;
					TCiter = TCset.iterator();
					int[] finalReportCounter= new int[3]; 

					for(int Key : a){
						if (TestCaseExecutionDetails.get(Key) == "PASS") {
							finalReportCounter[1]=finalReportCounter[1]+1;
							finalReportCounter[0]=finalReportCounter[0]+1;
						} else if (TestCaseExecutionDetails.get(Key).startsWith("FAIL")) {
							finalReportCounter[2]=finalReportCounter[2]+1;
							finalReportCounter[0]=finalReportCounter[0]+1;

						} else if (TestCaseExecutionDetails.get(Key) == ("SKIPPED")) {
							skipped=skipped+1;
						}
					}
					log.info("Total Number of Cases:"+totalTestCases);
					log.info("Executed:"+finalReportCounter[0]);
					log.info("Passed:"+finalReportCounter[1]);
					log.info("Failed:"+finalReportCounter[2]);
					log.info("Skipeed:"+(totalTestCases-finalReportCounter[0]));
					log.info("Application URL:"+appurl);
					log.info("Start Time:"+hMap.get("StartTime"));

					TED.setStartTime(hMap.get("ExecutionStartTime"));
					TED.setTotalTestCases(totalTestCases);
					TED.setTotalExecuted(finalReportCounter[0]);
					TED.setPassed(finalReportCounter[1]);
					TED.setFailed(finalReportCounter[2]);
					TED.setSkipped(totalTestCases-finalReportCounter[0]);
					TED.setURL(appurl);

					List<String> resultSummary = new ArrayList<String>();

					resultSummary.add(Browser);
					resultSummary.add(Integer.toString(totalTestCases));
					resultSummary.add(Integer.toString(finalReportCounter[1]));
					resultSummary.add(Integer.toString(finalReportCounter[2]));
					resultSummary.add(Integer.toString(totalTestCases-ReportCounters[0]));
					try {
						expxl.exportTestSummary(resultSummary);
					}catch(Exception summaryReportError) {
						System.out.println("Unable to export summary report in invokeBrowser method ");
						log.error("Unable to export summary report in invokeBrowser method");
						log.info("Error:"+summaryReportError);
					}
					if(finalReportCounter[1]!= (finalReportCounter[0] - finalReportCounter[2])){
						log.warn("WARNNING:: Final results mismatched");
						log.info("WARNNING:: Actual data in finalReportCounter: Total:"+finalReportCounter[0]+" :: PASS: "+finalReportCounter[1]+" ::FAIL: "+finalReportCounter[2]);
					}
					if(totalTestCases!=finalReportCounter[0]+skipped){
						log.warn("WARNNING:: Final results mismatched for skipped count");
						log.info("WARNNING:: Actual data totalTestCases:"+totalTestCases+" finalReportCounter: Total:"+finalReportCounter[0]+" :: skipped: "+skipped);

					}
					log.info("TotalTestCases:"+totalTestCases);
					log.info("finalReportCounter:Total:"+finalReportCounter[0]+" :: PASS: "+finalReportCounter[1]+" ::FAIL: "+finalReportCounter[2]);

					TemplateGenerator htmlTemplate = new TemplateGenerator();
					htmlTemplate.buildTemplate_BrowserLevel(this,finalReportCounter[0], finalReportCounter[1],finalReportCounter[2],TestCaseExecutionDetails);
				}
				else{
					Set TCset=failedExecutionDetails.keySet();
					Iterator TCiter = TCset.iterator();
					int a[] = new int[TCset.size()];
					int count = 0;
					while (TCiter.hasNext()) {
						a[count] = Integer.parseInt(TCiter.next().toString());
						count++;
					}
					Arrays.sort(a);
					int skipped=0;
					int[] failedReportCounter= new int[3]; 
					for(int Key : a){
						if (failedExecutionDetails.get(Key) == "PASS") {
							failedReportCounter[1]=failedReportCounter[1]+1;
							failedReportCounter[0]=failedReportCounter[0]+1;
						} else if (failedExecutionDetails.get(Key).startsWith("FAIL")) {
							failedReportCounter[2]=failedReportCounter[2]+1;
							failedReportCounter[0]=failedReportCounter[0]+1;

						} else if (failedExecutionDetails.get(Key) == ("SKIPPED")) {
							skipped=skipped+1;
						}
					}	
					TemplateGenerator htmlTemplate = new TemplateGenerator();
					htmlTemplate.buildTemplate_BrowserLevel(this,failedReportCounter[0], failedReportCounter[1],failedReportCounter[2],failedExecutionDetails);

					List<String> resultSummary = new ArrayList<String>();
					resultSummary.add(Browser);
					resultSummary.add(Integer.toString(TestCases.size()));
					resultSummary.add(Integer.toString(failedReportCounter[1]));
					resultSummary.add(Integer.toString(failedReportCounter[2]));
					resultSummary.add(Integer.toString(TestCases.size()-failedReportCounter[0]));

					try {
						expxl.exportTestSummary(resultSummary);
					}catch(Exception summaryReportError) {
						System.out.println("Unable to export summary report in invokeBrowser method ");
						log.error("Unable to export summary report in invokeBrowser method");
						log.info("Error:"+summaryReportError);
					}
				}
				if(onFailedCaseExecution)
				{
					System.out.println();
					isFailedCaseExecuted=true;
					generateConsolidatedReport();
				}
				executionStatus.put(Browser.toUpperCase(), TED);
				CreateClient.executionStatusForAllClients.put(client.toUpperCase(), executionStatus);

				log.error("Stopping the theread, Unable to invoke browser: "+Browser +"Error:"+onLocalBrowserFail.getMessage());

				System.out.println("Stopping the thread, Unable to invoke browser: "+Browser +"Error:"+onLocalBrowserFail.getMessage());
				CreateClient.updateExecutionStatus(Browser.toUpperCase(),client.toUpperCase()); 
				Thread.currentThread().stop();
			}
		}
	}


	private static JSONObject extractObject(HttpResponse resp) throws IOException, JSONException {
		InputStream contents = resp.getEntity().getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy(contents, writer, "UTF8");
		JSONObject objToReturn = null;
		try {
			objToReturn = new JSONObject(writer.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objToReturn;
	}

	/*'#########################################################################################################
	'Function name		:	getNode
	'Description		:	This function is to get the node to be connected for invoking a browser

	'Parameters			:	N/A
	'#########################################################################################################*/

	public String getNode(String browser)
	{
		String node = null;
		Properties gridprops=new Properties();
		try {
			FileInputStream in = new FileInputStream(ProjPath+"/properties/Grid.properties");
			gridprops.load(in);
			node=gridprops.getProperty(browser);
		}catch(Exception e) {
			System.out.println("Error occured while reading the grid.properties file" +e.getLocalizedMessage());
			log.warn("Error occured while reading the grid.properties file" +e.getLocalizedMessage());
		}
		return node;
	}


	/*'#########################################################################################################
	'Function name		:	tearDown
	'Description		:	This function is to stop the selenium object

	'Parameters			:	N/A
	'#########################################################################################################*/

	public void tearDown() throws Exception {
		if(webdriver!=null)
			System.out.println("quitting the browser ");
		try {
			Thread.sleep(5000);
			log.info("quitting the browser ");
			webdriver.quit();
		}catch(Exception e) {
			log.error("################Quit Error: ",e);
			System.out.println("Unable to teminated the browser");
			log.error("---------Unable to teminated the browser");
			log.error(e.getMessage());
		}
	}


	/*'#########################################################################################################
	'Function name		:	testInitiation
	'Description		:	This function is to read the testdata from excel sheet, submit them to the browser and writing the results to an excel sheet.

	'Parameters			:	N/A
	'#########################################################################################################*/

	public void testInitiation(String browser,String appUrl,String client,String user1,
			String password1,String executionlog,Long lngTestStepExecutionStartTime,String NodeIp) throws Exception {
		boolean caseFound = false;
		// variables used to track the failed cases
		isExecutionStarted=true;
		String strErrorMsg = "";
		String arrCon[] = null;
		String stepDescription = null;
		int stepNo = 0;
		boolean auctionSetFlag = false;

		//This is to store the success & failure of a test case
		ResultDetails resultDetails=new ResultDetails();		
		//This is to store test data of a test case
		System.out.println("Test cases to be executed are: " +TestCases);
		log.info("Test cases to be executed are: " +TestCases);

		//This is to repeat the test for the number of testcases that are in the Test Data
		System.out.println("test case size is---------------" +TestCases);
		for(int i=testCase;i<TestCases.size();i++){
			testCase=i;
			currentTestCase=testCase;
			//Reading Data from Test Data details Excel Sheet for specific Test Case Id.
			TestData = impxl.displayFromExcel(TestCases.get(i));
			//Array list to store the result of the test case
			result = new ArrayList<String>();
			//This is to get the Test Data details i.e. a row in Test Data Details, & submit to the selenium
			caseFound = false;
			TestType test = null;
			
			//Launch URL
			try {
				webdriver.get(appurl);
				//If any unknown alert displayed, handle that
				try{
					Alert alert = webdriver.switchTo().alert();
					alert.accept();
				}
				catch(Exception e){}
				
			}catch(Exception e) {
				System.out.println("Error: Unable to load URL");
			}
			
			
			
			for(int k=1;k<=TestData.size();k++){
				boolean onAuctionStep = false;
				System.out.println("*****************************************************");
				resultDetails=new ResultDetails();
				resultDetails.setFlag(false);//This is to store the success & failure of a test case    			
				//Retrieving & storing the testdata in a bean
				tdd=(TestDataDetails)TestData.get(k);
				log.info("------------------------------------------");
				log.info("Test case being Executed: "+tdd.getTestCaseID());
				//Finding the test case to be executed in the test data details
				TestCaseDetails.put(tdd.getTestCaseID(), tdd.getTestCaseTitle());
				if(!(TestCases.get(i).equals(tdd.getTestCaseID()) && (tdd.getBrowserType().equalsIgnoreCase("COMMON") || tdd.getBrowserType().toUpperCase().indexOf(Browser.toUpperCase()) != -1)&&getClient(tdd.getclientName(),tdd))){
					log.warn("-----------------------Step Skipped::"+" 	TestCaseNo:  "+tdd.getTestCaseID()+"	 StepNo: "+tdd.getTestDataID()+"  	::	Action Type:  "+tdd.getActionType()+" 	::		ClientName In TestData:  \""+tdd.getclientName()+"\" 	::	ClientName in config:  \""+client+"\"-----------------------------");
					System.out.println("-----------------------Step Skipped::"+" 	TestCaseNo:  "+tdd.getTestCaseID()+"	 StepNo: "+tdd.getTestDataID()+"  	::	Action Type:  "+tdd.getActionType()+" 	::		ClientName In TestData:  \""+tdd.getclientName()+"\" 	::	ClientName in config:  \""+client+"\"-----------------------------");
				}
				if(TestCases.get(i).equals(tdd.getTestCaseID()) && (tdd.getBrowserType().equalsIgnoreCase("COMMON") || tdd.getBrowserType().toUpperCase().indexOf(Browser.toUpperCase()) != -1)&&getClient(tdd.getclientName(),tdd)){
					if (!caseFound) {
						caseFound = true;
					}
					log.info("Test step:"+tdd.getTestDataID());			
					int tcID =tdd.getTestCaseID();
					if (!(testCaseID.contains(tcID))) { testCaseID.add(tcID);}

					webdriverCopy = webdriver;
					//These String variables are to store the data Fields & corresponding data Values of the test case
					String dataFields=tdd.getDataFields();
					String fieldName=tdd.getFieldName();
					String dataValues=tdd.getDataValues();
					stepNo= tdd.getTestDataID();
					stepDescription=tdd.getWorkingPage();
					System.out.println("Test case being Executed "+tdd.getTestCaseID()+" Step No --- "+stepNo);
					log.info("Datavalue is----------" +dataValues);
					try
					{
						if(dataValues.equalsIgnoreCase("clientuser")) {
							dataValues= user1;
							System.out.println("dataFields : "+dataFields);
							System.out.println("dataValues : "+dataValues);
						}else if(dataValues.equalsIgnoreCase("clientpwd")) {
							dataValues= password1;
							System.out.println("dataFields : "+dataFields);
							System.out.println("dataValues : "+dataValues);
						}
						else if(dataValues.equalsIgnoreCase("appurl")) {
							dataValues=appUrl;
							System.out.println("dataFields : "+dataFields);
							System.out.println("dataValues : "+dataValues);							
						}
					}catch(Exception e) {
						log.debug("Exception: "+e.getMessage());
					}

					if(dataValues==null)
						dataValues = "";						
					System.out.println("DataField : "+dataFields);
					log.info("DataField : "+dataFields);
					System.out.println("DataValue : "+dataValues);
					log.info("DataValue : "+dataValues);

					//This is object of the class in which different functions exists with different functionalities
					test=new TestType(this);
					//From Here, Based on the type of test mentioned in the test data, corresponding functionalities are implemented

					log.info("<In Set values of the page>");
					//Internally Based on the data provided in the test Data again the functionalities differ

					log.info("{Data Fields & Data Values Exist}");
					//						System.out.println("--------------------------setting values-----------------------------");
					log.info("--------------------------setting values-----------------------------");
					String actionType = tdd.getActionType();
					System.out.println("Action Type : "+ actionType);
					log.info("Action Type : "+ actionType);

					if(actionType == null){
						resultDetails.setErrorMessage("Action Field is Empty");
						resultDetails.setFlag(false);
					}
					
					else{
						resultDetails = test.performAction(webdriverCopy,dataFields,dataValues, actionType, fieldName,browser,user1,password1);					
						log.info(" RESULT "+resultDetails.getFlag());
					}

					//IF:3:Next
					//check whether there is IF condition associated with the test step
					/*if (tdd.getCondition() != null && tdd.getCondition().indexOf("IF") != -1) { 
						log.info(" tdd.getCondition() "+tdd.getCondition());
						arrCon = tdd.getCondition().split(":");
						try
						{
							compVar= Integer.parseInt(arrCon[1]);
						}catch(Exception e) {
							log.debug("Exception: "+e.getMessage());
						}
						if(resultDetails.getFlag())
						{
							System.out.println("?   P A S S   ?");
							if(arrCon[1].equalsIgnoreCase("NEXT")){
								System.out.println("Continue next step...");
								log.info("Continue next step...");
							}else {
								new java.math.BigInteger(arrCon[1]);
								System.out.println( "step change to "+ Integer.parseInt(arrCon[1]));
								log.info("step change to "+ compVar);
								k=compVar-1;
							}
						}else {
							System.out.println("?   F A I L   ?");
							if(arrCon[2].equalsIgnoreCase("NEXT")){
								System.out.println("Continue next step...");
								log.info("Continue next step...");
								resultDetails.setFlag(true);
							}else {
								compVar = Integer.parseInt(arrCon[2]);
								System.out.println( "step change to "+ compVar);
								log.info("step change to "+ compVar);
								k=compVar-1;
								resultDetails.setFlag(true);
							}
						}
					}	*/		
					//Adding the test case id, data id and the result to the ArrayList		    		
					result.add(Integer.toString(tdd.getTestCaseID()));
					result.add(Integer.toString(tdd.getTestDataID()));
					result.add((String)tdd.getTestCaseTitle());	   		
					if(!resultDetails.getFlag()){
						System.out.println("?   F A I L   ?");
						log.info("?   F A I L   ?");
						result.add("Fail");
						System.out.println("Browser Type : "+browser);
						log.info("Browser Type : "+browser);
						log.info("Adding Failed case"+tdd.getTestCaseID());
						failed.add(tdd.getTestCaseID());
						if ("FF".equalsIgnoreCase(browser)||"IE8".equalsIgnoreCase(browser) ||"IE9".equalsIgnoreCase(browser)||"IE10".equalsIgnoreCase(browser)||"IE11".equalsIgnoreCase(browser)||"GCHROME".equalsIgnoreCase(browser)||"safari".equalsIgnoreCase(browser)) {
							SimpleDateFormat ScreenShot = new SimpleDateFormat("MMddyy_HHmmss");
							Calendar cal = Calendar.getInstance();
							SimpleDateFormat strDirFormat = new SimpleDateFormat("MMddyy");
							String strDirectoryName = strDirFormat.format(new Date());
							String reportsPath = ProjPath+"\\TestReports\\"+strDirectoryName;
							try {
//								htmlReportsPath=hostAddress+"//"+ProjPath.substring(ProjPathLibpath.lastIndexOf("\\")+1)+"\\TestReports\\"+strDirectoryName;
								htmlReportsPath=sharedPath+"\\TestReports\\"+strDirectoryName;
							}catch(Exception e) {
								log.warn("Unable to save the screenshot using HostAddress:"+e.getMessage());
							}
							File f = new File(reportsPath);
							try {
								if (!f.exists()) {
									f.mkdir();
									log.info("Directory Created");
								}
							} catch(Throwable e) {
								System.out.println("Unable to create directory");
								log.error("Unable to create directory");
							}
							try {
								String screenShotFileName=tdd.getTestCaseID()+"_"+ScreenShot.format(cal.getTime())+"_"+browser+".png";
								strScreenshotName = reportsPath+"\\"+screenShotFileName;
								htmlReportsPath=htmlReportsPath+"\\"+screenShotFileName;
								if(onAuctionStep){
									f = ((TakesScreenshot)auctionWebdriver).getScreenshotAs(OutputType.FILE);
								}else{
									f = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
								}
								FailedCaseScreenShot.put(tdd.getTestCaseID(), htmlReportsPath);
								

							}catch(Exception e) {
								if(!e.getMessage().contains("target window already closed")){
									WebDriver augmentedDriver=  new Augmenter().augment(webdriver);
									if(onAuctionStep){
										augmentedDriver = new Augmenter().augment(auctionWebdriver);
									}else{
										augmentedDriver = new Augmenter().augment(webdriver);
									}
									try {
										f = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
										FailedCaseScreenShot.put(tdd.getTestCaseID(), htmlReportsPath);
									}catch(Exception ee) {
										FailedCaseScreenShot.put(tdd.getTestCaseID(), "Browser Crashed");
										strScreenshotName="Browser Crashed";
										log.error("Unable to capture the screenshot");
										log.error("Error:"+e.getMessage());
									}
								}
							}
							try {
								FileUtils.copyFile(f, new File(strScreenshotName));
							}catch (IOException ioe) {
								System.out.println(ioe.getMessage());
								log.warn(ioe.getMessage());
							}
							strScreenshotName = ". Screen Shot : "+strScreenshotName;
						}

						/**
						 *  !!Block Start!! To track the failed testcases list in a string variable
						 */						
						if (failedCases.equals("")) {								
							failedCases = tdd.getTestCaseID()+"";
							previousFailedTCID = tdd.getTestCaseID();
						}else {
							if (tdd.getTestCaseID() - previousFailedTCID == 1) {
								if (sequenceNumber)
									failedCases = failedCases.replace(failedCases.substring(failedCases.lastIndexOf("-")+1),tdd.getTestCaseID()+"");
								else
									failedCases =  failedCases+"-"+tdd.getTestCaseID();
								sequenceNumber = true;
							}else {
								failedCases = failedCases+","+tdd.getTestCaseID();
								sequenceNumber = false;								
							}
							previousFailedTCID = tdd.getTestCaseID();
						}
						log.info("failedCases = "+failedCases);
						if(resultDetails.getErrorMessage()!= null) {
							strErrorMsg = "Test case failed at Step No. :: "+ tdd.getTestDataID() +"   Error Message ::  "+ resultDetails.getErrorMessage() + FindPlatformErrors();
						}else {
							strErrorMsg = "Test case failed at Step No. :: "+ tdd.getTestDataID() + FindPlatformErrors();
						}
						String strMsg = "";	
						if ((resultDetails.getWarningMessage()!= null)) {
							if (hMap.get("strWarningMessage")!= null) {
								strMsg = hMap.get("strWarningMessage") +"\n"+ "!! Warning !! Step No. :: "+ tdd.getTestDataID() + "   Message ::  " + resultDetails.getWarningMessage();
							} else {
								strMsg = "!! Warning !! Step No. :: "+ tdd.getTestDataID() + " Message ::  " + resultDetails.getWarningMessage();
							}
							log.info("strMsg = "+strMsg);
							hMap.put("strWarningMessage", strMsg);
							result.add(strMsg+"\n"+strErrorMsg+strScreenshotName);		    				
						}else if (hMap.get("strWarningMessage")!= null) {
							result.add(hMap.get("strWarningMessage")+"\n"+strErrorMsg+strScreenshotName);
						}else {
							hMap.put("strWarningMessage", "");
							result.add(""+"\n"+strErrorMsg+strScreenshotName);
						}
						try {
							//IF:Next:3
							if ((tdd.getCondition() != null) && (tdd.getCondition().indexOf("IF") != -1)){// && (tdd.getActionType().toUpperCase() == "VERIFY")) {	
									log.debug(" tdd.getCondition() "+tdd.getCondition());
									arrCon = tdd.getCondition().split(":");
									if(arrCon[2].equalsIgnoreCase("NEXT")){
									System.out.println("Continue next step...");
									log.info("Continue next step...");
	//								resultDetails.setFlag(true);
								}else {
									compVar = Integer.parseInt(arrCon[2]);
									System.out.println( "step change to "+ compVar);
									log.info("step change to "+ compVar);
									k=compVar-1;
								}
								resultDetails.setFlag(true);
								if(failed.size()>0)
									failed.remove(new Integer(tdd.getTestCaseID()));
								
							}
							
						}catch (NumberFormatException ex) {
							System.out.println("invalid step number."+arrCon[2]);
							log.debug("invalid step number."+arrCon[2]);
						}
						
						TestCaseExecutionDetails.put(tdd.getTestCaseID(), "FAIL"+strErrorMsg);
						if(onFailedCaseExecution){
							failedExecutionDetails.put(tdd.getTestCaseID(), "FAIL"+strErrorMsg);
						}
					}
					else{
						System.out.println("?   P A S S   ?");
						log.info("?   P A S S   ?");
						result.add("Pass");
						String strMsg = "";		    			
						TestCaseExecutionDetails.put(tdd.getTestCaseID(), "PASS");
						if(onFailedCaseExecution){
							failedExecutionDetails.put(tdd.getTestCaseID(), "PASS");
						}
						if ((resultDetails.getWarningMessage()!= null)) {
							if (hMap.get("strWarningMessage")!= null) {
								strMsg = hMap.get("strWarningMessage") +"\n"+ "!! Warning !! Step No. :: "+ tdd.getTestDataID() + "   Message ::  " + resultDetails.getWarningMessage();
							}else {
								strMsg = "!! Warning !! Step No. :: "+ tdd.getTestDataID() + " Message ::  " + resultDetails.getWarningMessage();
							}
							log.info("strMsg = "+strMsg);
							hMap.put("strWarningMessage", strMsg);
							result.add(strMsg);		    				
						}else if (hMap.get("strWarningMessage")!= null) {
							result.add(hMap.get("strWarningMessage"));
						}else {
							hMap.put("strWarningMessage", "");
							result.add("");
						}
						
						try {
							//IF:3:Next
							if ((tdd.getCondition() != null) && (tdd.getCondition().indexOf("IF") != -1)){ // && (tdd.getActionType().toUpperCase() == "VERIFY")) {
								arrCon = tdd.getCondition().split(":");
//								System.out.println("arrCon[1]: "+arrCon[1]);
//								k = Integer.parseInt(arrCon[1].split("-")[1])-1;
								/*try
								{
									compVar= Integer.parseInt(arrCon[1]);
								}catch(Exception e) {
									log.debug("Exception: "+e.getMessage());
								}*/
								if(arrCon[1].equalsIgnoreCase("NEXT")){
									System.out.println("Continue next step...");
									log.info("Continue next step...");
								}else {
//									new java.math.BigInteger(arrCon[1]);
									compVar= Integer.parseInt(arrCon[1]);
									System.out.println( "step change to "+ Integer.parseInt(arrCon[1]));
									log.info("step change to "+ compVar);
									k=compVar-1;
								}
							}
						}catch (NumberFormatException ex) {
							System.out.println("invalid step number."+arrCon[1]);
							log.debug("invalid step number."+arrCon[1]);
						}
						
						
					}
					result.add((new java.util.Date()).toString());
					
					//To Stop executing the current test case and to proceed with the next test case if any of the test fails.
					if(!resultDetails.getFlag() ){
						//To check if the failure is caused due to 404 error then to load the logout url.
						if((!FailedCaseScreenShot.get(tdd.getTestCaseID()).contains("Browser Crashed")&&webdriver.getTitle().equalsIgnoreCase("404 Not Found"))) {
							try {
								webdriver.get(appUrl);
								try{
									Alert alert = webdriver.switchTo().alert();
									alert.accept();
								}
								catch(Exception e){}
							}catch(Throwable e) {			
								System.out.println("Exception : "+e.getMessage());
								log.error("Exception : "+e.getMessage());
							}
						}
						if (resultDetails.getErrorMessage()==null) {	break;					
						}else if (resultDetails.getErrorMessage().equals("")) { break;
						}else {
							break;
						}
					}		    		
				}
				//Loop the steps
				try {
					if (tdd.getCondition() != null && tdd.getCondition().toUpperCase().indexOf("LOOP") != -1&&(!(FailedCaseScreenShot.get(tdd.getTestCaseID())!=null)||(FailedCaseScreenShot.get(tdd.getTestCaseID()).contains("Browser Crashed"))))
					{
						int from = k+1;
						String condition[] = tdd.getCondition().split(":");
						if (condition[1].split("-")[0].toUpperCase().equalsIgnoreCase("NEXT")) {
							System.out.println("Goto Next Step");
							log.info("Goto Next Step");
							from = k; 
						} else {
							from = Integer.parseInt(condition[1].split("-")[0]); //Loop:3-5:4 - from is 3
						}
						int to = Integer.parseInt(condition[1].split("-")[1]); //Loop:3-5:4 - to is 5
						int lcnt = Integer.parseInt(condition[2]); //Loop:3-5:4 - loop for 4 times
						test.executeSteps(webdriver, from, to, lcnt, i, TestData, resultDetails, result, browser, user1, password1);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
//					System.out.println("invalid step number."+arrCon[0].split(":")[1]);
//					log.debug("invalid step number."+arrCon[0].split(":")[1]);
				}
			}
			if (caseFound) {	
				log.debug("Case found");
				List<String> exportResult = new ArrayList<String>();
				exportResult = result;
				if(!onFailedCaseExecution){
					ReportCounters[0] = ReportCounters[0] + 1;
					log.debug("Incrementing ReportCounters[0]:"+ReportCounters[0]);
				}
				if(onFailedCaseExecution){
					failedReportCounter[0]=failedReportCounter[0]+1;
					log.debug("Incrementing failedReportCounter[0]:"+failedReportCounter[0]);
				}
				for(int ii=0;ii<result.size(); ii=ii+6){
					if(result.get(ii+3).equals("Fail")) {
						exportResult = result.subList(ii, ii+6);
						if(!onFailedCaseExecution){
							ReportCounters[2] = ReportCounters[2] + 1;
						}
						if(onFailedCaseExecution){
							failedReportCounter[2]=failedReportCounter[2]+1;
						}
						break;
					}
				}

				log.info("The results are-------------------" +exportResult);
				Date endDate = new Date();
				String strTestStepExecutionTimeDuration = timeDuration(endDate.getTime() - lngTestStepExecutionStartTime);
				System.out.println("End time is-------"+strTestStepExecutionTimeDuration); 
				log.info("End time is-------"+strTestStepExecutionTimeDuration);
				log.info("The results are-------------------" +exportResult);
				expxl.exportExcelRows(exportResult,browser,client);
//				DBTracking  db = new DBTracking(this);			        
//				db.TestResultTracking(exportResult, browser, client,attributeID2,executionlog,stepNo,stepDescription,strTestStepExecutionTimeDuration,NodeIp,build_ID);

			}
			else  {	   
				log.info("? S K I P P E D ?");
				log.info("Test case:"+tdd.getTestCaseID());
				log.info((String)tdd.getTestCaseTitle());
				log.info(" No Test Steps Available");
				TestCaseExecutionDetails.put(tdd.getTestCaseID(), "SKIPPED");

				if(onFailedCaseExecution){
					failedExecutionDetails.put(tdd.getTestCaseID(), "SKIPPED");
				}

				java.util.List<String> exportResult = new ArrayList<String>();
				exportResult.add(Integer.toString(tdd.getTestCaseID()));
				exportResult.add(Integer.toString(tdd.getTestDataID()));
				exportResult.add((String)tdd.getTestCaseTitle());	
				exportResult.add("Skipped");
				exportResult.add(" No Test Steps Available");
				exportResult.add((new java.util.Date()).toString());

				log.info("The results are-------------------" +exportResult);
				Date endDate = new Date();
				String strTestStepExecutionTimeDuration = timeDuration(endDate.getTime() - lngTestStepExecutionStartTime);
				System.out.println("End time is-------"+strTestStepExecutionTimeDuration); 
				log.info("End time is-------"+strTestStepExecutionTimeDuration);
				log.info("The results are-------------------" +exportResult);
				expxl.exportExcelRows(exportResult,browser,client);
//				DBTracking  db = new DBTracking(this);			        
//				db.TestResultTracking(exportResult, browser, client,attributeID2,prgmID,executionlog,stepNo,stepDescription,strTestStepExecutionTimeDuration,NodeIp,build_ID);

			}
			/*try {
				//Start code added for auction browser
				try {
					if(auctionSetFlag){
						auctionWebdriver.quit();
						auctionSetFlag=false;
					}						
				}catch(Exception e){log.error("Unable to kill the Auction browser:",e);}
				//End code added for auction browser
				webdriver.get(appUrl);
			}catch(Exception e){
				testCase=currentTestCase+1;
				log.info("Current  TestCase id:"+currentTestCase+" Increasing testCase variable to:"+testCase);
				log.error("Unable to load application url");
				log.error("Error:",e);
				try {
					webdriver.quit();
				}catch(Exception ee){
					log.debug("Exception: "+e.getMessage());
				}
				invokeBrowser();
			}*/
			log.info("out loop:"+i+" size:"+TestCases.size()+" TestCases:"+TestCases+" testcases:"+testcases);
		}
		System.out.println("Falied cases are ------" +failedCases+"------"+client+"-----" +browser);
		Thread.sleep(2000);
		if(browser.equalsIgnoreCase("ff"))			
		{			
			failMap.put("failedCases_ff"+client+"-"+browser,failedCases+"_"+client+"-"+browser);	
		}
		
		if(browser.equalsIgnoreCase("IE9"))
		{	
			failMap.put("failedCases_ie9"+client+"-"+browser,failedCases+"_"+client+"-"+browser);	
		}
		if(browser.equalsIgnoreCase("IE10"))
		{	
			failMap.put("failedCases_ie10"+client+"-"+browser,failedCases+"_"+client+"-"+browser);	
		}
		if(browser.equalsIgnoreCase("IE11"))
		{	
			failMap.put("failedCases_ie11"+client+"-"+browser,failedCases+"_"+client+"-"+browser);	
		}
		if(browser.equalsIgnoreCase("gchrome"))
		{	
			failMap.put("failedCases_gchrome"+client+"-"+browser,failedCases+"_"+client+"-"+browser);	
		}
		if(browser.equalsIgnoreCase("safari"))
		{	
			failMap.put("failedCases_safari"+client+"-"+browser,failedCases+"_"+client+"-"+browser);	
		}

		System.out.println("Failed test cases details are------" +failMap);

		if(!onFailedCaseExecution){
			ReportCounters[1] = ReportCounters[0] - ReportCounters[2];
		}
		else
			failedReportCounter[1]=failedReportCounter[0]-failedReportCounter[2];

		hMap.put("EndTime", scrShot.format(new Date()));
		TemplateGenerator htmlTemplate = new TemplateGenerator();
		log.info("Total Number of Cases:"+TestCases.size());

		List<String> resultSummary = new ArrayList<String>();

		if(!onFailedCaseExecution){
			resultSummary.add(Browser);
			resultSummary.add(Integer.toString(TestCases.size()));
			resultSummary.add(Integer.toString(ReportCounters[1]));
			resultSummary.add(Integer.toString(ReportCounters[2]));
			resultSummary.add(Integer.toString(TestCases.size()-ReportCounters[0]));
			htmlTemplate.buildTemplate_BrowserLevel(this,ReportCounters[0], ReportCounters[1],ReportCounters[2],TestCaseExecutionDetails);

		}
		if(onFailedCaseExecution){
			Set TCset=failedExecutionDetails.keySet();
			Iterator TCiter = TCset.iterator();
			int a[] = new int[TCset.size()];
			int count = 0;
			while (TCiter.hasNext()) {
				a[count] = Integer.parseInt(TCiter.next().toString());
				count++;
			}
			Arrays.sort(a);
			int skipped=0;
			int[] failedReportCounter= new int[3]; 
			for(int Key : a){
				if (failedExecutionDetails.get(Key) == "PASS") {
					failedReportCounter[1]=failedReportCounter[1]+1;
					failedReportCounter[0]=failedReportCounter[0]+1;
				} else if (failedExecutionDetails.get(Key).startsWith("FAIL")) {
					failedReportCounter[2]=failedReportCounter[2]+1;
					failedReportCounter[0]=failedReportCounter[0]+1;

				} else if (failedExecutionDetails.get(Key) == ("SKIPPED")) {
					skipped=skipped+1;
				}
			}	

			htmlTemplate.buildTemplate_BrowserLevel(this,failedReportCounter[0], failedReportCounter[1],failedReportCounter[2],failedExecutionDetails);
			resultSummary.add(Browser);
			resultSummary.add(Integer.toString(TestCases.size()));
			resultSummary.add(Integer.toString(failedReportCounter[1]));
			resultSummary.add(Integer.toString(failedReportCounter[2]));
			resultSummary.add(Integer.toString(TestCases.size()-failedReportCounter[0]));
		}

		//Exporting the summary
		expxl.exportTestSummary(resultSummary);

		log.info("End Time:"+hMap.get("EndTime"));
		TED.setEndTime(hMap.get("EndTime"));

		log.info("Data in Test Execution Details:"+TestCaseExecutionDetails);
		log.info("Data in Failed Execution Deatils:"+failedExecutionDetails);

		log.info("ReportCounter:Total:"+ReportCounters[0]+" :: PASS: "+ReportCounters[1]+" ::FAIL: "+ReportCounters[2]);
		log.info("FailedReportCounter:Total:"+failedReportCounter[0]+" :: PASS: "+failedReportCounter[1]+" ::FAIL: "+failedReportCounter[2]);

		EmailTestReport emp=new EmailTestReport();
		try{
			if(!onFailedCaseExecution)
				emp.postMail_BrowserLevel(this,TED,Browser.toUpperCase(),client);
			if(onFailedCaseExecution)
				emp.postMail_BrowserLevel(this,TED,Browser.toUpperCase(),client);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		if(executeFailedCases&&failed.size()>0){
			executeFailedCases=false;
			executeFailedCases();
			isFailedCaseExecuted=true;
		}
	}


	/*'#########################################################################################################
	'Function name		:	executeFailedCases
	'Description		:	This function is to execute the failed cases again.
	'						
	'Parameters			:	N/A
	'#########################################################################################################*/
	public void executeFailedCases() {
		try {
			onFailedCaseExecution=true;
			expxl = new ExportTestResultsExcel (this);
			hMap.put("TimeStamp", scrShot.format(new Date()));
			hMap.put("StartTime", scrShot.format(new Date()));
			TestCases.clear();
			Collections.sort(failed);
			// Removing duplicates in failed list
			HashSet hs = new HashSet();
			hs.addAll(failed);
			failed.clear();
			failed.addAll(hs);
			int count = failed.size();
			for(int i=0;i < count;i++) {
				TestCases.add(failed.get(i));
			}
			Collections.sort(TestCases);
			TED = new TestExecutionDetails();
			TED.setLogFile(logFileName);
			TED.setStartTime(hMap.get("StartTime"));
			TED.setTotalTestCases(TestCases.size());
			log.info("---------------------------FAILED CASES EXECUTION---------------------------------------------");
			log.info(" Executing Failed Cases");
			log.info("setting total test cases for failed test cases execution:"+TestCases.size());
			expxl.exportExcelHeader(Browser,client);
			testCase=0;
			failed.clear();
			testInitiation(Browser,appurl,client,user1,pwd1,executionlog,startTime,hostFound);
			onFailedCaseExecution=false;
		}catch(Exception e) {
			System.out.println("Unbale to execute failed cases");
			log.error("Unbale to execute failed cases");
			log.error( "Error: ", e );
		}
	}
	public static String timeDuration(long timeDuration) {
		int hour, days;
		String strDuration;
		long milliSeconds = timeDuration;
		timeDuration = (timeDuration / 1000);
		strDuration = timeDuration + " sec";
		if (timeDuration == 0)
			strDuration = milliSeconds + " milli seconds";
		if (timeDuration >= 60) {
			int sec = (int) timeDuration % 60;
			int min = (int) timeDuration / 60;
			strDuration = min + " min " + sec + " sec";
			if (min >= 60) {
				hour = min / 60;
				min = min % 60;
				strDuration = hour + " hour " + min + " min " + sec + " sec";
				if (hour >= 23) {
					days = hour / 24;
					hour = hour % 24;
					strDuration = days + " day " + hour + " hour " + min
							+ " min " + sec + " sec";
				}
			}
		}
		return strDuration;
	}

	/*'#########################################################################################################
	'Function name		:	CloseBrowser
	'Description		:	Close all the existing open browsers
	'						
	'Parameters			:	imageName type of browser - IE8 / IE6 / FF / Gchrome
	'#########################################################################################################*/
	public void CloseBrowser(String imageName) {
		if(imageName.equalsIgnoreCase("IE8") || imageName.equalsIgnoreCase("IE6"))
			imageName="iexplore.exe";
		else if (imageName.equalsIgnoreCase("FF"))
			imageName="firefox.exe";
		else if (imageName.equalsIgnoreCase("GCHROME"))
			imageName="chrome.exe";
		else if (imageName.equalsIgnoreCase("SAFARI"))
			imageName = "Safari.exe";
		Runtime r = Runtime.getRuntime(); 
		// check to see if the process is running 
		Process p = null; 
		String listCommand = "tasklist /FI \"IMAGENAME eq " + imageName + "\" /NH /FO CSV"; 
		String killCommand = "taskkill /f /im " + imageName + " /t"; 
		try { 
			p = r.exec(listCommand); 
		}catch (IOException e) { 	
			log.error("Exception: "+e.getMessage() );
		} 
		try { 
			// if running, error stream will be empty, i.e. null 
			BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream())); 
			if(err.readLine() == null) { 
				// if it is running, kill it 
				r.exec(killCommand); 
				Thread.sleep(5000);     // give it 5 seconds to die 
			} 
		}catch (IOException e) {
			log.error("IOException: "+e.getMessage() );
		}catch (InterruptedException e) {
			log.error("InterruptedException: "+e.getMessage() );
		} catch (Exception e) { 
			log.error("Exception: "+e.getMessage() );
		} 
	}

	/*'#########################################################################################################
	'Function name		:	getDataSource
	'Description		:	Method to get all the Data source details.
	'						
	'Parameters			:	
	'#########################################################################################################*/
	public void getDataSource(){
		Properties dbProps=new Properties();
		try {
			FileInputStream in = new FileInputStream(CreateClient.ProjPath+"/properties/dataSource.properties");
			dbProps.load(in);
			hMap.put("configFile", dbProps.getProperty("configFile"));
			configFile=hMap.put("configFile", dbProps.getProperty("configFile"));
			hMap.put("testDataFile", dbProps.getProperty("testDataFile"));
			hMap.put("testDataAttachment", dbProps.getProperty("testDataAttachment"));
			hMap.put("checkList", dbProps.getProperty("checkList"));
			hMap.put("PlatformErrors", dbProps.getProperty("PlatformErrors"));			
			in.close();
		}catch(IOException io) {
			System.out.println("Unable to read Datasource Properties File.");
			log.error("Unable to read Datasource Properties File: "+io.getMessage());
		}		
	}

	/*'#########################################################################################################
	'Function name		:	FindPlatformErrors
	'Description		:	Method the check the application platform errors in case of any test failure
	'						
	'Parameters			:	N/A
	'#########################################################################################################*/
	public String FindPlatformErrors(){
		System.out.println("Platform Errors : "+hMap.get("PlatformErrors"));
		log.error("Platform Errors : "+hMap.get("PlatformErrors"));
		try {			
			String [] arrErrors = hMap.get("PlatformErrors").split("::");
			System.out.println("Length ="+arrErrors.length);
			log.info("Length ="+arrErrors.length);
			for(int i=0;i<=arrErrors.length-1;i++){
				System.out.println("Error "+i+" = "+arrErrors[i]);
				log.error("Error "+i+" = "+arrErrors[i]);
				if (webdriver.getPageSource().contains(arrErrors[i])) {
					System.out.println("Platform Error. "+arrErrors[i]);
					log.error("Platform Error. "+arrErrors[i]);
					strError = ". Due to Platform Error. "+arrErrors[i];

					break;
				} else {
					strError = "";
				}				
			}return strError;		
		}catch(Throwable e) {			
			System.out.println("Unable to get the Platform Error list from Hash Map.");
			log.debug("Unable to get the Platform Error list from Hash Map.");
			return strError;
		}
	}


	/*'#########################################################################################################
	'Function name		:	getClient
	'Description		:	Method the to get the clientnames on which the test step need to get executed
	'						
	'Parameters			:	N/A
	'#########################################################################################################*/
	public  boolean getClient(String clientNames,TestDataDetails tdd){
		TestType test=new TestType(this);
		if(clientNames.toLowerCase().contains(",core")||clientNames.toLowerCase().contains("core,")||clientNames.equalsIgnoreCase("{core}"))
			clientNames=clientNames.toLowerCase().replace("core", test.getValue("dt:core#1"));
		if(clientNames.toLowerCase().contains(",custom")||clientNames.toLowerCase().contains("custom,")||clientNames.equalsIgnoreCase("{custom}"))
			clientNames=clientNames.toLowerCase().replace("custom", test.getValue("dt:custom#1"));
		if(clientNames.contains("!{")){
			if(clientNames.startsWith("!{")&&clientNames.endsWith("}"))
			{
				clientNames=clientNames.substring(clientNames.indexOf("!{")+2, clientNames.indexOf("}"));
				if(clientNames.toLowerCase().contains("core")){
					clientNames=clientNames.toLowerCase().replace("core", test.getValue("dt:core#1"));
					if(!(clientNames.toLowerCase().contains(client.toLowerCase())))
						return true;
				}else if(clientNames.toLowerCase().contains("custom")) {
					clientNames=clientNames.toLowerCase().replace("custom", test.getValue("dt:custom#1"));
					if(!(clientNames.toLowerCase().contains(client.toLowerCase())))
						return true;
				}else {
					if(!(clientNames.toLowerCase().contains(client.toLowerCase())))
						return true;
				}
			}
		}
		else if(tdd.getclientName().equalsIgnoreCase("COMMON") || clientNames.toLowerCase().contains(client.toLowerCase())){
			return true;
		}
		return false;
	}


}