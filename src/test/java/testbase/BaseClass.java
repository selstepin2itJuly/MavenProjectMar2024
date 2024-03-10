package testbase;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class BaseClass {
	private static final Logger logger = (Logger) LogManager.getLogger(BaseClass.class);
	public WebDriver driver;
	//public static WebDriver sdriver;
	
	public void scrollToElement(WebElement ele)
	{
		logger.info("Scroll To element -->"+ele);
		JavascriptExecutor j = (JavascriptExecutor) driver;
		//((JavascriptExecutor) sdriver).executeScript("", "");
		j.executeScript("arguments[0].scrollIntoView()", ele);//true
		j.executeScript("window.scrollBy(0,400)", "");
	}
	public void scrollToElementByActions(WebElement ele)
	{
		logger.info("Scroll To element By Actions -->"+ele);
		JavascriptExecutor j = (JavascriptExecutor) driver;
		Actions ac = new Actions(driver);
		ac.scrollToElement(ele).perform();
		j.executeScript("window.scrollBy(0,400)", "");
	}
	
	public void clickOnElementUsingActions(WebElement ele)
	{
		logger.info("Click on element by Actions-->"+ele);
		new Actions(driver).click(ele).perform();
	}
	/*public static void scrollToElement(WebElement ele)
	{
		JavascriptExecutor j = (JavascriptExecutor) sdriver;
		//((JavascriptExecutor) sdriver).executeScript("", "");
		j.executeScript("arguments[0].scrollIntoView()", ele);//true
		j.executeScript("window.scrollBy(0,400)", "");
	}
	public static void scrollToElementByActions(WebElement ele)
	{
		JavascriptExecutor j = (JavascriptExecutor) sdriver;
		Actions ac = new Actions(sdriver);
		ac.scrollToElement(ele).perform();
		j.executeScript("window.scrollBy(0,400)", "");
	}*/
	
	public void waitForElementClickable(WebElement ele)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		//wait.pollingEvery(Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(ele));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	public void waitForElementVisbility(WebElement ele)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		//wait.pollingEvery(Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(ele));
		wait.until(ExpectedConditions.visibilityOf(ele));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	}
	
	public void attachScreenshot() throws IOException
	{
		File loc = new File("ScreenshotFolder");
		String path = null;
		if (loc.exists()) {
			path = loc.getAbsolutePath();
		} else {
			loc.mkdirs();
			path = loc.getAbsolutePath();
		}
		TakesScreenshot ts = (TakesScreenshot) driver;
		File from = ts.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(from, new File(path + "/" + getDate() + "_image.jpg"));// jpg & png

		TakesScreenshot ts1 = (TakesScreenshot) driver;
		String src = ts1.getScreenshotAs(OutputType.BASE64);
		String image="<img src=\"data:image/png;base64,"+src+"\" height=\"600\" width=\"800\" />";
		Reporter.log(image);
	}
	
	public String getDate() {

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MMM_dd_HH_mm_ss_SSS");
		String dd = sdf.format(d);
		return dd;
	}
}
