package webstaurantstore.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import webstaurantstore.pageobjects.CartPage;
import webstaurantstore.pageobjects.LandingPage;
import webstaurantstore.testcomponents.BaseTest;

public class WebstaurantStoreTest extends BaseTest {

	@Test(dataProvider = "getData", groups = {"AutomatedTask"})
	public void addLastProductRemoveProductFromCart(HashMap<String, String> input)
	{
		LandingPage landingPage = getLandingPage();
		landingPage.searchForProduct(input.get("product"));
		Assert.assertTrue(landingPage.SearchCriteriaInProductDesc(input.get("productSearchCriteria")));
		String lastProductDescription = landingPage.getLastProductDescription();
		landingPage.addLastProductToTheCart();
		CartPage cartPage = landingPage.goToCartPage();
		Assert.assertTrue(cartPage.checkCartForProduct(lastProductDescription));
		cartPage.deleteCartProduct();
		String emptyCartMessage = cartPage.checkCartIsEmpty();
		Assert.assertEquals(input.get("cartEmptyMessage"), emptyCartMessage);
	}
	
	@DataProvider
	public Object[][] getData() throws IOException 
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+ "\\src\\test\\java\\webstaurantstore\\data\\WebstaurantStoreTest.json");
		return new Object [][] {{data.get(0)}, {data.get(1)}};
	}
	
	
}
