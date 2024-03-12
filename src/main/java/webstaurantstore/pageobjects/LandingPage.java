package webstaurantstore.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webstaurantstore.abstractcomponents.AbstractComponents;

public class LandingPage extends AbstractComponents{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "input[data-testid='searchval']")
	private WebElement searchProductBox;
	
	@FindBy(css = "button[value='Search']")
	private WebElement searchProductButton;
	
	@FindBy(css = "[data-testid='itemDescription']")
	private List<WebElement> productDescriptions;
	
	@FindBy(css = ".product-box-container")
	private List<WebElement> productsList;
	
	@FindBy(linkText = "View Cart")
	private WebElement viewCartPopupButton;
	
	private By lastProductLocator = By.cssSelector("[data-testid='itemDescription']");
	private By lastAddToCartLocator = By.cssSelector("input[data-testid='itemAddCart']");
	private By viewCartPopupLocator = By.linkText("View Cart");
	
	public void searchForProduct(String productName) 
	{
		searchProductBox.sendKeys(productName);
		searchProductButton.click();
	}
	
	public Boolean SearchCriteriaInProductDesc(String productSearchCriteria) 
	{
		Boolean searchCriteriaFound = productDescriptions.stream().allMatch(productDesc->productDesc.getText().contains(productSearchCriteria));
		return searchCriteriaFound;
	}
	
	public List<WebElement> getProductList() 
	{
		return productsList;
	}
	
	public WebElement getLastProduct() 
	{
		WebElement lastProduct = productsList.stream().reduce((first, second) -> second).orElse(null);
		return lastProduct;
	}
	
	public String getLastProductDescription() 
	{
		WebElement lastProductDescription = getLastProduct().findElement(lastProductLocator);
		String lastProductDescriptionText = lastProductDescription.getText();
		return lastProductDescriptionText;
	}
	
	public void addLastProductToTheCart() 
	{
		WebElement lastProductAddToCartButton = getLastProduct().findElement(lastAddToCartLocator);
		lastProductAddToCartButton.click();
		waitForElementToAppear(viewCartPopupLocator);
	}
	
	public CartPage goToCartPage() 
	{
		viewCartPopupButton.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
}
