package webstaurantstore.abstractcomponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponents {

	WebDriver driver;
	WebDriverWait explicitWait;

	public AbstractComponents(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}
	
	public void waitForElementToAppear(By locator) 
	{
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}
