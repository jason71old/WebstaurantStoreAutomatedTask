package webstaurantstore.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import webstaurantstore.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	LandingPage landingPage;
	Properties properties = new Properties();
	
	public WebDriver initializeWebDriver() throws IOException 
	{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\webstaurantstore\\resources\\GlobalData.properties");
		properties.load(fis);
		String browserName = System.getProperty("browser")!= null ? System.getProperty("browser") : properties.getProperty("browser");
		if(browserName.contains("chrome")) 
		{
			ChromeOptions chromeOptions = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")) 
			{
				chromeOptions.addArguments("headless");
			}
			driver = new ChromeDriver(chromeOptions);
			driver.manage().window().setSize(new Dimension(1440, 900));
		}
		else if(browserName.equalsIgnoreCase("edge")) 
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox")) 
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	
 	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException 
	{
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data  = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
 		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException 
	{
		TakesScreenshot screenShot = (TakesScreenshot)driver; 
		File source = screenShot.getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + testCaseName + ".png";
		File file = new File(filePath);  
		FileUtils.copyFile(source, file);
		return filePath;
	}
	
	@BeforeMethod(alwaysRun=true)
	public void goToLandingPage() throws IOException 
	{
		driver = initializeWebDriver();
		driver.get(properties.getProperty("url"));
	}
	
	public LandingPage getLandingPage() 
	{
		LandingPage landingPage = new LandingPage(driver);
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() 
	{
		driver.quit();
	}	
}
