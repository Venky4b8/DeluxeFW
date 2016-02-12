package com.java;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import com.google.common.collect.Multimap;

import com.java.Objects.TestExecutionDetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;


public class EmailTestReport
{
	
	Properties props = new Properties();
	private String smtpHostName = null;
	private String recipient = null;
	private	String reportType = null;
	private String subject = null;
	private String from = null;
	private String message = null;
	private String port = null;
	private String countersText = null;
	String testHTMLReport;
	String testResultPath;

	public EmailTestReport() {
		try {
			InputStream is= new FileInputStream(CreateClient.dirPath+"/properties/Email.properties");
			System.out.println(is);
			props.load(is);
			is.close();
		}catch(IOException io) {
            CreateClient.log.error(io.getMessage());
        }	
	}
	
	/*'#########################################################################################################
	'Function name		:	postMail_BrowserLevel
	'Description		:	This function is to send the browser level execution report email
							
	'Parameters			:	N/A
	'#########################################################################################################*/

	public void postMail_BrowserLevel(
		SeleniumDriver sd, TestExecutionDetails TED,
		String browserName, String clientName) {	
		String testResultPath = null;
		String htmlResultPath = null;
		InternetAddress[] addressTo = null;
		String line;				
		Message msg;
		try{
			sd.log.info("Sending report mail details:");
			testResultPath=TED.getExcelReportPath();
			htmlResultPath=TED.getHTMLReportPath();
			smtpHostName = props.getProperty("SMTP_HOST_NAME");
			recipient = props.getProperty("recipients");
			reportType = props.getProperty("reportType");
			from = props.getProperty("from");
			subject = props.getProperty("subject");
			message = props.getProperty("message");
			port = props.getProperty("SMTP_PORT");	
			countersText="app url"+":"+TED.getURL();
			if(sd.onFailedCaseExecution)
				subject=subject+" - "+clientName+"-"+""+" - "+browserName+" - Failed Case Execution";
			else if(sd.isFailedCaseExecuted)
				subject=subject+" - "+clientName+"-"+""+" - "+browserName+" - Consolidated ";
			else
				subject=subject+" - "+clientName+"-"+""+" - "+browserName;
			sd.log.info("Excel Report Path:"+testResultPath);
			sd.log.info("HTML Report Path:"+htmlResultPath);
			sd.log.info("smtpHostName:"+smtpHostName);
			sd.log.info("Recipient:"+recipient);
			sd.log.info("From:"+recipient);
			sd.log.info("Subject:"+subject);
			countersText = "<br></br><table style=\"background-color:#FFFFE0;\" BORDER=5 BORDERCOLOR=BLACK cellpadding=8>"+
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\">Build ID	</font></b></td><td style=\"background-color:#BDB76B;color:#ffffff;\"> <font style=\"font-weight:bold\" color=\"black\"> "+ CreateClient.build_ID + " </font></td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Client Name	</font>	</b></td><td> "+ clientName + " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Program ID	</font>	</b></td><td> "+ "" + " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Browser Executed	</font>	</b></td><td> "+ browserName + " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> URL				</font>	</b></td><td> "+ TED.getURL()		 + " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Total Cases Executed 	</font></b></td><td> "+ TED.getTotalTestCases()+ " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b>  <font style=\"font-weight:bold\" color=\"black\"> Total Cases Passed	</font>	</b></td><td style=\"background-color:lightgreen;\"> "+ TED.getPassed() + " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\">  Total Cases Failed	</font>	</b></td><td style=\"background-color:#FA0000; color:#FFFFFF;\" > "+ TED.getFailed() + " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\">  Total Cases Skipped	</font>	</b></td><td> "+ TED.getSkipped() + " </td></tr>" +
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Execution Start Time	</font></b></td><td> "+ new SimpleDateFormat("MMddyy_HHmmss").parse(TED.getStartTime()) + " </td></tr>" + 
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Execution End Time	</font></b></td><td> "+ new SimpleDateFormat("MMddyy_HHmmss").parse(TED.getEndTime())+ " </td></tr>" + 
					"</table> ";

			Properties newprops = new Properties();
			newprops.put("mail.smtp.host", smtpHostName);
			newprops.setProperty("mail.port", port);		
			Session session = Session.getDefaultInstance(newprops, null);

			if (reportType.equalsIgnoreCase("Basic")) {
				message = message.replace("of Build ID-&&buildID&&","");
				message = message.replace("&&Counters&&",countersText);
			} else if (reportType.equalsIgnoreCase("HTML")) {
				message = "";
				try {
					BufferedReader input = new BufferedReader(new FileReader(testHTMLReport));			
					while ((line = input.readLine()) != null){				
						message = message+line;
					}		
				}catch (IOException ioe) {	
					sd.log.debug("Exception: "+ioe.getMessage());
				}
			}
			msg = new MimeMessage(session);
			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);
			String[] recipientsNames = recipient.split(",");
			for(int x=0;x<recipientsNames.length;x++)
			{
				String client=recipientsNames[x].split("-")[0];
				String recipient=recipientsNames[x].split("-")[1];				
				if(client.equalsIgnoreCase(clientName))
				{
					String[] recipients = recipient.split(";");
					addressTo =new InternetAddress[recipients.length];
					for (int ii = 0; ii < recipients.length; ii++)
					{
						addressTo[ii] = new InternetAddress(recipients[ii]);
					}
				}

			}		
			if(addressTo!=null){
				msg.setRecipients(Message.RecipientType.TO, addressTo);
				msg.setSubject(subject /*+ SeleniumDriver.hMap.get("TimeStamp")*/);		
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(message, "text/html");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);	
				messageBodyPart = new MimeBodyPart();
				DataSource tResult = new FileDataSource(testResultPath);
				messageBodyPart.setDataHandler(new DataHandler(tResult));
				messageBodyPart.setFileName(testResultPath.substring(testResultPath.lastIndexOf('/')+ 1, testResultPath.length()));
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				DataSource tData = new FileDataSource(htmlResultPath);
				messageBodyPart.setDataHandler(new DataHandler(tData));
				messageBodyPart.setFileName(tData.getName());
				multipart.addBodyPart(messageBodyPart);
				msg.setContent(multipart);
				System.out.println("Sending Email...");
				Transport.send(msg);
				System.out.println("Report E-mail Sent.");
			}
			else{
				sd.log.info("No email recipients added for "+clientName+" Client in properties file");
				System.out.println("No email recipients added for "+clientName+" Client in properties file");
			}


		}catch(Exception e)	{
			sd.log.error("Unable to send email");
			sd.log.error("Error:"+e.getMessage());
			System.out.println("Error at Browser_Level mail sending "+e.getMessage());
		}

	}
	
	
	/*'#########################################################################################################
	'Function name		:	postMail_clientLevel
	'Description		:	This function is to send the client level execution report email.
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void postMail_clientLevel(
			Multimap<String, HashMap<String, TestExecutionDetails>> executionStatusForAllClients,
			List<String> browserNames, List<String> clientNames) {
		try{
			String testResultPath = null;
			String htmlResultPath = null;
			InternetAddress[] addressTo = null;

			HashSet duplicateRemove = new HashSet();
			CreateClient.log.info("------------------------------");
			CreateClient.log.info("In client level mail sending:");
			CreateClient.log.info("List of browser before removing duplicates:"+CreateClient.browserNames);
			CreateClient.browserNames.removeAll(Collections.singleton(null));
			Collections.sort(CreateClient.browserNames);
			duplicateRemove.addAll(CreateClient.browserNames);
			Collections.sort(browserNames);
			CreateClient.browserNames.clear();
			CreateClient.browserNames.addAll(duplicateRemove);
			Collections.sort(CreateClient.browserNames);
			Collections.sort(clientNames);
			CreateClient.log.info("List of browser after removing duplicates:"+CreateClient.browserNames);
			String currentClientName;
			for(String clientName:clientNames){
				CreateClient.log.info("Client Name:"+clientName);
				HashMap<String,String> clientExecutionDetails=new HashMap<String,String>();
				Collection<HashMap<String, String>> clientList=CreateClient.clientLevelDetails.get(clientName.toUpperCase());
				for(HashMap<String,String> clientDetails:clientList){
					for(String key:clientDetails.keySet()){
						clientExecutionDetails.put(key, clientDetails.get(key));
					}
				}
				CreateClient.clientReports_URLs.put(clientName.toUpperCase().toUpperCase(), clientExecutionDetails);
				CreateClient.log.info("");
				System.out.println("=========================================");
				String line;				
				Message msg;

				smtpHostName = props.getProperty("SMTP_HOST_NAME");
				recipient = props.getProperty("recipients");
				reportType = props.getProperty("reportType");
				from = props.getProperty("from");
				subject = props.getProperty("subject");
				subject=subject+" - "+clientName;
				currentClientName=clientName;
				message = props.getProperty("message");
				port = props.getProperty("SMTP_PORT");	

				CreateClient.log.info("smtpHostName :"+smtpHostName);
				CreateClient.log.info("recipient :"+recipient);
				CreateClient.log.info("reportType :"+reportType);
				CreateClient.log.info("from :"+from);
				CreateClient.log.info("subject :"+subject);
				CreateClient.log.info("message :"+message);
				CreateClient.log.info("port :"+port);

				countersText="app url"+":"+CreateClient.clientReports_URLs.get(clientName.toUpperCase()).get("URL");

				testResultPath=CreateClient.clientReports_URLs.get(clientName.toUpperCase()).get("excelReportPath");
				htmlResultPath=CreateClient.clientReports_URLs.get(clientName.toUpperCase()).get("htmlReportPath");

				try{
					System.out.println("Client Name:"+clientName);
					Collection<HashMap<String, TestExecutionDetails>> clientWiseStatus=executionStatusForAllClients.get(clientName.toUpperCase());

					HashMap<String, TestExecutionDetails> browser_Wise_Map=new HashMap<String, TestExecutionDetails>();
					ArrayList<String> browserdetails=new ArrayList<String>();
					System.out.println();
					for(HashMap<String, TestExecutionDetails> client:clientWiseStatus){
						browserdetails.addAll(client.keySet());
					}
					for(String browser:browserdetails){
						for(HashMap<String, TestExecutionDetails> client:clientWiseStatus){
							System.out.println(client.get(browser)!=null);
							if((client.get(browser)!=null))
							{
								browser_Wise_Map.put(browser, client.get(browser));
								break;
							}
						}
					}
					String browser="";
					String total="";
					String passed="";
					String failed="";
					String skipped="";
					String startTime="";
					String endTime="";
					String url="";
					HashMap<String, TestExecutionDetails> clientnames=new HashMap<String,TestExecutionDetails>();
					for(HashMap<String, TestExecutionDetails> client:clientWiseStatus){
						System.out.println(client.keySet().iterator().next());
						clientnames.put(client.keySet().iterator().next(), client.get(client.keySet().iterator().next()));
					}
					Map<String, TestExecutionDetails> treeMap = new TreeMap<String, TestExecutionDetails>(clientnames);
					ArrayList<String> browserNamesInClient=new ArrayList<String>();
					for(Entry<String, TestExecutionDetails> entry : treeMap.entrySet()){
						browserNamesInClient.add(entry.getKey());
					}

					String browserName;
					CreateClient.log.info("Browser Names in client:"+browserNamesInClient);
					for(int i=0;i<browserNames.size();i++){
						browserName=browserNames.get(i);
						CreateClient.log.info("Browser Name:"+browserName);
						if((browserNamesInClient.contains(browserName.toUpperCase()))){

							treeMap = new TreeMap<String, TestExecutionDetails>(clientnames);
							for(Entry<String, TestExecutionDetails> entry : treeMap.entrySet()) {
								if(entry.getKey().equals(browserName.toUpperCase())){

									TestExecutionDetails TED=entry.getValue();

									browser=browser+"<td > "+ browserName.toUpperCase()+ " </td>";

									url=url+"<td> "+ TED.getURL()		 + " </td>";
									total=total+"<td > "+TED.getTotalTestCases() + " </td>";
									passed=passed+"<td style=\"background-color:lightgreen;\"> "+ TED.getPassed() + " </td>";
									failed=failed+"<td  style=\"background-color:#FA0000; color:#FFFFFF;\"> "+TED.getFailed()+ " </td>";


									skipped=skipped+"<td > "+TED.getSkipped() + " </td>";

									startTime=startTime+"<td > "+new SimpleDateFormat("MMddyy_HHmmss").parse(TED.getStartTime())+ " </td>";
									endTime=endTime+"<td > "+new SimpleDateFormat("MMddyy_HHmmss").parse(TED.getEndTime())+ " </td>";

									CreateClient.log.info("Browser: "+browser);
									CreateClient.log.info("URL: "+url);
									CreateClient.log.info("Total: "+total);
									CreateClient.log.info("Passed: "+passed);
									CreateClient.log.info("Failed: "+failed);
									CreateClient.log.info("Skipped: "+skipped);
									CreateClient.log.info("StartTime: "+startTime);
									CreateClient.log.info("EndTime: "+endTime);

								}
							}
						}
						else{

							CreateClient.log.warn("RESULTS ARE MISSING FOR : "+browser+" Client:"+clientName);
							CreateClient.log.warn("Making all deatils as NOT EXECUTED");
							browser=browser+"<td > "+ browserName.toUpperCase()+ " </td>";

							url=url+"<td> "+ "NOT EXECUTED"		 + " </td>";
							total=total+"<td > "+"NOT EXECUTED" + " </td>";
							passed=passed+"<td style=\"background-color:lightgreen;\"> "+ "NOT EXECUTED" + " </td>";
							failed=failed+"<td  style=\"background-color:#FA0000; color:#FFFFFF;\"> "+"NOT EXECUTED"+ " </td>";

							System.out.println("Skipped:"+"NOT EXECUTED");
							skipped=skipped+"<td > "+"NOT EXECUTED" + " </td>";

							startTime=startTime+"<td > "+"NOT EXECUTED"+ " </td>";
							endTime=endTime+"<td > "+"NOT EXECUTED"+ " </td>";
						}
					}
					countersText = "<br></br><table style=\"background-color:#FFFFE0;\" BORDER=5 BORDERCOLOR=BLACK cellpadding=8>"+
					"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\"> Build ID		</font></b></td><th style=\"background-color:#BDB76B;style=\"font-weight:bold\"color:#ffffff;\" cellpadding=8 colspan=\""+browserNames.size()+"\"> "+ CreateClient.build_ID + " </th></tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\"> Client Name		</font></b></td><th style=\"background-color:#BDB76B;style=\"font-weight:bold\"color:#ffffff;\" cellpadding=8 colspan=\""+browserNames.size()+"\"> "+ currentClientName + " </th></tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Browser Executed		</font></b></td> "+ browser + " </tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> URL				</font>	</b></td> "+ url		 + " </tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\">  Total Cases Executed 	</font></b></td> "+ total+ " </tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Total Cases Passed		</font></b></td >"+  passed+ " </tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Total Cases Failed		</font></b></td> "+ failed + " </tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\">  Total Cases Skipped	</font>	</b></td> "+ skipped+ " </tr>" +
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Execution Start Time	</font></b></td> "+ startTime+ "</tr>" + 
							"<tr><td style=\"background-color:#BDB76B;color:#ffffff;\"><b> <font style=\"font-weight:bold\" color=\"black\"> Execution End Time	</font></b></td> "+ endTime+ " </tr>" + 
							"</table> ";

					Properties newprops = new Properties();
					newprops.put("mail.smtp.host", smtpHostName);
					newprops.setProperty("mail.port", port);		
					Session session = Session.getDefaultInstance(newprops, null);

					if (reportType.equalsIgnoreCase("Basic")) {
						message = message.replace("of Build ID-&&buildID&&","");
						message = message.replace("&&Counters&&",countersText);
					} else if (reportType.equalsIgnoreCase("HTML")) {
						message = "";
						try {
							BufferedReader input = new BufferedReader(new FileReader(testHTMLReport));			
							while ((line = input.readLine()) != null){				
								message = message+line;
							}		
						} catch (IOException ioe) {	
							CreateClient.log.debug("Exception: "+ioe.getMessage());
						}
					}
					msg = new MimeMessage(session);

					// set the from and to address
					InternetAddress addressFrom = new InternetAddress(from);
					msg.setFrom(addressFrom);

					String[] recipientsNames = recipient.split(",");
					for(int x=0;x<recipientsNames.length;x++)
					{
						String client=recipientsNames[x].split("-")[0];
						String recipient=recipientsNames[x].split("-")[1];				

						if(client.equalsIgnoreCase(clientName.split("-")[0]))
						{
							System.out.println("Client name: " +client);
							System.out.println("Receipt name: "+recipient);
							String[] recipients = recipient.split(";");
							addressTo =new InternetAddress[recipients.length];
							for (int ii = 0; ii < recipients.length; ii++)
							{
								addressTo[ii] = new InternetAddress(recipients[ii]);
							}
						}

					}			
					msg.setRecipients(Message.RecipientType.TO, addressTo);	
					msg.setSubject(subject /*+ SeleniumDriver.hMap.get("TimeStamp")*/);		
					BodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setContent(message, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);	
					messageBodyPart = new MimeBodyPart();
					DataSource tResult = new FileDataSource(testResultPath);
					messageBodyPart.setDataHandler(new DataHandler(tResult));
					messageBodyPart.setFileName(testResultPath.substring(testResultPath.lastIndexOf('/')+ 1, testResultPath.length()));
					multipart.addBodyPart(messageBodyPart);
					messageBodyPart = new MimeBodyPart();
					DataSource tData = new FileDataSource(htmlResultPath);
					messageBodyPart.setDataHandler(new DataHandler(tData));
					messageBodyPart.setFileName(tData.getName());
					multipart.addBodyPart(messageBodyPart);

					msg.setContent(multipart);
					System.out.println("Sending Email...");
					Transport.send(msg);
					System.out.println("Report E-mail Sent.");
					System.out.println();

				}catch(Exception e) {
					System.out.println("Error @ sending Client Level Mail:");
					CreateClient.log.error("Error @ sending Client Level Mail:");
					CreateClient.log.error("Error:",e);
				}
			}

		}catch(Exception e) {
			System.out.println("Unable to send Client Level Mail reprot");
			System.out.println("Error:"+e.getMessage());
			CreateClient.log.error("Unable to send Client Level Mail reprot");
			CreateClient.log.error("Error:",e);
		}

	}


	/*'#########################################################################################################
	'Function name		:	postMail_Consolidated
	'Description		:	This function is to send the consolidated execution report email
							
	'Parameters			:	N/A
	'#########################################################################################################*/
	
	public void postMail_Consolidated(
			Multimap<String, HashMap<String, TestExecutionDetails>> executionStatusForAllClients,
			List<String> browserNames, List<String> clientNames,String browserName){

		CreateClient.log.debug("Sending Consildated report mail for clients");
		String browers_Tag="";
		String totalPassFail_Tag="";
		String ClientWiseTestStatus_Tag="";
		String browserWiseTestStaus_Tag="";
		String URL="";
		String line;				
		Message msg;
		InternetAddress[] addressTo = null;
		smtpHostName = props.getProperty("SMTP_HOST_NAME");
		recipient = props.getProperty("recipients");
		reportType = props.getProperty("reportType");
		from = props.getProperty("from");
		subject = props.getProperty("subject");
		if(browserName.length()>0){ 
	 						subject=subject+" - "+browserName.toUpperCase(); 
				}
		
		message = props.getProperty("message");
		port = props.getProperty("SMTP_PORT");	
		List<String> browserList= new ArrayList<String>();
		List<String> browserDiff= new ArrayList<String>();
		Collections.sort(browserNames);
		Set<String> browserNamesset = new HashSet<String>();  
		browserNamesset.addAll(browserNames); 
		browserNames.clear();
		browserNames.addAll(browserNamesset);

		try{
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
				for(int i=0;i<browserNames.size();i++){
					browser=browserNames.get(i);

					if((browserNamesInClient.contains(browser.toUpperCase()))){

						treeMap = new TreeMap<String, TestExecutionDetails>(clientnames);
						for(Entry<String, TestExecutionDetails> entry : treeMap.entrySet()) {
							if(entry.getKey().equals(browser.toUpperCase())){
								TestExecutionDetails TED=entry.getValue();

								if((URL==null) || URL.equals(""))
									URL=TED.getURL();

								if(!(TED.getHTMLSharedPath()==null))
									browserWiseTestStaus_Tag=browserWiseTestStaus_Tag+"<td>"+TED.getTotalTestCases()+"</td>"+"<td>"+TED.getPassed()+"</td>"+"<td>"+TED.getFailed()+"</td>"+"<td>"+(TED.getTotalTestCases()-TED.getTotalExecuted())+"</td>"+"<td><a href=\"file:///"+TED.getHTMLSharedPath()+"\" target=\"_blank\">html</a> | <a href=\"file:///"+TED.getLogFile()+"\" target=\"_blank\"> log </a></td>";
								else
									browserWiseTestStaus_Tag=browserWiseTestStaus_Tag+"<td>"+TED.getTotalTestCases()+"</td>"+"<td>"+TED.getPassed()+"</td>"+"<td>"+TED.getFailed()+"</td>"+"<td>"+(TED.getTotalTestCases()-TED.getTotalExecuted())+"</td>"+"<td>Execution terminated unexpectedly</td>";

								break;
							}

						}
					}
					else {

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
			}
			String countersText = "<br></br><table   style=\"background-color:#FFFFE0;\" align: center; BORDER=5 BORDERCOLOR=BLACK cellpadding=12>"+

						"<tr> <th  rowspan=\"2\" style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Client</font></b></th>	" +
						"	  <th rowspan=\"2\" style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >URL</font></b></th>"	+
						"	  <th rowspan=\"2\" style=\"background-color:#BDB76B;color:#ffffff;\" ><b> <font style=\"font-weight:bold\" color=\"black\" >Integrated Report</font></b></th>"	
						+browers_Tag+"</tr>"+"<tr>"+totalPassFail_Tag+" </tr>"+ClientWiseTestStatus_Tag+

						"</table> ";
			Properties newprops = new Properties();
			newprops.put("mail.smtp.host", smtpHostName);
			newprops.setProperty("mail.port", port);		
			Session session = Session.getDefaultInstance(newprops, null);

			if (reportType.equalsIgnoreCase("Basic")) {
				message = message.replace("&&buildID&&",CreateClient.build_ID);
				message = message.replace("&&Counters&&",countersText);
			} else if (reportType.equalsIgnoreCase("HTML")) {
				message = "";
				try {
					BufferedReader input = new BufferedReader(new FileReader(testHTMLReport));			
					while ((line = input.readLine()) != null){				
						message = message+line;
					}		
				} catch (IOException ioe) {	
					CreateClient.log.debug("Exception: "+ioe.getMessage());
				}
			}
			msg = new MimeMessage(session);

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			String[] recipientsNames = recipient.split(",");
			for(int x=0;x<recipientsNames.length;x++)
			{
				String client=recipientsNames[x].split("-")[0];
				String recipient=recipientsNames[x].split("-")[1];				

				if(client.equalsIgnoreCase("All"))
				{
					System.out.println("Client name: " +client);
					System.out.println("Receipt name: "+recipient);
					String[] recipients = recipient.split(";");
					addressTo =new InternetAddress[recipients.length];
					for (int ii = 0; ii < recipients.length; ii++)
					{
						addressTo[ii] = new InternetAddress(recipients[ii]);
					}
				}

			}			
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			msg.setSubject(subject /*+ SeleniumDriver.hMap.get("TimeStamp")*/);		
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);	

			messageBodyPart = new MimeBodyPart();
			DataSource tData = new FileDataSource(CreateClient.consolidatedHTMLReport);
			messageBodyPart.setDataHandler(new DataHandler(tData));
			messageBodyPart.setFileName(tData.getName());
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);
			System.out.println("Sending Email...");
			Transport.send(msg);

			System.out.println("Report E-mail Sent.");
			
		}catch(Exception e) {
			CreateClient.log.error("Exception while sending consilidated report mail ");
			CreateClient.log.error("Error:",e);
			System.out.println("Error:"+e.getMessage());		}
	}
}


