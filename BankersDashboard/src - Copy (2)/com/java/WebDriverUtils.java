package com.java;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;



public class WebDriverUtils {	


	/*'#########################################################################################################
	'Function name		:	getWebElement
	'Description		:	This function is to make the driver wait until the element is present
							
	'Parameters			:	Locator parameter is the object locator
		               		seconds parameter is the number of seconds that the driver need to wait for an element presence
	'#########################################################################################################*/
	
	public static WebElement getWebElement(WebDriver webdriver,
			final By byLocator, int seconds) throws Exception {
		WebElement webElement = null;
		System.out.println("Waiting for element - " + byLocator.toString()
				+ " - to present.....");
		try {
			webElement = (new WebDriverWait(webdriver, seconds))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(byLocator);
						}
					});

		} catch (Exception e) {
			System.out.println("Timed-out waiting for element - "
					+ byLocator.toString() + " - to present.");
		}
		return webElement;

	}

	
	/*'#########################################################################################################
	'Function name		:	locatorToByObj
	'Description		:	This function is to locate the element on the web page
							
	'Parameters			:	Locator parameter is the object locator		               		
	'#########################################################################################################*/
	
	public static By locatorToByObj(WebDriver webdriver, String locator) {
			int time = 60;
			FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(webdriver);
			// Locating CSS element
			try{
			if (locator.startsWith("css=")) {
				locator = locator.substring(4, locator.length());
				try {
					fWait.withTimeout(time, TimeUnit.SECONDS)
							.pollingEvery(500, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class,
									ElementNotVisibleException.class)
							.until(ExpectedConditions
									.presenceOfElementLocated(By
											.cssSelector(locator)));
				} catch (TimeoutException e) {
					return null;
				}
				System.out.println("Element Found by CSS");
				return By.cssSelector(locator);
			}

			// Locating XPATH element
			if (locator.startsWith("xpath=") || locator.startsWith("//") || locator.startsWith("(//")) {
				if (locator.startsWith("xpath="))
					locator = locator.substring(6, locator.length());
				try {

					fWait.withTimeout(time, TimeUnit.SECONDS)
							.pollingEvery(800, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class,
									ElementNotVisibleException.class)
							.until(ExpectedConditions
									.presenceOfElementLocated(By.xpath(locator)));
				} catch (TimeoutException e) {
					return null;
				}
				System.out.println("Element Found by XPATH");
				return By.xpath(locator);
			}

			if (locator.startsWith("class=")) {
				locator = locator.substring(6, locator.length());
				try {
					fWait.withTimeout(time, TimeUnit.SECONDS)
							.pollingEvery(500, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class,
									ElementNotVisibleException.class)
							.until(ExpectedConditions
									.presenceOfElementLocated(By
											.className(locator)));
				} catch (TimeoutException e) {
					return null;
				}
				System.out.println("Element Found by Class Name");
				return By.className(locator);
			}

			long startTime = new Date().getTime();
			for (int i = 0; i < time / 5; i++) {

				try {
					fWait.withTimeout(1, TimeUnit.SECONDS)
							.pollingEvery(500, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class,
									ElementNotVisibleException.class)
							.until(ExpectedConditions
									.presenceOfElementLocated(By
											.linkText(locator)));
					System.out.println("Element Found by Link");
					return By.linkText(locator);
				} catch (TimeoutException e) {
				} catch (NoSuchElementException e) {
				} catch (ElementNotVisibleException e) {
				}
				
				try {
					fWait.withTimeout(1, TimeUnit.SECONDS)
							.pollingEvery(500, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class,
									ElementNotVisibleException.class)
							.until(ExpectedConditions
									.presenceOfElementLocated(By.id(locator)));
					System.out.println("Element Found by ID");
					return By.id(locator);
				} catch (TimeoutException e) {
				} catch (NoSuchElementException e) {
				} catch (ElementNotVisibleException e) {
				}

				try {
					fWait.withTimeout(1, TimeUnit.SECONDS)
							.pollingEvery(500, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class,
									ElementNotVisibleException.class)
							.until(ExpectedConditions
									.presenceOfElementLocated(By.name(locator)));
					System.out.println("Element Found by Name");
					return By.name(locator);
				} catch (TimeoutException e) {
				} catch (NoSuchElementException e) {
				} catch (ElementNotVisibleException e) {
				}

				

				try {
					fWait.withTimeout(1, TimeUnit.SECONDS)
							.pollingEvery(500, TimeUnit.MILLISECONDS)
							.ignoring(NoSuchElementException.class,
									ElementNotVisibleException.class)
							.until(ExpectedConditions
									.presenceOfElementLocated(By
											.partialLinkText(locator)));
					System.out.println("Element Found by Partial Link");
					return By.linkText(locator);
				} catch (TimeoutException e) {
				} catch (NoSuchElementException e) {
				} catch (ElementNotVisibleException e) {
				}

			

				if (time < (new Date().getTime() - startTime) / 1000)
					break;
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return null;

	}

	
	/*'#########################################################################################################
	'Function name		:	isElementPresent
	'Description		:	This function waits for an element to be present and returns true if the element is present, else returns false
							
	'Parameters			:	Locator parameter is the object locator
		               		Seconds parameter is the number of seconds that the driver need to wait for an element presence               		
	'#########################################################################################################*/
	
	public static boolean isElementPresent(WebDriver webdriver,
			final By byLocator, int seconds) throws Exception {
		System.out.println("+++1st Method");
		try{
			if ((new WebDriverWait(webdriver, seconds))
					.until(new ExpectedCondition<WebElement>() {
						public WebElement apply(WebDriver d) {
							return d.findElement(byLocator);
						}
					}) == null)
				return false;
			else
				return true;
		}
		catch(Throwable e){return false;}
	}


	/*'#########################################################################################################
	'Function name		:	isElementPresent
	'Description		:	This function returns true if the element is present, else returns false
							
	'Parameters			:	Locator parameter is the object locator         		
	'#########################################################################################################*/
	
	public static boolean isElementPresent(WebDriver webdriver, String locator){
		if(locatorToByObj(webdriver, locator)!=null)		      
			return true;
		else
			return false;
	}

	
	/*'#########################################################################################################
	'Function name		:	waitAndClick
	'Description		:	This function Wait for element to appear and click
							
	'Parameters			:	Locator parameter is the object locator         		
	'#########################################################################################################*/
	
	public static void waitAndClick(WebDriver webdriver, final By byLocator,
			int timeOut) throws Exception {
		WebElement element = getWebElement(webdriver, byLocator, timeOut);
		System.out.println("Click ->" + byLocator.toString());
		element.click();
	}


	/*'#########################################################################################################
	'Function name		:	getAlert
	'Description		:	This function returns the message of the alert/confirmation box
							Note: Web Driver handles both alert and confirmation boxes in same way
							
	'Parameters			:	N/A         		
	'#########################################################################################################*/
	
	public static String getAlert(WebDriver webdriver){
		String alertText;
		String currentWindowHandle = webdriver.getWindowHandle();//store current window handle
		Alert alert = webdriver.switchTo().alert();
		// Get the text from the alert/confirmation
		alertText = alert.getText();
		webdriver.switchTo().window(currentWindowHandle);//switch back to the current window
		return alertText;
	}

 
	/*'#########################################################################################################
	'Function name		:	getSelectedLabel
	'Description		:	This function returns the selected label of a combo box/list 
							
	'Parameters			:	Locator parameter is the object locator         		
	'#########################################################################################################*/
	
	public static String getSelectedLabel(WebDriver webdriver, String locator){
		Select select = new Select(webdriver.findElement(locatorToByObj(webdriver, locator)));
		System.out.println("Selected Value 1 " + select.getFirstSelectedOption().getText());    	
		return select.getFirstSelectedOption().getText();
	}

 
	/*'#########################################################################################################
	'Function name		:	getSelectedOptions
	'Description		:	This function returns the selected options of a combo box/list 
							
	'Parameters			:	Locator parameter is the object locator         		
	'#########################################################################################################*/
	public static String[] getSelectedOptions(WebDriver webdriver, String locator){
		Select select = new Select(webdriver.findElement(locatorToByObj(webdriver, locator)));
		String[] selectOptions = new String[select.getAllSelectedOptions().size()];

		for (int i=0;i<selectOptions.length;i++)
			selectOptions[i]=select.getAllSelectedOptions().toArray()[i].toString();

		return selectOptions;
	}


	/*'#########################################################################################################
	'Function name		:	select
	'Description		:	This function Selects the given option based on visible text/label -- also works as addSelection( )
							
	'Parameters			:	Locator parameter is the object locator   
	      					Option parameter is the value that needs to be selected from select box
	'#########################################################################################################*/
	public static void select(WebDriver webdriver, String locator, String option){
		Select select = new Select(webdriver.findElement(locatorToByObj(webdriver, locator)));
		select.selectByVisibleText(option);
	}


	/*'#########################################################################################################
	'Function name		:	selectByIndex
	'Description		:	This function Selects the given option based on its index -- also works as addSelection( )
							
	'Parameters			:	Locator parameter is the object locator   
	      					Option parameter is the value index that needs to be selected from select box
	'#########################################################################################################*/
	
	public static void selectByIndex(WebDriver webdriver, String locator, int option){
		Select select = new Select(webdriver.findElement(locatorToByObj(webdriver, locator)));
		select.selectByIndex(option);
	}


  
	/*'#########################################################################################################
	'Function name		:	selectByIndex
	'Description		:	This function returns the text content of a specific cell 
							
	'Parameters			:	Locator parameter is the table locator 
	'#########################################################################################################*/
	public static String getTable(WebDriver webdriver, String tableLocator, String rowNum, String colNum){
		WebElement table = webdriver.findElement(locatorToByObj(webdriver, tableLocator));
		java.util.List<WebElement> tr_collection=table.findElements(By.xpath("//tr"));
		int row_num = 1;

		if(!tr_collection.isEmpty() && tr_collection.size() >= 1 )
			for(WebElement trElement : tr_collection)
			{
				java.util.List<WebElement> td_collection=trElement.findElements(By.xpath("//tr["+row_num+"]/td"));
				int col_num=1;          
				if(!td_collection.isEmpty() && td_collection.size() >= 1 )  
					for(WebElement tdElement : td_collection)
					{
						if((Integer.toString(row_num).equalsIgnoreCase(rowNum))&&(Integer.toString(col_num).equalsIgnoreCase(colNum)))
							return tdElement.getText();
						col_num++;
					}
				row_num++;
			}
		return null;
	}

	
	/*'#########################################################################################################
	'Function name		:	waitForElementToPresent
	'Description		:	This function is to wait for the element to be present
							
	'Parameters			:	Locator parameter is the object locator   
	      					Seconds parameter is the number of seconds that the driver need to wait for an element presence
	'#########################################################################################################*/
	
	public static void waitForElementToPresent(WebDriver webdriver,
			final By byLocator, int seconds) throws Exception {

		if ((new WebDriverWait(webdriver, seconds))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(byLocator);
					}
				}) == null)
			throw new Exception("Timed-out waiting for - "
					+ byLocator.toString() + " - element to present...");

	}


	/*'#########################################################################################################
	'Function name		:	selectWindow
	'Description		:	This function Switches the focus to the window specified
							
	'Parameters			:	windowTitle parameter is the title of the window
	'#########################################################################################################*/
	
	public static void selectWindow(WebDriver webdriver, String windowTitle){
		WebDriver popup = null;
		Set<String> windowHandles = webdriver.getWindowHandles();
		for(int i=0;i<windowHandles.size();i++){
			popup = webdriver.switchTo().window(windowHandles.toArray()[i].toString());
			if (popup.getTitle().equals(windowTitle))
				break;
		}
	}


	/*'#########################################################################################################
	'Function name		:	waitForPageToLoad
	'Description		:	This function Waits for the page to load completely
							
	'Parameters			:	timeOut should be in milliseconds
	'#########################################################################################################*/
	public static void waitForPageToLoad(final WebDriver webdriver, String timeOut){

		int waitTime=10;
		if(timeOut!=null&&timeOut.equals("")&&(!(timeOut.length()==0)))
		{
			
			waitTime=Integer.parseInt(timeOut)/1000;
			if(waitTime<5){
				waitTime=5;
			}
		}
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
				        public Boolean apply(WebDriver driver) {
				          return (((JavascriptExecutor) webdriver).executeScript("return document.readyState").equals("complete"));
				        }
				      };
				  
				     Wait<WebDriver> wait = new WebDriverWait(webdriver,waitTime);
			
				      try {
				  
				              wait.until(expectation);
				          
				      } catch(Throwable error) {
				          System.out.println("Page still loaing");    
				      }
	}


	/*'#########################################################################################################
	'Function name		:	isTextPresent
	'Description		:	This function returns true if the text is present in the URL else returns false
							
	'Parameters			:	Text parameter should be the value that need to be verified
	'#########################################################################################################*/
	
	public static boolean isTextPresent(WebDriver webdriver, String text){
		Selenium sel=new WebDriverBackedSelenium(webdriver, webdriver.getCurrentUrl());
		return sel.isTextPresent(text);
	}
	
	
	/*'#########################################################################################################
	'Function name		:	totalNoOfElemts
	'Description		:	This function returns total number of elements available on the web page with the given locator id/css/path
							
	'Parameters			:	Text parameter should be the value that needs to be verified
	'#########################################################################################################*/
	
	public static int totalNoOfElemts(WebDriver webdriver, String locator) {
		int time = 10;
		System.out.println("Starting Time :: "+new Date().toString());
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(webdriver);
		// Locating CSS element
		if (locator.startsWith("css=")) {
			locator = locator.substring(4, locator.length());
			try {
				fWait.withTimeout(time, TimeUnit.SECONDS)
						.pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By
										.cssSelector(locator)));
			} catch (TimeoutException e) {
				return 0;
			}
			
			return webdriver.findElements(By.cssSelector(locator)).size();
		}
	
		// Locating XPATH element
		if (locator.startsWith("xpath=") || locator.startsWith("//") || locator.startsWith("(//")) {
			if (locator.startsWith("xpath="))
				locator = locator.substring(6, locator.length());
			try {
	
				fWait.withTimeout(time, TimeUnit.SECONDS)
						.pollingEvery(800, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By.xpath(locator)));
			} catch (TimeoutException e) {
				return 0;
			}
			
			return webdriver.findElements(By.xpath(locator)).size();
		}
		if (locator.startsWith("class=")) {
			locator = locator.substring(6, locator.length());
			try {
				fWait.withTimeout(time, TimeUnit.SECONDS)
						.pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By
										.className(locator)));
			} catch (TimeoutException e) {
				return 0;
			}
			
			return webdriver.findElements(By.className(locator)).size();
		}
	
		long startTime = new Date().getTime();
		for (int i = 0; i < time / 5; i++) {
	
			try {
				fWait.withTimeout(1, TimeUnit.SECONDS)
						.pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By.id(locator)));
				return webdriver.findElements(By.id(locator)).size();
			} catch (TimeoutException e) {
			} catch (NoSuchElementException e) {
			} catch (ElementNotVisibleException e) {
			}
			try {
				fWait.withTimeout(1, TimeUnit.SECONDS)
						.pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By.name(locator)));
				return webdriver.findElements(By.name(locator)).size();
			} catch (TimeoutException e) {
			} catch (NoSuchElementException e) {
			} catch (ElementNotVisibleException e) {
			}
			try {
				fWait.withTimeout(1, TimeUnit.SECONDS)
						.pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By
										.linkText(locator)));
				return webdriver.findElements(By.linkText(locator)).size();
			} catch (TimeoutException e) {
			} catch (NoSuchElementException e) {
			} catch (ElementNotVisibleException e) {
			}
	
			try {
				fWait.withTimeout(1, TimeUnit.SECONDS)
						.pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By
										.partialLinkText(locator)));
				return webdriver.findElements(By.partialLinkText(locator)).size();
			} catch (TimeoutException e) {
			} catch (NoSuchElementException e) {
			} catch (ElementNotVisibleException e) {
			}
	
			try {
				fWait.withTimeout(1, TimeUnit.SECONDS)
						.pollingEvery(500, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class,
								ElementNotVisibleException.class)
						.until(ExpectedConditions
								.presenceOfElementLocated(By
										.xpath("//a[text()=\"" + locator
												+ "\"")));
				return webdriver.findElements(By.xpath("//a[text()=\"" + locator+ "\"")).size();
			} catch (TimeoutException e) {
			} catch (NoSuchElementException e) {
			} catch (ElementNotVisibleException e) {
			}
				if (time < (new Date().getTime() - startTime) / 1000)
				break;
		}

	return 0;
	}
}
