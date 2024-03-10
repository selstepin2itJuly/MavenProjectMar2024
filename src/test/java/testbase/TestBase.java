package testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class TestBase extends BaseClass {
	private static final Logger logger = (Logger) LogManager.getLogger(TestBase.class);
	public Properties prop;
	public String browser;
	public WebDriver getInstance() throws IOException
	{
		logger.info("Read config file");
		String conf = "./src/main/resources/config/config.properties";
		FileInputStream inStream = new FileInputStream(new File(conf));
		prop = new Properties();
		prop.load(inStream);
		browser = prop.getProperty("browser").toLowerCase();
		logger.info("Get the browser type: "+browser);
		switch(browser)
		{
			case "chrome": 	System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeagent"));
							driver = new ChromeDriver();
							break;
			case "firefox":	System.setProperty("webdriver.gecko.driver", prop.getProperty("firefoxagent"));
							FirefoxOptions opt = new FirefoxOptions();
							opt.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
							driver = new FirefoxDriver(opt);
							break;
			case "edge":	System.setProperty("webdriver.edge.driver", prop.getProperty("edgeagent"));
							driver = new EdgeDriver();
							break;
						
			default:
				logger.error("Browser name not found, missing or incorrect!!");
				new Throwable().initCause(null);
		
		}
		driver.manage().window().maximize();	
		//driver.manage().window().setSize(new Dimension(667,375));
		driver.get(prop.getProperty("url"));
		logger.info("Url opened:"+prop.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		logger.info("Implicit wait:"+15);
		//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}
	
	/*public static WebDriver getInstancestatic()
	{
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		sdriver = new ChromeDriver();
		sdriver.manage().window().maximize();
		//sdriver.manage().window().setSize(new Dimension(667,375));
		sdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		return sdriver;
	}*/
	
	public void closeDriver() {
		driver.quit();
	}
}
