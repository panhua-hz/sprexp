package tasks;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GridTest {

	public static void main(String[] args) throws MalformedURLException {
		//DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setBrowserName("*firefox");
		//CommandExecutor executor = new SeleneseCommandExecutor(new URL("http://localhost:4444/"), new URL("http://www.google.com/"), capabilities);
		//WebDriver driver = new RemoteWebDriver(executor, capabilities);
		//WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/"), capabilities);
		
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		//DesiredCapabilities capability = DesiredCapabilities.chrome();
		//capability.setBrowserName("firefox"); 
		//capability.setPlatform("WINDOWS");  
		//capability.setVersion("17.0.1");
		WebDriver driver = new RemoteWebDriver(  new URL("http://192.168.0.105:4444/wd/hub"), capability);
		
		
		// And now use this to visit Google
        driver.get("https://www.baidu.com/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        //WebElement element = driver.findElement(By.name("q"));
        WebElement element = driver.findElement(By.id("kw"));
        // Enter something to search for
        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
        
        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());
        
        //Close the browser
        driver.quit();
	}

}
