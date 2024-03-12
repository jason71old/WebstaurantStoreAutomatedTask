package webstaurantstore.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandaloneTest {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://webstaurantstore.com");
		
		//LANDING PAGE*******************************************************
		
		//search for product
		driver.findElement(By.cssSelector("input[data-testid='searchval']")).sendKeys("stainless work table");
		//click search button
		driver.findElement(By.cssSelector("button[value='Search']")).click();
		//Verify search criteria is displayed in each product description
		List<WebElement> productDescriptions = driver.findElements(By.cssSelector("[data-testid='itemDescription']"));
		Boolean searchCriteriaFound = productDescriptions.stream().allMatch(productDesc->productDesc.getText().contains("Table"));
		Assert.assertTrue(searchCriteriaFound);
		//get all products
		List<WebElement> productsList = driver.findElements(By.cssSelector(".product-box-container"));
		//get the last product
		WebElement lastProduct = productsList.stream().reduce((first, second) -> second).orElse(null);
		//get the last item description
		WebElement lastProductDescription = lastProduct.findElement(By.cssSelector("[data-testid='itemDescription']"));
		String lastProductDescriptionText = lastProductDescription.getText();
		//add the last product to the cart
		WebElement lastProductAddToCartButton = lastProduct.findElement(By.cssSelector("input[data-testid='itemAddCart']"));
		lastProductAddToCartButton.click();
		//wait for the popup to appear
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("View Cart")));
		//go to cart
		driver.findElement(By.linkText("View Cart")).click();
		
		//CART PAGE***********************************************************

		//verify the product in the cart 
		List<WebElement> cartItemsDesc = driver.findElements(By.cssSelector(".itemDescription a"));
		Boolean cartItemFound = cartItemsDesc.stream().anyMatch(itemDesc->itemDesc.getText().equalsIgnoreCase(lastProductDescriptionText));
		Assert.assertTrue(cartItemFound);
		//remove product from the cart
		driver.findElement(By.cssSelector(".deleteCartItemButton")).click();
		//wait for cart is empty message
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='cartEmpty']")));
		String emptyCartMessage = driver.findElement(By.cssSelector("div[class='cartEmpty']")).getText().split("\\r?\\n")[0];
		//verify cart is empty is displayed
		Assert.assertEquals("Your cart is empty.", emptyCartMessage);
		driver.quit();
	}
}
