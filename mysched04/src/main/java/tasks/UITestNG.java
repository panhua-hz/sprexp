package tasks;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;

public class UITestNG {
	private static final Logger logger = LoggerFactory.getLogger(UITestNG.class);
	private WebDriver driver = null;
	
	@Test
	@Parameters({ "testUrl"})
	public void uitest(String testUrl) {
		//driver.get("https://www.baidu.com/");
		driver.get(testUrl);
		WebElement element = driver.findElement(By.id("kw"));
		element.sendKeys("Cheese!");
		element.submit();
		
		logger.info("Page title is: " + driver.getTitle());
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });
		logger.info("Page title is: " + driver.getTitle());
	}

	@BeforeSuite
	@Parameters({ "driverLocation", "driverType"})
	public void beforeSuite(String driverLocation, String driverType) {
		//System.setProperty("webdriver.gecko.driver", "D:/swused/selenium/webdrivers/geckodriver.exe");
		//driver = new FirefoxDriver();
		System.setProperty("webdriver.gecko.driver", driverLocation);
		if (driverType.equalsIgnoreCase("FireFox")){
			driver = new FirefoxDriver();
		}
	}

	@AfterSuite
	public void afterSuite() {
		driver.quit();
	}

}
