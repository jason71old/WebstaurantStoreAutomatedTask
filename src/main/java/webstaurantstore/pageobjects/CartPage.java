package webstaurantstore.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webstaurantstore.abstractcomponents.AbstractComponents;

public class CartPage extends AbstractComponents{

	WebDriver driver;

	public CartPage(WebDriver driver) 
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".itemDescription a")
	private List<WebElement> cartProductDescriptions;
	
	@FindBy(css = ".deleteCartItemButton")
	private WebElement deleteCartProduct;
	
	private By cartEmptymessage = By.cssSelector("div[class='cartEmpty']");
	
	public List<WebElement> getCartProductDescsriptions() 
	{
		return cartProductDescriptions;
	}
	
	public Boolean checkCartForProduct(String productDescription) 
	{
		Boolean productFound = cartProductDescriptions.stream().anyMatch(itemDesc->itemDesc.getText()
				.equalsIgnoreCase(productDescription));
		return productFound;
	}
	
	public void deleteCartProduct() 
	{
		deleteCartProduct.click();
		waitForElementToAppear(cartEmptymessage);
	}
	
	public String checkCartIsEmpty() 
	{
		String emptyCartMessage = driver.findElement(cartEmptymessage).getText().split("\\r?\\n")[0];
		return emptyCartMessage;
	}
}
