package com.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.seleniumhq.jetty7.util.log.Log;
import org.testng.Assert;
import org.testng.AssertJUnit;

import com.java.ImportnExport.ImportConfigDetailsExcel;
import com.java.ImportnExport.ImportTestDataDetailsExcel;
import com.java.Objects.ResultDetails;
import com.java.Objects.TestDataDetails;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class TestType {

	String browserType = null;
	String strScreenshotName = "";
	public static ArrayList<Integer> testCaseID = new ArrayList<Integer>();
	SeleniumDriver sd;
	public TestType(SeleniumDriver sd) {
		this.sd=sd;
	}

	ResultDetails resultDetails = new ResultDetails();

	public enum DataFileds {TXT,RDB,COB,CHK,SLB,BTN,LNK,CNF,XPH,CBS,DBV,TTL,ALT,MSG,WND,IMG,GET,TBL,EDT,LST,NAM,URL,ELM,CMP,HMV, CRT, PRC, DDL, DEX, DDC, INV, EST, CSS, LOV, CRO, ACT, JSC, CTX, DTL, ATN, ALR, REG, CRD, PHN, GTL, GCH, ENT, GTR, ZER, CSH, GTZ, ZRO, LTZ};

	public enum SelectDataFileds {RDB, COB, SLB, WND};

	public enum ClickDataFileds {BTN,LNK,CNF,XPH, IMG,PLT, HDN, SWC, SWK, SCK, TCK};

	public enum CheckDataFileds {CHK};

	public enum EnterDataFileds {TXT,BTN,EDT, RDN, STV, PRM, CRT, CID, JSC, CEN}; //EDT is Text Editor

	public enum WaitForFields {IMG, TTL, BTN, LNK, COB, MSG,TXT,XPH};

	public enum SelectWindow {TTL};

	public enum CloseWindow {TTL};

	public enum goBack {TTL};

	public enum order{CNF,CNL, AWB};

	public enum points{DEC,INC};

	public enum Tables {TBL};

	public enum ActionTypes {GMAIL,VERIFY,REMOVEITEMS,VERIFYURL,REWARDPOINTS,VERIFYMANDATORYFIELDS,GREATERTHAN,VERIFYPRESENT,VERIFYNOTPRESENT,VERIFYCONTINUE,SELECT,SELECTFRAME,SELECTANDWAIT,CLICK, CLICKANDWAIT,CHECK,UNCHECK,ENTER,CLEARANDENTER,WAITFORELEMENT,SELECTWINDOW,CLOSEWINDOW,GOBACK,ISDISABLED,ISENABLED, STOREVALUE,STOREATTRIBUTE,VERIFYATTRIBUTE,VERIFYVALUEINROW,WAITTIME,OPENURL,MOUSEOVER,SELECTIFRAMEBYINDEX,SELECTFRAMEBYNAME,SELECTPARENTPAGE,EXECUTETESTCASE,SELECTOPTIONBYPOSITION,STORERNDNAME,TRAINING,SWITCHTODEFAULT, REMOVEALLITEMS, PROMONAME, VERIFYGRIDCOUNT, VERIFYSORTINGORDER, SETPROMODETAILS, VERIFYREWARDPROMOPOINTS, VERIFYCARTCOUNT, CALCULATION,SETDATE, VERIFYSAVINGS, VERIFYTEXTNOTPRESENT, DELETECOOKIES, CANCELORDER, ORDERSPLIT, SEARCHCATEGORY,FINDDAILYDEALTOEXPIRE, FLUSHMEMCACHE,VERIFYADDTOCARTBTN, SETTIME, CHECKYOUPAY, POSTDEDUCTIONPOINTS, CLICKHIDDEN, REMOVEADDRESSESS, UPDATEVALUE, RELOADAUCTION, CLICKAUCTIONPRODUCT, VERIFYAUCTIONTIMEOUT, SELECTAUCTION, INCBIDAMOUNT, SEARCHITEMSCOUNT,VERIFYAUCTIONAVAILABLE, URLUPDATE,AUCTIONADMINVIEWCLICK, CANCELPRINT, VERIFYAUCTIONTIMEEXTENDS, VERIFYSEARCHHEADER, ACTIVITIESVIEWPRICING, CLICKTABLEOBJECT, VERIFYTABLE, PAGERELOAD, CLEAR, CATALOGUPLOAD, AJAXCLICK, URLNAVIGATION, SELECTBUDGETWINDOW, CERTIFICATIONERR, SAVEUSERS, GENERATEUSERNAME, MASKINGVERIFICATION, VERIFYSEEDETAILSBUTTON, REWARDSCASH, COMPAREREWARDSCASH, VALIDATEREWARDSCASH, ENTERREWARDSCASH};

	/*'#########################################################################################################
	'Function name		:	performAction
	'Description		:	This function is to perform the specific action on the web page

	'Parameters			:	N/A
	'#########################################################################################################*/

	public ResultDetails performAction(WebDriver webdriver, String fieldText, String value, String actionType, String fieldName,String browser,String user1,String password1,String user2,String password2) {
		try {
			ActionTypes actTypes = ActionTypes.valueOf(actionType.toUpperCase());
			switch (actTypes) {

			case AUCTIONADMINVIEWCLICK:
				resultDetails = auctionAdminViewClick(webdriver, fieldText,value);
				break;
			case VERIFY:
				resultDetails = verify(webdriver, fieldText, value, fieldName);
				break;
			case VERIFYNOTPRESENT:
				resultDetails = verifyNotPresent(webdriver, fieldText, value);
				break;
			case VERIFYPRESENT:
				resultDetails = verifyPresent(webdriver, fieldText, value);
				break;
			case UPDATEVALUE:
				resultDetails =updateValue(webdriver, fieldText, value);
				break;				
			case MASKINGVERIFICATION:
				resultDetails = maskingVerfication(webdriver, fieldText);
				break;
			case VERIFYCONTINUE:
				resultDetails = verifyContinue(webdriver, fieldText, value, fieldName);
				break;
			case VERIFYSEARCHHEADER:
				resultDetails = verifySearchHeader(webdriver, fieldText, value);
				break;
			case GENERATEUSERNAME:
				resultDetails =generateUserName(webdriver, fieldText, value);
				break;
			case ACTIVITIESVIEWPRICING:
				resultDetails = activitiesViewPricing(webdriver, value);
				break;				
			case SELECTAUCTION:
				resultDetails = selectAuction(webdriver, fieldText, value);
				break;				
			case INCBIDAMOUNT:
				resultDetails = increaseBitAmount(webdriver,fieldText, value);
				break;
			case SETDATE:
				resultDetails = setDate(webdriver, fieldText, value);
				break;
			case CLICKAUCTIONPRODUCT:
				resultDetails = auctionsProductClick(webdriver, fieldText, value);
				break;
			case VERIFYAUCTIONTIMEEXTENDS:
				resultDetails = verifyAuctionTimeExtends(webdriver, fieldText, value);
				break;
			case SELECT:
				resultDetails = select(webdriver, fieldText, value);
				break;
			case SELECTFRAME:
				resultDetails = selectFrame(webdriver, fieldText, value);
				break;
			case VERIFYAUCTIONAVAILABLE:
				resultDetails = verifyAuctionAvailable(webdriver, fieldText, value);
				break;
			case SELECTANDWAIT:
				resultDetails = select(webdriver, fieldText, value);
				WebDriverUtils.waitForPageToLoad(webdriver, "40000");
				break;
			case CLICK:
				resultDetails = click(webdriver, fieldText, value, fieldName);
				break;
			case CLICKANDWAIT:
				resultDetails = click(webdriver, fieldText, value, fieldName);					
				WebDriverUtils.waitForPageToLoad(webdriver, value);
				break;
			case SELECTBUDGETWINDOW:
				resultDetails= selectBudgetWindow(webdriver, fieldText, value);
				break;
			case CHECK:
				String fieldType1 = fieldText.substring(0, 3);
				fieldType1 = fieldType1 + "C";
				fieldText = fieldType1 + fieldText.substring(3, fieldText.length());
				resultDetails = checkOrUncheck(webdriver, fieldText, value, fieldName);
				break;
			case RELOADAUCTION:
				resultDetails = reloadAuction(webdriver, fieldText);
				break;
			case UNCHECK:
				String fieldType2 = fieldText.substring(0, 3);
				fieldType2 = fieldType2 + "U";
				fieldText = fieldType2 + fieldText.substring(3, fieldText.length());
				resultDetails = checkOrUncheck(webdriver, fieldText, value, fieldName);
				break;
			case ENTER:
				resultDetails = enter(webdriver, fieldText, value);
				break;
			case CLICKTABLEOBJECT:
				resultDetails = clickTableObject(webdriver, fieldText, value, fieldName);
				break;
			case VERIFYTABLE:
				resultDetails = verifyTable(webdriver, fieldText, value, fieldName);
				break;
			case CLEARANDENTER:
				resultDetails = clearAndEnter(webdriver, fieldText, value,browser);
				break;
			case WAITFORELEMENT:
				resultDetails = waitForElement(webdriver, fieldText, value);
				break;
			case CATALOGUPLOAD:
				resultDetails = catalogUpload(webdriver);
				break;				
			case URLUPDATE:
				WebDriverUtils.waitForPageToLoad(webdriver,"10000");
				String currentUrl=webdriver.getCurrentUrl().split("com/")[0];
				String afterUpdating=currentUrl+"com/gateway?t=loadprogramattributes&code=rewards1";				
				try {
					webdriver.get(afterUpdating); //url
					resultDetails.setFlag(true);
				}catch(Exception e) {
					sd.log.info("exception value : " + e.getMessage());
					resultDetails.setFlag(false); 
					resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
			case SELECTWINDOW:
				resultDetails = selectWindow(webdriver, fieldText, value);
				break;
			case CLICKHIDDEN:
				resultDetails = clickHidden(webdriver, fieldText);
				break;
			case POSTDEDUCTIONPOINTS:
				resultDetails = postDeductionPoints(webdriver, fieldText,value);
				break;
			case CLOSEWINDOW:
				resultDetails = closeWindow(webdriver, fieldText, value);
				break;
			case FINDDAILYDEALTOEXPIRE:
				resultDetails = findDailyDealToExpire(webdriver);
				break;
			case CLEAR:
				resultDetails = clear(webdriver, fieldText, value,browser);
				break;	
			case GOBACK:				
				try{
					webdriver.navigate().back();
					WebDriverUtils.waitForPageToLoad(webdriver, "30000");
					resultDetails.setFlag(true);
				}catch (Exception e) {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
					return resultDetails;
				}			
				break;
			case ISDISABLED:
				resultDetails = isDisabled(webdriver, fieldText);
				break;
			case ISENABLED:
				resultDetails = isEnabled(webdriver, fieldText);
				break;
			case STOREVALUE:
				resultDetails = storeValue(webdriver, fieldText, value, fieldName);
				break;
			case STOREATTRIBUTE:
				resultDetails =storeAttribute(webdriver, fieldText, value);
				break;
			case VERIFYATTRIBUTE:
				resultDetails =verifyAttribute(webdriver, fieldText, value);
				break;				
			case SEARCHITEMSCOUNT:
				resultDetails = searchItemsCount(webdriver, value);
				break;
			case VERIFYVALUEINROW:
				resultDetails =verifyValueInRow(webdriver, fieldText, value, fieldName);
				break;		
			case WAITTIME:
				resultDetails =waitTime(webdriver, value);
				break;		
			case OPENURL:
				value = getValue(value);
				if(value==null){
					value=fieldText;
				}
				sd.log.info("Open URL : " + value);
				try {
					webdriver.get(value); //url
					resultDetails.setFlag(true);
				}catch (Exception e) {
					sd.log.error("exception value : " + e.getMessage());
					resultDetails.setFlag(false);					
					resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
			case MOUSEOVER:
				sd.log.info("Field ::" + fieldText);
				String field = fieldText.substring(3, fieldText.length());
				try{
					WebElement myElement = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field));
					sd.log.info(myElement);
					Actions builder = new Actions(webdriver);
					builder.moveToElement(myElement).build().perform();
					Thread.sleep(500);
					resultDetails.setFlag(true);
				}catch (Exception e) {				
					sd.log.error("exception value : " + e.getMessage());
					resultDetails.setFlag(false);					
					resultDetails.setErrorMessage("Element: " + field + " is not found");
				}	
				break;
			case PAGERELOAD:
				webdriver.navigate().refresh();
				resultDetails.setFlag(true);
				break;	
			case VERIFYMANDATORYFIELDS:
				resultDetails = verifyMandatoryFields(webdriver, fieldText, value);
				break;
			case REWARDPOINTS:
				resultDetails = rewardsPoints(webdriver, fieldText, value);
				break;
			case REMOVEITEMS:
				resultDetails = removeItems(webdriver);
				break;
			case REMOVEALLITEMS:
				resultDetails =removeAllItems(webdriver);
				break;
			case SELECTIFRAMEBYINDEX:
				resultDetails = selectIframeByIndex(webdriver, value);
				break;
			case SELECTFRAMEBYNAME:
				resultDetails = selectFrameByName(webdriver, fieldText, value);
				break;
			case SELECTPARENTPAGE:
				resultDetails = selectParentPage(webdriver);
				break;	
			case CANCELORDER:
				resultDetails = cancelorder(webdriver, fieldText, value);
				break;
			case CERTIFICATIONERR:
				resultDetails = handleBrowserCertficationError(webdriver,browser);
				break;
			case REMOVEADDRESSESS:
				resultDetails = removeAddresses(webdriver,fieldText);
				break;
			case FLUSHMEMCACHE:
				try{
					Runtime.getRuntime().exec("cmd /c start C:\\InvokePythonBatch.bat");
					resultDetails.setFlag(true);					
				}catch(Exception e){
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Mem cache Flush not happened");
					return resultDetails;					
				}
				break;
			case EXECUTETESTCASE:
				try {
					HashMap<Integer,TestDataDetails> Rows =  new HashMap<Integer,TestDataDetails>();
					ArrayList<String> result = new ArrayList<String>();	
					//Concept#<TC ID>:from-to
					String concept = value.split("#")[0];
					int TCID = Integer.parseInt((value.split("#")[1]).split(":")[0]);						
					int from = Integer.parseInt(((value.split("#")[1]).split(":")[1]).split("-")[0]);					
					int to = Integer.parseInt(((value.split("#")[1]).split(":")[1]).split("-")[1]);
					System.out.println("##########################################################");
					System.out.println("The Concept sheet name is ---------- " +concept);
					sd.log.info("The Concept sheet name is ---------- " +concept);
					System.out.println("The Concept ID  is ---------- " +TCID);
					sd.log.info("The Concept ID  is ---------- " +TCID);
					System.out.println("Steps being executed in the Concept are ---------- "+from+":"+to);
					sd.log.debug("Steps being executted in the Concept are: "+from+":"+to);
					ResultSet rs;
					String xlsPath = ImportConfigDetailsExcel.TestInputsPath+"\\TestInputs\\"+sd.hMap.get("testDataFile");
					System.out.println("Test data details path ---------- "+xlsPath);
					sd.log.info("Test data details path ---------- "+xlsPath);
					System.out.println("##########################################################");
					//Connection to excel sheet as database
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+xlsPath+";DriverID=22;READONLY=false","","");
					String sql="Select * from ["+concept+"$]Where \"Test Case ID\" = "+TCID+" Order by \"Test Priority\"";
					Statement st = conn.createStatement();
					rs = st.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();
					int numberOfColumns = rsmd.getColumnCount();
					//Loop through the columns in each row.
					int rscount=0;
					while (rs.next()) {
						TestDataDetails tdd=new TestDataDetails();
						int caseId = 0;
						int dataId = 0;
						rscount = rscount+1;
						for (int c = 1; c <= numberOfColumns; c++) {
							switch (rsmd.getColumnType(c)) {
							//Assigning each cell value in a row to a bean based on the type of value
							case Types.BOOLEAN :	
								break;
							case 8 : // cell type numeric.
								int str = rs.getInt(c);
								sd.log.info(" str("+c+") = "+str);
								if(c==1){
									caseId = str;
									sd.log.info("Test case Id is---"+caseId);
									tdd.setTestCaseID(str);
									sd.log.info(tdd.getTestCaseID());
								}if(c==2) {
									dataId = str;
									tdd.setTestDataID(str);
								}if(c==5)
									tdd.setDataFields(str+"");
								if(c==6)
									tdd.setDataValues(str+"");															
								break;
							case 12 :	// cell type string.
								String strValue = rs.getString(c);
								if(c==3)
									tdd.setTestCaseTitle(strValue);
								if(c==4)
									tdd.setWorkingPage(strValue);
								if(c==5)
									tdd.setDataFields(strValue);	
								if(c==6)
									tdd.setDataValues(strValue);
								if(c==7)
									tdd.setActionType(strValue);
								if(c==8)
									tdd.setCondition(strValue);
								if(c==9)
									tdd.setBrowserType(strValue.toUpperCase());							
								if(c==10)
									tdd.setclientName(strValue.toUpperCase());								
								if(c==11) 
									tdd.setFieldName(strValue);								
								break;
							case Types.NULL:
								if(c==9)
									tdd.setBrowserType("COMMON");							
								if(c==10)
									tdd.setclientName("COMMON");
								if(c==11) 
									tdd.setFieldName("NONE");											
								break;
							case Types.OTHER:
								break;					           		
							default :			
								break;
							}
						}
						Rows.put(rscount,tdd);
						sd.log.info("row values are--------" +Rows);
					}
					resultDetails=executeSteps(webdriver,from,to,1,TCID, Rows, resultDetails, result,browser,user1,password1,user2,password2);
					rs.close();
					st.close();
					conn.close();
				}catch (Exception e) {
					sd.log.debug("exception value : " + e.getMessage());
					resultDetails.setFlag(false);					
					resultDetails.setErrorMessage("Execute Test case failed.");					
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
			case SELECTOPTIONBYPOSITION:
				resultDetails = selectOptionByPosition(webdriver, fieldText, value);
				break;
			case STORERNDNAME:
				resultDetails = storeRndName(webdriver, fieldText, value);
				break;
			case VERIFYURL:		
				resultDetails = verifyURL(webdriver, fieldText, value);
				break;
			case GMAIL:
				webdriver.get("https://www.gmail.com");                       
				Thread.sleep(60000);
				String valueArray[]=value.split("::");
				for(int i=0;i<valueArray.length;i++){
					valueArray[i]=getValue(valueArray[i]);
				}
				try{
					webdriver.findElement(By.id("gmail-sign-in")).click();
					sd.log.debug("Clicked on gmail-sign-in link");
				}catch(Exception e){
					sd.log.debug("Exception in Gmail opening- Click on signin link failed"+e.getMessage());
				}
				try{
					webdriver.findElement(By.id("account-chooser-link")).click();
					Thread.sleep(10000);
					webdriver.findElement(By.id("account-chooser-add-account")).click();
					sd.log.debug("Clicked on sign-in with different user link");
				}
				catch(Exception e){
					sd.log.debug("Exception in Gmail opening- Click on sign-in with different user link failed"+e.getMessage());
				}
				try{
					webdriver.findElement(By.id("Email")).sendKeys(valueArray[0]);
					webdriver.findElement(By.id("Passwd")).sendKeys(valueArray[1]);
					webdriver.findElement(By.id("signIn")).click();
				}catch(Exception e){
					try{
						webdriver.findElement(By.id("Passwd")).sendKeys(valueArray[1]);
						webdriver.findElement(By.id("signIn")).click();}
					catch(Throwable e1){
						sd.log.debug("Exception in Gmail opening"+e.getMessage());
					}
				}
				Thread.sleep(10000);
				resultDetails = gmail(webdriver, fieldText,valueArray[2]);
				break;
			case CHECKYOUPAY:
				resultDetails = checkYouPay(webdriver, fieldText,value);
				break;
			case VERIFYADDTOCARTBTN:
				resultDetails = clickAddToCartButton(webdriver, fieldText);
				break;	
			case VERIFYAUCTIONTIMEOUT:
				resultDetails = verifyAuctionTimeout(webdriver, fieldText);
				break;	
			case SWITCHTODEFAULT:
				resultDetails = switchtodefault(webdriver);
				break;
			case VERIFYGRIDCOUNT:
				try {					
					String itemsPerPage= webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,value)).getText();				
					int expectedItems= Integer.parseInt(itemsPerPage.split("-")[1].split("of")[0].trim());
					System.out.println("The expected number of items per page are"+expectedItems);
					AssertJUnit.assertEquals(expectedItems,webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, fieldText)).size());
					resultDetails.setFlag(true);
				}catch (AssertionError e) {
					sd.log.error("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.toString());
					return resultDetails;
				}catch (Exception e) {
					sd.log.error("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
					return resultDetails;
				}
				break;
			case VERIFYSORTINGORDER:
				String str = value;
				try {
					for (int i = 1; i <=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldText.split("::")[0])).size()- 1; i++) {
						String str1 =webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,(fieldText.split("::")[0]+"["+i + "]"+fieldText.split("::")[1]))).getText();
						if(str1.contains(",")) {
							str1=str1.replace(",","");
						}
						String str2 =webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split("::")[0]+"["+(i + 1) +"]"+ fieldText.split("::")[1])).getText();
						if(str2.contains(",")) {
							str2=str2.replace(",","");
						}
						sd.log.info("In Verify sorting order the two strings are: "+str1 +"-----" + str2);
						if(str.equalsIgnoreCase("Name A to Z")) {
							if(str1.compareTo(str2) > 0)
								AssertJUnit.assertFalse(true);
						} else if(str.equalsIgnoreCase("Name Z to A")) {
							if(str1.compareTo(str2) < 0)
								AssertJUnit.assertFalse(true);
						} else if(str.equalsIgnoreCase("You Pay - Low to High")) {
							if(Float.parseFloat(str1.substring(1))>Float.parseFloat(str2.substring(1)))
								AssertJUnit.assertFalse(true);
						} else if(str.equalsIgnoreCase("You Pay - High to Low")) {
							if(Float.parseFloat(str1.substring(1))<Float.parseFloat(str2.substring(1)))
								AssertJUnit.assertFalse(true);
						} else if(str.equalsIgnoreCase("Xtra Points - Low to High")||str.equalsIgnoreCase("Points - Low to High")||str.equalsIgnoreCase("Savings Dollars - Low to High")||str.equalsIgnoreCase("Reward Dollars - Low to High")||str.equalsIgnoreCase("Rewards CashSM - Low to High")) {
							if(Float.parseFloat(str1.substring(1))>Float.parseFloat(str2.substring(1)))
								AssertJUnit.assertFalse(true);
						} else if(str.equalsIgnoreCase("Xtra Points - High to Low")||str.equalsIgnoreCase("Points - High to Low")||str.equalsIgnoreCase("Savings Dollars - High to Low")||str.equalsIgnoreCase("Reward Dollars - High to Low")||str.equalsIgnoreCase("Rewards CashSM - High to Low")) {
							if(Float.parseFloat(str1.substring(1))<Float.parseFloat(str2.substring(1)))
								AssertJUnit.assertFalse(true);
						}
					}
					resultDetails.setFlag(true);
				}catch (AssertionError e) {
					sd.log.info("Assertion order : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.toString());
					return resultDetails;
				}catch (Exception e) {
					sd.log.error("exception value : " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.toString());
					return resultDetails;
				}
				break;
			case DELETECOOKIES:
				try{
					webdriver.manage().deleteAllCookies();
					resultDetails.setFlag(true);
				}catch(Exception e) { 
					sd.log.error("Unable to delete cookies: "+e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(e.getMessage());
				}
				break;
			case SETPROMODETAILS:      
				resultDetails = setPromoDetails(webdriver);
				break;
			case VERIFYREWARDPROMOPOINTS:
				resultDetails = verifyRewardPromoPoints(webdriver,fieldText, value);
				break;
			case VERIFYCARTCOUNT:
				resultDetails = verifyCartCount(webdriver,fieldText, value);
				break;
			case VERIFYTEXTNOTPRESENT:
				resultDetails = verifyTextNotPresent(webdriver, fieldText, value, fieldName);
				break;
			case CALCULATION:
				resultDetails = calculation(webdriver, fieldText, value, fieldName);
				break;
			case VERIFYSAVINGS:
				resultDetails = verifySavings(webdriver, fieldText, value, fieldName);
				break;
			case SEARCHCATEGORY:
				resultDetails = searchCategory(webdriver, fieldText,value);
				break;
			case SETTIME:
				resultDetails = settime(webdriver, fieldText, value);
				break;
			case CANCELPRINT:
				resultDetails= cancelPrintButton(webdriver);
				break;
			case ORDERSPLIT:
				resultDetails= orderSplit(webdriver, value);
				break;
			case URLNAVIGATION:
				String currentUrl1;
				String URL;
				String afterUpdating1 = null;
				WebDriverUtils.waitForPageToLoad(webdriver,"10000");
				Thread.sleep(8000);
				URL=webdriver.getCurrentUrl();
				if(URL.contains(".com")){
					currentUrl1=URL.split(".com")[0];
					afterUpdating1=currentUrl1+".com/gateway?t="+value;
				}else if(URL.contains(".org")) {
					currentUrl1=URL.split(".org")[0];
					afterUpdating1=currentUrl1+".org/gateway?t="+value;
				}else {
					currentUrl=URL.split("t=")[0];
					afterUpdating1=currentUrl+"t="+value;
				}
				try {
					webdriver.get(afterUpdating1); //url
					resultDetails.setFlag(true);
				}catch (Exception e) {
					System.out.println("exception value : " + e.getMessage());  
					sd.log.info("exception value : " + e.getMessage());
					resultDetails.setFlag(false);                               
					resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
					WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				}
				break;
			case SAVEUSERS:
				resultDetails =saveUsers(webdriver,value);
				break;
			case VERIFYSEEDETAILSBUTTON:
				resultDetails = verifySeeDetailsButton(webdriver, fieldText,value,fieldName,user1,password1,user2,password2,browser);
				break;
			case REWARDSCASH:
				resultDetails = rewardsCash(webdriver, fieldText, value);
				break;
			case COMPAREREWARDSCASH:
				resultDetails = compareRewardsCash(webdriver, fieldText, value);
				break;
			case ENTERREWARDSCASH:
				resultDetails = enterRewardsCash(webdriver, fieldText);
				break;

			case VALIDATEREWARDSCASH:
				resultDetails = validateRewardsCash(webdriver, fieldText);
				break;

			}
			return resultDetails;
		}catch (Exception e) {
			sd.log.debug("Unable to perform action" + fieldText);	
			sd.log.error("Exception: "+e.getMessage());	
			String field;
			if (fieldName!=null&&fieldName.equalsIgnoreCase("NONE"))
				field = fieldText.substring(3, fieldText.length());
			else 
				field = fieldName;
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());			
			return resultDetails;
		}
	}



	/*'#########################################################################################################
	'Function name		:	CANCELPRINTBUTTON
	'Description		:	This function is to cancel the  print window popup

	'Parameters			:	No need to pass field/value	               
	'#########################################################################################################*/

	private ResultDetails cancelPrintButton(WebDriver webdriver) {
		ResultDetails resultDetails = new ResultDetails();
		String title=" ";
		if(sd.Browser.equalsIgnoreCase("GCHROME"))
			title=webdriver.getTitle();
		sd.log.info("Title:"+title);
		String serverName = sd.hostFound;
		sd.log.info("Node IP:"+serverName);
		String returnedValue = "";
		int port = Integer.parseInt("9878");
		sd.log.info("Port:"+port);
		try {
			System.out.println("Connecting to " + serverName + " on port "
					+ port);
			Socket client = new Socket(serverName, port);
			sd.log.info("Just connected to "+ client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(""+client.getLocalSocketAddress());
			if(sd.Browser.equalsIgnoreCase("GCHROME"))
				out.writeUTF("start C:\\SAF\\Cancel_Print_Chrome.exe::"+title);
			else
				out.writeUTF("start C:\\SAF\\Cancel_Print_IE_FF.exe::"+" ");
			out.writeUTF("PRINTAUTOIT");//Action that need to be performed on server side 
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			sd.log.info("Server says -- " + in.readUTF());
			returnedValue = in.readUTF();
			System.out.println("Server says -- " + returnedValue);
			client.close();
		}catch (IOException e) {
			sd.log.error("Exception in cancelPrintButton: "+e.getMessage());
			resultDetails.setFlag(false);
			return resultDetails;
		}
		resultDetails.setFlag(true);
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	VERIFYAUCTIONTIMEEXTENDS
	'Description		:	This function is to verify whether the auction time extends in the user flow when the extend time setup in the admin

	'Parameters			:	fieldText parameter should be given as the object id/path
	                        value parameter should be given as the extended time in seconds
	                        Ex://div[@class='timeLeft']/span   30
	'#########################################################################################################*/

	public ResultDetails verifyAuctionTimeExtends(WebDriver webdriver,String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String timeleft = null;
		int flag=0;
		try{
			resultDetails.setFlag(true);
			int time=Integer.parseInt(sd.hMap.get("EndTime"))*60000;
			int temp=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText().indexOf("s");
			while(time>0){
				timeleft=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
				sd.log.info("timeleft: "+timeleft);                    
				int extendTime= Integer.parseInt(timeleft.substring(temp-2,temp));
				sd.log.info("the extend time is-----: "+extendTime);
				if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText().contains("00h 00m")){
					if(extendTime<Integer.parseInt(value)){
						flag=1;
						break;
					}else{
						continue;
					}
				}else{
					if(time>=5000){
						Thread.sleep(5000);
						time=time-5000;
					}else{
						Thread.sleep(time);
						time=0;
					}
				}                         
			}     
			if(flag==1){
				WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//div[@class='fieldWrap']/a"));
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].click();", ele);
				timeleft=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
				int timeextended=Integer.parseInt(timeleft.substring(temp-2,temp));
				if(timeextended>(Integer.parseInt(value)-10)){
					resultDetails.setFlag(true);
				}
				else{
					sd.log.debug("Auction time is not extented");
					resultDetails.setFlag(false);					
					resultDetails.setErrorMessage("Auction time is not extented");
				}
			}else{
				sd.log.debug("Auction time out Error");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Auction time out Error");
			}
		}catch(Exception e){
			sd.log.error("Exception in verifyAuctionTimeExtends: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Exception in verifyAuctionTimeExtends:"+e.getMessage());
			return resultDetails;
		}
		return resultDetails;

	}


	/*'#########################################################################################################
	'Function name		:	AUCTIONADMINVIEWCLICK
	'Description		:	This function is used click on the View link in the Admin flow - Search auctions page.
	'						
	'Parameters			:	fieldText parameter should be given as BTN/XPH/LNK followed by object id 
							Ex: //div[@class='currBid']/span[@class='bidAmt']	HMVcurrentbidamount2
	'#########################################################################################################*/

	public ResultDetails auctionAdminViewClick(WebDriver webdriver, String fieldText,String value){                            
		ResultDetails resultDetails = new ResultDetails();
		try{
			fieldText = fieldText.substring(3, fieldText.length());                     
			int rows=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldText+"//tr")).size();
			int flag=0;
			value = getValue(value);
			for(int r=1;r<=rows;r++)
			{                                                             
				Thread.sleep(5000);
				if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText+"/tr["+r+"]/"+"td"+"[5]")).getText().trim().equals(value)){
					WebElement ele = webdriver.findElement(WebDriverUtils
							.locatorToByObj(webdriver,fieldText+"/tr["+r+"]/"+"td"+"[30]"+"/a"));
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					js.executeScript("arguments[0].click();", ele);
					Thread.sleep(5000);
					flag=1;
					break;
				}
			}             
			if(flag==1){
				resultDetails.setFlag(true);
			}else{
				sd.log.debug("Element not found");      
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Element not found");
			}                                                                             
		}catch (Exception e)  {
			sd.log.error("Exception in auctionAdminViewClick: " + e.getMessage());                              
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element not found");
			return resultDetails;
		}             
		return resultDetails;
	}
	/*'#########################################################################################################
	'Function name		:	clickADDTOCARTBUTTON
	'Description		:	This function is to verify and click on the product which consists of the add to cart button in the merchandise work flow and also this will
							verify whether the product added allowed to checkout flow. 
	'						
	'Parameters			:	fieldText parameter should start with XPH followed by products div and next page locators separated by "::"
							Ex: XPH//div[@id='product-listing']//div[@class='content']:://div[@class='page-number']/a[2]
							    XPH<productslist locator>::<Next locator>
	'#########################################################################################################*/
	public ResultDetails clickAddToCartButton(WebDriver webdriver, String fieldText){	
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText.substring(3, fieldText.length());
		String fieldArray[]=field.split("::");
		int flag=0,count=1;
		String nextButtonClass=null;
		WebElement ele;
		String currentURL=null;
		JavascriptExecutor js;
		try{			
			do{
				nextButtonClass = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[1])).getAttribute("class");
				int i=1,size=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"//div[starts-with(@class,'price-matrix grid')]")).size();
				for(i=1;i<=size;i++){
					if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"/div[starts-with(@class,'price-matrix grid')]["+i+"]")).getText().trim().toLowerCase().contains("add to cart")){
						currentURL=webdriver.getCurrentUrl();
						ele = webdriver.findElement(WebDriverUtils
								.locatorToByObj(webdriver, fieldArray[0]+"/div["+i+"]//a[contains(@href, 'addnewtocart')]"));
						js = (JavascriptExecutor) webdriver;
						js.executeScript("arguments[0].click();", ele);	
						Thread.sleep(15000);	
						String cartDetails = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//div[@id='container']")).getText();
						if(count<10 && (cartDetails.contains("Please remove")|| cartDetails.contains("Please lower the quantity") || cartDetails.contains("Print magazines can only be shipped to the 50 United States") ||cartDetails.contains("You Already Reached The Maximum Purchase Limit")) ){
							System.out.println(cartDetails);
							if(!cartDetails.contains("You Already Reached The Maximum Purchase Limit")){
								ele = webdriver.findElement(WebDriverUtils
										.locatorToByObj(webdriver, "//input[@class='btn_remove_cart_item']"));
								js = (JavascriptExecutor) webdriver;
								js.executeScript("arguments[0].click();", ele);	
								count++;						
								Thread.sleep(15000);								
							}
							ele = webdriver.findElement(WebDriverUtils
									.locatorToByObj(webdriver, "//div[@id='continueshopping_on_titlebar']/a[text()='Continue Shopping']"));
							js = (JavascriptExecutor) webdriver;
							js.executeScript("arguments[0].click();", ele);	
							count++;
							Thread.sleep(15000);
							if(currentURL!=null && currentURL.contains("pageNum")){
								webdriver.get(currentURL);
							}						
						}else{
							flag=1;
							break;
						}
					}			
				}
				if(flag==1){
					break;
				}else{
					ele = webdriver.findElement(WebDriverUtils
							.locatorToByObj(webdriver, fieldArray[1]));
					js = (JavascriptExecutor) webdriver;
					js.executeScript("arguments[0].click();", ele);	
					Thread.sleep(4000);
				}
			}while(nextButtonClass.trim().equals("pageNext active"));
			if(flag!=1){
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Add to cart button not available");				
			}else{
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");	
			}
		}catch(Exception e){
			System.out.println("exception value : " + e.getMessage());	
			sd.log.debug("exception value : " + e.getMessage());	
			resultDetails.setFlag(false);					
			resultDetails.setErrorMessage("Add to cart element not found");
			return resultDetails;			
		}
		return resultDetails;
	}

	/*'#########################################################################################################
    'Function name             :      VERIFYSEEDETAILSBUTTON
    'Description         	   :      This function is to verify and click on the product which consists of the see details button in the merchandise work flow
    '                                        
    'Parameters                :      FieldText parameter should start with XPH followed by products div and next page locators separated by "::"
                                      Ex: XPH//div[@id='product-listing']//div[@class='content']:://div[@class='page-number']/a[2]
                                          XPH<productslist locator>::<Next locator>
    '#########################################################################################################*/

	public ResultDetails verifySeeDetailsButton(WebDriver webdriver, String fieldText, String value,String fieldName,String user1,String password1,String user2,String password2,String browser){
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText.substring(3, fieldText.length());
		String fieldArray[]=field.split("::");
		int flag=0;
		String nextButtonClass=null;
		try{                 
			do{
				Thread.sleep(4000);
				nextButtonClass = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[1])).getAttribute("class");
				int i=1,size=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"//div[starts-with(@class,'price-matrix grid')]")).size();
				for(i=1;i<=size;i++){
					Thread.sleep(4000);
					if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"/div[starts-with(@class,'price-matrix grid')]["+i+"]")).getText().trim().toLowerCase().contains("see details")){
						WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"/div["+i+"]//a[@class='btn see-details']"));
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						js.executeScript("arguments[0].click();", ele);    
						Select select = new Select(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"attribute1")));
						List<WebElement> selectOptions = select.getOptions();
						for (WebElement webElement : selectOptions)
						{
							System.out.println("The options in the select box are ::"+ webElement.getText());                                        
							if(webElement.getText().equalsIgnoreCase(value)){
								Thread.sleep(4000);
								flag=1;                                                
							}                                                      
						}
						if(flag==1){
							break;
						}else{
							performAction(webdriver,fieldText,"marketplace","URLNAVIGATION", fieldName,browser,user1,password1,user2,password2);
							WebElement element1=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"Gift Cards"));
							JavascriptExecutor js1=(JavascriptExecutor) webdriver;
							js1.executeScript("arguments[0].click();",element1);
							WebElement element2=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"eGift Cards"));
							JavascriptExecutor js2=(JavascriptExecutor) webdriver;
							js1.executeScript("arguments[0].click();",element2);
							Thread.sleep(4000);
						}
					}
				}
				if(flag==1)
				{
					break;
				}
				else{
					flag=0;
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[1])).click();
					Thread.sleep(4000);                             
				}
			}while(nextButtonClass.trim().equals("pageNext active"));                  
			if(flag!=1){
				sd.log.debug("Add to cart button not available");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Add to cart button not available");                       
			}else{
				resultDetails.setFlag(true);
			}      
		}catch(Exception e){
			sd.log.error("Exception in verifyAddToCartButton: " + e.getMessage());     
			resultDetails.setFlag(false);                                 
			resultDetails.setErrorMessage("Add to cart element not found");
			return resultDetails;                    
		}
		return resultDetails;
	}  

	/*'#########################################################################################################
	'Function name		:	INCREASEBITAMOUNT
	'Description		:	This function is to verify whether the bid amount is getting increased as expected in the user flow while placing the the bid.

	'Parameters			:	fieldText parameter should be given as  object id/path
	                        value parameter should be given as HMVbidAmount (bidAmount is the variable name)
	                        Ex: //div[@class='currBid']/span[@class='bidAmt']	HMVbidAmount
	'#########################################################################################################*/

	public ResultDetails increaseBitAmount(WebDriver webdriver,String fieldText, String value){
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText;
		System.out.println("field = "+field);
		sd.log.info("field = "+field);
		value = getValue(value);			
		try{
			String prevBidPoint,latestBidPoint;
			int prevBidCount,latestBidCount;
			if(value.contains("."))
			{
				value=value.substring(0,value.indexOf("."));
			}
			prevBidPoint = value;
			prevBidCount=Integer.parseInt(prevBidPoint);
			Thread.sleep(3000);
			latestBidPoint=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
			if(latestBidPoint.contains("."))
			{
				latestBidPoint=latestBidPoint.substring(0,latestBidPoint.indexOf("."));
			}
			latestBidCount=Integer.parseInt(latestBidPoint);
			AssertJUnit.assertEquals(latestBidCount,prevBidCount+1);
			resultDetails.setFlag(true);
		}catch (AssertionError e) {
			sd.log.info("Assertopm error in IncreaseBidAmount : " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Assertopm error in IncreaseBidAmount : " + e.getMessage());
			return resultDetails;
		}catch(Exception e){
			System.out.println("exception value : " + e.getMessage());	
			sd.log.error("Exception in  increaseBitAmount: " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Exception in  increaseBitAmount: " + e.getMessage());
			return resultDetails;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	GETFIELD
	'Description		:	This function is to get the locator path by replacing with required dynamic content when it consists of HMV

	'Parameters			:   locator is the object id/path 	             
	'#########################################################################################################*/

	public String getField(WebDriver webdriver, String locator) {
		try{
			if(locator.contains("%HMV")) {	
				String strsub = locator.substring(locator.indexOf("%")+1 ,locator.lastIndexOf("%"));   
				sd.hMap.get(strsub.substring(3, strsub.length()));
				locator = locator.replace("%" + strsub + "%",sd.hMap.get(strsub.substring(3)).toString());
				sd.log.info("locator = "+locator);
			}
		}catch(Exception e){
			sd.log.error("Error in getField: "+e.getMessage());			
		}
		return locator;
	}

	/*'#########################################################################################################
	'Function name		:	HANDLEBROWSERCERTFICATIONERROR
	'Description		:	This function is to handle the browser level certification errors

	'Parameters			:   N/A	             
	'#########################################################################################################*/

	public ResultDetails handleBrowserCertficationError(WebDriver webdriver,String browser) throws InterruptedException {
		ResultDetails resultDetails = new ResultDetails();
		Thread.sleep(15000);
		try{
			if(browser.toLowerCase().contains("ie") && webdriver.getTitle().contains("Certificate")){
				webdriver.navigate().to("javascript:document.getElementById('overridelink').click()");
			}
			Thread.sleep(10000);
			resultDetails.setFlag(true);		 
		}catch(Exception e){
			sd.log.error("Error in Handle Browser Certification Error method: "+e.getMessage());	
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error in Handle Browser Certification Error method: "+e.getMessage());
			return resultDetails;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	VERIFYAUCTIONTIMEOUT
	'Description		:	This function is to verify whether the auction is getting time out as per the admin auctions settings given.

	'Parameters			:	fieldText parameter should be given as  the object id/path
	         				Ex: //div[@class='timeLeft']/span
	'#########################################################################################################*/

	public ResultDetails verifyAuctionTimeout(WebDriver webdriver,String fieldText) {
		ResultDetails resultDetails = new ResultDetails();
		int flag=0;
		try{
			resultDetails.setFlag(true);
			int time=Integer.parseInt(sd.hMap.get("EndTime"))*60000;
			while(time>0){
				System.out.println(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText());
				if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText().trim().equals("00:00:00")){
					flag=1;
					break;
				}else{
					if(time>=5000){
						Thread.sleep(5000);
						time=time-5000;
					}else{
						Thread.sleep(time);
						time=0;
					}
				}				
			}	
			if(flag==1){
				resultDetails.setFlag(true);
			}else{
				if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText().trim().equals("00:00:00")){
					resultDetails.setFlag(true);
				}else{
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Auction time out Error");
				}
			}
		}catch(Exception e){
			sd.log.error("Exception in verifyAuctionTimeout: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Unable to verifyAuctionTimeout: "+e.getMessage());
			return resultDetails;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	ORDERSPLIT
	'Description		:	This function is to get the order number using with "-"

	'Parameters			:	No need to pass field/value	               
	'#########################################################################################################*/

	public ResultDetails orderSplit(WebDriver webdriver, String value){
		ResultDetails resultDetails = new ResultDetails();
		try{
			String beforeSplitting=sd.hMap.get(value);
			String afterSplitting;
			if(beforeSplitting!=null && beforeSplitting.contains("-")){
				afterSplitting=beforeSplitting.split("-")[1];
			}else{
				afterSplitting=beforeSplitting;
			}
			sd.log.info("After Splitting the Ordernum:"+afterSplitting);
			sd.hMap.put(value,afterSplitting);
			resultDetails.setFlag(true);
		}catch(Exception e){	
			sd.log.info("Exception:"+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Unable to perform Ordersplit: "+e.getMessage());	
			return resultDetails;
		}
		return resultDetails;

	}	


	/*'#########################################################################################################
	'Function name		:	CATALOGUPLOAD
	'Description		:	This function is to execute the catalog upload procedure

	'Parameters			:	N/A
	'#########################################################################################################*/

	public ResultDetails catalogUpload(WebDriver webdriver) throws  SQLException{
		ResultDetails resultDetails = new ResultDetails();
		String jdbcDriver1 =  "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Connection conn1=null;
		Statement st=null;
		try{
			Class.forName(jdbcDriver1);
			String  url1="jdbc:sqlserver://10.120.100.52:61446;databaseName=DRcore_test";
			String password1 = "p*fR3bCE!5Hz3H_90dNvi";
			String userName1 = "auto_app";
			conn1 =  DriverManager.getConnection(url1,userName1,password1);
			System.out.println("Connected successfully");
			sd.log.info("Connected successfully");
			String sql="exec catalogupload";
			st=conn1.createStatement();
			st.executeUpdate(sql);
			resultDetails.setFlag(true);	                     
		}catch(SQLException e){
			System.out.println("exception value : " + e.getMessage());
			sd.log.debug("Exception in Catalogupload : " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Catalog upload procedure executionfailed");
			return resultDetails;
		}catch(Exception e) {
			System.out.println("exception value : " + e.getMessage());
			sd.log.debug("Exception in Catalogupload : " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Catalog upload procedure executionfailed");
			return resultDetails;
		}finally {
			st.close();
			conn1.close();
		}
		return resultDetails;
	}




	/*'#########################################################################################################
	'Function name		:	VERIFYAUCTIONAVAILABLE
	'Description		:	This function is to verify whether the auction Which is created in the adminflow is available  in the userflow or not.

	'Parameters			:	field parameter should be given as CSS followed by object id or path
	                        field -CSS//div[@class='auctions']//li
                            value -HMV<AuctionID>
	'#########################################################################################################*/

	public ResultDetails verifyAuctionAvailable(WebDriver webdriver, String field, String value) {
		ResultDetails resultDetails = new ResultDetails();
		sd.log.info("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());		
		int flag=0;
		value = getValue(value);
		switch (dfs) {
		case CSS:
			try{
				do{
					if(WebDriverUtils.isElementPresent(webdriver,By.xpath(field+"[contains(@class,'"+value+"')]"),05))
					{
						resultDetails.setFlag(true);
						flag=1;
						break;
					}else{
						if(WebDriverUtils.isElementPresent(webdriver,By.xpath("//a[@class='pageNext active']"),05)){
							webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//a[@class='pageNext active']")).click();
							Thread.sleep(10000);
						}else{
							resultDetails.setFlag(false);
							resultDetails.setErrorMessage("Auction not found");
							sd.log.debug("The Auction  ::+" +value +   "::not Found");
							break;
						}
					}
				}while(WebDriverUtils.isElementPresent(webdriver,By.xpath("//a[@class='pageNext active']"),05));
			}catch(Exception e) {
				sd.log.error("Exception in verifyAuctionAvailable: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The page   ::+" +value +   "::not Found");
				return resultDetails;
			}
			break;
		}
		return resultDetails;

	}


	/*'#########################################################################################################
	'Function name		:	SELECTAUCTION
	'Description		:	This function is to find and click on the auction(Which is created in the adminflow) in the userflow.

	'Parameters			:	field parameter should be given as LNK/CRO followed by auctions display div path
	                        field - LNK//div[@class='auctions']//div[@class='auctionItemBottom']/a
                            value -HMV<AuctionID>
	'#########################################################################################################*/

	public ResultDetails selectAuction(WebDriver webdriver, String field, String value) throws InterruptedException {
		sd.log.info("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());           
		ResultDetails resultDetails = new ResultDetails();
		resultDetails.setFlag(false);
		String fieldArray[];
		int flag=0;
		String temp = value;		
		if(value.contains(":"))
			value = value.split(":")[0];
		value = getValue(value);
		switch (dfs) {	
		case DTL:
			Thread.sleep(5000);
			WebDriverUtils.waitForPageToLoad(webdriver,"10000");
			String currentUrl=webdriver.getCurrentUrl().split("t=")[0];
			String afterUpdating = "";
			if(!temp.contains(":"))
				afterUpdating=currentUrl+"t=auctiondetails&auctionid="+value.trim()+"&auctiontype=main";
			else
				afterUpdating=currentUrl+"t=auctiondetails&auctionid="+value.trim()+"&auctiontype=ca";
			try {
				webdriver.get(afterUpdating);
				Thread.sleep(5000);//url
				resultDetails.setFlag(true);
			}catch (Exception e) {
				sd.log.info("exception value : " + e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Window with title   ::+" +value +  "::not Found");
				WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				return resultDetails;
			}
			break;
		case ATN:			
			Thread.sleep(5000);
			WebDriverUtils.waitForPageToLoad(webdriver,"10000");
			String currentUrl1=webdriver.getCurrentUrl().split("t=")[0];
			String afterUpdating1=currentUrl1+"t=auctions";
			try {
				webdriver.get(afterUpdating1);
				Thread.sleep(5000);//url
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}catch (Exception e) { 
				sd.log.info("exception value : " + e.getMessage());
				resultDetails.setFlag(false);                                
				resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
				WebDriverUtils.waitForPageToLoad(webdriver, "50000");
				return resultDetails;
			}
			break;	
		case LNK:
			try{
				Thread.sleep(20000);
				do{
					Thread.sleep(3000);
					if(WebDriverUtils.isElementPresent(webdriver,By.xpath(field+"[contains(@href,'"+value+"')]"),05))
					{	
						sd.log.debug("Auction: Expected auction available. About to click");
						WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field+"[contains(@href,'"+value+"')]"));
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						js.executeScript("arguments[0].click();", ele);
						flag=1;
						break;
					}else{
						if(WebDriverUtils.isElementPresent(webdriver,By.xpath("//a[@class='pageNext active']"),05)){
							System.out.println("Clicking next page");
							sd.log.debug("Auction: Clicking next page");
							WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//a[@class='pageNext active']"));
							JavascriptExecutor js = (JavascriptExecutor) webdriver;
							js.executeScript("arguments[0].click();", ele);
							Thread.sleep(10000);
						}else{
							break;
						}
					}
				}while(WebDriverUtils.isElementPresent(webdriver,By.xpath("//a[@class='pageNext active']"),05));			

				if(flag==1){
					resultDetails.setFlag(true);					
				}else{
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("The Auction  ::+" +value +   "::not Found");
					sd.log.debug("The Auction  ::+" +value +   "::not Found");					
				}
			}catch(Exception e) {
				System.out.println("Tha page    ::+" +value +   "::not Found");
				sd.log.debug("The page  ::+" +value +   "::not Found: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The page   ::+" +value +   "::not Found due to- "+e.getMessage());

				return resultDetails;
			}
			break;
		case CRO:
			try{
				fieldArray=field.split("::");
				int rows=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"//li")).size();
				for(int r=1;r<=rows;r++)
				{
					String classAttribute= webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"/li["+r+"]")).getAttribute("class");
					if(classAttribute.trim().contains(value)) {
						WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldArray[0]+"/li["+r+"]/a"));
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						js.executeScript("arguments[0].click();", ele);
						flag=1;
						break;
					}else{
						if(!webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[1])).getAttribute("style").contains("display: none;")){
							WebElement ele1= webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[1]));
							JavascriptExecutor js = (JavascriptExecutor) webdriver;
							js.executeScript("arguments[0].click();", ele1);
							Thread.sleep(1000);
						}
					}
				}
				if(flag==1){
					resultDetails.setFlag(true);
				}else {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Auction not found");
					sd.log.debug("The Auction  ::+" +value +   "::not Found");
				}
			}catch(Exception e) {
				System.out.println("Tha page    ::+" +value +   "::not Found");
				sd.log.debug("The page  ::+" +value +   "::not Found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The page   ::+" +value +   "::not Found");
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	AUCTIONSPRODUCTCLICK
	'Description		:	This functions is used to click product name link in the Closed auctions page.	

	'Parameters			:	fieldText parameter should be given as XPH//div[@class='auctions']
	                        value parameter should be given as HMV<ProductName>		                                                	                       
	'#########################################################################################################*/

	public ResultDetails auctionsProductClick(WebDriver webdriver, String fieldText,String value){	
		ResultDetails resultDetails = new ResultDetails();
		try{
			value = getValue(value);
			fieldText = fieldText.substring(3, fieldText.length());	
			int flag=0;
			int rows=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldText+"//div[@class='auction']")).size();
			String productName;
			for(int r=1;r<=rows;r++)
			{
				productName= webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText+"/div[@class='auction']["+r+"]/span")).getText();
				if(productName.trim().toLowerCase().equals(value.trim().toLowerCase())){
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText+"/div[@class=\'auction\']["+r+"]/span")).click();
					flag=1;
					break;
				}
			}
			if(flag==1){
				resultDetails.setFlag(true);
			}else{
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Product not available");
			}
		}catch (AssertionError e) {
			sd.log.info("Assertion Error : " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}catch (Exception e) 	{
			sd.log.error("Exception in auctionsProductClick: " + e.getMessage());		
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element not found");
			return resultDetails;
		}	
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	SEARCHITEMSCOUNT
	'Description		:	This functions is to verify the search results count displaying as expected or not in Hotels and condos workflow

	'Parameters			:	value parameter should be given as numeric(i.e expected search results count)		                                                	                       
	'#########################################################################################################*/

	private ResultDetails searchItemsCount(WebDriver webdriver, String value) {
		ResultDetails resultDetails = new ResultDetails();
		try{
			//If more than one page displayed for the search results
			if(webdriver.findElement(By.xpath("//a[contains(@href,'pagenbr=2')]"))!=null)
			{
				int cnt = webdriver.findElements(By.cssSelector("div.hotel-logo")).size();
				System.out.println("Number of search items in the page: "+cnt);
				if(cnt==Integer.parseInt(value)) {
					resultDetails.setFlag(true);
				}else {
					sd.log.debug("Number of search items displayed are less than "+value);
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Number of search items displayed are less than "+value);
				}
			}
		}catch(Exception e) {
			sd.log.error("Exception in searchItemsCount: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Search Items count failure");
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	RELOADAUCTION
	'Description		:	This function is to click on reloadauction link in the admin flow - Search auctions - View page

	'Parameters			:	fieldText parameter should be given as XPH//table[@class='horizontal']/tbody
	'#########################################################################################################*/

	public ResultDetails reloadAuction(WebDriver webdriver, String fieldText){                        
		try{
			fieldText = fieldText.substring(3, fieldText.length());     
			String URL;
			try{
				WebDriverUtils.waitForPageToLoad(webdriver,"60000");                                            
				Thread.sleep(5000);
				URL=webdriver.getCurrentUrl();
				if(!URL.contains("t="))  {
					Thread.sleep(8000);
					URL=webdriver.getCurrentUrl();
				}
				WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "Reload Core5.0 Auctions Manager"));
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].click();", ele);
				Thread.sleep(10000);
				AssertJUnit.assertTrue(webdriver.getPageSource().toLowerCase().trim().contains("auction manager has been reloaded."));
				try {
					webdriver.navigate().back();
				}catch(Exception e) {
					webdriver.get(URL);
				}
				Thread.sleep(4000);
			}catch(Exception e) {
				System.out.println("exception value : " + e.getMessage());        
				sd.log.debug("Exception value : " + e.getMessage());                    
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Unable to do relaod auction");
				return resultDetails;
			}
			int rows=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldText+"//tr")).size();
			System.out.println("the number of rows are---------"+rows);
			for(int r=1;r<=rows;r++)
			{
				WebDriverUtils.waitForPageToLoad(webdriver,"60000");                                            
				Thread.sleep(5000);
				URL=webdriver.getCurrentUrl();
				if(!URL.contains("t="))  {
					Thread.sleep(8000);
					URL=webdriver.getCurrentUrl();
				}
				WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText+"/tr["+r+"]/"+"td"+"[10]"+"/a"));
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].click();", ele);
				Thread.sleep(10000);
				AssertJUnit.assertTrue(webdriver.getPageSource().toLowerCase().trim().contains("auction manager has been reloaded."));
				try{
					webdriver.navigate().back();
				}catch(Exception e) {
					webdriver.get(URL);
				}
			}                                                             
			resultDetails.setFlag(true);
		}catch (AssertionError e) {
			System.out.println("Assertion error " + e.getMessage());
			sd.log.info("Assertion error : " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}catch (Exception e) {
			System.out.println("Exception value : " + e.getMessage());        
			sd.log.debug("Exception value : " + e.getMessage());                    
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element not found");
			return resultDetails;
		}             
		return resultDetails;
	}

	/*'#########################################################################################################
	'Function name		:	REMOVEADDRESSES
	'Description		:	This function is to remove all the existing addresses from the address book

	'Parameters			:	fieldText parameter should be given as XPH//div[@id='update-address']//div[@class='panel-rounded update-address']
	'#########################################################################################################*/

	public ResultDetails removeAddresses(WebDriver webdriver, String fieldText){
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText.substring(3, fieldText.length());
		try{
			int size;
			try{
				size = webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field)).size();
			}catch(Exception e) {
				resultDetails.setFlag(true);
				return resultDetails;
			}
			for(int i=1;i<=size;i++){	
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"["+1+"]"+"//a[@class='btn delete-address']")).click();
				Thread.sleep(8000);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//a[@class='imgBtn yes']")).click();
				Thread.sleep(8000);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "css=a.close")).click();
				Thread.sleep(8000);
			}
			resultDetails.setFlag(true);
		}catch(Exception e){
			sd.log.error("Exception in removeAddresses : " + e.getMessage());	
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Unable to remove address"+e.getMessage());
			return resultDetails;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	UPDATEVALUE
	'Description		:	This function is used  
	'						
	'Parameters			:	field parameter should be given as MSG/BTN/LNK followed by object id 
							Eg - BTNcss=table[id*='libvwreditor']
	'#########################################################################################################*/

	public ResultDetails updateValue(WebDriver webdriver,String fieldText, String value){
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText;
		sd.log.info("field = "+field);
		String value1 = getValue(value.split("::")[0]); 
		try{
			String prevBidPoint,latestBidPoint;
			int prevBidCount,latestBidCount;
			prevBidPoint = value1;
			prevBidCount=Integer.parseInt(prevBidPoint);
			latestBidPoint=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
			latestBidCount=Integer.parseInt(latestBidPoint);
			int var = Integer.parseInt(value.split("::")[1]);
			sd.log.info("Var is "+var);
			sd.log.info("Prev Bid Count "+prevBidCount);
			sd.log.info("Latest Value "+latestBidCount);
			AssertJUnit.assertEquals(latestBidCount,prevBidCount+var);
			resultDetails.setFlag(true);
		}catch (AssertionError e) {
			sd.log.error("exception value : " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}catch(Exception e){ 
			sd.log.error("Exception in updateValue: " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to store random name");
			return resultDetails;
		}
		return resultDetails;
	}

	/*'#########################################################################################################
	'Function name		:	CLEAR
	'Description		:	This function is used to clear the data in a textbox 
	'						
	'Parameters			:	field parameter should be given as TXT followed by object id 
							Eg - TXTusername
	'#########################################################################################################*/

	public ResultDetails clear(WebDriver webdriver, String fieldText,String value, String browser) {
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText.substring(3, fieldText.length());
		value = getValue(value);		
		try {
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
			try {
				if(!browser.toLowerCase().equals("safari")){
					String del = Keys.chord(Keys.CONTROL, "a")+ Keys.DELETE;
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).sendKeys(del);
				}
			}catch (Exception e) {
				sd.log.debug("Exception: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Unable to clear the data: "+e.getMessage());
			}
			resultDetails.setFlag(true);
		}catch(Exception ee){
			sd.log.debug("Exception: "+ee.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Unable to clear the data: "+ee.getMessage());
		}
		return resultDetails;
	}

	/*'#########################################################################################################
	'Function name		:	CLICKHIDDEN
	'Description		:	This function is used to click the btn/lnk using javascript. 
	'						
	'Parameters			:	field parameter should be given as  object id/path 
							Eg - //ul[@id="drop-down-menu"]/li/div/div[2]/
	'#########################################################################################################*/
	public ResultDetails clickHidden(WebDriver webdriver, String fieldText) {
		ResultDetails resultDetails = new ResultDetails();
		try {
			WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldText));
			JavascriptExecutor js = (JavascriptExecutor) webdriver;
			js.executeScript("arguments[0].click();", ele);
			WebDriverUtils.waitForPageToLoad(webdriver, "50000");
			resultDetails.setFlag(true);
		} catch (Exception e) {
			sd.log.debug("Unable to perform action" + fieldText);	
			sd.log.error("Exception in clickHidden: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	POSTDEDUCTIONPOINTS
	'Description		:	This function is to check points after deducting youpay from total points
	'						
	'Parameters			:	field parameter should be Current Points locator
							value parameter should be as HMV<totalcash>::HMV<youpay>
	'#########################################################################################################*/

	public ResultDetails postDeductionPoints(WebDriver webdriver,String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		try{
			String currPoints = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
			String prevPoints = sd.hMap.get(value.split("::")[0]);
			String youPay = sd.hMap.get(value.split("::")[1]);
			currPoints = replacePriceChar(currPoints);
			prevPoints = replacePriceChar(prevPoints);
			youPay = replacePriceChar(youPay); System.out.println("currPoints: "+currPoints);
			sd.log.info("prevPoints: "+prevPoints);
			sd.log.info("youPay: "+youPay);
			Double currentPoints = Double.parseDouble(currPoints);
			sd.log.info("currentPoints: "+currentPoints);
			Double previousPoints = Double.parseDouble(prevPoints);
			Double youPayPoints = Double.parseDouble(youPay);
			DecimalFormat df = new DecimalFormat("#.00");
			Double f3 = previousPoints-youPayPoints;
			Double finalDeduction = Double.valueOf(df.format(f3));
			sd.log.info("finalDeduction: "+finalDeduction);
			int comp = Double.compare(currentPoints, finalDeduction);
			if(comp==0){
				resultDetails.setFlag(true);
			}else{
				sd.log.debug("Points deduction failure:: "+"OldPoints="+prevPoints+". Deduction="+youPay+". Expected Points="+finalDeduction+". ActualPoints="+currPoints);
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Points deduction failure:: "+"OldPoints="+prevPoints+". Deduction="+youPay+". Expected Points="+finalDeduction+". ActualPoints="+currPoints);
			}
		}catch(Exception e)	{
			sd.log.error("Exception in postDeductionPoints: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("PostDeductionPoints calculation failed");
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	SETTIME
	'Description		:	This function is to set the date/time as EST time 
	'						
	'Parameters			:	fieldText  parameter should starts with system date followed by number and sepearated by |
							value parameter should be the field locator followed by number of minutes 
							Ex: field:systemdate|-1,Value: <locator>  - This would set the locator value as current date-1 i.e yesterday's date by converting to EST
								field:systemdate,Value:<locator>|20 - This would add 20 mnts to the current system date and convert to EST and set the value to locator
	'#########################################################################################################*/

	public ResultDetails settime(WebDriver webdriver,String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		int minutes = 0;
		int date = 0;
		try{
			if(value.contains("|")){
				minutes = Integer.parseInt(value.split("\\|")[1]);
				value=value.split("\\|")[0];
			}
			if(fieldText.contains("|")){
				date=Integer.parseInt(fieldText.split("\\|")[1]);
				fieldText=fieldText.split("\\|")[0];
			}
			sd.log.info("time value :: "+value);
			if(fieldText.equalsIgnoreCase("systemdate")){
				Calendar calendar = Calendar.getInstance();
				sd.log.info("Original = " + calendar.getTime());
				if(date!=0){
					calendar.add(Calendar.DATE, date);
				}if(minutes!= 0){
					calendar.add(Calendar.MINUTE, minutes);
				}
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
				df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
				String ESTTIME = df.format(calendar.getTime());
				sd.log.info("EST time: "+ESTTIME);
				String ESTDATE = ESTTIME.split(" ")[0];
				String ESTTIMEMODE = ESTTIME.split(" ")[2];
				ESTTIME = ESTTIME.split(" ")[1];
				String Hours= ESTTIME.split("\\:")[0];
				String Minutes= ESTTIME.split("\\:")[1];
				String Seconds= ESTTIME.split("\\:")[2];
				if(value.contains("post")){
					webdriver.findElement(By.id("mailpostmark_deadline")).sendKeys(ESTDATE);
				}else if(value.contains("receipt")){
					webdriver.findElement(By.id("mailreceipt_deadline")).sendKeys(ESTDATE);
				}else
					webdriver.findElement(By.name(value+"_date")).sendKeys(ESTDATE);
				Select select = new
				Select(webdriver.findElement(By.name(value+"_hour")));
				if(Hours.startsWith("0")){
					Hours = Hours.substring(1);
				}
				select.selectByVisibleText(Hours.concat(":00 ").concat(ESTTIMEMODE));
				webdriver.findElement(By.name(value+"_minute")).sendKeys(Minutes);
				webdriver.findElement(By.name(value+"_second")).sendKeys(Seconds);
			}else{
				webdriver.findElement(By.name(value+"_date")).sendKeys(value.split("\\|")[0]);
				Select select = new
				Select(webdriver.findElement(By.name(fieldText+"_hour")));
				select.selectByVisibleText(value.split("\\|")[1]);
				webdriver.findElement(By.name(fieldText+"_minute")).sendKeys(value.split("\\|")[2]);
				webdriver.findElement(By.name(fieldText+"_second")).sendKeys(value.split("\\|")[3]);
			}
			resultDetails.setFlag(true);
		}catch (Exception e) {
			sd.log.error("Exception in Settime: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	VERIFYADDTOCARTBUTTON
	'Description		:	This function is to verify and click on the product which consists of the add to cart button in the merchandise work flow
	'						
	'Parameters			:	fieldText parameter should start with XPH followed by products div and next page locators separated by "::"
							Ex: XPH//div[@id='product-listing']//div[@class='content']:://div[@class='page-number']/a[2]
							    XPH<productslist locator>::<Next locator>
	'#########################################################################################################*/

	public ResultDetails verifyAddToCartButton(WebDriver webdriver, String fieldText){
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText.substring(3, fieldText.length());
		String fieldArray[]=field.split("::");
		int flag=0;
		String nextButtonClass=null;
		try{			
			do{
				nextButtonClass = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[1])).getAttribute("class");
				int i=1,size=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"//div[starts-with(@class,'price-matrix grid')]")).size();
				for(i=1;i<=size;i++){
					if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"/div[starts-with(@class,'price-matrix grid')]["+i+"]")).getText().trim().toLowerCase().contains("add to cart")){
						webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[0]+"/div["+i+"]//a[@class='btn add-to-cart']")).click();
						Thread.sleep(4000);
						flag=1;
						break;
					}			
				}
				if(flag==1){
					break;
				}else{
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldArray[1])).click();
					Thread.sleep(4000);
				}
			}while(nextButtonClass.trim().equals("pageNext active"));			
			if(flag!=1){
				sd.log.debug("Add to cart button not available");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Add to cart button not available");				
			}else{
				resultDetails.setFlag(true);
			}
		}catch(Exception e){
			sd.log.error("Exception in verifyAddToCartButton: " + e.getMessage());	
			resultDetails.setFlag(false);					
			resultDetails.setErrorMessage("Add to cart element not found");
			return resultDetails;			
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	FINDDAILYDEALTOEXPIRE
	'Description		:	This function is to find the active daily to expire in the admin daily deals workflows
	'						
	'Parameters			:	N/A
	'#########################################################################################################*/

	public ResultDetails findDailyDealToExpire(WebDriver webdriver)
	{
		ResultDetails resultDetails = new ResultDetails();
		sd.log.info("SetPromo ClientName "+CreateClient.dailyDealCategory.get(sd.hMap.get("ClientName").toUpperCase()));
		try{
			int flag=0;
			Select select = new Select(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"sid")));
			select.selectByVisibleText(CreateClient.dailyDealCategory.get(sd.hMap.get("ClientName").toUpperCase()));
			Thread.sleep(3000);
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//input[@value='BySID']")).click();
			Thread.sleep(10000);
			if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "//table[@class='horizontal sortedTable ']")).getText().contains("No results found")){
				flag =0;
			}else{
				System.out.println(webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, "//table[@class='horizontal sortedTable ']//tr")).size());				
				SimpleDateFormat sdf= new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("EST"));
				Date d1 = new Date();
				Date date1 = sdf.parse(sdf.format(d1));
				System.out.println(sdf.format(date1));
				for(int i=1;i<=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, "//table[@class='horizontal sortedTable ']//tbody//tr")).size();i++){	
					String productCode = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "//table[@class='horizontal sortedTable ']//tbody/tr["+i+"]/td["+2+"]")).getText();
					String startDate = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "//table[@class='horizontal sortedTable ']//tbody/tr["+i+"]/td["+5+"]")).getText();
					String endDate = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "//table[@class='horizontal sortedTable ']//tbody/tr["+i+"]/td["+6+"]")).getText();
					if(!productCode.contains("_")){
						Date starDT = sdf.parse(startDate);
						Date endDT = sdf.parse(endDate);
						if(date1.compareTo(starDT)>=0 && date1.compareTo(endDT)<=0 ){
							flag=1;			        		
							webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, productCode)).click();
							Thread.sleep(10000);
							break;
						}
					}	

				}
			}
			if(flag==0){
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "Catalog")).click();
				Thread.sleep(10000);				
			}
			resultDetails.setFlag(true);
		}catch(Exception e){
			sd.log.error("Exception in findDailyDealToExpire: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to store random name");
			return resultDetails;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	SETDATE
	'Description		:	This function is to set the current/future/previous date to the date field and also to set the date as currendatetime+x mnts. (i.e Currenttime+15 mnts)

	'Parameters			:	fieldText parameter should be given as TXT/DDL/DEX/EST followed by object path/id
							value parameter should be dt:futuredate/dt:currentdate/dt:previousdate
							EX: TXTdatetextbox  dt:futuredate	                        
	'#########################################################################################################*/

	public ResultDetails setDate(WebDriver webdriver, String fieldText,String value)
	{
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		DataFileds dfs = DataFileds.valueOf(fieldType);
		Date date = new Date();
		long t;
		Date afterAddingTenMins;
		SimpleDateFormat dateTimeFormat;
		String estDate;
		switch(dfs){	
		case CTX:		
			try {
				value = getDate(value);				
				Selenium seleniumObj = new WebDriverBackedSelenium(webdriver, sd.appurl);
				seleniumObj.type(field, value);
				resultDetails.setFlag(true);
			}catch (Exception e) {
				System.out.println("Exception value : " + e.getMessage());	
				sd.log.error("Exception in setDate: " + e.getMessage());	
				resultDetails.setFlag(false);					
				resultDetails.setErrorMessage("Exception: "+e.getMessage());	
			}
			break;
		case TXT:		
			try {
				value = getDate(value);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(value);
				resultDetails.setFlag(true);
			}catch (Exception e) {
				System.out.println("Exception value : " + e.getMessage());	
				sd.log.error("Exception in setDate: " + e.getMessage());	
				resultDetails.setFlag(false);					
				resultDetails.setErrorMessage("Exception: "+e.getMessage());	
			}
			break;
		case DDL:
			try {	     	 
				t=date.getTime();
				afterAddingTenMins=new Date(t + (5 * 60000));
				dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
				dateTimeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
				estDate =dateTimeFormat.format(afterAddingTenMins);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(estDate);
				resultDetails.setFlag(true);
			}catch (Exception e) {
				System.out.println("exception value : " + e.getMessage());	
				sd.log.error("Exception in setDate: " + e.getMessage());
				resultDetails.setFlag(false);					
				resultDetails.setErrorMessage("Exception: "+e.getMessage());	
				return resultDetails;
			}
			break;
		case DEX:
			try {
				t=date.getTime();
				Date afterAddingOneMonth=new Date(t + (1000 * 60 * 60 * 24));
				dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy"); 
				dateTimeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
				estDate =dateTimeFormat.format(afterAddingOneMonth);
				String dateArray[];
				if(!(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value")==null && webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value").equals("") && webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value").equals(" "))){
					dateArray= webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value").trim().split(" ");
					estDate = estDate+" "+dateArray[1];
				}else{
					estDate = estDate+" "+"12:59:59";
				}
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(estDate);
				resultDetails.setFlag(true);
			}catch (Exception e) {
				System.out.println("exception value : " + e.getMessage());	
				sd.log.error("Exception in setDate: " + e.getMessage());	
				resultDetails.setFlag(false);					
				resultDetails.setErrorMessage("Exception: "+e.getMessage());	
				return resultDetails;
			}
			break;
		case EST:
			try {
				t=date.getTime();
				if(value!=null && value.length()>3 && value.substring(0, 3).equals("END")){
					value=value.substring(3,value.length());
					sd.hMap.put("EndTime", value);
				}

				afterAddingTenMins=new Date(t + (Integer.parseInt(value) * 60000));
				dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
				dateTimeFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
				estDate =dateTimeFormat.format(afterAddingTenMins);
				String filedArray[]=field.split("::");
				String dateArray[]=estDate.split(" ");
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, filedArray[0])).clear();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, filedArray[0])).sendKeys(dateArray[0]);
				sd.log.info("Date selected as: "+dateArray[0]);
				String timeArray[]=dateArray[1].split(":");
				Select select = new Select(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, filedArray[1])));
				if(timeArray[0].equals("12") && dateArray[2].equals("AM")) {
					select.selectByVisibleText(timeArray[0]+" "+dateArray[2]);
					sd.log.info("Hours selected as: "+timeArray[0]+" "+dateArray[2]);
				}else{
					select.selectByVisibleText(timeArray[0]+":00 "+dateArray[2]);
					sd.log.info("Hours selected as: "+timeArray[0]+":00 "+dateArray[2]);
				}
				Thread.sleep(3000);
				Select select1 = new Select(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, filedArray[2])));				
				select1.selectByVisibleText(timeArray[1]);
				sd.log.info("Mnts selected as: "+timeArray[1]);
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}catch (Exception e){
				System.out.println("exception value : " + e.getMessage());
				sd.log.debug("exception value : " + e.getMessage());	
				resultDetails.setFlag(false);	
				resultDetails.setErrorMessage("Wait Error: "+e.getMessage());	
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	SEARCHCATEGORY
	'Description		:	This function is to search the daily deal category based on client name and also to set the inventory to the daily deal

	'Parameters			:	fieldText parameter should starts with DDC/INV followed by object id/path
							Ex: DDC//div[@id='hidden10052-active']/table/tbody                  
	'#########################################################################################################*/

	public ResultDetails searchCategory(WebDriver webdriver, String fieldText,String value){
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		DataFileds dfs = DataFileds.valueOf(fieldType);
		String valueArray[]=null;
		if(value!=null && value.split("::").length>1){
			valueArray=value.split("::");
			value= getValue(valueArray[0]);
		}else{
			value= getValue(value);
		}
		switch(dfs){
		case DDC:		
			try{
				int flag=0;
				int rows=webdriver.findElements(By.xpath(field+"//tr")).size();
				System.out.println("the number of rows are---------"+rows);
				for(int r=1;r<=rows;r++)
				{
					if(webdriver.findElement(By.xpath(field+"/tr"+"["+r+"]/"+"td"+"[3]")).getText().contains(CreateClient.dailyDealCategory.get(sd.hMap.get("ClientName").toUpperCase()))){
						WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field+"/tr"+"["+r+"]/"+"td[1]/a"));
						JavascriptExecutor js = (JavascriptExecutor) webdriver;
						js.executeScript("arguments[0].click();", ele);
						flag=1;						
						break;
					}				
				}	
				if(flag==1)
				{ 
					sd.log.info("Element found and clicked on it");
				}
				else{
					sd.log.info("The element is not found");
					WebElement ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"Add Single-SKU Pricing"));
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					js.executeScript("arguments[0].click();", ele);
				}
				resultDetails.setFlag(true);
				return resultDetails;	
			}	catch (Exception e) {
				sd.log.error("Exception in searchCategory: " + e.getMessage());
				resultDetails.setFlag(true);						
				return resultDetails;
			}
		case INV:
			try {
				int flag=0;
				for(int i=1;i<=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field+"//tr")).size();i++){	
					String productCode = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+2+"]")).getText();
					if(productCode.equalsIgnoreCase(value)){
						webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+7+"]/input")).clear();
						webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+7+"]/input")).sendKeys(valueArray[1]);
						flag=1;
						break;
					}
				}
				if(flag==1){				
					for(int i=1;i<=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field+"//tr")).size();i++){	
						String productCode = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+2+"]")).getText();
						if(productCode.contains("_")){
							if(flag==1){
								webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+7+"]/input")).clear();
								webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+7+"]/input")).sendKeys(valueArray[1]);
								flag=2;
							}else{
								webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+7+"]/input")).clear();
								webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field+"/tr["+i+"]/td["+7+"]/input")).sendKeys("0");
							}
						}
					}
					resultDetails.setFlag(true);		
				}
				if(flag==0){
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("No records found or no childs available");			
				}
			}catch(Exception e){
				sd.log.error("Exception in searchCategory: " + e.getMessage());	
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Exception : "+e.getMessage());	
				return resultDetails;
			}

		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	CANCELORDER
	'Description		:	This function is to click on the cancel order link in the Accounts history page

	'Parameters			:	fieldText parameter should be given as object id/path
							//div[@id='history']//table//tbody	HMVorderno                 
	'#########################################################################################################*/

	public ResultDetails cancelorder(WebDriver webdriver, String fieldText,String value){
		ResultDetails resultDetails = new ResultDetails();
		try{
			value = getValue(value);
			if(value.split("-").length>1){
				value=value.split("-")[1];
			}
			int flag=0;
			int rows=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,fieldText+"//tr")).size();
			System.out.println("the number of rows are---------"+rows);
			for(int r=1;r<=rows;r++)
			{
				System.out.println("----------------"+webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText+"/tr["+r+"]/"+"td"+"[2]")).getText());
				if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText+"/tr["+r+"]")).getText().trim().contains("Cancel Order") &&webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText+"/tr["+r+"]")).getText().trim().contains(value.trim())){
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText+"/tr["+r+"]/"+"td"+"[2]"+"//a")).click();
					flag=1;						
					break;
				}
			}	
			if(flag==1)
			{ 
				System.out.println("Element found and clicked on it");
				resultDetails.setFlag(true);
			}else {
				resultDetails.setFlag(false);
				sd.log.debug("Order ID or Cancel Order Link not available");
				resultDetails.setErrorMessage("Order ID or Cancel Order Link not available");
			}
			return resultDetails;	
		}catch (Exception e) {
			System.out.println("exception value : " + e.getMessage());	
			sd.log.error("Exception in cancelorder: " + e.getMessage());		
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element not found");
			return resultDetails;
		}			
	}


	/*'#########################################################################################################
	'Function name		:	VERIFYTEXTNOTPRESENT
	'Description		:	This function is to verify whether the given text is not present in the specified field

	'Parameters			:	fieldText parameter should starts with XPH/HMV followed bye object id/path
							value parameter should be given as text that we need to find.(Eg: $)
							Ex: XPH//div[@id='dd-content']//dl[@class='price-matrix']/dd[1] 	$               
	'#########################################################################################################*/

	public ResultDetails verifyTextNotPresent(WebDriver webdriver, String field, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		sd.log.info("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());		
		resultDetails.setFlag(false);
		if(value!=null&&(value.equals(""))) {
			sd.log.info("DataValue should not be empty");
			resultDetails.setErrorMessage(" Value Field is empty ");
			return resultDetails;
		}
		value = getValue(value);
		switch (dfs) {
		case XPH:
			try {                             
				sd.log.debug("text=="+webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText() + " value="+ value);
				sd.log.debug("res=="+webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText().contains(value));
				if(!webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText().trim().toLowerCase().contains(value.trim().toLowerCase())) {
					resultDetails.setFlag(true);
				} else if(!webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getAttribute("value").trim().toLowerCase().contains(value.trim().toLowerCase())) {
					resultDetails.setFlag(true);
				} else {
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Value ::	'"+value+" present in the string : " +webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText());
				}
			}catch (Throwable e) {
				sd.log.error("Exception in verifyTextNotPresent: " + e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Element not found:" + value);
				return resultDetails;
			}
			break;
		case HMV:
			try{
				String dataValue= sd.hMap.get(value);
				if(!webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText().contains(dataValue)){
					resultDetails.setFlag(true);
				}else {
					sd.log.error("The element not found");
					resultDetails.setFlag(false);
				}
			}catch(Exception e) {
				sd.log.error("Exception in verifyTextNotPresent: " + e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The order number not Found");
				return resultDetails;
			}
			break;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	VERIFYSAVINGS
	'Description		:	This function is to verify the savings(%) in the dailydeals workflow

	'Parameters			:	fieldText parameter should be given as object id/path
							value parameter should be given as variablename							
            				Ex:<retailprice locator>::<points locator>  <variablename>
            				//dd[@class='retail']:://dd[@class='incentives']	Savingspercent
	'#########################################################################################################*/

//	public ResultDetails verifySavings (WebDriver webdriver, String fieldText, String value, String fieldName) {
//		ResultDetails resultDetails = new ResultDetails();
//		System.out.println("SetPromo ClientName "+sd.hMap.get("ClientName"));
//		String fieldClassAttribute ;		
//		Double savings;
//			DataFileds dfs = DataFileds.valueOf(fieldText.substring(0, 3));
//			fieldText = fieldText.substring(3, fieldText.length());	
//			String numArray[] = fieldText.split("::");
//			switch (dfs) {
//				case DDL:
//					try{
//						fieldClassAttribute =webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getAttribute("class");
//						numArray[0]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[0])).getText().substring(1);
//						numArray[1]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getText().substring(1);    
//						numArray[0] = numArray[0].replaceAll(",","");
//						numArray[1] = numArray[1].replaceAll(",","");
//						if(fieldClassAttribute.equals("incentives") && !numArray[1].contains("."))
//							savings = (Double.parseDouble(numArray[1]))/(Double.parseDouble(numArray[0]));
//						else 
//							savings =((Double.parseDouble(numArray[1])*100)/(Double.parseDouble(numArray[0])));
//						sd.log.info("Savings is: "+savings);
//						String savingsPercent[]= String.valueOf(savings).split("\\.");
//						sd.log.info("Savings percent: "+savingsPercent[0]);
//						sd.log.info("Savings percent: "+savingsPercent[1]);
//						resultDetails.setFlag(true);
//						if(sd.hMap.get("ClientName").equalsIgnoreCase("TGI Fridays"))
//							sd.hMap.put(value, savingsPercent[0]+" %");
//						else 
//							sd.hMap.put(value, savingsPercent[0]+"%");
//												
//					}catch(Exception e) {
//						System.out.println("exception value : " + e.getMessage());    
//						sd.log.error("Exception in verifySavings: " + e.getMessage());       
//						resultDetails.setFlag(false);                                 
//						resultDetails.setErrorMessage("Wait Error: "+e.getMessage());
//						return resultDetails;                    
//					}
//					break;
//				case CHK:
//			        try{
//			              fieldClassAttribute =webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getAttribute("class");
//			              numArray[0]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[0])).getText().substring(1);
//			              numArray[1]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getText().substring(1);    
//			              numArray[0] = numArray[0].replaceAll(",","");
//			              numArray[1] = numArray[1].replaceAll(",","");
//			              if(fieldClassAttribute.equals("incentives") && !numArray[1].contains(".")) 
//			                    savings = (Double.parseDouble(numArray[1]))/(Double.parseDouble(numArray[0]));
//			              else 
//			                    savings =((Double.parseDouble(numArray[1])*100)/(Double.parseDouble(numArray[0])));			             
//			              double roundOff = Math.round( savings);
//			              sd.log.info("Savings is: "+savings);
//			              sd.log.info("Savings is: "+roundOff);             
//			              String savingsPercent[]= String.valueOf(roundOff).split("\\.");
//			              sd.log.info("Savings percent: "+savingsPercent[0]);
//			              sd.log.info("Savings percent: "+savingsPercent[1]);
//			              resultDetails.setFlag(true);
//			              if(sd.hMap.get("ClientName").equalsIgnoreCase("TGI Fridays"))
//			                    sd.hMap.put(value, savingsPercent[0]+" %");
//			              else 
//			                    sd.hMap.put(value, savingsPercent[0]+"%");			            
//			        }catch(Exception e) {
//			              System.out.println("exception value : " + e.getMessage());    
//			              sd.log.error("Exception in verifySavings: " + e.getMessage());       
//			              resultDetails.setFlag(false);                                 
//			              resultDetails.setErrorMessage("Wait Error: "+e.getMessage());
//			              return resultDetails;                    
//			        }
//			        break;	
//			}
//			return resultDetails;	
//		}		
	public ResultDetails verifySavings (WebDriver webdriver, String fieldText, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		System.out.println("SetPromo ClientName "+sd.hMap.get("ClientName"));
		String fieldClassAttribute ;		
		Double savings;
			DataFileds dfs = DataFileds.valueOf(fieldText.substring(0, 3));
			fieldText = fieldText.substring(3, fieldText.length());	
			String numArray[] = fieldText.split("::");
			switch (dfs) {
				case DDL:
					try{
						fieldClassAttribute =webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getAttribute("class");
						numArray[0]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[0])).getText().substring(1);
						numArray[1]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getText().substring(1);    
						numArray[0] = numArray[0].replaceAll(",","");
						numArray[1] = numArray[1].replaceAll(",","");
						if(fieldClassAttribute.equals("incentives") && !numArray[1].contains(".") && Double.parseDouble(numArray[1])%100==0)						
							savings = (Double.parseDouble(numArray[1]))/(Double.parseDouble(numArray[0]));
						else 
							savings =((Double.parseDouble(numArray[1])*100)/(Double.parseDouble(numArray[0])));
						sd.log.info("Savings is: "+savings);
						String savingsPercent[]= String.valueOf(savings).split("\\.");
						sd.log.info("Savings percent: "+savingsPercent[0]);
						sd.log.info("Savings percent: "+savingsPercent[1]);
						resultDetails.setFlag(true);
						if(sd.hMap.get("ClientName").equalsIgnoreCase("TGI Fridays"))
							sd.hMap.put(value, savingsPercent[0]+" %");
						else 
							sd.hMap.put(value, savingsPercent[0]+"%");
												
					}catch(Exception e) {
						System.out.println("exception value : " + e.getMessage());    
						sd.log.error("Exception in verifySavings: " + e.getMessage());       
						resultDetails.setFlag(false);                                 
						resultDetails.setErrorMessage("Wait Error: "+e.getMessage());
						return resultDetails;                    
					}
					break;
				case CHK:
			        try{
			              fieldClassAttribute =webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getAttribute("class");
			              numArray[0]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[0])).getText().substring(1);
			              numArray[1]=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[1])).getText().substring(1);    
			              numArray[0] = numArray[0].replaceAll(",","");
			              numArray[1] = numArray[1].replaceAll(",","");
			              if(fieldClassAttribute.equals("incentives") && !numArray[1].contains(".") && Double.parseDouble(numArray[1])%100==0) 
			                    savings = (Double.parseDouble(numArray[1]))/(Double.parseDouble(numArray[0]));
			              else 
			                    savings =((Double.parseDouble(numArray[1])*100)/(Double.parseDouble(numArray[0])));			             
			              double roundOff = Math.round( savings);
			              sd.log.info("Savings is: "+savings);
			              sd.log.info("Savings is: "+roundOff);             
			              String savingsPercent[]= String.valueOf(roundOff).split("\\.");
			              sd.log.info("Savings percent: "+savingsPercent[0]);
			              sd.log.info("Savings percent: "+savingsPercent[1]);
			              resultDetails.setFlag(true);
			              if(sd.hMap.get("ClientName").equalsIgnoreCase("TGI Fridays"))
			                    sd.hMap.put(value, savingsPercent[0]+" %");
			              else 
			                    sd.hMap.put(value, savingsPercent[0]+"%");			            
			        }catch(Exception e) {
			              System.out.println("exception value : " + e.getMessage());    
			              sd.log.error("Exception in verifySavings: " + e.getMessage());       
			              resultDetails.setFlag(false);                                 
			              resultDetails.setErrorMessage("Wait Error: "+e.getMessage());
			              return resultDetails;                    
			        }
			        break;	
			}
			return resultDetails;	
		}


	/*'#########################################################################################################
	'Function name		:	VERIFYCARTCOUNT
	'Description		:	This function is to verify the  increase/decrease in shopping cart count

	'Parameters			:	fieldText parameter should start with PRM/CRT followed by object id/path
							value parameter should be givenas variablename
							Ex: PRM<shopping cart icon locator>     <variablename> 
								PRM//li[@id='shopping-cart-icon']//a	Cartcount
	'#########################################################################################################*/

	public ResultDetails verifyCartCount(WebDriver webdriver,String fieldText, String value){
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		System.out.println("field = "+field);
		sd.log.info("field = "+field);	
		try{
			EnterDataFileds edf = EnterDataFileds.valueOf(fieldType
					.toUpperCase());
			String prevShoppingText,latestShoppingText;
			int prevShoppingCount,latestShoppingCount;
			switch (edf) {
			case PRM:
				if(value!=null && value!="" && sd.hMap.get(value)!=null && sd.hMap.get(value)!="-1"){
					prevShoppingText = sd.hMap.get(value);   
					if(prevShoppingText.indexOf("(")!=-1 && prevShoppingText.indexOf(")")!=-1){
						prevShoppingCount =  Integer.parseInt(prevShoppingText.substring(prevShoppingText.indexOf("(")+1, prevShoppingText.indexOf(")")));
						latestShoppingText = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
						latestShoppingCount =  Integer.parseInt(latestShoppingText.substring(latestShoppingText.indexOf("(")+1, latestShoppingText.indexOf(")")));
					}else{
						prevShoppingCount =  Integer.parseInt(prevShoppingText);
						latestShoppingText = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
						latestShoppingCount =  Integer.parseInt(latestShoppingText);
					}
					AssertJUnit.assertEquals(latestShoppingCount,prevShoppingCount+1);
					resultDetails.setFlag(true);
				}else{
					latestShoppingText = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
					if(latestShoppingText.indexOf(")")!=-1){
						latestShoppingCount =  Integer.parseInt(latestShoppingText.substring(latestShoppingText.indexOf("(")+1, latestShoppingText.indexOf(")")));
					}else{
						latestShoppingCount =  Integer.parseInt(latestShoppingText);
					}
					AssertJUnit.assertEquals(latestShoppingCount,1);
					resultDetails.setFlag(true); 
				}
				break;
			case CRT:
				String valueArray[]=value.split("::");
				String fieldArray[]=field.split("::");
				int expectedvalue;
				if(value!=null && value!="" && sd.hMap.get(valueArray[1])!=null && sd.hMap.get(valueArray[1])!="-1"){
					prevShoppingText = sd.hMap.get(valueArray[1]);	
					if(prevShoppingText.indexOf("(")!=-1 && prevShoppingText.indexOf(")")!=-1){
						prevShoppingCount =  Integer.parseInt(prevShoppingText.substring(prevShoppingText.indexOf("(")+1, prevShoppingText.indexOf(")")));
						latestShoppingText = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldArray[2])).getText();
						latestShoppingCount =  Integer.parseInt(latestShoppingText.substring(latestShoppingText.indexOf("(")+1, latestShoppingText.indexOf(")")));
					}else{
						prevShoppingCount =  Integer.parseInt(prevShoppingText);
						latestShoppingText = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldArray[2])).getText();
						latestShoppingCount =  Integer.parseInt(latestShoppingText);		
					}
					int prevQuanity= Integer.parseInt(sd.hMap.get(valueArray[0]));
					if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldArray[1])).getAttribute("value").trim().toLowerCase().equals("update")){
						int latestQuantity = Integer.parseInt(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, fieldArray[0])).getAttribute("value").trim());
						expectedvalue = prevShoppingCount+(latestQuantity-prevQuanity);
					}else{
						expectedvalue = prevShoppingCount-prevQuanity;	
					}
					if(expectedvalue!=0)
						AssertJUnit.assertEquals(latestShoppingCount,expectedvalue);
					else
						AssertJUnit.assertEquals(latestShoppingCount,"");
					resultDetails.setFlag(true);
				}else{
					latestShoppingText = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
					if(latestShoppingText.indexOf(")")!=-1)
						latestShoppingCount =  Integer.parseInt(latestShoppingText.substring(latestShoppingText.indexOf("(")+1, latestShoppingText.indexOf(")")));
					else
						latestShoppingCount =  Integer.parseInt(latestShoppingText);
					AssertJUnit.assertEquals(latestShoppingCount,1);
					resultDetails.setFlag(true); 
				}
			}	
		}catch (AssertionError e) {
			sd.log.error("Assertion error: " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}catch(Exception e){
			sd.log.error("Exception in verifyCartCount: " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to store random name");
			return resultDetails;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	CALCULATION
	'Description		:	This function is to perform the calculation on the given values.(If the text starts with "-" will perform subtraction else will perform addition)

	'Parameters			:	fieldText parameter should start with PRM/CRT followed by object id/path separated by ::
							value parameter should be givenas variablename
							Ex: <locator1>::<locator2>::<locator3>  <variablename>
                                //div[@class='finalprice_container']/table//dd:://div[@class='finalprice_container']/table//dd[2]
	'#########################################################################################################*/

	public ResultDetails calculation (WebDriver webdriver, String fieldText, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		sd.log.info("SetPromo ClientName "+sd.hMap.get("ClientName"));
		DecimalFormat df = new DecimalFormat("#.00");
		String fieldClassAttribute;
		double sum = 0;
		try {
			String numArray[] = fieldText.split("::");
			for (int i=0; i<numArray.length; i++) {
				Thread.sleep(3000);
				try {
					if (numArray[i].startsWith("HMV")) {
						numArray[i]=getValue(numArray[i]);
						numArray[i]=numArray[i].replaceAll("[,$]", "");                               
						sum = sum - Double.parseDouble(numArray[i]);                               
						sum = Double.valueOf(df.format(sum));
					} else {
						fieldClassAttribute = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[i])).getAttribute("class");    
						numArray[i] = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, numArray[i])).getText(); 
						numArray[i]=numArray[i].replaceAll("[,$]", "");
						System.out.println(fieldClassAttribute);
						if(fieldClassAttribute!=null &&!fieldClassAttribute.equals("")&&!fieldClassAttribute.equals("price")){
							if (numArray[i].contains("–") ||numArray[i].contains("-")) {
								numArray[i]=numArray[i].replaceAll("[-–]","");                                            
								if((fieldClassAttribute.equals("incentives")|| fieldClassAttribute.equals("cashrewards")) && !numArray[i].contains(".") && Double.parseDouble(numArray[i])%100==0) {
									sum = sum - (Double.parseDouble(numArray[i])/100);
								}else{
									sum = sum - Double.parseDouble(numArray[i]);
								}
							} else {
								sum = sum + Double.parseDouble(numArray[i]);
							}
							sum = Double.valueOf(df.format(sum));
						}
					}
				}catch (ArithmeticException e) {
					sd.log.error("ArithmeticException in calculation: " + e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("ArithmeticException in calculation:"+e.getMessage());  
					return resultDetails;
				}
				System.out.println("Final Summary Value is : "+sum);
			}
			String[] s = String.valueOf(sum).split("\\.");
			if(s[s.length - 1].length()<2) {
				sd.hMap.put(value, "$"+sum+"0");
			}else {
				sd.hMap.put(value, "$"+sum);
			}
			resultDetails.setFlag(true);
		}catch (Exception e) {
			sd.log.error("Exception in calculation: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Exception in calculation: "+e.getMessage());
			return resultDetails;
		}
		return resultDetails;
	}

	/*'#########################################################################################################
	'Function name		:	VERIFYREWARDPROMOPOINTS
	'Description		:	This function is to verify the rewardpoints

	'Parameters			:	fieldText parameter should start with PRM/CRT followed by object id/path separated by ::
							value parameter should be givenas variablename
							Ex: <locator1>::<locator2>::<locator3>  <variablename>
                                //div[@class='finalprice_container']/table//dd:://div[@class='finalprice_container']/table//dd[2]
	'#########################################################################################################*/

	public ResultDetails verifyRewardPromoPoints(WebDriver webdriver,String fieldText, String value){
		ResultDetails resultDetails = new ResultDetails();
		try{
			String field = fieldText.substring(3, fieldText.length());
			resultDetails.setFlag(false);   
			if(sd.hMap.get(value.split(",")[0])!=null && sd.hMap.get(value.split(",")[1])!=null){
				String PrevPoints =  sd.hMap.get(value.split(",")[0]);
				PrevPoints= PrevPoints.replaceAll(",", "");
				String promoText[] = sd.hMap.get(value.split(",")[1]).split("\\s");
				String rewardPoints = String.valueOf(Float.parseFloat(promoText[0])+Float.parseFloat(PrevPoints));
				String latestPoints = webdriver.findElement(By.xpath(field)).getText().replaceAll(",", "");
				AssertJUnit.assertEquals(rewardPoints,latestPoints);
				resultDetails.setFlag(true);           
			}
			return resultDetails;			
		}catch (AssertionError e) {
			System.out.println("AssertionError : " + e.getMessage());	
			sd.log.info("AssertionError : " + e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}catch(Exception e) {
			System.out.println("Exception is : " + e.getMessage());	
			sd.log.error("Exception in verifyRewardPromoPoints: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to verifyRewardPromoPoints");
			return resultDetails;
		}

	}


	/*'#########################################################################################################
	'Function name		:	SETPROMODETAILS
	'Description		:	This function is to clientname and program id in the promo code creation workflow

	'Parameters			:	N/A
	'#########################################################################################################*/

	public ResultDetails setPromoDetails(WebDriver webdriver)
	{
		ResultDetails resultDetails = new ResultDetails();
		String clientInfo;
		WebElement element;
		JavascriptExecutor js;
		try{
			element = webdriver.findElement(WebDriverUtils
					.locatorToByObj(webdriver, "client_name"));
			js = (JavascriptExecutor) webdriver;
			js.executeScript("arguments[0].click();", element);
			Thread.sleep(2000);
			clientInfo = CreateClient.clientID.get(sd.hMap.get("ClientName").toUpperCase());	                
			for(int i=0;i<clientInfo.length();i++) {
				element.sendKeys(clientInfo.substring(i, i+1));
			}
			sd.log.info("SetPromo ClientID "+element);
			element.sendKeys(" ");
			element.sendKeys("- ");
			clientInfo = sd.hMap.get("ClientName");	                
			for(int i=0;i<clientInfo.length()-3;i++) {
				element.sendKeys(clientInfo.substring(i, i+1));
			}
			sd.log.info("SetPromo ClientName "+element);
			String url = webdriver.getCurrentUrl();
			DefaultSelenium seleniumObj = new WebDriverBackedSelenium(webdriver,url);
			Thread.sleep(800);
			seleniumObj.fireEvent("client_name", "keydown");

			Thread.sleep(10000);
			element = webdriver.findElement(WebDriverUtils
					.locatorToByObj(webdriver, "//div[@class='ac_results']/ul/li"));
			js = (JavascriptExecutor) webdriver;
			js.executeScript("arguments[0].click();", element);
			Thread.sleep(14000);
			Select select = new Select(webdriver.findElement(By.id("program_list")));
			select.selectByValue(sd.hMap.get("ProgramID"));
			Thread.sleep(6000);
			element = webdriver.findElement(WebDriverUtils
					.locatorToByObj(webdriver, "program_add"));
			js = (JavascriptExecutor) webdriver;
			js.executeScript("arguments[0].click();", element);
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
			return resultDetails;
		}catch(Exception e) {
			sd.log.error("Exception in setPromoDetails: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to setPromoDetails");			
			return resultDetails;
		}
	}

	/*'#########################################################################################################
	'Function name		: 	SWITCHTODEFAULT
	'Description		:	This function is to select the default frame

	'Parameters			:	N/A
	'#########################################################################################################*/
	public ResultDetails switchtodefault(WebDriver webdriver)
	{
		try{
			webdriver.switchTo().defaultContent();
			resultDetails.setFlag(true);
			return resultDetails;
		}
		catch(Exception e){
			System.out.println("ERROR---------------------" +e.toString());
			sd.log.error("Exception in switchtodefault: " +e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to select default frame");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		: 	STORERNDNAME
	'Description		:	This function is to store the value in the hasmap

	'Parameters			:	name parameter should be given as HMV

							Ex: 
	'#########################################################################################################*/
	public ResultDetails storeRndName(WebDriver webdriver, String name,String key) {
		try{
			name = getValue(name);
			sd.hMap.put(key, name);
			resultDetails.setFlag(true);
			return resultDetails;
		} catch (Exception e) {
			sd.log.error("Exception in storeRndName: " +e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to store random name");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		: 	CHECKYOUPAY
	'Description		:	This function is to verify the youpay value

	'Parameters			:	field = Hotel Rate::SavingDollars/Points::YouPay
	       					YouPay = Hotel Rate - SavingDollars(or)Points
	     				    //div[@id='total-rates']/dl/dt[2]:://div[@id='total-rates']/dl/dt[3]:://div[@id='total-rates']/dl/dt[4]
	'#########################################################################################################*/

	public ResultDetails checkYouPay(WebDriver webdriver, String fieldText,String value) {
		ResultDetails resultDetails = new ResultDetails();
		try{
			String actRate = webdriver.findElement(By.xpath(fieldText.split("::")[0])).getText();
			String savings = webdriver.findElement(By.xpath(fieldText.split("::")[1])).getText();
			String pay = webdriver.findElement(By.xpath(fieldText.split("::")[2])).getText();
			actRate = replacePriceChar(actRate);
			savings = replacePriceChar(savings);
			Double savings2 = Double.parseDouble(savings);
			sd.log.info("1st Rate: "+actRate);
			sd.log.info("1st Save: "+savings);
			sd.log.info("1st YouPay displayed: "+pay);
			pay = replacePriceChar(pay);
			if(savings!=null && (savings.length()>2)&&(!savings.contains(".")))  {
				savings = savings.substring(0,savings.length()-2);
			}
			Double Rate = Double.parseDouble(actRate);
			Double Save = Double.parseDouble(savings);
			Double youPay = Double.parseDouble(pay);
			sd.log.info("Rate: "+Rate);
			sd.log.info("Save: "+Save);
			sd.log.info("YouPay displayed: "+youPay);
			DecimalFormat df = new DecimalFormat("#.00");
			if(Save>Rate) {
				Save = Save/100;
			}
			Double f3 = Rate-Save;
			Double finalYouPay = Double.valueOf(df.format(f3));
			sd.log.info("Expected YouPay: "+finalYouPay);
			System.out.println("Expected YouPay: "+finalYouPay);
			int comp = Double.compare(youPay, finalYouPay);
			if(comp==0) {
				System.out.println("Youpay successful");
				resultDetails.setFlag(true);
			}else {
				try{
					Double f4 = Rate-savings2;
					Double finalYouPay2 = Double.valueOf(df.format(f4));
					int comp2 = Double.compare(youPay, finalYouPay2);
					if(comp2==0)
					{
						resultDetails.setFlag(true);
					}

					else{
						sd.log.debug("Youpay calculation failed:: Rate=" +Rate+". Save="+Save+". Actual Youpay= "+youPay+". Expected youpay= "+finalYouPay);
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("Youpay calculation failed:: Rate=" +Rate+". Save="+Save+". Actual Youpay= "+youPay+". Expected youpay= "+finalYouPay);
					}
				}
				catch(Exception e){
					sd.log.error("Youpay calculation failed: "+e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Youpay calculation failed");
				}
			}
		}catch(Exception e) {
			sd.log.error("Youpay calculation failed: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Youpay calculation failed");
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		: 	REPLACEPRICECHAR
	'Description		:	This function is to replace the $,-,— and comma with empty space in the given string

	'Parameters			:	str parameter should be a string
	'#########################################################################################################*/

	private String replacePriceChar(String str)
	{
		try{
			str = str.replaceAll("[—$,-]", "");
			str = str.trim(); 
		}catch(Exception e)	{
			sd.log.error("Exception: "+e.getMessage());
		}
		return str;
	}


	/*'#########################################################################################################
	'Function name		: 	SELECTFRAMEBYINDEX
	'Description		:	This function is to select the applicationwindow frames by index

	'Parameters			:	value parameter should be given as numeric. (Frame1: index=0 ;; Frame2: index=1)
	'#########################################################################################################*/

	public ResultDetails selectIframeByIndex(WebDriver webdriver, String value) {
		ResultDetails resultDetails = new ResultDetails();
		try{
			webdriver.switchTo().frame(Integer.parseInt(value));
			resultDetails.setFlag(true);
			return resultDetails;
		}catch (Exception e) {
			sd.log.error("Iframe with index:: "+value+" not found");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Iframe with index:: "+value+" not found");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		: 	SELECTBUDGETWINDOW
	'Description		:	

	'Parameters			:	
	'#########################################################################################################*/

	public ResultDetails selectBudgetWindow(WebDriver webdriver, String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		try {
			String parentWindHandle = "";
			try {
				parentWindHandle = webdriver.getWindowHandle();
				WebDriver popup = null;					
				Set<String> arr = webdriver.getWindowHandles();
				for(String handle : arr)
				{
					if(!handle.equals(parentWindHandle))
					{
						popup = webdriver.switchTo().window(handle);
						System.out.println("New window title:: "+webdriver.getTitle());
						break;
					}
				}

				Thread.sleep(1000);					
				System.out.println("Clicking on "+field+" link in budget popup");
				resultDetails = click(webdriver, fieldText, "", "link");
				System.out.println("Clicked on "+field+" link in budget popup");
				if(!resultDetails.getFlag()){
					popup.close();
					System.out.println("Popup closed");

				}
				webdriver.switchTo().window(parentWindHandle);

				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("");
			}catch (Throwable e) {
				webdriver.switchTo().window(parentWindHandle);
				System.out.println(value+"   ::Title not found");
				sd.log.debug(value+"   ::Title not found");
				resultDetails.setFlag(false);					
				resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
				sd.log.debug("Window with title   ::+" +value +   "::not Found");
				return resultDetails;
			}

			return resultDetails;

		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(value+"   ::Title not found");
			sd.log.debug(value+"   ::Title not found");
			resultDetails.setFlag(false);			
			resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
			sd.log.debug("Window with title   ::+" +value +   "::not Found");
			return resultDetails;
		}
	}

	/*'#########################################################################################################
	'Function name		:	VERIFYPRESENT
	'Description		:	This function is  to verify whether the expected values/fields are present in the given location/web page
	'						
	'Parameters			:	field parameter should be given as MSG/BTN/LNK/ACT followed by object path 
							Eg - BTNbuttonID
	'#########################################################################################################*/

	public ResultDetails verifyPresent(WebDriver webdriver, String field, String value) {
		ResultDetails resultDetails = new ResultDetails();
		sd.log.info("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());		
		resultDetails.setFlag(false);
		if (value.substring(0, 3).equals("HMV")) {					
			value = sd.hMap.get(value.substring(3));
		}
		switch (dfs) {
		case MSG:
			try {
				AssertJUnit.assertTrue(webdriver.getPageSource().toLowerCase().trim().contains(value.toLowerCase().trim()));
				resultDetails.setFlag(true);
			}catch (Throwable e) {
				System.out.println("Text :: +"+value+"   :: found which is NOT expected.");
				sd.log.debug("Text :: +"+value+"   :: found which is NOT expected.");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text :: +"+value+"   :: found which is NOT expected.");
				return resultDetails;
			}
			break;
		case BTN:			 
			try {				
				AssertJUnit.assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
			}catch (Throwable e) { 
				System.out.println("Object :: +"+value+"   :: found which is NOT expected.");
				sd.log.error("Object :: +"+value+"   :: found which is NOT expected."+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Object :: +"+value+"   :: found which is NOT expected.");
				return resultDetails;
			}
			break;
		case IMG:			 
			try {				
				AssertJUnit.assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
			}catch (Throwable e) {
				System.out.println("Object :: +"+value+"   :: found which is NOT expected.");
				sd.log.error("Object :: +"+value+"   :: found which is NOT expected."+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Object :: +"+value+"   :: found which is NOT expected.");
				return resultDetails;
			}
			break;
		case LNK:			
			try {
				AssertJUnit.assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
			}catch (Throwable e) {
				System.out.println("Link object :: +"+value+"   :: found which is NOT expected.");
				sd.log.error("Link object :: +"+value+"   :: found which is NOT expected."+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Link object :: +"+value+"   :: found which is NOT expected.");
				return resultDetails;
			}
			break;
		case ACT:
			value=getValue(value);
			try {
				AssertJUnit.assertTrue(WebDriverUtils.isElementPresent(webdriver,field+"[contains(@href,'"+value+"')]"));
				resultDetails.setFlag(true);
			}catch (Throwable e) {
				System.out.println("Text :: +"+value+"   :: found which is NOT expected.");
				sd.log.error("Text :: +"+value+"   :: found which is NOT expected."+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text :: +"+value+"   :: found which is NOT expected.");
				return resultDetails;
			}

			break;
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	SELECTOPTIONBYPOSITION
	'Description		:	This function is to select the option in a combo box by postion (If not identified by index concept).


	'Parameters			:	fieldText should starts with XPH and followed by object id/path
							value: 0 - select first value,1- second second value.
	 						if value need to be selct by javascript, value should start with js:, i,e., "js:1" and fieldtext should start with COB
	 						Ex: XPH//select[@id='selectBox']  js:1
	'#########################################################################################################*/

	public ResultDetails selectOptionByPosition(WebDriver webdriver, String fieldText,String value) {
		ResultDetails resultDetails = new ResultDetails();
		String field = fieldText.substring(3, fieldText.length());
		try{
			WebElement select = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field));
			Select dropDown = new Select(select);           
			List<WebElement> Options = dropDown.getOptions();
			for(int i=0;i<Options.size();i++){
				sd.log.info("Options value:: "+Options.get(i).getText());
				int index = 0;
				if(value.contains("js")) {
					String str1 = value.split(":")[1];
					index = Integer.parseInt(str1);
				}else
					index = Integer.parseInt(value);
				if(i==index){
					Thread.sleep(2000);

					if(value.contains("js"))
						select(webdriver,fieldText,Options.get(i).getText());
					else
						dropDown.selectByVisibleText(Options.get(i).getText());}
			}
			resultDetails.setFlag(true);
			return resultDetails;
		} catch (Exception e) {
			sd.log.error("Exception in selectOptionByPosition: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.getMessage());
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	WAITITME
	'Description		:	This function is to holdon the execution process for sometime	

	'Parameters			:	Value should be given as numeric (i.e. 10/20)
	'#########################################################################################################*/

	public ResultDetails waitTime(WebDriver webdriver, String value)
	{
		ResultDetails resultDetails = new ResultDetails();
		try {
			if(value==null || value.equals("") || (Integer.parseInt(value)>100)){
				value="10";
			}
			int sleepTime = Integer.parseInt(value)*1000;
			System.out.println("sleepTime: "+sleepTime);
			sd.log.debug("sleepTime: "+sleepTime);
			Thread.sleep(sleepTime);
			resultDetails.setFlag(true);
		}catch (Exception e) {
			System.out.println("exception value : " + e.getMessage());	
			sd.log.error("Wait Error: "+e.getMessage());	
			resultDetails.setFlag(false);					
			resultDetails.setErrorMessage("Wait Error: "+e.getMessage());	
		}
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	GETVALUE
	'Description		:	This function is to get the actual value for the Data Values column

	'Parameters			:	value passed from the data sheet starts with RND/RNE/HMVC/DDL/d:
							RND - To generate random number,HMV - value wil be passed as key to hashmap,DDL - to get the value in the dailydealcategory hashmap
							If value does not starts with RND/RNE/HMVC/DDL/d: then the function return the same value. 
	'#########################################################################################################*/
	public String getValue(String value) {
		if (value.length() > 3) {
			if (value.substring(0, 3).equals("RND")) {
				Random randomGenerator = new Random();
				long intRandom = randomGenerator.nextInt(99000)+10000;
				value = value.substring(3, value.length())+intRandom;
			}else if (value.substring(0, 3).equals("RNE")) {
				// Random randomGenerator = new Random();
				String Rndnum = value.substring(3, value.length()) + RandomStringUtils.randomNumeric(5);
				String email = Rndnum + "@" + value.substring(3, value.length()) + ".com";
				value = email;
			}else if (value.substring(0, 3).equals("HMV")) {
				value = sd.hMap.get(value.substring(3));
			} else if (value.substring(0, 3).equals("DDL")) {
				value=CreateClient.dailyDealCategory.get(sd.hMap.get(value.substring(3)).toUpperCase());
			}else if (value.indexOf("d:") != -1) {
				value = getDate(value);
			} else if (value.startsWith("dt:")) {					
				if (value.substring(3).indexOf("#") == -1) {			
					value = value+"1";
				}
				if (sd.parameterDetails.containsKey(value.substring(3).replace("#","").toLowerCase())) {
					value = sd.parameterDetails.get(value.substring(3).replace("#","").toLowerCase());
				}else if (value.toLowerCase().startsWith("dt:")&& (value.toLowerCase().endsWith("#admin") || value.toLowerCase().endsWith("#gmail"))) {
					value = value.substring(3,value.length()-6);
					String browser=sd.Browser.toString().toLowerCase();

					if (sd.parameterDetails.containsKey(value.toLowerCase()+"_"+browser.toLowerCase()+"1")) {
						value = sd.parameterDetails.get(value.toLowerCase()+"_"+browser.toLowerCase()+"1");
					} else {
						System.out.println("ERROR : Unable to find the value for the Parameter '"+value+"_"+browser+"' in the Hasp Map.");	
						sd.log.info("ERROR : Unable to find the value for the Parameter '"+value+"_"+browser+"' in the Hasp Map.");
					}
				}
				//				else if (value.toLowerCase().startsWith("dt:")&& value.toLowerCase().endsWith("#gmail")) {
				//				value = value.substring(3,value.length()-6);
				//				String browser=sd.Browser.toString().toLowerCase();
				//
				//				if (sd.parameterDetails.containsKey(value.toLowerCase()+"_"+browser.toLowerCase()+"1")) {
				//					value = sd.parameterDetails.get(value.toLowerCase()+"_"+browser.toLowerCase()+"1");
				//				} else {
				//					System.out.println("ERROR : Unable to find the value for the Parameter '"+value+"_"+browser+"' in the Hasp Map.");	
				//					sd.log.info("ERROR : Unable to find the value for the Parameter '"+value+"_"+browser+"' in the Hasp Map.");
				//				}
				//			}
				else {
					System.out.println("ERROR : Unable to find the value for the Parameter '"+value+"' in the Hasp Map.");	
					sd.log.info("ERROR : Unable to find the value for the Parameter '"+value+"' in the Hasp Map.");
				}
			}

		}
		System.out.println("value = "+value);
		sd.log.info("value = "+value);
		return value;
	}

	/*'#########################################################################################################
	'Function name		:	GETMENUANDMOUSEOVER
	'Description		:	This function is to DO MOUSEOVER over a locator

	'Parameters			:	field should be given as object id/path
	'#########################################################################################################*/

	public void getMenuAndMouseOver(WebDriver webdriver, String field) {
		ResultDetails resultDetails = new ResultDetails();
		boolean isitSubMenu = false;
		String Id = "";
		WebElement myElement = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field));
		if ((field.equalsIgnoreCase("Content Libraries")) || (field.equalsIgnoreCase("Libraries & Subscriptions")) || (field.equalsIgnoreCase("Quick Links")) || (field.equalsIgnoreCase("My Text Variables")) || (field.equalsIgnoreCase("Bulk Upload Files"))) {
			isitSubMenu = true;
			Id = "//a[contains(text(),'Manage Content')]";
		} else if ((field.equalsIgnoreCase("Enable Site Modules")) || (field.equalsIgnoreCase("ToDo List")) || (field.equalsIgnoreCase("FAQ Knowledgebase")) || (field.equalsIgnoreCase("Message Center")) || (field.equalsIgnoreCase("Training Programs"))) {
			Id = "//a[contains(text(),'Site Modules')]";
		} else if ((field.equalsIgnoreCase("Header")) || (field.equalsIgnoreCase("Main Navigation")) || (field.equalsIgnoreCase("Footer"))) {
			Id = "//a[contains(text(),'Site Design')]";
		} else if ((field.equalsIgnoreCase("Login Page Message"))) {
			Id = "//a[contains(text(),'System Administration')]";
		}
		if (isitSubMenu){
			try{
				myElement= webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, Id));
				Actions builder = new Actions(webdriver);
				builder.moveToElement(myElement).build().perform();			
				Thread.sleep(2000);
				resultDetails.setFlag(true);	
			}catch (Exception e) 	{
				System.out.println("exception value : " + e.getMessage());
				sd.log.error("Exception in getMenuAndMouseOver : " + e.getMessage());
				resultDetails.setFlag(false);					
				resultDetails.setErrorMessage("Element: " + field + " is not found");
			}
		}
	}


	/*'#########################################################################################################
	'Function name		:	SELECT
	'Description		:	This function is to perform the select operations on combobox /list box / radio button / window with specific title

	'Parameters			:	fieldText parameter should be given as COB/RDB/SLB/WND followed by object path/id
							value parameter should be the value that needs to be selected from the combo/list box.
							Ex:SLB//select[@id='selectbox']
	'#########################################################################################################*/

	public ResultDetails select(WebDriver webdriver, String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		String selectBy = "";		
		if(value!=null && !value.startsWith("dt:"))
		{
			if (value.indexOf(":") != -1) {
				selectBy = value.split(":")[0];
				value = value.split(":")[1];
			}
		}				
		value = getValue(value);
		try {
			SelectDataFileds sdf = SelectDataFileds.valueOf(fieldType.toUpperCase());
			Select select = new Select(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)));
			switch (sdf) {
			case COB:
				System.out.println(" In COB " + field + " : " + value);	
				sd.log.info(" In COB " + field + " : " + value);
				if (selectBy.equalsIgnoreCase("text"))
					select.selectByVisibleText(value);
				else if (selectBy.equalsIgnoreCase("value"))
					select.selectByValue(value);
				else if (selectBy.equalsIgnoreCase("index"))
					select.selectByIndex(Integer.parseInt(value));
				else if (selectBy.equalsIgnoreCase("")) {
					try {
						field = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("id");							
						String Script = "";
						Script += "javascript:var s = document.getElementById('"+field+"');";
						Script += "for (i = 0; i< s.options.length; i++){";						
						Script += "   if (s.options[i].text.trim().toUpperCase() == '"+value.toUpperCase()+"'){";
						Script += "      s.options[i].selected = true;";
						Script += "      s.click();";
						Script += "      break;";
						Script += "   }";
						Script += "}";						
						sd.log.debug("Java Script : "+ Script);
						((JavascriptExecutor) webdriver).executeScript(Script);						
						Thread.sleep(2000);
					} catch(Exception e) {
						System.out.println("Exception occured in select : "+e.getMessage());
						sd.log.debug("Exception occured in select : "+e.getMessage());
					}
				}				
				resultDetails.setFlag(true);
				break;
			case RDB:
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).click();
				resultDetails.setFlag(true);
				break;
			case SLB:
				WebDriverUtils.select(webdriver, field, value);
				resultDetails.setFlag(true);
				break;
			case WND:
				webdriver.switchTo().window(value);
				resultDetails.setFlag(true);
				break;
			}
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Option ::"+ value+ ":: not found in Combo box :: "+field);
			sd.log.error("Unable to perform Action Select "+e.getMessage());	
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	SELECTFRAME
	'Description		:	This function is used to switch the selenium driver control to a specific window(i.e parent/new)

	'Parameters			:	fieldText parameter should be given as BTN followed by window id
	                        Ex: BTNParent
	'#########################################################################################################*/

	public ResultDetails selectFrame(WebDriver webdriver, String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());		
		try {
			ClickDataFileds cdf = ClickDataFileds.valueOf(fieldType.toUpperCase());			
			switch (cdf) {			
			case BTN:	
				if(field!=null && field.equalsIgnoreCase("parent")){
					webdriver.switchTo().defaultContent();
				}else
					webdriver.switchTo().frame(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)));
				resultDetails.setFlag(true);
				break;			
			}
			return resultDetails;
		} catch (Exception e) {
			sd.log.error("Exception: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Exception: "+e.getMessage());
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	SELECTFRAMEBYNAME
	'Description		:	This function is used to switch the selenium driver control to a specific frame considering window name

	'Parameters			:	fieldText parameter should be given as BTN followed by window name
	                        Ex: BTNaddwindow
	'#########################################################################################################*/

	public ResultDetails selectFrameByName(WebDriver webdriver, String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);		
		value = getValue(value);
		try {
			ClickDataFileds cdf = ClickDataFileds.valueOf(fieldType.toUpperCase());			
			switch (cdf) {			
			case BTN:					
				webdriver.switchTo().frame(value);
				resultDetails.setFlag(true);
				break;			
			}
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Frame with value: " +value+ "not found");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	SELECTPARENTPAGE
	'Description		:	This function is used to switch the selenium driver control to parent window from child

	'Parameters			:	fieldText parameter should be given as BTN followed by window name
	                        Ex: BTNaddwindow
	'#########################################################################################################*/

	public ResultDetails selectParentPage(WebDriver webdriver) {
		ResultDetails resultDetails = new ResultDetails();
		try {
			webdriver.switchTo().defaultContent();
			resultDetails.setFlag(true);
			return resultDetails;
		} catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Failed to select parent page");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	CLICK
	'Description		:	This function is to click on the link/button/image.

	'Parameters			:	field parameter should be given asHDN/LNK/PLT/BTN/CNF/XPH/IMG/SWC/SWK followed by field path/id
	                        Ex: LNKauctionID
	'#########################################################################################################*/

	public ResultDetails click(WebDriver webdriver, String fieldText, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		sd.log.info("Field ::" + fieldText);
		String fieldType = fieldText.substring(0, 3);

		String field = fieldText.substring(3, fieldText.length());
		field=getField(webdriver,field);
		WebElement element;
		if ((field.length() > 3) && (field.substring(0, 3).equals("HMV"))) {					
			field = sd.hMap.get(field.substring(3));
		}
		try {
			ClickDataFileds cdf = ClickDataFileds.valueOf(fieldType.toUpperCase());
			switch (cdf) {
			case HDN:
				WebElement ele = webdriver.findElement(WebDriverUtils
						.locatorToByObj(webdriver, field));
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].click();", ele);
				resultDetails.setFlag(true);
				break;
			case LNK:
				sd.log.info("BEFORE CLICK on "+field);					
				getMenuAndMouseOver(webdriver, field);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).click();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("Unable to click the object " + fieldName);				
				break;
			case PLT:
				webdriver.findElement(By.partialLinkText(field)).click();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("Unable to click the object " + fieldName);				
				break;	
			case BTN:
				sd.log.info("BEFORE CLICK");	
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).click();	
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("Unable to click the object " + fieldName);
				sd.log.info("Unable to click the object " + fieldName);
				break;					
			case CNF:
				Alert alert = webdriver.switchTo().alert();
				if (value.equalsIgnoreCase("CANCEL")) 
					alert.dismiss();
				else 
					alert.accept();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("Click the object " + fieldName);
				sd.log.info("Click the object " + fieldName);
				sd.log.info("Unable to click the object " + fieldName);
				break;	
			case XPH:	
				sd.log.info("BEFORE CLICK");
				Thread.sleep(7000);
				WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(webdriver, webdriver.getCurrentUrl());
				selenium.click(field);
				selenium.waitForPageToLoad("60000");
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("Unable to click the object " + fieldName);
				sd.log.info("Unable to click the object " + fieldName);
				break;
			case IMG:
				sd.log.info("BEFORE CLICK");
				Actions action = new Actions(webdriver);
				action.moveToElement(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field))).build().perform();
				WebDriverWait waitToBeClickable = new WebDriverWait(webdriver, 10);
				try{
					if(field.toLowerCase().contains("Review Your Order".toLowerCase())){
						System.out.println("for Review Your Order");
					}
					waitToBeClickable.until(ExpectedConditions.elementToBeClickable(WebDriverUtils.locatorToByObj(webdriver, field)));
				}catch(Exception e) {
					System.out.println("==============Unable to wait for Element to be Clickable====================");
				}
				action.click(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field))).perform();
				resultDetails.setFlag(true);
				resultDetails.setErrorMessage("Unable to click the object " + fieldName);
				sd.log.info("Unable to click the object " + fieldName);
				break;
			case SWC:
				String p1 = field.split("::")[0];
				String p2 = field.split("::")[1];
				Thread.sleep(5000);
				List list = webdriver.findElements(By.xpath(p1));
				System.out.println(list.size());
				for(int i=1;i<=list.size();i++){
					System.out.println("xpath is :: " + p1+"["+i+"]"+p2);
					webdriver.findElement(By.xpath(p1+"["+i+"]"+p2)).click();
					Thread.sleep(1000);
				}
				resultDetails.setFlag(true);
				break;
			case SWK:
				List li =webdriver.findElements(By.xpath(field.split("::")[0]));
				int k=li.size();
				String text,text1;
				if(!(k%2==0)){
					k=k-1;
				}
				for(int i=1;i<=k/2;i++) {
					try{
						Thread.sleep(5000);
						webdriver.findElement(By.xpath(field.split("::")[0]+"["+(2*i)+"]"+field.split("::")[1])).click();//a
						Thread.sleep(5000);
						text=webdriver.findElement(By.xpath(field.split("::")[0]+"["+(2*i)+"]"+field.split("::")[1])).getText();
						WebElement ele12 = webdriver.findElement(By.xpath(value.split("::")[0]+"["+(2*i)+"]"+value.split("::")[1]));
						if(ele12.isDisplayed()){
							ele12.click();
							resultDetails.setFlag(true);
							break;
						}
					}catch (Exception e) {
						sd.log.error("Exception: "+e.getMessage());
					}
					try{
						Thread.sleep(5000);
						webdriver.findElement(By.xpath(field.split("::")[0]+"["+((2*i)-1)+"]"+field.split("::")[1])).click();
						Thread.sleep(5000);
						text1=webdriver.findElement(By.xpath(field.split("::")[0]+"["+((2*i)-1)+"]"+field.split("::")[1])).getText();
						WebElement ele1 = webdriver.findElement(By.xpath(value.split("::")[0]+"["+((2*i)-1)+"]"+value.split("::")[1]));
						if(ele1.isDisplayed()) {
							ele1.click();
							resultDetails.setFlag(true);
							break;
						}
					}catch (Exception e) {
						sd.log.error("Exception in click: "+e.getMessage());
					}
				}
				break;	
			case SCK:
				element = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field));
				try{
					try{
						webdriver.findElement(By.tagName("body")).click();}catch(Throwable e){}
						element.sendKeys("\n");
				}catch(Throwable e) {	                
					sd.log.error("Unable to click on the elemnt using SCK: "+e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Unable to click on the elemnt using SCK: "+e.getMessage());
					return resultDetails;
				}
				sd.log.info("Clicked element using SCK");
				resultDetails.setFlag(true);
				break;
			case TCK:
				element = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field));
				try{
					webdriver.findElement(By.tagName("body")).click();
					try{
						webdriver.findElement(By.cssSelector("a.close")).click();
					}catch(Exception e) {
						sd.log.error("Exception in TCK click: "+e.getMessage());
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("Unable to click on the elemnt using TCK: "+e.getMessage());
					}
					Thread.sleep(3000);
					JavascriptExecutor js1 = (JavascriptExecutor) webdriver;
					js1.executeScript("arguments[0].click();", element);
				}catch(Throwable e){
					sd.log.error("Exception in TCK click: "+e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Unable to click on the elemnt using TCK: "+e.getMessage());
					return resultDetails;
				}
				resultDetails.setFlag(true);
				break;
			}
			return resultDetails;
		}catch (IllegalArgumentException e) {
			sd.log.error("IllegalArgumentException in click:: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("For click action type the data field should be BTN, CNF, LNK, IMG or XPH");
			return resultDetails;
		}catch (Exception e) {
			sd.log.error("Exception in click:: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element ::"+ field+ ":: not found");
			sd.log.info("Element ::"+ field+ ":: not found");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	CHECKORUNCHECK
	'Description		:	This function is to perform Check or uncheck operation on the Check box control

	'Parameters			:	field parameter should be given as chk followed by object path/id
	                        field - CHKCcheckboxID,CHKUcheckboxID
	'#########################################################################################################*/

	public ResultDetails checkOrUncheck(WebDriver webdriver, String fieldText, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		try {
			CheckDataFileds cdf = CheckDataFileds.valueOf(fieldType
					.toUpperCase());
			switch (cdf) {
			case CHK:
				String chkFlag = field.substring(0, 1);
				field = field.substring(1, field.length());
				if (WebDriverUtils.isElementPresent(webdriver, field)
						&& chkFlag.equalsIgnoreCase("C")
						&& !webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).isSelected()) {
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).click();
				}
				if (WebDriverUtils.isElementPresent(webdriver, field)
						&& chkFlag.equalsIgnoreCase("U")
						&& webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).isSelected()) {
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).click();
				}
				Thread.sleep(1000);				
				resultDetails.setFlag(true);
				break;
			}
			return resultDetails;
		}catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("For check/uncheck action type the data field should be CHK");
			sd.log.error("For check/uncheck action type the data field should be CHK");
			return resultDetails;
		}catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element ::"+ fieldName+ ":: not found");
			sd.log.error("Element ::"+ fieldName+ ":: not found");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	ENTER
	'Description		:	This function is to perform enter operations on different Text box controls.

	'Parameters			:	field parameter should be given as TXT/CEN/BTN/EDT/STV/RDN/CID followed by object ID/PATH
	                        EX: TXTusername
	'#########################################################################################################*/

	public ResultDetails enter(WebDriver webdriver, String fieldText, String value) throws InterruptedException {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		System.out.println("field = "+field);
		sd.log.info("field = "+field);
		value = getValue(value);
		try {
			EnterDataFileds edf = EnterDataFileds.valueOf(fieldType
					.toUpperCase());
			switch (edf) {
			case TXT:
				Thread.sleep(3000);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(value);
				resultDetails.setFlag(true);
				break;
			case CEN:
				Thread.sleep(3000);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).click();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(value);
				resultDetails.setFlag(true);
				break;
			case BTN:
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).sendKeys(value.split("-")[0]);
				Thread.sleep(5000);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).sendKeys(value.split("-")[1]);
				resultDetails.setFlag(true);	
				break;				
			case EDT:				
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(value);
				resultDetails.setFlag(true);
				break;
			case STV:
				System.out.println(field+" "+sd.hMap.get(value));
				sd.log.info("Stored value:"+sd.hMap.get(value));
				By by1=WebDriverUtils.locatorToByObj(webdriver,field);
				JavascriptExecutor executor1 = (JavascriptExecutor)webdriver;
				if(by1!=null&&by1.toString().toLowerCase().startsWith("by.id")){
					executor1.executeScript("document.getElementById('"+field+"').value = '"+sd.hMap.get(value)+"';");
					resultDetails.setFlag(true);
					return resultDetails;
				}
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,
						field)).sendKeys(sd.hMap.get(value));
				resultDetails.setFlag(true);
				break;
			case RDN:
				Random rand = new Random();
				int  randomNum = rand.nextInt(1000000);
				String number= Integer.toString(randomNum);
				if(number.contains("0")){
					number=number.replace("0","");
				}
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,
						field)).sendKeys("VL_"+number);
				resultDetails.setFlag(true);
				break;
			case CID:
				WebElement element = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field));	               
				String str = CreateClient.clientID.get(sd.hMap.get("ClientName").toUpperCase());	                
				for(int i=0;i<str.length();i++) {
					element.sendKeys(str.substring(i, i+1));
				}
				element.sendKeys(" ");
				element.sendKeys("- ");
				str = sd.hMap.get("ClientName");	                
				for(int i=0;i<str.length()-3;i++) {
					element.sendKeys(str.substring(i, i+1));
				}
				String url = webdriver.getCurrentUrl();
				DefaultSelenium seleniumObj = new WebDriverBackedSelenium(webdriver,url);
				Thread.sleep(800);
				seleniumObj.fireEvent(field, "keydown");
				resultDetails.setFlag(true);
				break;
			case JSC:							
				By by=WebDriverUtils.locatorToByObj(webdriver,field);
				JavascriptExecutor executor = (JavascriptExecutor)webdriver;
				if(by!=null&&by.toString().toLowerCase().startsWith("by.id")){
					executor.executeScript("document.getElementById('"+field+"').value = '"+value+"';");
					resultDetails.setFlag(true);
				}else{
					if(!by.toString().toLowerCase().startsWith("by.id"))
						sd.log.error("Element not found with id but found with "+by);
					else
						sd.log.error("Element not found ");
					resultDetails.setFlag(false);
					return resultDetails;
				}
				break;
			}
			return resultDetails;			
		} catch (IllegalArgumentException e) {
			sd.log.error("IllegalArgumentException in click: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("For ENTER action type the data field should be TXT");
			sd.log.debug("For ENTER action type the data field should be TXT");
			return resultDetails;
		}		
	}


	/*'#########################################################################################################
	'Function name		:	CLEARANDENTER
	'Description		:	This function is to clear the value in textbox control and to perfom enter operations on different Text box controls.

	'Parameters			:	field parameter should be given as TXT/BTN/EDT/STV followed by object ID/PATH
	                        EX: TXTusername
	'#########################################################################################################*/

	public ResultDetails clearAndEnter(WebDriver webdriver, String fieldText, String value,String browser) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		value = getValue(value);		

		try {
			EnterDataFileds edf = EnterDataFileds.valueOf(fieldType
					.toUpperCase());
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
			try {
				if(!browser.toLowerCase().equals("safari")){
					String del = Keys.chord(Keys.CONTROL, "a")+ Keys.DELETE;
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).sendKeys(del);
				}else{
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
					webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).clear();
				}

			} catch (Exception e) {}
			switch (edf) {
			case TXT:				
			case BTN:
			case EDT:
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).sendKeys(value);
				resultDetails.setFlag(true);
				break;
			case STV:
				System.out.println(field+" "+sd.hMap.get(value));
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,
						field)).sendKeys(sd.hMap.get(value));
				resultDetails.setFlag(true);
				break;
			}
			return resultDetails;			
		} catch (Exception e) {
			sd.log.error("Exception in clearAndEnter: "+e.getMessage());
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("For ENTER action type the data field should be TXT");
			sd.log.debug("For ENTER action type the data field should be TXT");
			return resultDetails;
		}		
	}


	/*'#########################################################################################################
	'Function name		:	WAITFORELEMENT
	'Description		:	This function is to wait for an element to load till the given specified time. If time not specified in the datasheet will wait 3seconds as a default.

	'Parameters			:	field parameter should be given as XPH/LNK/TTL/MSG followed by object ID/PATH
	                        EX: XPH//div[@id='username']  
	'#########################################################################################################*/

	public ResultDetails waitForElement(WebDriver webdriver, String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		int waitTime=3;		
		if(value!=null && !value.equals("")){
			if(Integer.parseInt(value)>100){
				waitTime = Integer.parseInt(value)/1000;
			}else {
				waitTime = Integer.parseInt(value);
			}			
		}
		try {
			WaitForFields wff = WaitForFields.valueOf(fieldType.toUpperCase());
			switch (wff) {
			case BTN:				
			case IMG:
			case COB:
			case TXT:
			case XPH:
				try {
					for (int second = 0;; second++) {
						if (second >= waitTime) Assert.fail("timeout");
						try { if (WebDriverUtils.isElementPresent(webdriver, field)) break;
						} catch (Exception e) {}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
				}catch (Throwable e) {
					System.out.println(field +"   :: Element not Found");
					sd.log.debug(field +"   :: Element not Found");
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(field +"   :: Element not Found");
					return resultDetails;
				}
				break;
			case LNK:
				try {
					for (int second = 0;; second++) {
						if (second >= waitTime) Assert.fail("timeout");
						try { if (WebDriverUtils.isElementPresent(webdriver, field)) break;
						} catch (Exception e) {}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
				}catch (Throwable e) {
					System.out.println("link="+field +"   :: Link not Found");
					sd.log.debug("link="+field +"   :: Link not Found");
					resultDetails.setFlag(false);					
					resultDetails.setErrorMessage("link="+field +"   :: Link not Found");
					return resultDetails;
				}
				break;
			case TTL:
				try {
					for (int second = 0;; second++) {
						if (second >= waitTime) Assert.fail("timeout");
						try { if (field.equals(webdriver.getTitle())) break; } catch (Exception e) {}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
				}catch (Throwable e) {
					System.out.println(field +"   :: Title not Found");
					sd.log.debug(field +"   :: Title not Found");
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(field +"   :: Title not Found");
					return resultDetails;
				}
				break;
			case MSG:
				try {
					for (int second = 0;; second++) {
						if (second >= waitTime) Assert.fail("timeout");
						try { if (webdriver.getPageSource().toLowerCase().trim().contains(field.toLowerCase().trim())) break; } catch (Exception e) {}
						Thread.sleep(1000);
					}
					resultDetails.setFlag(true);
				}catch (Throwable e) {
					System.out.println("GOOD>>>");
					sd.log.debug("GOOD>>>");
					resultDetails.setFlag(false);						
					resultDetails.setErrorMessage(field +"   :: Text not Found");
					return resultDetails;
				}
				break;
			}
			return resultDetails;
		} catch (IllegalArgumentException e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("For SELECT action type the data field should be TXT");
			sd.log.debug("For SELECT action type the data field should be TXT");
			return resultDetails;
		}catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(field +"   :: Element not Found");
			sd.log.debug(field +"   :: Element not Found");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	SELECTWINDOW
	'Description		:	This function is to select the window or to switch the selenium driver control to that window

	'Parameters			:	field parameter should be given as XPH/LNK/TTL/MSG followed by object ID/PATH
	                        EX: XPH//div[@id='username']  
	'#########################################################################################################*/
	public ResultDetails selectWindow(WebDriver webdriver, String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		try {
			SelectWindow wff = SelectWindow.valueOf(fieldType.toUpperCase());
			switch (wff) {				
			case TTL:
				try {
					for (int second = 0;; second++) {
						if (second >= 60) 
							break;
						else			
							Thread.sleep(200);			
					}
					if(field!=null && !field.equals("")) {
						webdriver.switchTo().window(webdriver.getWindowHandle());
						System.out.println("OOOOOOOOOOOOOOOOOOO : "+webdriver.getTitle());
						sd.log.info("OOOOOOOOOOOOOOOOOOO : "+webdriver.getTitle());
					}
					else
						WebDriverUtils.selectWindow(webdriver, value);
					webdriver.manage().window().maximize();
					resultDetails.setFlag(true);
				}catch (Throwable e) {
					sd.log.error("Exception: "+e.getMessage());
					sd.log.debug(value+"   ::Title not found");
					resultDetails.setFlag(false);					
					resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
					sd.log.debug("Window with title   ::+" +value +   "::not Found");
					return resultDetails;
				}
				break;
			}
			return resultDetails;

		}catch (Throwable e) {
			sd.log.error("Exception: "+e.getMessage());
			sd.log.debug(value+"   ::Title not found");
			resultDetails.setFlag(false);			
			resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
			sd.log.debug("Window with title   ::+" +value +   "::not Found");
			return resultDetails;
		}
	}


	/*'#########################################################################################################
	'Function name		:	CLOSEWINDOW
	'Description		:	This function is to to Close a browser window

	'Parameters			:	
	'#########################################################################################################*/
	public ResultDetails closeWindow(WebDriver webdriver, String fieldText, String value) {
		ResultDetails resultDetails = new ResultDetails();
		String fieldType = fieldText.substring(0, 3);
		String field = fieldText.substring(3, fieldText.length());
		try {
			SelectWindow wff = SelectWindow.valueOf(fieldType.toUpperCase());
			switch (wff) {				
			case TTL:
				try {
					for (int second = 0;; second++) {
						if (second >= 60) 
							break;
						else			
							Thread.sleep(200);			
					}
					if(field!=null) {
						webdriver.switchTo().window(webdriver.getWindowHandle());
						System.out.println("OOOOOOOOOOOOOOO : "+ webdriver.getTitle());
						sd.log.info("OOOOOOOOOOOOOOO : "+ webdriver.getTitle());
					}else {
						String parentWindow = webdriver.getTitle();
						WebDriverUtils.selectWindow(webdriver, value);
						webdriver.close();
						WebDriverUtils.selectWindow(webdriver, parentWindow); //switching back to parent window
					}
					resultDetails.setFlag(true);
				}catch (Exception e) 	{
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
					sd.log.debug("Window with title   ::+" +value +   "::not Found");
					return resultDetails;
				}
				break;
			}				
		}catch (Exception e) 	{
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
			sd.log.debug("Window with title   ::+" +value +   "::not Found");
			return resultDetails;
		}
		return resultDetails;
	} 


	/*'#########################################################################################################
	'Function name		:	VERIFYCONTINUE
	'Description		:	This function is to verify whether the form values and the expected values are equal or not and continue the test with a warning message even though verification failed.

	'Parameters			:	field parameter should be given as TXT/RDB/COB/CHK/SLB/BTN/LNK/CNF/XPH/CBS/DBV/TTL/ALT/MSG/WND/IMG/GET/TBL/EDT
							/LST/NAM/URL/ELM/CMP/HMV/CRT/PRC/DDL/DEX/DDC/INV/EST/CSS/LOV/CRO/ACT/JSC  followed by object id 
							Eg - BTNcss=table[id*='libvwreditor'],HMVVariableName
	'#########################################################################################################*/

	public ResultDetails verifyContinue(WebDriver webdriver, String field, String value, String fieldName) {	
		ResultDetails resultDetails = new ResultDetails();
		resultDetails=verify( webdriver,  field,  value,  fieldName) ;
		String tempwarn = resultDetails.getErrorMessage();
		resultDetails.setWarningMessage(tempwarn);
		resultDetails.setFlag(true);
		return resultDetails;
	}


	/*'#########################################################################################################
	'Function name		:	VERIFY
	'Description		:	This function is to verify whether the form values and the expected values are equal or not

	'Parameters			:	field parameter should be given as TXT/RDB/COB/CHK/SLB/BTN/LNK/CNF/XPH/CBS/DBV/TTL/ALT/MSG/WND/IMG/GET/TBL/EDT/LST/NAM/
							URL/ELM/CMP/HMV/CRT/PRC/DDL/DEX/DDC/INV/EST/CSS/LOV/CRO/ACT/JSC  followed by object id 
							Eg - BTNcss=table[id*='libvwreditor'],HMVVariableName
	'#########################################################################################################*/

	public ResultDetails verify(WebDriver webdriver, String field, String value, String fieldName) {
		ResultDetails resultDetails = new ResultDetails();
		Double fieldvalue = 0.0;
		sd.log.info("field= " + field + " value= " + value);
		DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
		field = field.substring(3, field.length());	
		resultDetails.setFlag(false);
		if(value!=null&&(value.equals(""))){			
			System.out.println("DataValue should not be empty");
			resultDetails.setErrorMessage(" Value Field is empty ");
			return resultDetails;
		}
		value = getValue(value);
		switch (dfs) {
		case URL:
			try{
				webdriver.getCurrentUrl().contains(value);
				resultDetails.setFlag(true);
			}catch(Exception e) {
				System.out.println("Tha page    ::+" +value +   "::not Found");
				sd.log.error("The page  ::+" +value +   "::not Found"+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The page   ::+" +value +   "::not Found");
				return resultDetails;
			}
			break;
		case HMV:
			try{
				String itemValue= sd.hMap.get(value);
				String actualValue = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText();
				if(itemValue!=null && actualValue!=null) {
					if(itemValue.contains("-")) {
						itemValue=itemValue.split("-")[1];
					} if(itemValue.contains(" ")) {
						itemValue=itemValue.replaceAll(" ","");
					} if(actualValue.contains(" ")) {
						actualValue=actualValue.replaceAll(" ","");
					} if(actualValue.contains(itemValue)) {
						resultDetails.setFlag(true);
					}else {
						sd.log.info("Actual value does not match with expected value."+"Expected value= "+itemValue+"Actual value= "+actualValue);
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("Actual value does not match with expected value."+"Expected value= "+itemValue+"Actual value= "+actualValue);
					}
				}else{
					resultDetails.setFlag(false);
					sd.log.debug("itemValue/actualvalue is null: "+itemValue+" "+actualValue);
				}
			}catch(Exception e) {
				System.out.println("The Item not Found");
				sd.log.error("The Item not Found: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The Item not Found");
				return resultDetails;
			}
			break;
		case CMP:
			try{
				String youPay=getValue(value);
				String comparePrice=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText();	
				if(comparePrice!=null && youPay!=null) {
					if(comparePrice.contains(",")){
						comparePrice.replaceAll(",", "");
					} if(comparePrice.contains("\n")){
						comparePrice = comparePrice.split("\n")[0];
					} if(comparePrice.contains(" ")){
						comparePrice.replaceAll(" ", "");
					} if(youPay.contains(",")){
						youPay =youPay.replaceAll(",", "");
					} if(youPay.contains(" ")){
						youPay =youPay.replaceAll(" ", "");
					}
					if(youPay.equalsIgnoreCase(comparePrice)){
						resultDetails.setFlag(true);
					} else{
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("The values are not equal" +value);
					}
				}else{
					resultDetails.setFlag(false);
					sd.log.debug("comparePrice/youPay is null: "+comparePrice+" "+youPay);
				}
			}catch(Exception e) {
				System.out.println("The values are not equal" +value);
				sd.log.error("exception value is: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The values are not equal" +value);
				return resultDetails;

			}
			break;
		case ELM:
			try{
				int childElements=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver,field)).size();
				if((childElements-1)<=Integer.parseInt(value)){
					resultDetails.setFlag(true);
				}else{
					System.out.println("The number of elements after sorting are more");
				}
			}catch(Exception e) {
				sd.log.error("exception value is: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The number of elements per page are more than   ::+" +value);
				return resultDetails;
			}
			break;
		case TTL:
			try {
				String title=webdriver.getTitle().trim();
				sd.log.info("The actual title is-----"+webdriver.getTitle());
				sd.log.info("The expected title is--------"+value.trim());
				if(title.contains(value)) {
					resultDetails.setFlag(true);
				}else {
					sd.log.debug("Unable to verify the window with the title" +value);
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Unable to verify the window with the title" +value);
				}
			}catch (Throwable e) {
				System.out.println("Window with title   ::+" +value +   "::not Found");
				sd.log.error("Window with title   ::+" +value +   "::not Found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Window with title   ::+" +value +   "::not Found");
				return resultDetails;
			}
			break;
		case TXT:
			try {
				if (field!=null && field.startsWith("/"))
					fieldvalue = Double.parseDouble((webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText()));					
				else {
					fieldvalue = Double.parseDouble((webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value")));
					if(fieldvalue == Double.parseDouble(value))
					{
						resultDetails.setFlag(true);
					}else {
						sd.log.debug("values not matched");
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("values not matched");
					}
				}
			}catch (Throwable e) {
				System.out.println(value+"    ::Text not found");
				sd.log.error("Exception: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text not found ::  " + fieldName);
				return resultDetails;
			}
			break;
		case XPH:
			try {                             
				sd.log.debug("text=="+webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText() + " value="+ value);
				sd.log.debug("res=="+webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText().contains(value));
				if(value.equalsIgnoreCase("NULL")) {
					if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText().trim().equalsIgnoreCase("")) {
						resultDetails.setFlag(true);
					}
				}else if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText().trim().toLowerCase().contains(value.trim().toLowerCase())) {
					resultDetails.setFlag(true);
				}else if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getAttribute("value").trim().toLowerCase().contains(value.trim().toLowerCase())) {
					resultDetails.setFlag(true);
				}else {
					sd.log.debug("Value ::	'"+value+" Not present in the string : " +webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Value ::	'"+value+" Not present in the string : " +webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,field)).getText());
				}
			}catch (Throwable e) {
				System.out.println("Element  not found :" +value);
				sd.log.error("Element  not found :" +value);
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Element not found:" + value);
				return resultDetails;
			}
			break;
		case RDB:
			try {
				AssertJUnit.assertEquals(value, webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value"));
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
			}catch (Throwable e) {
				sd.log.debug("Exception: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("RadioButton not found :" + value);
				return resultDetails;
			}
			break;
		case COB:
			try {
				if (value!=null && value.indexOf(":") != -1)
					AssertJUnit.assertEquals(value, WebDriverUtils.getSelectedLabel(webdriver, field));
				else {
					Selenium sel=new WebDriverBackedSelenium(webdriver, webdriver.getCurrentUrl());
					AssertJUnit.assertEquals(value, sel.getSelectedLabel(field));
				}
				resultDetails.setFlag(true);
			}catch (AssertionError ae){
				sd.log.error("Assertion error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Assertion error: " + ae.getMessage());
				return resultDetails;			
			}catch (Throwable e) {
				sd.log.error("Exception: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("ComboBox not found :" + value);
				return resultDetails;
			}
			break;
		case CHK:
			try {
				AssertJUnit.assertEquals(value, webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value"));
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
			}catch (Throwable e) {
				sd.log.error("exception value is: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("CheckBox not found :" + value);
				return resultDetails;
			}
			break;
		case SLB:				
			ArrayList<String> arr = new ArrayList<String>();
			arr = dataValuesTokens(value, "|");
			String[] options = WebDriverUtils.getSelectedOptions(webdriver, field);
			int size = options.length;
			sd.log.info("A Size: " + arr.size() + " size: " + size);
			if (arr.size() == size) {
				for (int i = 0; i < size; i++) {
					sd.log.info("..options= " + options[i]);
					try {
						AssertJUnit.assertEquals(options[i], arr.get(i));
						resultDetails.setFlag(true);
					}catch(AssertionError ae) {
						sd.log.error("Assertion Error: "+ae.getMessage());
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage(ae.getMessage());
					}catch (Throwable e) {
						sd.log.error("Options mismatch with expected result  ::" + field);
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("Options mismatch with expected result  ::" + field);
						return resultDetails;
					}
				}
			}
			break;
		case CBS:
			// CBS stands for Combo box Values
			ArrayList<String> arr1 = new ArrayList<String>();
			arr1 = dataValuesTokens(value, "|");				
			String[] options1 = WebDriverUtils.getSelectedOptions(webdriver, field);	
			int optionsSize = options1.length;
			sd.log.info("Test Data Size: " + arr1.size() + " OptionsSize: " + optionsSize);
			if (arr1.size() == optionsSize) {
				for (int i = 0; i < optionsSize; i++) {
					sd.log.info("Option = " + options1[i]);
					sd.log.info("Test Data = " + arr1.get(i));
					try {
						AssertJUnit.assertEquals(options1[i], arr1.get(i));
						resultDetails.setFlag(true);
					}catch(AssertionError ae) {
						sd.log.error("Assertion Error: "+ae.getMessage());
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage(ae.getMessage());
					}catch (Throwable e) {
						sd.log.error("Options mismatch with expected result in drop down: "+e.getMessage());
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage("Options mismatch with expected result in drop down");
						return resultDetails;
					}
				}
			}else {
				int count=0;
				for (int j =0 ;j<arr1.size();j++) {
					for (int i = 0; i < optionsSize; i++) {
						sd.log.info("Option = " + options1[i]);
						sd.log.info("Test Data = " + arr1.get(j));
						if (arr1.get(j).equalsIgnoreCase(options1[i])) {
							count ++ ;
							sd.log.debug("Test data found in options:: "+ count);
							break;
						}
					}
				}
				if (count > 0 && count == arr1.size()) {
					resultDetails.setFlag(true);
				}else {					
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("ComboBox values does not Match with expected result . ");
				}
			}
			sd.log.info(" in case flag= " + resultDetails.getFlag());
			break;
		case IMG:
		case BTN:
			try {
				if (value.substring(0, 3).equals("HMV")) {					
					value = sd.hMap.get(value.substring(3));
				}
				AssertJUnit.assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
			}catch (Throwable e) {
				System.out.println(" Button with id '" + value + "' doesn't exist");
				sd.log.error(" Button with id '" + value + "' doesn't exist");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Button '" + fieldName + "' doesn't exist");
				return resultDetails;
			}			
			break;
		case LNK:
			if (field!=null && (field.length() > 6) && (field.substring(field.length()-5, field.length()).equalsIgnoreCase("@href"))) {
				try {
					System.out.println(" Attribute value : " + webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.substring(0, field.length()-5))).getAttribute("href"));
					sd.log.debug(" Attribute value : " + webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.substring(0, field.length()-5))).getAttribute("href"));
					System.out.println(" Parameter value : " + String.valueOf(value));
					sd.log.debug(" Parameter value : " + String.valueOf(value));
					if (webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.substring(0, field.length()-5))).getAttribute("href").equalsIgnoreCase(String.valueOf(value))) 
					{
						resultDetails.setFlag(true);
					}else{
						resultDetails.setFlag(false);
						resultDetails.setErrorMessage(fieldName + " attribute value NOT matched. Expected : "+String.valueOf(value) +" Actual: "+ webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.substring(0, field.length()-5))).getAttribute("href"));
						return resultDetails;
					}
				}catch (Throwable e) {
					sd.log.error("Exception:  "+e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Error occured while getting the attribute value of :: "+ fieldName);
					return resultDetails;
				}	
				break;				
			}
			else {
				try {
					AssertJUnit.assertTrue(WebDriverUtils.isElementPresent(webdriver, value));
					resultDetails.setFlag(true);
				}catch(AssertionError ae) {
					sd.log.error("Assertion Error: "+ae.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage(ae.getMessage());
				} catch (Throwable e) {
					sd.log.error("Exception: "+e.getMessage());
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Link with id '" + value + "' doesn't exist");
					System.out.println("Link '" + fieldName + "' doesn't exist");
					sd.log.debug("Link '" + fieldName + "' doesn't exist");
					return resultDetails;
				}
			}
			break;
		case ALT:
			try {
				AssertJUnit.assertEquals(value, WebDriverUtils.getAlert(webdriver));
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
			} catch (Throwable e) {
				System.out.println("Alert box not found");
				sd.log.error("Alert box not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Alert box not found");
				return resultDetails;
			}
			break;
		case CNF:
			try {
				AssertJUnit.assertEquals(value, WebDriverUtils.getAlert(webdriver));
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
			} catch (Throwable e) {
				System.out.println("Confirmation box not found");
				sd.log.error("Confirmation box not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Confirmation box not found");
				return resultDetails;
			}
			break;
		case MSG:
			try {	
				sd.log.debug("value :: "+value);
				AssertJUnit.assertTrue(webdriver.getPageSource().toLowerCase().trim().contains(value.toLowerCase().trim()));
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
			} catch (Throwable e) {
				System.out.println("Text :: +"+value+"   :: not found");
				sd.log.error("Text :: +"+value+"   :: not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text :: +"+value+"   :: not found");
				return resultDetails;
			}
			break;
		case GET:
			try {
				if (value.substring(0, 3).equals("TXT")) {
					value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, value.substring(3))).getAttribute("value"); }
				else if (value.substring(0, 3).equals("LNK")) {
					value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, value.substring(3))).getText();
				}else {
					value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, value.substring(3))).getText();
				}
				AssertJUnit.assertEquals(sd.hMap.get(field),value);
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
			} catch (Throwable e) {
				resultDetails.setFlag(false);
				System.out.println("Actual Value :: "+ value + "   Expected Value ::" + sd.hMap.get(field));
				sd.log.error("Actual Value :: "+ value + "   Expected Value ::" + sd.hMap.get(field));
				resultDetails.setErrorMessage("Actual Value :: "+ value + "   Expected Value ::" + sd.hMap.get(field));
				return resultDetails;
			}
			break;
		case TBL:
			String [] tempValues = value.split(":");
			if (tempValues!=null && (tempValues[2].length() > 3) && (tempValues[2].substring(0, 3).equals("HMV"))) {					
				tempValues[2] = sd.hMap.get(tempValues[2].substring(3));
			}
			try {
				sd.log.debug(field+"."+tempValues[0]+"."+tempValues[1]);
				if (WebDriverUtils.getTable(webdriver, field, tempValues[0], tempValues[1]).equalsIgnoreCase(tempValues[2])) {
					System.out.println("Values are Equal.");
					sd.log.info("Values are Equal.");
					resultDetails.setFlag(true);
				}
			}catch (Throwable e) {
				System.out.println("Text :: "+tempValues[2]+"   :: not found");
				sd.log.error("Text :: "+tempValues[2]+"   :: not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Text :: +"+tempValues[2]+"   :: not found");
				return resultDetails;
			}
			break;		
		case PRC:
			try{
				DecimalFormat df = new DecimalFormat("#.00");
				String[] locators= field.split("::");
				String qtyBeforeUpdate,qtyAfterUpdate,cartcntBeforeUpdate,calculatedPrice=null;
				cartcntBeforeUpdate = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, locators[0])).getAttribute("value").trim();
				qtyBeforeUpdate=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, locators[2])).getText().trim();	
				qtyBeforeUpdate=qtyBeforeUpdate.replace("$", "");
				qtyBeforeUpdate = qtyBeforeUpdate.replace(",", "");
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, locators[0])).clear();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, locators[0])).sendKeys(value);
				Thread.sleep(2000);
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, locators[1])).click();
				Thread.sleep(20000);
				qtyAfterUpdate=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, locators[2])).getText().trim();
				qtyAfterUpdate = qtyAfterUpdate.replace("$", "");
				qtyAfterUpdate = qtyAfterUpdate.replace(",", "");				
				Double quantity = (Integer.parseInt(value)*Double.parseDouble(qtyBeforeUpdate))/(Integer.parseInt(cartcntBeforeUpdate));
				quantity =Double.valueOf(df.format(quantity));
				String[] s = String.valueOf(quantity).split("\\.");
				calculatedPrice = String.valueOf(quantity);
				if(s[s.length - 1].length()<2){
					calculatedPrice = calculatedPrice+"0";
				} if(qtyAfterUpdate.equals(calculatedPrice)) {					
					resultDetails.setFlag(true);
				}else {
					resultDetails.setFlag(false);
					sd.log.debug("Price not updated");
					resultDetails.setErrorMessage("Price not updated");
				}
			}catch(Exception e) {
				System.out.println("Element not found");
				sd.log.error("Exception:  "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Element :: :: not found");
				return resultDetails;				
			}			
			break;
		case LOV:
			try{
				sd.log.info("field = "+field);
				String prevBidPoint,latestBidPoint="";
				int prevBidCount,latestBidCount;
				prevBidPoint = getValue(value.split("::")[2]);;
				prevBidCount=Integer.parseInt(prevBidPoint);
				String storedValue = getValue(value.split("::")[1]);
				for (int i=1; i<=webdriver.findElements(WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0])).size(); i++) {
					if (storedValue.replaceAll(" ", "").equalsIgnoreCase(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0]+"["+i+"]"+field.split("::")[1])).getText().replaceAll(" ", ""))) {
						System.out.println("***Latest Count is"+webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0]+"["+i+"]"+field.split("::")[1]+"[@class='total']")).getText());
						latestBidPoint = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0]+"["+i+"]"+field.split("::")[1]+"[@class='total']")).getText();
						break;
					}
				}
				latestBidCount=Integer.parseInt(latestBidPoint);
				int var = Integer.parseInt(value.split("::")[0]);
				sd.log.debug("Var is "+var);
				sd.log.debug("Prev Bid Count "+prevBidCount);
				sd.log.debug("Latest Value "+latestBidCount);
				AssertJUnit.assertEquals(prevBidCount+var,latestBidCount);
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
				return resultDetails;
			}catch (Exception e) {
				sd.log.error("Exception: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(e.getMessage());
			}
			break;
		case ALR:
			try {
				Alert alert=webdriver.switchTo().alert();
				System.out.println(alert.getText());
				AssertJUnit.assertEquals(value,alert.getText());
				alert.accept();
				resultDetails.setFlag(true);
			}catch(AssertionError ae) {
				sd.log.error("Assertion Error: "+ae.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage(ae.getMessage());
				return resultDetails;
			}catch (Throwable e) {
				System.out.println("Alert box not found");
				sd.log.error("Alert box not found");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Alert box not found");
				return resultDetails;
			}
			break;
		case GTZ:
			try {
				value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText().trim();
				value=value.replaceAll("[,$]", "");
				if(Double.parseDouble(value)>0)
					resultDetails.setFlag(true);
				else
					resultDetails.setFlag(false);
			}catch (Throwable e) {                   
				sd.log.error("Value is not greater than zero");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("value is not greater than zero");
				return resultDetails;
			}
			break;
		case LTZ:
			try {
				value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText().trim();
				value=value.replaceAll("[,$]", "");
				if(Double.parseDouble(value)<0)
					resultDetails.setFlag(true);
				else
					resultDetails.setFlag(false);
			}catch (Throwable e) {                   
				sd.log.error("Value is not less than zero");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Value is not less than zero");
				return resultDetails;
			}
			break;
		case ZRO:
			try {
				value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText().trim();
				value=value.replaceAll("[,$]", "");
				if(Double.parseDouble(value)==0)
					resultDetails.setFlag(true);
				else
					resultDetails.setFlag(false);
			}catch (Throwable e) {                   
				sd.log.error("Value is not equals to zero");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Value is not equals to zero");
				return resultDetails;
			}
			break;	
	}
	return resultDetails;
}

/*'#########################################################################################################
	'Function name		:	ISDISABLED
	'Description		:	This function is to verify whether the specified element(field) is disabled or not 

	'Parameters			:	field parameter should start with XPH  followed by object id 
							Eg - XPH//table[@id='libvwreditor']
	'#########################################################################################################*/
public ResultDetails isDisabled(WebDriver webdriver, String field) {
	ResultDetails resultDetails = new ResultDetails();
	sd.log.info("field= " + field);
	DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
	field = field.substring(3, field.length());		
	resultDetails.setFlag(false);
	switch (dfs) {
	case IMG:
	case BTN:
	case XPH:
		try {
			AssertJUnit.assertFalse(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).isEnabled());
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			sd.log.debug("Exception: "+ field);
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element is Enabled::"+ field);
			sd.log.debug("Element is Enabled::"+ field);
			return resultDetails;
		}
		break;
	}
	return resultDetails;
}


/*'#########################################################################################################
	'Function name		:	ISENABLED
	'Description		:	This function is to verify whether the specified element(field) is Enabled or not 

	'Parameters			:	field parameter should start with XPH  followed by object id 
							Eg - XPH//table[@id='libvwreditor']
	'#########################################################################################################*/
public ResultDetails isEnabled(WebDriver webdriver, String field)	{
	ResultDetails resultDetails = new ResultDetails();
	sd.log.info("field= " + field);
	DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
	field = field.substring(3, field.length());		
	resultDetails.setFlag(false);
	switch (dfs) {
	case IMG:
	case BTN:
	case XPH:
		try {
			AssertJUnit.assertTrue(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).isEnabled());
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Element is Disabled:: "+ field);
			sd.log.debug("Element is Disabled:: "+ field);
			return resultDetails;
		}
		break;
	}
	return resultDetails;
}


/*'#########################################################################################################
	'Function name		:	STOREVALUE
	'Description		:	This function is to store the value from the UI and can refer in further test steps

	'Parameters			:	field parameter should start with TXT/COB/XPH/LNK/TBL/NAM/CRT/JSC/TTL/URL  followed by object id 
							key parameter the keyvalue to store the value in hashmap <variableName>
							Eg - XPH//table[@id='libvwreditor']   <variableName>
	'#########################################################################################################*/

public ResultDetails storeValue(WebDriver webdriver, String field, String key, String fieldName) {
	ResultDetails resultDetails = new ResultDetails();
	sd.log.info("field= " + field);
	DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
	field = field.substring(3, field.length());	
	resultDetails.setFlag(true);
	String value;
	if (fieldName.equalsIgnoreCase("NONE"))
		fieldName = field;		
	switch (dfs) {
	case TXT:
		try {
			value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value");
			System.out.println("Key:: "+ key+"  Value:: "+value);
			sd.log.debug("Key:: "+ key+"  Value:: "+value);
			sd.hMap.put(key, value);
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the value form:: "+ field);
			sd.log.debug("Error occured while storing the value form:: "+ field+" "+e.getMessage());
			return resultDetails;
		}
		break;
	case COB:
		try {
			value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("value");
			System.out.println("Key:: "+ key+"  Value:: "+ value);
			sd.log.debug("Key:: "+ key+"  Value:: "+ value);
			sd.hMap.put(key, value);
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the value form:: "+ field);
			sd.log.debug("Error occured while storing the value form:: "+ field);
			return resultDetails;
		}
		break;
	case XPH:			
		try {
			value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
			System.out.println("Key:: "+ key+"   Value:: "+ value);
			sd.log.debug("Key:: "+ key+"   Value:: "+ value);
			sd.hMap.put(key, value);			
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			if(fieldName.equalsIgnoreCase("Strorezero")){
				sd.hMap.put(key,"0");
				resultDetails.setFlag(true);}
			else{
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Error occured while storing the value for:: "+ fieldName);
				sd.log.debug("Error occured while storing the value for:: "+ fieldName);
				return resultDetails;
			}

		}
		break;
	case LNK:			
		try {
			value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
			System.out.println("Key:: "+ key+"  Value:: "+value);
			sd.log.debug("Key:: "+ key+"  Value:: "+ value);
			sd.hMap.put(key, value);			
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the value for "+ fieldName);
			sd.log.debug("Error occured while storing the value for "+ fieldName);
			return resultDetails;
		}
		break;
	case TBL:				
		String [] tempValues = key.split(":");
		String rowNum = tempValues[0];
		String colNum = tempValues[1];
		key = tempValues[2];
		try {
			value = WebDriverUtils.getTable(webdriver, field, rowNum, colNum);
			System.out.println("Key:: "+ key+"  Value:: "+ value);	
			sd.log.debug("Key:: "+ key+"  Value:: "+ value);
			sd.hMap.put(key,value);
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the value for "+ fieldName);
			sd.log.debug("Error occured while storing the value for "+ fieldName);
			return resultDetails;
		}
		break;
	case NAM:			
		try {
			value = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getAttribute("name");
			System.out.println("Key:: "+ key+"   Value:: "+ value);
			sd.log.debug("Key:: "+ key+"   Value:: "+ value);
			sd.hMap.put(key, value);
			resultDetails.setFlag(true);
		}catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the value for:: "+ fieldName);
			sd.log.debug("Error occured while storing the value for:: "+ fieldName);
			return resultDetails;
		}
		break;	
	case CRT:
		try {
			value =  webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
			System.out.println("Key:: "+ key+"   Value:: "+ value);
			sd.log.debug("Key:: "+ key+"   Value:: "+ value);
			String str = webdriver.findElement(By.xpath("//li[@id='shopping-cart-icon']/a | //li[@id='shopping-cart-holder'] | //li[@id='shopping-cart-holder']//span[@id='cartsize'] | //li[@id='shopping-cart-icon']")).getAttribute("style").trim();
			System.out.println(str); 
			if(!str.equalsIgnoreCase("display: none;")){                                                             
				sd.hMap.put(key, webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText());       
				resultDetails.setFlag(true);
			}else{
				sd.hMap.put(key, "-1");           
			}
		}catch (Throwable e) {
			if(fieldName.equalsIgnoreCase("Strorezero")){
				sd.hMap.put(key,"0");
				resultDetails.setFlag(true);
			}else {
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Error occured while storing the value for:: "+ fieldName);
				sd.log.debug("Error occured while storing the value for:: "+ fieldName);
				return resultDetails;
			}
		}
		break;
	case JSC:
		try{
			JavascriptExecutor executor = (JavascriptExecutor)webdriver;
			String date = (String)executor.executeScript("var a = $('"+field+"').val();return a;");
			System.out.println("value in the field: "+date);
			sd.hMap.put(key, date);
			resultDetails.setFlag(true);
		}catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the value form:: "+ field);
			sd.log.debug("Error occured while storing the value form:: "+ field);
			return resultDetails;
		}
		break;
	case TTL:
		try {
			value =  webdriver.getTitle();
			System.out.println("Key:: "+ key+"  Value:: "+ value);
			sd.log.debug("Key:: "+ key+"  Value:: "+ value);
			sd.hMap.put(key, value);
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the title ");
			sd.log.debug("Error occured while storing the title ");
			return resultDetails;
		}
		break; 
	case URL:
		try {
			String url = webdriver.getCurrentUrl();
			System.out.println("Key:: "+ key+"  Value:: "+ url);
			sd.log.debug("Key:: "+ key+"  Value:: "+ url);
			sd.hMap.put(key, url);
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the url ");
			sd.log.debug("Error occured while storing the url ");
			return resultDetails;
		}
		break; 
	case CSH:
		try {
			String cash = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
			if(cash.contains("Cash"))
			{
				cash=cash.split(" ")[0];
			}
			System.out.println("Key:: "+ key+"  Value:: "+ cash);
			sd.log.debug("Key:: "+ key+"  Value:: "+ cash);
			sd.hMap.put(key, cash);
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Error occured while storing the cash rewards");
			sd.log.debug("Error occured while storing the cash  rewards");
			return resultDetails;
		}
		break;

	}
	return resultDetails;
}


/*'#########################################################################################################
	'Function name		:	VERIFYNOTPRESENT
	'Description		:	This function is used TO verify whether the the expected fields are not present in the given web page 
	'						
	'Parameters			:	field parameter should be given as MSG/BTN/LNK followed by object id 
							Eg - BTNcss=table[id*='libvwreditor']
	'#########################################################################################################*/

public ResultDetails verifyNotPresent(WebDriver webdriver, String field, String value) {
	ResultDetails resultDetails = new ResultDetails();
	System.out.println("field= " + field + " value= " + value);
	sd.log.info("field= " + field + " value= " + value);
	DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
	field = field.substring(3, field.length());		
	resultDetails.setFlag(false);
	if (value.substring(0, 3).equals("HMV")) {					
		value = sd.hMap.get(value.substring(3));
	}
	switch (dfs) {
	case MSG:
		try {
			AssertJUnit.assertFalse(webdriver.getPageSource().toLowerCase().trim().contains(value.toLowerCase().trim()));
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			System.out.println("Text :: +"+value+"   :: found which is NOT expected.");
			sd.log.debug("Text :: +"+value+"   :: found which is NOT expected.");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Text :: +"+value+"   :: found which is NOT expected.");
			return resultDetails;
		}
		break;
	case BTN:			 
		try {				
			AssertJUnit.assertFalse(WebDriverUtils.isElementPresent(webdriver, value));
			resultDetails.setFlag(true);
		} catch (Throwable e) {
			System.out.println("Object :: +"+value+"   :: found which is NOT expected.");
			sd.log.debug("Object :: +"+value+"   :: found which is NOT expected.");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Object :: +"+value+"   :: found which is NOT expected.");
			return resultDetails;
		}
		break;	
	case LNK:			
		try {
			if(value==null)
				value=field;			
			if(!WebDriverUtils.isElementPresent(webdriver, value))
				System.out.println("valueis"+value);
			resultDetails.setFlag(true);
		}catch (Throwable e) {
			System.out.println("Link object :: +"+value+"   :: found which is NOT expected.");
			sd.log.debug("Link object :: +"+value+"   :: found which is NOT expected.");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Link object :: +"+value+"   :: found which is NOT expected.");
			return resultDetails;
		}
		break;
	}

	return resultDetails;
}


/*'#########################################################################################################
	'Function name		:	STOREATTRIBUTE
	'Description		:	This function is used to store the object's attribute value in environment variable.	
	'						
	'Parameters			:	field parameter should be given as BTN followed by object id @<attribute name>
	'						Eg - BTNcss=table[id*='libvwreditor']@id	
	'Assumptions		:	None	
	'#########################################################################################################*/

public ResultDetails storeAttribute(WebDriver webdriver, String field, String key) {
	System.out.println("field= " + field);
	sd.log.info("field= " + field);
	DataFileds dfs = DataFileds.valueOf(field.substring(0, 3));
	field = field.substring(3, field.length());		
	ResultDetails resultDetails = new ResultDetails();
	resultDetails.setFlag(true);
	switch (dfs) {
	case BTN:
		try {
			//				System.out.println(" field ---- : "+ field);
			sd.log.debug(" field ---- : "+ field);
			System.out.println("Key:: "+ key+"  Value:: "+ webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0])).getAttribute(field.split("::")[1]));
			sd.log.debug("Key:: "+ key+"  Value:: "+ webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0])).getAttribute(field.split("::")[1]));
			sd.hMap.put(key, webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field.split("::")[0])).getAttribute(field.split("::")[1]));
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		} catch (Throwable e) {
			resultDetails.setFlag(false);
			System.out.println(" Msg : "+ e.getMessage());
			sd.log.debug(" Msg : "+ e.getMessage());
			resultDetails.setErrorMessage("Error occured while storing the attribute value of object:: "+ field);
			return resultDetails;
		}
		break;
	}
	return resultDetails;
}

/*'#########################################################################################################
	'Function name		:	VERIFYATTRIBUTE
	'Description		:	This function is used to verify the object's attribute value.
	'						
	'Parameters			:	field parameter should be given as BTN followed by object id @<attribute name>
							Eg - BTNcss=table[id*='libvwreditor']@id
	'#########################################################################################################*/

public ResultDetails verifyAttribute(WebDriver webdriver, String field, String value) {
	//		System.out.println("field= " + field);
	ResultDetails resultDetails = new ResultDetails();
	sd.log.debug("field= " + field);
	field = field.substring(3, field.length());		

	resultDetails.setFlag(true);
	try {
		sd.log.debug(" field ---- : " + field);
		System.out.println("Attribute Value:: "
				+ webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver,
								field.split("::")[0])).getAttribute(
										field.split("::")[1]));
		sd.log.debug("Attribute Value:: "
				+ webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver,
								field.split("::")[0])).getAttribute(
										field.split("::")[1]));

		AssertJUnit.assertEquals(
				value,
				webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver,
								field.split("::")[0])).getAttribute(
										field.split("::")[1]));

		resultDetails.setFlag(true);

		System.out.println("Attribute '"
				+ value
				+ "' Value is as expected :: "
				+ webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver,
								field.split("::")[0])).getAttribute(
										field.split("::")[1]));

		sd.log.debug("Attribute '"
				+ value
				+ "' Value is as expected :: "
				+ webdriver.findElement(
						WebDriverUtils.locatorToByObj(webdriver,
								field.split("::")[0])).getAttribute(
										field.split("::")[1]));

	} catch (Throwable e) {
		resultDetails.setFlag(false);
		System.out.println(" Msg : " + e.getMessage());
		sd.log.debug(" Msg : " + e.getMessage());
		resultDetails
		.setErrorMessage("Error occured while retriveing the attribute value of object:: "
				+ field);
		return resultDetails;

	}
	return resultDetails;
}	



/*'#########################################################################################################
	'Function name		:	MASKINGVERIFICATION
	'Description		:	This function is used verify the masked fields
	'						
	'Parameters			:	field parameter should be given as REG/CRD followed by object id
	                        Eg - REGcnnmask
	'#########################################################################################################*/

public ResultDetails maskingVerfication(WebDriver webdriver, String fieldText){
	ResultDetails resultDetails = new ResultDetails();
	String value;
	DataFileds dfs = DataFileds.valueOf(fieldText.substring(0, 3));
	fieldText = fieldText.substring(3, fieldText.length());              
	switch (dfs) {       
	case REG:
		try{
			Thread.sleep(3000);
			webdriver.findElement(By.xpath("//body")).sendKeys("");                    
			Thread.sleep(3000);
			value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getAttribute("value");
			if(value==null) 
				value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText(); 
			if(value.contains("@"))
				value=value.split("@")[0];
			if(value.length()==1) {
				sd.log.debug("the first digit value is"+value);
				Assert.assertFalse(value.equals("*"));                        
			} else if(value.length()==2) {
				sd.log.debug("the first digit value is"+value.substring(0,1));
				Assert.assertFalse(value.substring(0,1).equals("*"));
				sd.log.debug("the second digit value is"+value.substring(1,2));
				Assert.assertTrue(value.substring(1,2).equals("*"));
			} else if(value.length()==3) {
				sd.log.debug("the first digit value is"+value.substring(0,1));
				Assert.assertFalse(value.substring(0,1).equals("*"));
				sd.log.debug("the second digit value is"+value.substring(1,2));
				Assert.assertTrue(value.substring(1,2).equals("*"));
				sd.log.debug("the second digit value is"+value.substring(2,3));
				Assert.assertTrue(value.substring(2,3).equals("*"));                 
			} else if(value.length()==4 || value.length()==5 ||value.length()==6){
				sd.log.debug("the first digit value is"+value.charAt(0));
				Assert.assertFalse(value.substring(0,1).equals("*"));                       
				for(int i=1;i<value.length()-1;i++)
				{
					sd.log.debug("the " +(i+1)+ " digit value is"+value.substring(i,i+1));
					Assert.assertTrue(value.substring(i,i+1).equals("*"));
				}
				sd.log.debug("the last digit value is"+value.substring(value.length()-1,value.length()));
				Assert.assertFalse(value.substring(value.length()-1,value.length()).equals("*"));
			} else if(value.length()>=7) {
				sd.log.debug("the first digit value is"+value.substring(0,1));
				Assert.assertFalse(value.substring(0,1).equals("*"));
				for(int i=2;i<value.length()-2;i++)
				{
					sd.log.debug("the " +i+ " digit value is"+value.substring(i,i+1));
					Assert.assertTrue(value.substring(i,i+1).equals("*"));
				}      
				System.out.println("the last digit value is"+value.substring(value.length()-1,value.length()));
				sd.log.debug("the last digit value is"+value.substring(value.length()-1,value.length()));
				Assert.assertFalse(value.substring(value.length()-1,value.length()).equals("*"));                 
			}
			resultDetails.setFlag(true);
		}catch (AssertionError e) {
			sd.log.error("Masked values are not equal--------"+e.toString());
			resultDetails.setFlag(false);
			resultDetails.setWarningMessage("Masked values are not equal--------"+e.toString());
			return resultDetails;
		}catch (Exception e) {
			sd.log.error("Masked values are not equal--------"+e.toString());
			resultDetails.setFlag(false);                   
			resultDetails.setErrorMessage("Masking is not verfied--------"+e.toString());
			return resultDetails;
		}
		break;
	case CRD:
		try{
			value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getAttribute("value");
			if(value==null) {
				value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText().trim();
				if(value.contains(" "))
					value=value.replaceAll(" ","");                 
			}
			for(int i=0;i<value.length();i++)
			{
				System.out.println("the " +i+ " digit value is"+value.substring(i,i+1));
				sd.log.debug("the " +i+ " digit value is"+value.substring(i,i+1));
				Assert.assertTrue(value.substring(i,i+1).equals("*"));
			}
			resultDetails.setFlag(true);
		}catch (AssertionError e) {
			sd.log.error("Masked values are not equal--------"+e.toString());
			resultDetails.setFlag(false);
			resultDetails.setWarningMessage("Masked values are not equal--------"+e.toString());
			return resultDetails;
		}catch (Exception e) {
			sd.log.error("Masked values are not equal--------"+e.toString());
			resultDetails.setFlag(false);                   
			resultDetails.setErrorMessage("Masking is not verfied--------"+e.toString());
			return resultDetails;
		}      
		break;
	case PHN:
		try{
			Thread.sleep(3000);
			webdriver.findElement(By.xpath("//body")).sendKeys("");       
			Thread.sleep(3000);
			value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getAttribute("value");
			if(value==null)
				value=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText().trim();
			sd.log.debug("The last digit which is masked in phone number is " +(value.length()-4));
			for(int i=0;i<value.length()-4;i++) {
				sd.log.debug("the " +i+ " digit value is"+value.substring(i,i+1));
				Assert.assertTrue(value.substring(i,i+1).equals("*"));
			}
			for(int i=value.length()-4;i<value.length();i++) {
				sd.log.debug("the " +i+ " digit value is"+value.substring(i,i+1));
				Assert.assertFalse(value.substring(i,i+1).equals("*"));
			}
			resultDetails.setFlag(true);
		}catch (AssertionError e) {
			sd.log.error("Masked values are not equal--------"+e.toString());
			resultDetails.setFlag(false);
			resultDetails.setWarningMessage("Masked values are not equal--------"+e.toString());
			return resultDetails;
		}catch (Exception e) {
			sd.log.error("Masked values are not equal--------"+e.toString());
			resultDetails.setFlag(false);                   
			resultDetails.setErrorMessage("Masking is not verfied--------"+e.toString());
			return resultDetails;
		}      
		break;
	}      
	return resultDetails;
}
/*'#########################################################################################################
    'Function name             :      CASHREWARDS
    'Description         :      This function is to verify reward cash decrement in the application using the rewards cash before update and youpay
    '                                        
    'Parameters                :      fieldText parameter should start with DEC followed by points locator id/path
                                             value parameter should be <points>;<youpay>
                            Ex: DEC//span[@id='rewardsbalance']        <points>;<youpay>     
    '#########################################################################################################*/

public ResultDetails rewardsCash(WebDriver webdriver, String fieldText, String value) throws InterruptedException{
	points pt = points.valueOf(fieldText.substring(0, 3));
	fieldText = fieldText.substring(3, fieldText.length());
	switch (pt) {
	case DEC:
		try{
			resultDetails.setFlag(true);
			String previousCash=value.split(";")[0];
			String previousCash1=sd.hMap.get(previousCash);
			String youPay=value.split(";")[1];
			String youPay1=sd.hMap.get(youPay).trim();
			String pp="";

			if(previousCash1.contains(",")){
				pp=previousCash1.replaceAll(",","");
			}
			else {
				pp=previousCash1;
			}      

			if(pp.contains("$")){
				pp=pp.substring(1);
			}      
			float prvCash= Float.parseFloat(pp);
			String pointsToBeReduced = null;
			if(youPay1.trim().contains("-")){
				pointsToBeReduced=youPay1.substring(1,youPay1.length());
			}else {
				pointsToBeReduced=youPay1; 
			}if(pointsToBeReduced.contains(",")){                                
				pointsToBeReduced=pointsToBeReduced.replaceAll(",","");
			}
			if(pointsToBeReduced.contains("$")){
				pointsToBeReduced=pointsToBeReduced.trim();
				pointsToBeReduced=pointsToBeReduced.substring(1);
			}


			float pay=Float.parseFloat(pointsToBeReduced);
			System.out.println("The cash to be reduced are----------"+pay);
			sd.log.debug("The cash to be reduced are----------"+pay);
			Thread.sleep(3000);
			String afterCash=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
			String pp2="";
			if(afterCash.contains(",")){
				pp2=afterCash.replaceAll(",","");                      
			}
			else {
				pp2=afterCash;
			}      
			if(pp2.contains("$")){
				pp2=pp2.substring(1);
			}      

			float aftpoints=Float.parseFloat(pp2);
			System.out.println("Rewards Cash after the check out process are------ " +aftpoints);  
			sd.log.debug("Rewards Cash after the check out process are------ " +aftpoints);
			float totPoints=prvCash-pay;                    
			System.out.println("After reducing the cash from the total points are"+totPoints);
			sd.log.debug("After reducing the cash from the total points are"+totPoints);                           
			Assert.assertEquals(aftpoints,totPoints);
			resultDetails.setFlag(true);
		}catch (AssertionError  e) {
			resultDetails.setFlag(true);
			resultDetails.setWarningMessage("Points are not equal--------"+e.toString());
			return resultDetails;
		}
		break;        
	}
	return resultDetails;                    
}



/*'#########################################################################################################
    'Function name             :      COMPAREREWARDSCASH
    'Description         :      This function is to compare the rewards cash applied and actual rewards cash in the Final summmary
    '                                        
    'Parameters                :      fieldText parameter should be given as object id/path
                            value parameter is the Rewards Cash value to be compared
    '#########################################################################################################*/
public ResultDetails compareRewardsCash(WebDriver webdriver, String fieldText, String value){
	try{          
		value=getValue(value);
		if(value.contains("$"))
		{
			value=value.trim();
			value=value.substring(1);
		}
		String cashhApplied=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
		if(cashhApplied.contains("-"))
		{
			cashhApplied=cashhApplied.replaceAll("-","");
		}
		if(cashhApplied.contains("$"))
		{
			cashhApplied=cashhApplied.trim();
			cashhApplied=cashhApplied.substring(1);
		}

		if(cashhApplied.equalsIgnoreCase(value)){
			resultDetails.setFlag(true);
		}else {
			System.out.println("Rewards cash applied and actual are not equal");
			sd.log.info("Rewards cash applied and actual are not equal");
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Rewards cash applied and actual are not equal");
		}                                                                    
		return resultDetails;      
	}catch (Exception e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(e.toString());
		return resultDetails;
	}                    
}


/*'#########################################################################################################
    'Function name             :      ENTERREWARDSCASH
    'Description         :      This function is to enter the Rewards Cash
    '                                        
    'Parameters                :      fieldText parameter should be given as 
                            EX: GCH//div[@class='finalprice_container']//dd[@class='finalprice']://span[@id='altCurrencyAmountDollars']://input[@id='applyother']
                            GCH: for entering the value greater than Rewards Cash
                            ENT: for entering the value less than order total and less than rewards cash
                            GTL: for entering the value greater than order total
    '#########################################################################################################*/
public ResultDetails enterRewardsCash(WebDriver webdriver, String fieldText){
	ResultDetails resultDetails = new ResultDetails();
	DataFileds daf=DataFileds.valueOf(fieldText.substring(0,3));
	fieldText=fieldText.substring(3,fieldText.length());
	String orderTotal=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[0])).getText();
	String rewardsCash=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[1])).getText();
	DecimalFormat df = new DecimalFormat("#.00");
	if(orderTotal.contains("$")){
		orderTotal=orderTotal.trim();
		orderTotal=orderTotal.substring(1);
	}

	if(rewardsCash.contains("$"))
	{
		rewardsCash=rewardsCash.trim();
		rewardsCash=rewardsCash.substring(1); 
	}

	double cash=Double.parseDouble(rewardsCash);
	double valueToBeEntered=Double.parseDouble(orderTotal); 
	switch (daf) {
	case ENT:
		try{                               
			do{
				valueToBeEntered=valueToBeEntered/2;                          
			}while(valueToBeEntered>cash);

			valueToBeEntered=Double.valueOf(df.format(valueToBeEntered));              
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[2])).clear();
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[2])).sendKeys(Double.toString(valueToBeEntered)); 
			System.out.println("The value entered is" +valueToBeEntered);
			sd.log.info("The value entered is" +valueToBeEntered);
			resultDetails.setFlag(true);                                                                    
		}
		catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}
		break;

	case GCH:
		try{
			valueToBeEntered=cash+5;
			valueToBeEntered=Double.valueOf(df.format(valueToBeEntered));              
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[2])).clear();
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[2])).sendKeys(Double.toString(valueToBeEntered));
			sd.log.info("The value entered is" +valueToBeEntered);
			resultDetails.setFlag(true);
		}
		catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			resultDetails.setErrorMessage("unable to enter the rewards cash");
			return resultDetails;
		}
		break;               
	case GTL:                  
		try{
			valueToBeEntered=valueToBeEntered+5;
			valueToBeEntered=Double.valueOf(df.format(valueToBeEntered));               
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[2])).clear();
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[2])).sendKeys(Double.toString(valueToBeEntered));
			sd.log.info("The value entered is" +valueToBeEntered);
			resultDetails.setFlag(true);
		}
		catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			resultDetails.setErrorMessage("unable to enter the rewards cash");
			return resultDetails;
		}
		break;

	}
	return resultDetails;
}

/*'#########################################################################################################
    'Function name             :      VALIDATEREWARDSCASH
    'Description         :      This function is to validate the rewards cash value
    '                                        
    'Parameters                :      fieldText parameter should be given as 
                            EX: GTL//div[@class='finalprice_container']//dd[@class='finalprice']://span[@id='altCurrencyAmountDollars']
                            ZER//span[@id='altCurrencyAmountDollars']
                            GTR//span[@id='altCurrencyAmountDollars']

                            GTL: for verifying that the rewards cash is greater than order total
                            ZER: for verifying that the rewards cash is equal to zero
                            GTR: for verifying that the rewards cash is greater than zero

    '#########################################################################################################*/
public ResultDetails validateRewardsCash(WebDriver webdriver, String fieldText){
	ResultDetails resultDetails = new ResultDetails();
	DataFileds daf=DataFileds.valueOf(fieldText.substring(0,3));
	fieldText=fieldText.substring(3,fieldText.length());
	DecimalFormat df = new DecimalFormat("#.00");
	switch (daf) {
	case GTL:
		try{
			String orderTotal=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[0])).getText();
			String rewardsCash=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText.split(":")[1])).getText();
			if(orderTotal.contains("$")){
				orderTotal=orderTotal.trim();
				orderTotal=orderTotal.substring(1);
			}

			if(rewardsCash.contains("$"))
			{
				rewardsCash=rewardsCash.trim();
				rewardsCash=rewardsCash.substring(1); 
			}

			double cash=Double.parseDouble(rewardsCash);
			double total=Double.parseDouble(orderTotal); 

			if(cash>total){
				resultDetails.setFlag(true);
				sd.log.debug("The rewards cash" +cash+ "is greater than order total"  +total);
			}
			else{
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("The rewards cash" +cash+ "is not greater than order total"  +total);
			}

		}
		catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}
		break;

	case ZER:
		try{
			String rewardsCash=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();                     
			if(rewardsCash.contains("$"))
			{
				rewardsCash=rewardsCash.trim();
				rewardsCash=rewardsCash.substring(1); 
			}                          
			double cash=Double.parseDouble(rewardsCash);
			if(cash==0.00){
				resultDetails.setFlag(true);
				sd.log.debug("The rewards cash" +cash+ "is Equal to zero");
			}
			else{
				resultDetails.setFlag(false);
				sd.log.debug("The rewards cash" +cash+ "is not Equal to zero");
				resultDetails.setErrorMessage("The rewards cash" +cash+ "is not Equal to zero");
			}
		}
		catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}
		break;               
	case GTR:                  
		try{

			String rewardsCash=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();                     
			if(rewardsCash.contains("$"))
			{
				rewardsCash=rewardsCash.trim();
				rewardsCash=rewardsCash.substring(1); 
			}      
			double cash=Double.parseDouble(rewardsCash);
			if(cash>0.00){
				resultDetails.setFlag(true);
				sd.log.debug("The rewards cash" +cash+ "is greater than zero");
			}
			else{
				resultDetails.setFlag(false);
				sd.log.debug("The rewards cash" +cash+ "is not greater than zero");
				resultDetails.setErrorMessage("The rewards cash" +cash+ "is not greater than zero");
			}
		}
		catch (Exception e) {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage(e.toString());
			return resultDetails;
		}
		break;

	}
	return resultDetails;
}



/*'#########################################################################################################
	'Function name		:	VERIFYVALUEINROW
	'Description		:	This function is used to verify the required value exist in the expected row in the table 
	'						
	'Parameters			:	field parameter should be given as TBL followed by table id
	'						TBL<Table ID> 
	'						Eg - TBLlibViewer_PagesTabPage_phPages_ContentLibraryAdmin_phSelectedPage_AdminMainPage_form1
	'						value parameter should be given in the below format seperated by colon(:)
	'						<column number>:<Expected value>:<Expected Row No.>

	'Assumptions		:	None

	'#########################################################################################################*/

public ResultDetails verifyValueInRow(WebDriver webdriver, String field, String value, String fieldName) 
{	
	field = field.substring(3, field.length());
	if (field.substring(0, 3).equals("HMV")) {					
		field = sd.hMap.get(field.substring(3));
	}		
	ResultDetails resultDetails = new ResultDetails();
	resultDetails.setFlag(false);
	String [] tempValues = value.split(":");		

	if (tempValues[1].substring(0, 3).equals("HMV")) {					
		tempValues[1] = sd.hMap.get(tempValues[1].substring(3));
	}	
	int intRowNo = Integer.parseInt(tempValues[2]);
	System.out.println("Row No : = "+intRowNo);
	sd.log.info("Row No : = "+intRowNo);
	try 
	{
		Thread.sleep(3000);
		if (WebDriverUtils.isElementPresent(webdriver, field)){

			List<WebElement> td_collection = webdriver.findElements(By.xpath("//table[@id='"+field+"']//tr["+intRowNo+"]/td"));
			String strVal = td_collection.get(Integer.parseInt(tempValues[0])+1).getText();
			if (strVal.equalsIgnoreCase(tempValues[1])) {
				resultDetails.setFlag(true);
				System.out.println("Value '"+tempValues[1]+"' found in the table row : "+intRowNo+", as Expected.");
				sd.log.debug("Value '"+tempValues[1]+"' found in the table row : "+intRowNo+", as Expected.");
			} else {
				resultDetails.setFlag(false);
				System.out.println("Value '"+tempValues[1]+"' found in the table row : "+intRowNo+", as Expected.");
				sd.log.debug("Value '"+tempValues[1]+"' found in the table row : "+intRowNo+", as Expected.");
				resultDetails.setErrorMessage("Value '"+tempValues[2]+"' found in the table which is NOT Expected.");
			}
			return resultDetails;
		} else {
			resultDetails.setFlag(false);
			System.out.println("Table '"+fieldName+"' Not Found.");
			sd.log.debug("Table '"+fieldName+"' Not Found.");
			resultDetails.setErrorMessage("Table '"+fieldName+"' Not found.");
			return resultDetails;		
		}	
	} catch (Throwable e) {
		resultDetails.setFlag(false);
		System.out.println("error message : "+e.getMessage());
		sd.log.debug("error message : "+e.getMessage());
		resultDetails.setErrorMessage(e.getMessage());
		return resultDetails;
	}		
}


/*'#########################################################################################################
	'Function name		:	COMPAREVALUES
	'Description		:	This function is compares two values of type double
	'						
	'Parameters			:	value1,value2 are the values to be compared
	'#########################################################################################################*/

public ResultDetails compareValues(WebDriver webdriver, String value1, String value2) {
	try {
		Double val1 = Double.parseDouble(getValue(value1));
		Double val2 = Double.parseDouble(getValue(value2));
		System.out.println("COMPAREVALUES: "+ val1 + " : " + val2);
		sd.log.debug("COMPAREVALUES: "+ val1 + " : " + val2);
		int i = Double.compare(val1,val2);
		if(i==0)
		{
			resultDetails.setFlag(true);
		}else {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("");
		}
		return resultDetails;
	} catch (Exception e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(e.toString());
		return resultDetails;
	}
}

public String getDate(String value) 
{			
	String [] tempValues = value.split(":");
	String strReqDate = "";
	DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	Date today = new Date();

	if (tempValues[0].equalsIgnoreCase("d")) {
		if ((tempValues[1].length() > 3) && (tempValues[1].substring(0, 3).equals("HMV"))) {
			tempValues[1] = sd.hMap.get(tempValues[1].substring(3));
		}
		if (tempValues.length == 2) {
			Calendar calendar = Calendar.getInstance();
			if (tempValues[1].equalsIgnoreCase("currentdate")) {
				strReqDate = sdf.format(today);
				System.out.println("Current Date  = "+strReqDate);
				sd.log.info("Current Date  = "+strReqDate);
			} else if (tempValues[1].equalsIgnoreCase("effectivedate")) {		
				calendar.setTime(today);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				Date reqDate = calendar.getTime();
				strReqDate = sdf.format(reqDate);
				System.out.println("Effective Date = "+strReqDate);
				sd.log.info("Effective Date = "+strReqDate);
			} 
			//Code added to set the future date as currentdate +2 days
			else if (tempValues[1].split("\\|")[0].equalsIgnoreCase("futuredate")) {
				int noOfDays=2;
				if(tempValues[1].split("\\|").length==2){
					noOfDays = Integer.parseInt(tempValues[1].split("\\|")[1]);							
				}				
				calendar.setTime(today);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+noOfDays);
				Date reqDate = calendar.getTime();
				strReqDate = sdf.format(reqDate);
				System.out.println("Effective Date = "+strReqDate);
				sd.log.info("Effective Date = "+strReqDate);
			} 
			//Code added to set the future date as previous date that is currendate -1.
			else if (tempValues[1].equalsIgnoreCase("pastdate")) {		
				calendar.setTime(today);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
				Date reqDate = calendar.getTime();
				strReqDate = sdf.format(reqDate);
				System.out.println("Effective Date = "+strReqDate);
				sd.log.info("Effective Date = "+strReqDate);
			}
			else if (tempValues[1].equalsIgnoreCase("nextdate")) {		
				calendar.setTime(today);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
				Date reqDate = calendar.getTime();
				strReqDate = sdf.format(reqDate);
				System.out.println("Effective Date = "+strReqDate);
				sd.log.info("Effective Date = "+strReqDate);
			}
			return strReqDate;

		} else if (tempValues.length == 4) {
			Calendar cal = Calendar.getInstance();
			if (tempValues[1].equalsIgnoreCase("currentdate")) 
				cal.setTime(new Date());				
			else if (tempValues[1].equalsIgnoreCase("effectivedate")) {
				cal.setTime(today);
				cal.set(Calendar.DAY_OF_MONTH, 1);
			} else {
				try {
					today = (Date)sdf.parse(tempValues[1]);
					cal.setTime(today);
				} catch (ParseException e)
				{ 
					System.out.println("Exception :"+e);
					sd.log.info("Exception :"+e);
				}
			}
			if (tempValues[2].equals("M")) {
				cal.add(Calendar.MONTH, Integer.parseInt(tempValues[3]));
			} else if (tempValues[2].equals("d"))
				cal.add(Calendar.DATE, Integer.parseInt(tempValues[3]));
			else if (tempValues[2].equals("y"))
				cal.add(Calendar.YEAR, Integer.parseInt(tempValues[3]));
			else
				cal.add(Calendar.MONTH,0);
			strReqDate = sdf.format(cal.getTime());
			System.out.println("Required date : "+strReqDate);
			sd.log.info("Required date : "+strReqDate);
		}
	}sd.hMap.put("strDate", strReqDate);
	return strReqDate;		
}

public ArrayList<String> dataValuesTokens(String data,String delimiter){
	ArrayList<String> DataValuesTokens = new ArrayList<String>();
	StringTokenizer st = new StringTokenizer (data);
	while(st.hasMoreElements()){
		DataValuesTokens.add(st.nextToken(delimiter));
	}
	return DataValuesTokens;
}


/*'#########################################################################################################
	'Function name		:	VERIFYURL
	'Description		:	This function is to verify the url
	'						
	'Parameters			:	value parameter is the URL that need to be verified
	'#########################################################################################################*/

public ResultDetails verifyURL(WebDriver webdriver, String fieldText, String value) {
	try {
		String clientURL=webdriver.getCurrentUrl();
		if(clientURL.contains(value)) {
			resultDetails.setFlag(true);
		}else {
			resultDetails.setFlag(false);
		}
		return resultDetails;
	} catch (Exception e) {
		sd.log.error("Exception: "+e.getMessage());
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(e.toString());
		return resultDetails;
	}
}


/*'#########################################################################################################
	'Function name		:	CLICKTABLEOBJECT
	'Description		:	This function is to click on the Viedetails link in the AccountsHistory page.
	'						
	'Parameters			:	
	'#########################################################################################################*/

private ResultDetails clickTableObject(WebDriver webdriver, String fieldText, String value, String fieldName) {
	ResultDetails resultDetails = new ResultDetails();
	fieldText = fieldText.substring(3);
	boolean flag = false;
	try {
		for (int i=1 ; i <= webdriver.findElements(By.xpath(value.split("::")[0])).size(); i++) {
			if (webdriver.findElement(By.xpath(value.split("::")[0]+"["+i+"]"+value.split("::")[1])).getText().contains(value.split("::")[2])) {
				WebElement ele = webdriver.findElement(WebDriverUtils
						.locatorToByObj(webdriver, fieldText.split("::")[0]+"["+i+"]"+fieldText.split("::")[1]));
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].click();", ele);
				flag = true;
				break;
			}
		}
		AssertJUnit.assertTrue(flag);
		resultDetails.setFlag(true);
	}catch(AssertionError ae){
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("Assertion error: "+ae.getMessage());
	}catch (Exception e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("Expected Text is not found");
	}
	return resultDetails;
}


/*'#########################################################################################################
	'Function name		:	VERIFYTABLE
	'Description		:	This function is to verify a particular text in the table row
	'						
	'Parameters			:	
	'#########################################################################################################*/

public ResultDetails verifyTable(WebDriver webdriver, String fieldText, String value, String fieldName) {		
	ResultDetails resultDetails = new ResultDetails();
	fieldText = fieldText.substring(3);
	boolean flag = false;
	try {
		for (int i=1 ; i <= webdriver.findElements(By.xpath(value.split("::")[0])).size(); i++) {
			if (webdriver.findElement(By.xpath(value.split("::")[0]+"["+i+"]"+value.split("::")[1])).getText().contains(value.split("::")[2])) {
				if (webdriver.findElement(By.xpath(fieldText.split("::")[0]+"["+i+"]"+fieldText.split("::")[1])).getText().contains(fieldText.split("::")[2])) {
					flag = true;
				}
				break;
			}
		}
		AssertJUnit.assertTrue(flag);
		resultDetails.setFlag(true);
	}catch (Exception e) {
		sd.log.error("Exception: "+e.getMessage());
		sd.log.info("Expected Text is not found");
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("Expected Text is not found");
	}
	return resultDetails;
}


/*'#########################################################################################################
	'Function name		:	GMAIL
	'Description		:	This function is to verify the order number in the order confirmation and order cancelation emails
	'						
	'Parameters			:	fieldText parameter should contains CNF/CNL 
	                        value parameter is the order number to be verified
	'#########################################################################################################*/

public ResultDetails gmail(WebDriver webdriver, String fieldText, String value) {
	try { 
		resultDetails=orderSplit(webdriver,value);
		String ordernum=sd.hMap.get(value);
		System.out.println("the order number is----------"+ordernum);
		order or = order.valueOf(fieldText);		
		int flag = 0;
		switch (or) {
		case CNF:				
		case CNL:
			try{
				webdriver.findElement(By.id("gbqfq")).sendKeys(ordernum);
				webdriver.findElement(By.id("gbqfb")).click();
				Thread.sleep(15000);
				webdriver.findElement(By.xpath("//table//tr/td[6]/div[@role='link']/div/div[2]/span")).click();
				Thread.sleep(30000);
				List<WebElement> elements=webdriver.findElements(By.xpath("//table[@role='presentation']//span"));			
				for(WebElement elem:elements){
					System.out.println("the values are-------------" +elem.getText());
					if(elem.getText().contains(ordernum)){
						flag=1;
					}						
				}
				if(flag!=1){
					Thread.sleep(60000);
					for(WebElement elem:elements){
						System.out.println("the values are-------------" +elem.getText());
						if(elem.getText().contains(ordernum))
							flag=1;													
					}
				}
				if(flag==1){
					System.out.println("element available");// Set flag true
					resultDetails.setFlag(true);
				}else{
					System.out.println("element not available");//Set flag false
					resultDetails.setFlag(false);
					resultDetails.setErrorMessage("Order number not found");
				}
			}catch(Exception e){
				sd.log.error("Exception: "+e.getMessage());
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Order number not found");
				return resultDetails;
			}
			break;
		}		
		return resultDetails;
	} catch (Exception e) {
		sd.log.error("Exception: "+e.getMessage());
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(e.toString());
		return resultDetails;
	}

}

/*'#########################################################################################################
	'Function name		:	VERIFYMANDATORYFIELDS
	'Description		:	This function is to verify the mandatroy field displaying erro messages while submiting the page without values.
	'						
	'Parameters			:	fieldText parameter should be given as object id/path
	                        value parameter is the error message to be verified
	'#########################################################################################################*/

public ResultDetails verifyMandatoryFields(WebDriver webdriver, String fieldText, String value){
	try{
		List<WebElement> elements=webdriver.findElements(By.id(fieldText));			
		for(WebElement elem:elements){
			System.out.println("the values are-------------" +elem.getText());
			if(elem.getText().contains(value)){
				System.out.println("Madatory field is verified");
				sd.log.info("Madatory field is verified");
				resultDetails.setFlag(true);
			}else {
				System.out.println("Madatory field is not present");
				sd.log.info("Madatory field is not present");
				resultDetails.setFlag(false);
				resultDetails.setErrorMessage("Madatory field is not present");
			}				
		}						
		return resultDetails;	
	}catch (Exception e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(e.toString());
		return resultDetails;
	}			
}


/*'#########################################################################################################
	'Function name		:	REMOVEITEMS
	'Description		:	This function is to verify the invalid items from the cart
	'						
	'Parameters			:	N/A
	'#########################################################################################################*/

public ResultDetails removeItems(WebDriver webdriver){
	try{	
		try{
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//li[@id='shopping-cart-icon']/a")).click(); 
			if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//span[contains(text(),'Please remove')]"))!=null){
				while((webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//span[contains(text(),'Please remove')]"))!=null))  {
					WebElement ele = webdriver.findElement(WebDriverUtils
							.locatorToByObj(webdriver, "//span[contains(text(),'Please remove')]/../../../li[*]//input[@class='btn_remove_cart_item']"));
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					js.executeScript("arguments[0].click();", ele);
					resultDetails.setFlag(true);
				}
			}
		}
		catch(Exception e){	
			resultDetails.setFlag(true);
		}
		try{
			webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//li[@id='shopping-cart-icon']/a")).click();
			if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//span[contains(text(),'Please lower the quantity')]"))!=null)
			{

				while(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//span[contains(text(),'Please lower the quantity')]"))!=null) {
					WebElement ele = webdriver.findElement(WebDriverUtils
							.locatorToByObj(webdriver, "//span[contains(text(),'Please lower the quantity')]/../../../li[*]//input[@class='btn_remove_cart_item']"));
					JavascriptExecutor js = (JavascriptExecutor) webdriver;
					js.executeScript("arguments[0].click();", ele);
					resultDetails.setFlag(true);
				}
			}				
		}
		catch(Exception e){	
			resultDetails.setFlag(true);
		}
		try{
			if(webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//div[@id='contentWrap']/div[1]/div[3]")).getText().contains("empty")){
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//div[@id='continueshopping_on_titlebar']/a[text()='Continue Shopping']")).click();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"Merchandise")).click();
				webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"Featured Deals")).click();
				verifyAddToCartButton(webdriver,"XPH//div[@id='product-listing']//div[@class='content']:://div[@class='page-number']/a[2]");
				Thread.sleep(2000);					
			}
		}catch(Exception e) {	
			resultDetails.setFlag(true);
		}
		return resultDetails;	
	}catch (Exception e) {
		sd.log.error("Exception: "+e.getMessage());
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(e.toString());
		return resultDetails;
	}
}


/*'#########################################################################################################
	'Function name		:	REMOVEALLITEMS
	'Description		:	This function is to remove all the items from shopping cart
	'						
	'Parameters			:	N/A
	'#########################################################################################################*/

public ResultDetails removeAllItems(WebDriver webdriver){
	try{
		String cartCount= webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,"//li[@id='shopping-cart-icon']/a | //li[@id='shopping-cart-holder']//span[@id='cartsize']")).getText();
		WebElement ele;
		JavascriptExecutor js;
		System.out.println(cartCount);
		if(cartCount!=null && cartCount!=""){		
			ele = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, "//li[@id='shopping-cart-icon']/a | //li[@id='shopping-cart-holder']//span[@id='cartsize']"));
			js = (JavascriptExecutor) webdriver;
			js.executeScript("arguments[0].click();", ele);
			WebDriverUtils.waitForPageToLoad(webdriver, "30000");
			if(cartCount.indexOf("(")!=-1){
				cartCount=cartCount.substring(cartCount.indexOf("(")+1, cartCount.indexOf(")"));
			}
			for(int i=1;i<=Integer.parseInt(cartCount);i++){
				ele = webdriver.findElement(WebDriverUtils
						.locatorToByObj(webdriver, "//input[@class='btn_remove_cart_item']"));
				js = (JavascriptExecutor) webdriver;
				js.executeScript("arguments[0].click();", ele);
				WebDriverUtils.waitForPageToLoad(webdriver, "30000");
			}

		}
		resultDetails.setFlag(true);
		return resultDetails;	
	}catch (Exception e) {
		resultDetails.setFlag(true);
		return resultDetails;
	}			
}


/*'#########################################################################################################
	'Function name		:	REWARDPOINTS
	'Description		:	This function is to verify reward points increment/decrement in the application using the points before update and youpay
	'						
	'Parameters			:	fieldText parameter should start with DEC/INC followed by points locator id/path
							value parameter should be <points>;<youpay>
	                        Ex: DEC//span[@id='rewardsbalance']	     <points>;<youpay>	
	'#########################################################################################################*/

public ResultDetails rewardsPoints(WebDriver webdriver, String fieldText, String value) throws InterruptedException{
	points pt = points.valueOf(fieldText.substring(0, 3));
	fieldText = fieldText.substring(3, fieldText.length());

	switch (pt) {
	case DEC:
		try{
			resultDetails.setFlag(true);
			String previousPoints=value.split(";")[0];
			String previousPoints1=sd.hMap.get(previousPoints);
			String youPay=value.split(";")[1];
			String youPay1=sd.hMap.get(youPay);
			String pp="";

			if(previousPoints1.contains(",")){
				pp=previousPoints1.replaceAll(",","");
			}else {
				pp=previousPoints1;
			}		  
			float prvPoints= Float.parseFloat(pp);
			String pointsToBeReduced = null;
			if(youPay1.trim().contains("–")){
				pointsToBeReduced=youPay1.substring(1,youPay1.length());
			}else {
				pointsToBeReduced=youPay1;	
			}if(pointsToBeReduced.contains(",")){
				pointsToBeReduced=pointsToBeReduced.replaceAll(",","");
			}

			float pay=Float.parseFloat(pointsToBeReduced);
			System.out.println("The points to be reduced are----------"+pay);
			Thread.sleep(30000);
			String afterPoints=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
			String pp2="";

			if(afterPoints.contains(",")){
				pp2=afterPoints.replaceAll(",","");				
			}else {
				pp2=afterPoints;
			}			
			float aftpoints=Float.parseFloat(pp2);
			System.out.println("Points after the check out process are------ " +aftpoints);			
			float totPoints=prvPoints-pay;			
			System.out.println("After reducing the points from the total points are"+totPoints);
			Assert.assertEquals(aftpoints,totPoints);
			resultDetails.setFlag(true);
		}catch (AssertionError  e) {
			resultDetails.setFlag(true);
			resultDetails.setWarningMessage("Points are not equal--------"+e.toString());
			return resultDetails;
		}
		break;
	case INC:
		try{
			resultDetails.setFlag(true);
			String previousPoints=value.split(";")[0];
			String previousPoints1=sd.hMap.get(previousPoints);
			String youPay=value.split(";")[1];
			String youPay1=sd.hMap.get(youPay);
			String pp;
			if(previousPoints1.contains(",")){
				pp=previousPoints1.replaceAll(",","");
			}else {
				pp=previousPoints1;
			}		  
			float prvPoints= Float.parseFloat(pp);
			String pointsToBeReduced=youPay1.substring(1,youPay1.length());

			if(pointsToBeReduced.contains(",")){
				pointsToBeReduced=pointsToBeReduced.replaceAll(",","");;
			}

			float pay=Float.parseFloat(pointsToBeReduced);
			System.out.println("The points to be added are----------"+pay);
			String afterPoints=webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).getText();
			String pp2;
			if(afterPoints.contains(",")){
				pp2=afterPoints.replaceAll(",","");				
			}else {
				pp2=afterPoints;
			}			
			float aftpoints=Float.parseFloat(pp2);
			System.out.println("Points after the check out process are------ " +aftpoints);			
			float totPoints=prvPoints+pay;			
			System.out.println("After adding the points,the total points are"+totPoints);
			Assert.assertEquals(aftpoints,totPoints);
			resultDetails.setFlag(true);
		}catch (AssertionError e) {
			resultDetails.setFlag(true);
			resultDetails.setWarningMessage("Points are not equal--------"+e.toString());
			return resultDetails;
		}catch (Exception e) {
			resultDetails.setFlag(false);			
			resultDetails.setErrorMessage("Exception value is--------"+e.toString());
			return resultDetails;
		}
		break;
	}
	return resultDetails;			
}


/*'#########################################################################################################
	'Function name		:	VERIFYSEARCHHEADER
	'Description		:	This function is used to verify search header in the attractions page displayed with the expected start and enddates or not.

	'Parameters			:	value=Fort Lauderdale, Florida::startdate::enddate
							field = XPH//div[@id='content-searchresults']/div/h4
							Eg searchText- Attractions and Tickets | Fort Lauderdale, Florida | MM/DD/YYY -  MM/DD/YYY
	'#########################################################################################################*/

private ResultDetails verifySearchHeader(WebDriver webdriver,String fieldText, String value) {

	try{

		String field = fieldText.substring(3,fieldText.length());
		String startDate = sd.hMap.get(value.split("::")[1]);
		String endDate = sd.hMap.get(value.split("::")[2]);
		String expecText = "Attractions and Tickets | "+value.split("::")[0]+" | "+startDate+" - "+endDate;
		System.out.println("Expected search header: "+expecText);
		String actualText = webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver, field)).getText();
		System.out.println("Actual search header: "+actualText);
		if(expecText.equalsIgnoreCase(actualText.trim()))
		{
			resultDetails.setFlag(true);
		}else {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("Activities Search header not displayed as expected");
		}
	}catch(Exception e) {
		sd.log.error("Exception: "+e.getMessage());
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("Activities Search header verification failed");
	}
	return resultDetails;

}


/*'#########################################################################################################
	'Function name		:	ACTIVITESVIEWPRICING
	'Description		:	This function is to click on view pricing link on the activites page.

	'Parameters			:	Value parameter should be given as activityname 
							Ex: Fort Lauderdale Water Taxi
	'#########################################################################################################*/

public ResultDetails activitiesViewPricing(WebDriver webdriver, String value) {
	try{

		boolean flag = false;
		int itemsCount = webdriver.findElements(By.cssSelector("div.panel-rounded")).size();
		System.out.println("Number of search results displayed: "+itemsCount);
		String attractionName = "";
		for(int i=1;i<=itemsCount;i++)
		{
			attractionName = webdriver.findElement(By.xpath("//div[starts-with(@class,'content')]/div["+i+"]/div[1]/div[1]/div[2]/h3/a")).getText();
			System.out.println("attractionName: "+attractionName);
			if(attractionName.equalsIgnoreCase(value))
			{
				//View Pricing button
				click(webdriver, "HDN//div[starts-with(@class,'content')]/div["+i+"]/div[1]/div[2]/div[2]/a", "", "View Pricing button");
				Thread.sleep(3000);
				//Adult selection
				new Select(webdriver.findElement(By.xpath("//div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div/div/select"))).selectByValue("2");
				Thread.sleep(1000);
				//Children selection
				new Select(webdriver.findElement(By.xpath("//div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div[2]/div/select"))).selectByValue("1");
				//Check you pay verification for Adult
				Thread.sleep(1000);
				resultDetails = checkYouPay(webdriver, "//div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div/div[2]/dl/dd[1]:://div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div/div[2]/dl/dd[2]:://div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div/div[2]/dl/dd[3]", "");
				if(!resultDetails.getFlag())
					break;
				//Check you pay verification for Children
				resultDetails = checkYouPay(webdriver, "//div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div[2]/div[2]/dl/dd[1]:://div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div[2]/div[2]/dl/dd[2]:://div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[4]/div[2]/div[2]/dl/dd[3]", "");
				if(!resultDetails.getFlag())
					break;
				Thread.sleep(1000);
				//Add Activity check box
				click(webdriver, "HDN//div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[3]/div/input", "", "Add activity checkbox");
				//View Pricing Summary button
				Thread.sleep(1000);
				click(webdriver, "HDN//div[starts-with(@class,'content')]/div["+i+"]/div[3]/div[3]/div[4]/a", "", "View Pricing Summary button");
				flag = true;
				break;
			}
		}
		if(flag)
		{
			resultDetails.setFlag(true);
			resultDetails.setErrorMessage("");
		}else {
			resultDetails.setFlag(false);
			resultDetails.setErrorMessage("View Pricing functionality failed");
		}
	}catch(Exception e)  {
		sd.log.error("View Pricing functionality failed due to- "+e.getMessage());
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("View Pricing functionality failed due to- "+e.getMessage());
	}
	return resultDetails;
}





/*'#########################################################################################################
	'Function name		:	GenerateUserName
	'Description		:	This function is to generate a random username

	'Parameters			:	
	'#########################################################################################################*/

public ResultDetails generateUserName(WebDriver webdriver, String fieldText,String value) {
	ResultDetails resultDetails = new ResultDetails();
	try {
		String environment=getValue(value);
		Random rand = new Random();
		int  randomNum = rand.nextInt(1000000);
		String number= Integer.toString(randomNum);
		if(number.contains("0")){
			number=number.replace("0","");
		}
		String userName=sd.hMap.get("ProgramID")+"-"+environment+"-"+number;
		webdriver.findElement(WebDriverUtils.locatorToByObj(webdriver,fieldText)).sendKeys(userName);
		resultDetails.setFlag(true);
	} catch (Exception e) {
		sd.log.debug("Unable to enter the user name" + fieldText);    
		sd.log.error("Exception in Enter the user name: "+e.getMessage());
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage(e.getMessage());
	}
	return resultDetails;
}



/*'#########################################################################################################
	'Function name		:	SAVEUSERS
	'Description		:	This function is to save the user deails created in the admin in an excel sheet.

	'Parameters			:	
	'#########################################################################################################*/

public ResultDetails saveUsers(WebDriver webdriver,String value){
	try{
		createUserSheets(webdriver,value);
		String username=getValue(value.split(":")[0]);
		String password=getValue(value.split(":")[1]);
		String email=getValue(value.split(":")[2]);
		String screenName=getValue(value.split(":")[3]);
		String securityQuestion=getValue(value.split(":")[4]);
		String securityAnswer=getValue(value.split(":")[5]);
		String browser=sd.hMap.get("browser");
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		System.out.println(df.format(dateobj));
		String clientName=sd.hMap.get("ClientName")+"-"+sd.hMap.get("ProgramID");
		HSSFWorkbook workbook=new HSSFWorkbook();
		FileInputStream fis = null;
		HSSFRow row;
		fis=new FileInputStream(System.getProperty("user.dir")+"\\UserDetails\\Users.xls");
		POIFSFileSystem fs = new POIFSFileSystem(fis);         
		workbook= new HSSFWorkbook(fs);
		HSSFSheet sheet = workbook.getSheet(clientName);
		int rows= sheet.getPhysicalNumberOfRows();
		System.out.println("the number of rows are " +rows);

		row=sheet.createRow(rows);        
		sheet.setColumnWidth(0,  (short)(256*15));
		sheet.setColumnWidth(1,  (short)(256*15));
		sheet.setColumnWidth(2,  (short)(256*10));
		sheet.setColumnWidth(3,  (short)(256*50));
		sheet.setColumnWidth(4,  (short)(256*15));
		sheet.setColumnWidth(5,  (short)(256*30));
		sheet.setColumnWidth(6,  (short)(256*20));
		sheet.setColumnWidth(7,  (short)(256*15));
		sheet.setColumnWidth(8,  (short)(256*25));
		row.createCell(0).setCellValue(username);
		row.createCell(1).setCellValue(password);       
		row.createCell(2).setCellValue(browser);
		row.createCell(3).setCellValue(email);
		row.createCell(4).setCellValue(screenName);
		row.createCell(5).setCellValue(securityQuestion);
		row.createCell(6).setCellValue(securityAnswer);
		row.createCell(7).setCellValue(sd.hMap.get("Executionlog"));
		row.createCell(8).setCellValue(df.format(dateobj));    
		fis.close();         
		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"\\UserDetails\\Users.xls");
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		fis.close();                             
		resultDetails.setFlag(true);
		return resultDetails;      
	}catch (Exception e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("Unable to save the users--------"+e.toString());
		sd.log.debug("Exception+ "+e.getMessage());
		return resultDetails;
	}                    
}


/*'#########################################################################################################
	'Function name		:	CREATEUSERSSHEETS
	'Description		:	This function is to create the user sheets.

	'Parameters			:	
	'#########################################################################################################*/

public ResultDetails createUserSheets(WebDriver webdriver,String value){
	try{                 
		FileOutputStream op = null;
		HSSFWorkbook wb1 = null;
		System.out.println("the suer dir is------  " +System.getProperty("user.dir"));
		String clientName=sd.hMap.get("ClientName")+"-"+sd.hMap.get("ProgramID");
		//     String[] clientNames={"Verizon wireless-9338","Citi-8648","Weis Markets-9398","Xyz","bsudibw"};
		HSSFWorkbook wb=new HSSFWorkbook();
		String file= System.getProperty("user.dir")+"\\UserDetails\\Users.xls";
		File f=new File(file);            
		if(f.exists()){
			FileInputStream fin=new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(fin);         
			wb1 = new HSSFWorkbook(fs);

			int SheetCount=wb1.getNumberOfSheets();         
			int flag=0;
			int counter=0;
			for(int ss=counter;ss<SheetCount;ss++){                
				String sheetname=wb1.getSheetName(ss);
				if(sheetname.equalsIgnoreCase(clientName)){
					System.out.println("sheet name already exists");
					flag=1;
					break;
				}      
				else{
					flag=0;
					continue;
				}                    
			}
			if(flag==0)
			{
				wb1.createSheet(clientName);
				System.out.println("number of sheets" +wb1.getNumberOfSheets());
				HSSFSheet sheet1=wb1.getSheetAt(wb1.getSheetIndex(clientName));
				HSSFRow row =sheet1.createRow((short)0);
				row.createCell(0).setCellValue("User Name");
				row.createCell(1).setCellValue("Password");     
				row.createCell(2).setCellValue("Browser");
				row.createCell(3).setCellValue("Email ID");
				row.createCell(4).setCellValue("Screen Name");
				row.createCell(5).setCellValue("Security Question");
				row.createCell(6).setCellValue("Security Answer");
				row.createCell(7).setCellValue("Execution Log");
				row.createCell(8).setCellValue("Execution Timestamp");                                   
				System.out.println("Sheets are created");
				try {  
					fin.close(); 
					FileOutputStream fileOut = new FileOutputStream(file);
					wb1.write(fileOut);
					fileOut.flush();
					fileOut.close();
					fin.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}                                                                                                      
			}
			fin.close();                      
		}                          
		else{
			new File(System.getProperty("user.dir")+"//UserDetails").mkdir();
			f.createNewFile();
			op=new FileOutputStream(file);
			System.out.println("the suer dir is------  " +System.getProperty("user.dir"));       
			wb.createSheet(clientName); 
			System.out.println("Sheets are created");
			for(int s=0;s<wb.getNumberOfSheets();s++){
				HSSFSheet sheet1=wb.getSheetAt(s);
				HSSFRow row =sheet1.createRow((short)0);        
				row.createCell(0).setCellValue("User Name");
				row.createCell(1).setCellValue("Password");     
				row.createCell(2).setCellValue("Browser");
				row.createCell(3).setCellValue("Email ID");
				row.createCell(4).setCellValue("Screen Name");
				row.createCell(5).setCellValue("Security Question");
				row.createCell(6).setCellValue("Security Answer");
				row.createCell(7).setCellValue("Execution Log");
				row.createCell(8).setCellValue("Execution Timestamp"); 
				System.out.println("Username and Password Fields are created");
			}
			try {         
				wb.write(op);
				op.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		resultDetails.setFlag(true);
		return resultDetails;      
	}catch (Exception e) {
		resultDetails.setFlag(false);
		resultDetails.setErrorMessage("Unable to create an Excel sheet--------"+e.toString());
		sd.log.debug("Exception+ "+e.getMessage());
		return resultDetails;
	}                    
}

/*'#########################################################################################################
	'Function name		:	EXECUTESTEPS
	'Description		:	This function is to execute the steps from the Concept sheet in the Testdata sheet

	'Parameters			:	From parameter is the test steps number from which the execution starts
	                        To parameter is the test step number at which step the execution ends
	                        i is the Concept id in the Concepts sheet
	'#########################################################################################################*/

public ResultDetails executeSteps(WebDriver webdriver, int from, int to, int lcnt, int i, HashMap<Integer,TestDataDetails> TestData, ResultDetails resultDetails, ArrayList<String> result,String browser,String user1,String password1,String user2,String password2) {
	String arrCon[] = null;
	String strErrorMsg = "";
	String strMsg = "";
	int compVar=0;
	for (int l=1; l<=lcnt; l++) {
		sd.log.info("Looping " + l + "TCID = "+i);
		for(int k=from;k<=to;k++){
			//Retrieving & storing the testdata in a bean
			TestDataDetails tdd1=(TestDataDetails)TestData.get(k);
			//Finding the test case to be executed in the test data details
			if(!(tdd1.getBrowserType().equalsIgnoreCase("COMMON") || tdd1.getBrowserType().toUpperCase().indexOf(sd.browsername.toUpperCase()) != -1)&&(sd.getClient(tdd1.getclientName(),tdd1))){
				System.out.println("-----------------------Test Step in \"Concept\" Skipped::"+" 	TestCaseNo:  "+tdd1.getTestCaseID()+"	 StepNo: "+tdd1.getTestDataID()+"  	::	Action Type:  "+tdd1.getActionType()+" 	::		ClientName In TestData:  \""+tdd1.getclientName()+"\" 	::	ClientName in config:  \""+sd.client+"\"-----------------------------");
				sd.log.warn("-----------------------Test Step in \"Concept\"  Skipped::"+" 	TestCaseNo:  "+tdd1.getTestCaseID()+"	 StepNo: "+tdd1.getTestDataID()+"  	::	Action Type:  "+tdd1.getActionType()+" 	::		ClientName In TestData:  \""+tdd1.getclientName()+"\" 	::	ClientName in config:  \""+sd.client+"\"-----------------------------");
			}
			if((tdd1.getBrowserType().equalsIgnoreCase("COMMON") || tdd1.getBrowserType().toUpperCase().indexOf(sd.browsername.toUpperCase()) != -1)&&(sd.getClient(tdd1.getclientName(),tdd1))){
				System.out.println("Concept ID:"+tdd1.getTestCaseID()+" :: Step being Executed:"+k);
				sd.log.debug("Concept ID:"+tdd1.getTestCaseID()+" :: Step being Executed: "+tdd1.getTestCaseID()+"::"+k);
				int tcID =tdd1.getTestCaseID();
				if (!(testCaseID.contains(tcID))) { testCaseID.add(tcID);}        		      		
				//These String variables are to store the data Fields & corresponding data Values of the test case
				String dataFields=tdd1.getDataFields();
				String dataValues=tdd1.getDataValues();
				String fieldName=tdd1.getFieldName();
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
					}else if(dataValues.equalsIgnoreCase("auctionuser")) {
						dataValues= user2;
						System.out.println("dataFields : "+dataFields);
						System.out.println("dataValues : "+dataValues);
					}else if(dataValues.equalsIgnoreCase("auctionpwd")) {
						dataValues=password2;
						System.out.println("dataFields : "+dataFields);
						System.out.println("dataValues : "+dataValues);
					}
				}catch(Exception e) {
					sd.log.error("Exception: "+e.getMessage());
				}
				if(dataValues==null)
					dataValues = "";	
				System.out.println("dataFields : "+dataFields);
				sd.log.info("dataFields : "+dataFields);
				System.out.println("dataValues : "+dataValues);
				sd.log.info("dataValues : "+dataValues);
				//From Here, Based on the type of test mentioned in the test data, corresponding functionalities are implemented
				System.out.println("<In Set values of the page>");
				sd.log.debug("<In Set values of the page>");
				//Internally Based on the data provided in the test Data again the functionalities differ
				if(tdd1.getDataFields()!=null){
					System.out.println("{Data Fields & Data Values Exist}");	
					sd.log.debug("{Data Fields & Data Values Exist}");
					//String the fields & values by tokensing the dataFields,dataValues into an ArrayList		    				
					//Submitting the fields and values to Selenium
					System.out.println("--------------------------setting values-----------------------------");
					sd.log.info("--------------------------setting values-----------------------------");
					String actionType = tdd1.getActionType();
					System.out.println("Action Type : "+ actionType);	
					sd.log.info("Action Type : "+ actionType);
					if(actionType == null){
						resultDetails.setErrorMessage("Action Field is Empty");
						resultDetails.setFlag(false);				    		
					}else {
						if(dataFields!=null && dataFields.substring(0,3).equals("AUC")){
							dataFields = dataFields.substring(3, dataFields.length());
							resultDetails = this.performAction(sd.auctionWebdriver,dataFields,dataValues, actionType, fieldName,browser,user1,password1,user2,password2);
						}else{
							resultDetails = this.performAction(webdriver,dataFields,dataValues, actionType, fieldName,browser,user1,password1,user2,password2);
						}
						sd.log.info(" RESULT "+resultDetails.getFlag());
					}						    		
				}
				if (tdd1.getCondition() != null && tdd1.getCondition().indexOf("IF") != -1) { //  && tdd.getActionType() == "VERIFY") {
					System.out.println(" tdd.getCondition() "+tdd1.getCondition());
					sd.log.info(" tdd.getCondition() "+tdd1.getCondition());
					arrCon = tdd1.getCondition().split(":");
					try	{
						compVar= Integer.parseInt(arrCon[1]);
					}catch(Exception e) {
						sd.log.error("Exception: "+e.getMessage());
					}
					if(resultDetails.getFlag())
					{
						if(arrCon[1].equalsIgnoreCase("NEXT")){
							System.out.println("Continue next step...");
							sd.log.info("Continue next step...");
						}else {
							new java.math.BigInteger(arrCon[1]);								
							System.out.println( "step change to "+ Integer.parseInt(arrCon[1]));
							sd.log.info("step change to "+ compVar);
							k=compVar-1;
						}
					}else {
						if(arrCon[2].equalsIgnoreCase("NEXT")){
							System.out.println("Continue next step...");
							sd.log.info("Continue next step...");
							resultDetails.setFlag(true);
						}else {
							compVar = Integer.parseInt(arrCon[2]);
							System.out.println( "step change to "+ compVar);
							sd.log.info("step change to "+ compVar);
							k=compVar-1;
							resultDetails.setFlag(true);
						}
					}
				}
				//Adding the test case id, data id and the result to the ArrayList		    		
				result.add(Integer.toString(tdd1.getTestCaseID()));
				result.add(Integer.toString(tdd1.getTestDataID()));
				result.add((String)tdd1.getTestCaseTitle());	   		

				//Test case Pass / Fail 
				if(!resultDetails.getFlag()){
					System.out.println("?   F A I L   ?");
					sd.log.info("?   F A I L   ?");
					result.add("Fail");
					System.out.println("Browser Type : "+browser);
					sd.log.debug("Browser Type : "+browser);
					if (browser.equals("FF")||"IE8".equalsIgnoreCase(browser) ||"IE9".equalsIgnoreCase(browser)||"IE10".equalsIgnoreCase(browser)||"IE11".equalsIgnoreCase(browser)||"GCHROME".equalsIgnoreCase(browser)||"safari".equalsIgnoreCase(browser)) {
						String ProjPath,ProjPathLibpath;
						if(System.getProperty("user.dir").contains("bin")) {
							String dirpath = System.getProperty("user.dir");
							ProjPath = dirpath.substring(0, dirpath.lastIndexOf("\\"));
							System.out.println("ProjPath:"+ProjPath);
							sd.log.info("ProjPath:"+ProjPath);
							ProjPathLibpath=ProjPath.substring(0, sd.ProjPath.lastIndexOf("\\"));
						}else {
							ProjPath = System.getProperty("user.dir");
							ProjPathLibpath=ProjPath.substring(0, sd.ProjPath.lastIndexOf("\\"));
							System.out.println("ProjPath:"+ProjPath);
							sd.log.info("ProjPath:"+ProjPath);
						}
						if(resultDetails.getErrorMessage()!= null){
							strErrorMsg = "In CONCEPT: Test case failed at Step No. :: "+ tdd1.getTestDataID() +"   Error Message ::  "+ resultDetails.getErrorMessage() ;
							resultDetails.setErrorMessage(strErrorMsg);
							result.add(strErrorMsg+strScreenshotName);
						}else {
							strErrorMsg = "In CONCEPT: Test case failed at Step No. :: "+ tdd1.getTestDataID() ;
							resultDetails.setErrorMessage(strErrorMsg);
							result.add(strErrorMsg+strScreenshotName);
						}
						strScreenshotName = ". Screen Shot : "+strScreenshotName;
					}
					return resultDetails;	
				}else {
					System.out.println("?   P A S S   ?");
					sd.log.info("?   P A S S   ?");
					result.add("Pass");	
					if ((resultDetails.getWarningMessage()!= null)) {
						if (strMsg != null) {
							strMsg =  strMsg +"\n"+ "IN CONCEPT: !! Warning !! Step No. :: "+ tdd1.getTestDataID() + "   Message ::  " + resultDetails.getWarningMessage();
						} else {
							strMsg = "IN CONCEPT: !! Warning !! Step No. :: "+ tdd1.getTestDataID() + "   Message ::  " + resultDetails.getWarningMessage();
						}
					} 
					result.add(strMsg);		    								
				}
				result.add((new java.util.Date()).toString());	
				try {
					if ((tdd1.getCondition() != null) && (tdd1.getCondition().indexOf("IF") != -1) && (tdd1.getActionType().toUpperCase() == "VERIFY")) {			    										
						k = Integer.parseInt(arrCon[1].split("-")[1])-1;
						System.out.println( "step change to "+ k);
						sd.log.debug("step change to "+ k);
					}
				} catch (NumberFormatException ex) {
					System.out.println("invalid step number."+arrCon[0].split(":")[1]);
					sd.log.debug("invalid step number."+arrCon[0].split(":")[1]);
				}
			}
		}
	}
	System.out.println("Looping End......");
	sd.log.info("Looping End......");
	return resultDetails;
}

}
