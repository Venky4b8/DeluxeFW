package com.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.java.ImportnExport.ExportTestResultsExcel;
import com.java.Objects.TestExecutionDetails;
public class CreateClient{

	static String URL;
	static String clientName;
	static String browserColumn;
	static String browser;
	static String executionType;
//	static String build_ID;
	static String loginuser;
	static String auctionuser;
	static String user1;
	static String pwd1;
	
//	static String user2;
//	static String pwd2=null ;
	static String TC=null;
	public static List<String> browserNames= new ArrayList<String>();
	static String bankersDashboardLOGO="https://clients.xuat.bankersdashboard.com/assets/images/header/bdlogo.jpg";
	public static Multimap<String, HashMap<String,TestExecutionDetails>> executionStatusForAllClients = ArrayListMultimap.create();
	public static HashMap<String,HashMap<String,String>> clientReports_URLs=new HashMap<String,HashMap<String,String>>();
	public static Multimap<String, HashMap<String,String>> clientLevelDetails = ArrayListMultimap.create();
	static String consolidatedHTMLReport;
	public static String sharedPath;
	static HashMap<String,String> clientLOGO=new HashMap<String,String>();
//	public static HashMap<String,String> dailyDealCategory=new HashMap<String,String>();
	static HashMap<String,String> clientID=new HashMap<String,String>();
	static boolean executeFailedCases=false;
	static boolean isGridExecution=true;
	public static HashMap<String,String> logFiles=new HashMap<String,String>();
	public static HashMap<String,List<String>> excelReportsPaths=new HashMap<String,List<String>>();
	public static HashMap<String,Integer> testCasesCounts=new HashMap<String,Integer>();
	public static HashMap<String,String> executionStartTime =new HashMap
	<String,String>();
	public static HashMap<String,String> applicationURLs =new HashMap<String,String>();
	public static Logger log=Logger.getLogger(CreateClient.class.getName());
	static int client_Report1=0;
	static  int counter_report=0;
	public static String executionlog;
	public static String ProjPath ;
//	public static String dirPath;
	static HashMap<String,List<CreateThread>> createThreadObjects = new HashMap<String,List<CreateThread>>();
	static HashMap<String,Integer> browserObjCounter = new HashMap<String,Integer>();
	static HashMap<String,Thread> seqThreadCounter = new HashMap<String,Thread>();
	static int threadCounter;
	static List<CreateThread> objectArray = new ArrayList<CreateThread>();
	static ArrayList<String[]> browsersMap =new ArrayList<String[]>() ;
	static List<String> clientNameArray= new ArrayList<String>();
	static ArrayList<Integer> testcases =new ArrayList<Integer>();
	static List<Thread> threads = new ArrayList<Thread>();				
	static int thread_Counter=0;
	static String browserArray[];
	static HashMap<String,List<Thread>> parallelThreadCounter = new HashMap<String,List<Thread>>();
	static HashMap<String,List<String>> clientExecutionStatus=new  HashMap<String,List<String>>();
	static int clientsToExecuteParallel=0;
	public static HashMap<String,List<HashMap<String,Boolean>>> browseWiseExecutionStatus=new HashMap<String,List<HashMap<String,Boolean>>>();//<browserName,<ClientName,isCompleted>> 
	public static ArrayList<String> browserLevelReportList=new ArrayList<String>();//to save the browser names of generated browser Level report for all Clients 
	public static HashMap<String,List<HashMap<String,Boolean>>> clientWiseExecutionStatus=new HashMap<String,List<HashMap<String,Boolean>>>();//<ClientName,<browserName,isCompleted>> 
	public static ArrayList<String> clientLevelReportList=new ArrayList<String>();//to save the client names of generated client Level report for all browsers 
		
	public static void main(String[] args) throws SQLException, MessagingException, IOException, InterruptedException {
		CreateClient createClientObj=new CreateClient();
		
		if(System.getProperty("user.dir").contains("bin"))
		{
			String dirpath = System.getProperty("user.dir");
			ProjPath = dirpath.substring(0, dirpath.lastIndexOf("\\"));
			System.out.println("ProjPath:"+ProjPath);
		}else {
			ProjPath = System.getProperty("user.dir");
		}
		
		System.out.println("Execution Folder Path-----------"+ProjPath);
		
		String configPath = ProjPath+"\\TestInputs\\Config.xls";
		String testDataPath = ProjPath+"\\TestInputs\\TestData.xls";
		Connection conn=null;

		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;

		String sql2=null;
		String sql3 = null;
		String clients[] = null;	
		int browsersCount = 0;
		List<String> appUrls= new ArrayList<String>();
//		HashMap<Integer,Integer> attrMap= new HashMap<Integer,Integer>();
		String client;
//		String prgmid;
		Statement st=null,st2=null;
		Statement st3=null;

//		ArrayList<Integer> testcases1 = new ArrayList<Integer>();
		ArrayList<Integer> testcases =new ArrayList<Integer>();
		Date startDate = new Date();
		SimpleDateFormat dateFormate = new SimpleDateFormat("MMddyy_HHmmss");
		//executionlog=dateFormate.format(startDate);
		
		//Added Application Name to show on results instead of null value.
		executionlog="BankersDashBoard";
//		System.out.println("The execution log is---" +executionlog);
		Long lngTestStepExecutionStartTime = startDate.getTime();
//		System.out.println("Execution Start Time-----------"  +lngTestStepExecutionStartTime);
		
		String name = ProjPath+"\\logs\\Log"+"_"+executionlog+"_"+  dateFormate.format(new Date()) + ".log";
		FileAppender fa = new FileAppender(new PatternLayout("[%-5p][%9d] [%C] [%M] [%5L] - %m%n"),name,false);

		fa.activateOptions();
		log = Logger.getLogger(name.replace("-",""));
		log.addAppender(fa);
		log.info("Execution started");


		//To read all the attribute values from the Misc file in the properties folder
		try {
			FileInputStream inFile = new FileInputStream(ProjPath+"/properties/Misc.properties");
			Properties	miscProps=new Properties();
			miscProps.load(inFile);
			executeFailedCases =  Boolean.parseBoolean(miscProps.getProperty("executefailedcases","false"));
			isGridExecution = Boolean.parseBoolean(miscProps.getProperty("gridExecution","true"));						
			log.debug("Execute Failed Cases: "+executeFailedCases);
		}catch(Exception e){
			log.debug("Exception: "+e.getMessage());
		}

		//To read Config file "Setup" sheet
		try {	
			//Checking the command line arguments 
			if(args.length != 0) {
				System.out.println("Overwritting the Config File with the command line parameters");
				log.debug("Overwritting the Config File with the command line parameters");
				Connection con = null;
				String query;
				try {
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" +configPath+ ";DriverID=22;READONLY=false","","");
					st = con.createStatement();
					query = "Update [Setup$] set \"Test Cases to be Executed\"=" +"'"+args[0]+"'"+""+",\"Browser Type\"=" + "'"+args[1] +"'"+ ", \"Client Name\"=" +"'"+ args[2] +"'"+", \"Application URL\"="+"'"+args[3]+"'";
					System.out.println(query);
					st.executeUpdate(query);
					con.close();
				}catch(Exception e)	{
					log.debug("Exception: while updating the Config file with the command line arguments: " +e.getMessage());
					System.out.println("Error while updating the Config file with the command line arguments: "+e.getMessage());
				}
			}
			
			//To read Setup sheet in config file
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" +configPath+ ";DriverID=22;READONLY=false","","");
			String sql="Select  *  from [Setup$]";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				TC= rs.getString("Test Cases to be Executed");
				String[] tokens = TC.split(",");
				for(int i=0;i<tokens.length;i++){							    	
					if(!tokens[i].contains("-"))
						testcases.add(Integer.parseInt(tokens[i]));
					else{
						String[] range=tokens[i].split("-");
						String from = range[0];
						String to =  range[1];
						int f = Integer.parseInt(from);
						int t1 = Integer.parseInt(to);
						testcases.add(f);
						while(f!=t1){	
							f=f+1;
							int s = f;									    			
							testcases.add(s);
						}
					}
				}
				log.info("Test Cases Being executed:"+testcases);
				browserColumn = rs.getString("Browser Type");
				browsersCount=browserColumn.split(",").length;
				clientName = rs.getString("Client Name");
				log.info("Browsers:"+browserColumn);
				log.info("Clients:"+clientName);
				clients = clientName.split(",");	
//				executionType = rs.getString("Execution Type");
				executionType = "Sequential:1";
				log.info("Execution type:"+executionType);
//				build_ID=rs.getString("Build ID");
//				log.info("Build ID:"+build_ID);
//				int programCount;
				browserArray= browserColumn.split(",");
				Thread thread=new Thread();
				for(int i=0;i<browserArray.length;i++){
					browserObjCounter.put(browserArray[i], 0);
					seqThreadCounter.put(browserArray[i], thread);
				}
				for(int k=0; k<clients.length; k++) //Clients level loop
				{
					client= clients[k];//.split("-")[0];
//					
						clientNameArray.add(client);
						clientExecutionStatus.put(client, null);

						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("Client Execution Information:");
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						System.out.println("Client to be executed: " +client);
//						System.out.println("Program id: " +prgmid);
						log.info("Client to be executed: " +client);
						
						
						/*static String bankProfitCenter;
						static String bankConsolidate;*/
						

						sql3="Select  *  from [Setup$]";
						st3 = conn.createStatement();
						rs3 = st3.executeQuery(sql3);
						while(rs3.next())
						{
							URL = rs3.getString("Application URL");
							
							CreateClient.clientID.put(client.toUpperCase(), rs3.getString("Client Name"));
							appUrls.add(URL);
							System.out.println("Application URL: "+URL);
							log.info("Application URL: " +URL);
							browserArray = browserColumn.split(",");
							browsersMap.add(browserArray);
							for(int i = 0; i < browserColumn.split(",").length; i++) {
								HashMap<String,Boolean> clientRunningStatus=browseWiseExecutionStatus.get(browserArray[i].toUpperCase())==null ? new HashMap<String,Boolean>():browseWiseExecutionStatus.get(browserArray[i].toUpperCase()).get(0); 
  							    clientRunningStatus.put(clients[k].toUpperCase(), false); 
							 	List<HashMap<String,Boolean>> clientList=(browseWiseExecutionStatus.get(browserArray[i].toUpperCase())==null) ? new ArrayList<HashMap<String,Boolean>>():browseWiseExecutionStatus.get(browserArray[i].toUpperCase()); 
							 	if(clientList.size()!=0) 
							 			clientList.remove(0); 
							    clientList.add(clientRunningStatus); 
								browseWiseExecutionStatus.put(browserArray[i].toUpperCase(),clientList); 
								//-------- 
							 	HashMap<String,Boolean> browserRunningStatus=clientWiseExecutionStatus.get(clients[k].toUpperCase())==null ? new HashMap<String,Boolean>():clientWiseExecutionStatus.get(clients[k].toUpperCase()).get(0); 
    							browserRunningStatus.put(browserArray[i].toUpperCase(), false); 
    							List<HashMap<String,Boolean>> browserList=(clientWiseExecutionStatus.get(clients[k].toUpperCase())==null) ? new ArrayList<HashMap<String,Boolean>>():clientWiseExecutionStatus.get(clients[k].toUpperCase()); 
							 	if(browserList.size()!=0) 
							 			browserList.remove(0); 
							 	 browserList.add(browserRunningStatus); 
								 clientWiseExecutionStatus.put(clients[k].toUpperCase().toUpperCase(),browserList); 
								 //------------ 
								sql2="Select  *  from [Users$] where \"Client Name\" = '"+client+"'";
								st2 = conn.createStatement();
								rs2 = st2.executeQuery(sql2);
								while(rs2.next()){
									if("IE9".equalsIgnoreCase(browserArray[i])) {
										loginuser=	rs2.getString("IE9");	
									}else if("IE10".equalsIgnoreCase(browserArray[i])) {
										loginuser=	rs2.getString("IE10");										
									}else if("IE11".equalsIgnoreCase(browserArray[i])) {
										loginuser=	rs2.getString("IE11");	
									}else if("ff".equalsIgnoreCase(browserArray[i])){
										loginuser=	rs2.getString("ff");
									}else if("gchrome".equalsIgnoreCase(browserArray[i])){
										loginuser=	rs2.getString("gchrome");
									}
									user1=loginuser.split(":")[0];//.split(":")[0];
									pwd1=loginuser.split(":")[1];//.split(":")[1];
									
									CreateClient.clientLOGO.put(client.toUpperCase(), rs2.getString("Application Logo URL"));
									
									System.out.println("Browser Name: " +browserArray[i].toUpperCase()+"\nLogin UserID: " +user1+ "\nLogin Password: " +pwd1);	
									log.info("Browser Name:" +browserArray[i]+"\nLogin user:" +user1+ "\nLogin Password:" +pwd1);
								}
								CreateThread obj= new CreateThread(browserArray[i],URL,client,testcases,user1,pwd1,executionlog,lngTestStepExecutionStartTime);
								if("Parallel".equalsIgnoreCase(executionType)) {	
									Thread t=new Thread(); 
									ExecuteThread executeThreadObj = new ExecuteThread(obj);
									t= new Thread(executeThreadObj);
									log.debug("Thread name before setting name: "+t);
									t.setName("Thread_"+client+"-"+browserArray[i]);
									log.debug("Thread name: "+t);
									t.start();
									Thread.sleep(1000);
									threads.add(t); 
								}else{
									List<CreateThread> objectArray = new ArrayList<CreateThread>();
									if(createThreadObjects!=null && createThreadObjects.size()!=0 && createThreadObjects.get(browserArray[i])!=null)
										objectArray = createThreadObjects.get(browserArray[i]);
									objectArray.add(obj);
									createThreadObjects.put(browserArray[i],objectArray);
									threadCounter++;
								}								
							}							
						}
						Thread.sleep(2000);
//					}
				}
			}
			if("Parallel".equalsIgnoreCase(executionType)){
				createClientObj.joinThreads(threads, browsersCount);
				createClientObj.stopThreads(threads);
			}
			//To perform the sequential client execution
			else if("Sequential".equalsIgnoreCase(executionType.split(":")[0])) {
				try{	
					int size = clientNameArray.size();
					List<Thread> oldThreadObjects=new ArrayList<Thread>();
					List<Thread> newThreadObjects=new ArrayList<Thread>();
					if(executionType.contains(":"))
						clientsToExecuteParallel =Integer.parseInt(executionType.split(":")[1]);
					if(!executionType.contains(":") || clientsToExecuteParallel<=0)
						clientsToExecuteParallel=1;					
					if(clientsToExecuteParallel>size)
						clientsToExecuteParallel=size;
					
					//To invoke the threads in parallell as per the count given in config sheet (eg:Sequential:2)
					for(int i=0;i<browserArray.length;i++) {
						for(int j=0;j<clientsToExecuteParallel;j++) {								
							if(createThreadObjects.get(browserArray[i]).size()!=0 && browserObjCounter.get(browserArray[i])<=size) {
								oldThreadObjects= parallelThreadCounter.get(browserArray[i]);
								newThreadObjects = createClientObj.invokeClientsinParallel(browserObjCounter.get(browserArray[i]),createThreadObjects.get(browserArray[i]),browserArray[i],oldThreadObjects);
								parallelThreadCounter.put(browserArray[i],newThreadObjects);
							}									
						}						
					}
					//To iterate through the threads and will invoke new client execution once previous client execution completed.
					while(thread_Counter<threadCounter+(size*browsersCount)){
						for(int i=0;i<browserArray.length;i++) {
							for(int count =0;count<parallelThreadCounter.get(browserArray[i]).size();count++){
								  oldThreadObjects= parallelThreadCounter.get(browserArray[i]);
								  if(oldThreadObjects.get(count)!=null && !oldThreadObjects.get(count).isAlive()){
									    String threadName= oldThreadObjects.get(count).getName();
									    List<String> exeBrowsersList = new ArrayList<String>();
									    exeBrowsersList= clientExecutionStatus.get(threadName.split("_")[1]);
									    if(exeBrowsersList!=null)
									    	exeBrowsersList.add(browserArray[i]);
									    else{									    	
									    	exeBrowsersList = new ArrayList<String>();
									    	exeBrowsersList.add(browserArray[i]);
									    }
									    clientExecutionStatus.put(threadName.split("_")[1], exeBrowsersList);
									    oldThreadObjects.remove(count);
									    count=count-1;
									    if(browserObjCounter.get(browserArray[i])<size){
									    	oldThreadObjects=createClientObj.invokeClientInSequential(createThreadObjects.get(browserArray[i]),oldThreadObjects,browserObjCounter.get(browserArray[i]),browserArray[i]);
									    }
									    thread_Counter++;
									    browserObjCounter.put(browserArray[i], browserObjCounter.get(browserArray[i])+1);
									    parallelThreadCounter.put(browserArray[i],oldThreadObjects);
								  }
						    }							
						}
						generateBrowserLevelReportOnCompletion();
						createClientObj.generateClientLevelReport(browsersCount);
					}
					createClientObj.stopThreads(threads);
				}catch(Exception e){
					log.debug("Exception while invoking the threads "+e.getMessage());
				}
			}
		}catch (Exception e) {
			System.out.println("Catch block: Exception::" +e.getMessage());
			if("Parallel".equalsIgnoreCase(executionType))
				createClientObj.joinThreads(threads, browsersCount);	
			createClientObj.stopThreads(threads);
			if(counter_report==0) {
				for(int i=0;i<clientNameArray.size();i++) {	
					System.out.println("Client name is ---------" +clientNameArray.get(i));
					ExportTestResultsExcel expxl = new ExportTestResultsExcel();
					try {
						expxl.gatherMissedExecutionDetails(executionStatusForAllClients,browserNames,clientNameArray);
						expxl.exportExcelTestReport(browsersMap,clientNameArray.get(i)); 
					} catch (IOException e1) {
						log.debug("Unable to collect Missed Execution Details: "+e1.getMessage());
					}
				}
			}

		}finally {
			rs.close();
			rs2.close();
			rs3.close();
			st.close();
			st2.close();				
			st3.close();
			conn.close(); 
			TemplateGenerator tg=new TemplateGenerator();
			EmailTestReport emp=new EmailTestReport();
			tg.HtmlReport_Consolidated(executionStatusForAllClients,browserNames,clientNameArray);	
			Thread.sleep(10000);
			EmailTestReport empConsolidated=new EmailTestReport();
			empConsolidated.postMail_Consolidated(executionStatusForAllClients,browserNames,clientNameArray,"");
			log.info("Execution completed");
			System.out.println("Execution completed");
			Thread.currentThread().stop();
			log.info("Current Thread stopped");
			Runtime.getRuntime().exit(0);

		}
	}

	/*'#########################################################################################################
	'Function name		:	INVOKECLIENTINSEQUENTIAL
	'Description		:	This function is to invoke the client execution in a sequential manner (i.e once the previous client execution completed in that browser
	                        then next client execution will start)

	'Parameters			:	N/A
	'#########################################################################################################*/

	public List<Thread> invokeClientInSequential(List<CreateThread> sequentialThreadObj,List<Thread> oldThreadObjects,int browserObjCount,String browser) throws InterruptedException{
		try{
			Thread t=null;
			sequentialThreadObj = createThreadObjects.get(browser);
		    String client_prgmID = sequentialThreadObj.get(browserObjCount).client+"-"+sequentialThreadObj.get(browserObjCount).prgmID;
			ExecuteThread obj = new ExecuteThread(sequentialThreadObj.get(browserObjCount));
			t=new Thread(obj);
			log.debug("Thread name before setting name: "+t);
			t.setName("Thread_"+client_prgmID+"_"+browser);
			log.debug("Thread name: "+t);
			t.start();
			log.debug("Thread started: "+t);
			Thread.sleep(1000);
			threads.add(t);
		    oldThreadObjects.add(t);
		    thread_Counter++;
		}catch(Exception e){
			log.debug("Unable to invoke Sequential thread"+e.getMessage());
		}
		return oldThreadObjects;
	}

	public List<Thread> invokeClientsinParallel(int threadCount, List<CreateThread> executeThreadObjs,String browserName,List<Thread> oldThreadObjects) throws InterruptedException{
		try{
			Thread t=new Thread();
			ExecuteThread obj = new ExecuteThread(executeThreadObjs.get(threadCount));
			t=new Thread(obj);
			log.debug("Thread name before setting name: "+t);
			t.setName("Thread_"+executeThreadObjs.get(threadCount).client+"-"+executeThreadObjs.get(threadCount).prgmID+"_"+browserName);
			log.debug("Thread name: "+t);
			t.start();
			log.debug("Thread started: "+t);
			Thread.sleep(10000);
			threads.add(t);
			if(oldThreadObjects==null)
				oldThreadObjects=new ArrayList<Thread>();
			oldThreadObjects.add(t);
			thread_Counter++;
			browserObjCounter.put(browserName, browserObjCounter.get(browserName)+1);
			parallelThreadCounter.put(browserName, oldThreadObjects);
		}catch(Exception e){
			log.debug("Unable to invoke Sequential thread"+e.getMessage());
		}
		return oldThreadObjects;
	}

	
	
	/*'#########################################################################################################
	'Function name		:	GENERATECLIENTLEVELREPORT
	'Description		:	This function is to generate a report once the execution of the client completed in all the browsers

	'Parameters			:	N/A
	'#########################################################################################################*/

	public void generateClientLevelReport(int browsersCount) throws IOException, InterruptedException{
		try{
			for(int i=0;i<clientNameArray.size();i++){
					List<String> execCompletedClient= new ArrayList<String>();
					if(clientExecutionStatus.get(clientNameArray.get(i))!=null && clientExecutionStatus.get(clientNameArray.get(i)).size()==browsersCount){
						execCompletedClient.add(clientNameArray.get(i));
					ExportTestResultsExcel expxl = new ExportTestResultsExcel();
					System.out.println("In client level report generation block");
					expxl.gatherMissedExecutionDetails(executionStatusForAllClients,browserNames,execCompletedClient);
					expxl.exportExcelTestReport(browsersMap,execCompletedClient.get(0));
					counter_report++;
					Thread.sleep(2000);
					TemplateGenerator tg=new TemplateGenerator();
					tg.HtmlReport_ClientLevel(browsersMap,execCompletedClient,testcases);
					Thread.sleep(2000);
					EmailTestReport emp=new EmailTestReport();
					emp.postMail_clientLevel(executionStatusForAllClients,browserNames,execCompletedClient);
					clientExecutionStatus.put(clientNameArray.get(i), null);
				}
			}
		
		}catch(Exception e){
			log.debug("Exception While generating ClientLevelReport in Sequential execution"+e.getMessage());
		}
	}

	/*'#########################################################################################################
	'Function name		:	JOINTHREADS
	'Description		:	This function is to join all the threads ones the execution completed and stop the thread execution

	'Parameters			:	N/A
	'#########################################################################################################*/

	public void joinThreads(List<Thread> threads,int browsersCount){
			List<Thread> oldThreadList=new ArrayList<Thread>();
			List<Thread> newThreadList=new ArrayList<Thread>();
			
			for(int pos=0;pos<threads.size();pos++){
				oldThreadList.add(threads.get(pos));
				newThreadList.add(threads.get(pos));
			}
			while(newThreadList.size()>0){
				ListIterator litr = newThreadList.listIterator();
				while(litr.hasNext()){
					Thread curThread=(Thread) litr.next();
						if(!curThread.isAlive()){
						    String threadName= curThread.getName();
						    List<String> exeBrowsersList = new ArrayList<String>();						    
						    exeBrowsersList= clientExecutionStatus.get(threadName.split("_")[1]);
						    if(exeBrowsersList!=null)
						    	exeBrowsersList.add(threadName.split("_")[2]);
						    else{									    	
						    	exeBrowsersList = new ArrayList<String>();
						    	exeBrowsersList.add(threadName.split("_")[2]);
						    }
						    clientExecutionStatus.put(threadName.split("_")[1], exeBrowsersList);
							oldThreadList.remove(curThread);
							generateBrowserLevelReportOnCompletion();
						try{
							 generateClientLevelReport(browsersCount);
						}catch(Exception e){
							log.error("Error while generating client level report");
							log.error(e.getMessage());
						}
					}
				}
				newThreadList.clear();
				for(int pos=0;pos<oldThreadList.size();pos++){
					newThreadList.add(oldThreadList.get(pos));
				}
			}
	}

	/*'#########################################################################################################
	'Function name		:	STOPTHREADS
	'Description		:	This function is to STOP all the threads externally once the execution completed.

	'Parameters			:	N/A
	'#########################################################################################################*/

	public void stopThreads(List<Thread> threads){
		for (Thread curThread : threads) {
			try {				
				curThread.stop();
			}catch(Exception ee){
				System.out.println("Unable to kill thread");
				log.error("Unable to stop thread: "+curThread);
				log.error("Error: ",ee);
			}
		}
		System.out.println("Thread execution has finished---------------");
		log.info("Thread execution has finished---------------");
	}

	/*'#########################################################################################################
	'Function name		:	UPDATEEXECUTIONSTATUSS
	'Description		:	This function is to update the browserwise & clientwise execution status

	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public static void updateExecutionStatus(String browserName,String ClientName){
		//updating the execution completion status of browser to specific client as true

			log.debug("updating the execution completion status for: "+browserName.toUpperCase()+" : "+ ClientName.toUpperCase());
			browseWiseExecutionStatus.get(browserName.toUpperCase()).get(0).put(ClientName.toUpperCase(),true);		//{FF=[{SMART SAVINGS CLUB=true, SMART SAVINGS CLUB-9045=false}]}
			clientWiseExecutionStatus.get(ClientName.toUpperCase()).get(0).put(browserName.toUpperCase(),true); //{SMART SAVINGS CLUB-9045=[{FF=false}]}
	}
	
	
	/*'#########################################################################################################
	'Function name		:	GENERATEBROWSERLEVELREPORTONCOMPLETION
	'Description		:	This function is to generate the consolidated browser level report(i.e On completion of all the clients execution in the browser)

	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public static void generateBrowserLevelReportOnCompletion() {
		String browserName;
		for(int i=0;i<browserArray.length;i++){
			browserName=browserArray[i];
			List<HashMap<String,Boolean>> list=new ArrayList<HashMap<String,Boolean>>();
			list=browseWiseExecutionStatus.get(browserName.toUpperCase());
			
			List<String> browserNames= new ArrayList<String>();
			
			if(!list.get(0).containsValue(false)&& !browserLevelReportList.contains(browserName)){
				browserLevelReportList.add(browserName);
				browserNames.add(browserArray[i]);
				log.info("Execution Completed for Browser:"+browserArray[i]+" for All Clients i.e:"+clientNameArray);
				try{
					//ExportTestResultsExcel expxl = new ExportTestResultsExcel();
					//expxl.gatherMissedExecutionDetails(executionStatusForAllClients,Arrays.asList(browserArray[i]),Arrays.asList(clientNameArray.get(i)));
					//expxl.exportExcelTestReport(browsersMap,clientNameArray.get(i)); 
					
					TemplateGenerator tg=new TemplateGenerator();					
					tg.browserLevelReport_Consolidated(executionStatusForAllClients,browserNames,clientNameArray);
					EmailTestReport emp=new EmailTestReport();
					emp.postMail_Consolidated(executionStatusForAllClients,browserNames,clientNameArray,browserName);				
				}catch(Exception e){
					log.error("Error: While generting browser Level report for All Client:",e);
				}
			}
		}
	}
}

class CreateThread
{
	String client,browsername,appurl,user1,pwd1,user2,pwd2;
	ArrayList<Integer> testcases;
	HashMap<Integer, Integer> attributeID;
	String prgmID;
	String executionlog;
//	String build_ID;
	Long  startTime;

	/*'#########################################################################################################
	'Function name		:	CreateThread Constructor
	'Description		:	This method is to initialize the test execution details of each thread

	'Parameters			:	N/A
	'#########################################################################################################*/

	CreateThread(String browser,String URL,String Clientname,ArrayList<Integer> Tc,String user1,String password1,String executionlog,Long lngTestStepExecutionStartTime){
		client = Clientname;
		browsername=browser;
		appurl=URL;
		client=Clientname;
		testcases= Tc;
		this.user1=user1;
		pwd1=password1;
//		this.user2=user2;
//		pwd2=password2;
//		this.attributeID=attrMap;
//		this.prgmID=prgmid;
		this.executionlog =executionlog;
//		this.build_ID= build_ID;
		this.startTime=lngTestStepExecutionStartTime;
	}
}


class ExecuteThread implements Runnable
{
	CreateThread exThreadObj;

	/*'#########################################################################################################
	'Function name		:	ExecuteThread Constructor
	'Description		:	This method is to start thread execution

	'Parameters			:	N/A
	'#########################################################################################################*/
	ExecuteThread(CreateThread createThreadObj) {
		this.exThreadObj=createThreadObj;
	}
	public void run() {
		SeleniumDriver obj=new SeleniumDriver(exThreadObj);
		try {
			obj.setup();
		}catch (IOException e) {
			CreateClient.log.error(e.getMessage());
		}
	}

}


