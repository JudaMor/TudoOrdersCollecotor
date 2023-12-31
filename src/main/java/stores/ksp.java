package stores;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;

import base.CsvCovertor;
import base.HttpRequestSender;
import base.InitDriver;

public class ksp {

	public static void main(String[] args)throws InterruptedException, IOException  {
	HttpRequestSender requestSender = new HttpRequestSender();
	InitDriver initDriver = new InitDriver();
	
	initDriver.initializationWebDriver("http://soft.ksp.co.il/external/white_items/general/index.php");
    Thread.sleep(5000);
    initDriver.locateElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/form/div[1]/input")).sendKeys("tudoDesign");
    Thread.sleep(1000);
    initDriver.locateElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/form/div[2]/input")).sendKeys("ksptudo2020");
    Thread.sleep(500);
    initDriver.locateElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/form/button")).click();
    Thread.sleep(5000);
    initDriver.locateElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div/div/div[1]/div[2]/div[1]/button[3]/span/i")).click();
    Thread.sleep(5000);
    initDriver.closeDriver();

    CsvCovertor.organizeXLfiles();
	requestSender.removeFirstLineFromCSV(System.getProperty("user.dir")+"//ksp.csv");
	requestSender.sendWebhookAndCsvFile("https://hook.eu2.make.com/m289hxqxlh7v0vj314txke9jokunr1ci", System.getProperty("user.dir")+"//ksp.csv");
	}
    //כולל הורדת קובץ אקסל של הזמנות שעדיין לא נכנסו לטיפול?...
}
