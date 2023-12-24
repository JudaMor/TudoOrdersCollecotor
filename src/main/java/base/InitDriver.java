package base;


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InitDriver  
{
	protected static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
	protected static final ThreadLocal<WebDriverWait> threadWait = new ThreadLocal<>();
	protected static final ThreadLocal<Actions> threadAction = new ThreadLocal<>();
	
	RemoteWebDriver  driver;
	WebDriverWait wait;   
	public Actions action;
	List<String> handleWindows;
	String json = "";
	String parentWindow = null;
	

	public InitDriver() {
		
	}
	public   WebDriver getDriver() {
		return threadDriver.get();
	}
	public   WebDriverWait getWait() {
		return threadWait.get();
	}
	public   Actions getAction() {
		return threadAction.get();
	}
	
	public void switchToParentWindow() {
		getDriver().switchTo().window(parentWindow);
	}
	
	public void initializationWebDriver(String url) throws InterruptedException {
		System.out.println(System.getProperty("user.dir")+"/downloadFiles");
		System.setProperty("webDriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver120.exe");
		ChromeOptions options = new ChromeOptions();
		
		Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", System.getProperty("user.dir")+"\\downloadFiles");
        prefs.put("download.prompt_for_download", false); // Optional: Disable download prompt

        options.setExperimentalOption("prefs", prefs);
		options.addArguments("disable-gpu");
		options.addArguments("disable-site-isolation-trials");
		options.addArguments("no-sandbox");
		options.addArguments("window-size=1920,1200");
		options.addArguments("ignore-certificate-errors");
		options.addArguments("no-first-run");
		options.addArguments("disable-dev-shm-usage");
		options.addArguments("disable-web-security");
		options.addArguments("allow-running-insecure-content");
		options.addArguments("allow-insecure-localhost");
		options.addArguments("proxy-server='direct://'");
		options.addArguments("proxy-bypass-list=*");

		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";

		driver = new ChromeDriver(options);
		wait   = new WebDriverWait(driver,Duration.ofSeconds(30));
		action = new Actions(driver);
		
		threadDriver.set(ThreadGuard.protect(driver));
		threadWait.set(wait);
		threadAction.set(action);
		
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().navigate().to(url);
		parentWindow = getDriver().getWindowHandle();
		Thread.sleep(500);

	}
	
	public Alert waitForAlert() throws InterruptedException {
		int i=0;
		 Alert alert = null;
		   while(i++<5)
		   {
		        try
		        {
		             alert = getDriver().switchTo().alert();
		            break;
		        }
		        catch(NoAlertPresentException e)
		        {
		        	Thread.sleep(2* 1000);
		          continue;
		        }
		   }
		   return alert;
	}

	public void javaScriptclick(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

	}

	public void javaScriptclick(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", element);
	}

	public void executedJS(String script) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript(script);

	}
	public Object executedJS(String script, WebElement args) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		return executor.executeScript(script,args);

	}


	public WebElement locateElement(By by) {
		waitForElementLocated(by);
		return getDriver().findElement(by);
	}
	public void BackToPrimaryIframe() {
		getDriver().switchTo().parentFrame();
	}
	public void switchIframe(By by) throws InterruptedException {
		waitForElementLocated(by);
		getDriver().switchTo().frame(getDriver().findElement(by));
		Thread.sleep(1000);
	}
	public void switchIframe(WebElement webElement) throws InterruptedException {
		waitForElementVisiblity(webElement);
		getDriver().switchTo().frame(webElement);
		Thread.sleep(1000);
	}

	public void selectFromDropdown(WebElement element, String text) {
		Select  dropdown = new Select(element);
		dropdown.selectByVisibleText(text);
	}
	public void closeDriver() {
		getDriver().close();
		getDriver().quit();
	}
	public void waitForElementVisiblity(WebElement webElement) {
		getWait().until(ExpectedConditions.visibilityOf(webElement));
	}
	public void waitForElementVisiblity(List<WebElement> webElementList) {
		getWait().until(ExpectedConditions.visibilityOfAllElements(webElementList));
	}

	public  void waitForElementLocated(By by) {
		getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	public  void waitForElementClickable(WebElement webElement) {
		getWait().until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public boolean isDriverAlive() {
		try {
			getDriver().getTitle();
			return true;
		} catch (Exception e) {

			return false;
		}
	}
	
	

	public void chooseFromSpanList(WebElement element, String txtInList) throws InterruptedException {
		waitForElementVisiblity(element);
		element.click();
		Thread.sleep(200);
		javaScriptclick(getDriver().findElement(By.xpath("//span[contains(text(), '"+txtInList+"')]")));
		Thread.sleep(1000);
	}

	public void chooseFromPList(WebElement element, String txtInList) throws InterruptedException {
		waitForElementVisiblity(element);
		element.click();
		Thread.sleep(200);
		javaScriptclick(getDriver().findElement(By.xpath("//p[contains(text(), '"+txtInList+"')]")));
		Thread.sleep(500);
	}
	public  WebElement isSpanTagContain(String text) {

		return getDriver().findElement(By.xpath("//span[contains(text(), '"+text+"')]"));
	}
	public  WebElement isDivTagContain(String text) {

		return getDriver().findElement(By.xpath("//div[contains(text(), '"+text+"')]"));
	}

	/*
	 *  Initiate start
	 */


	public boolean isElementExistOnTheDOM(By by) {
		return !getDriver().findElements(by).isEmpty();
	}
}


