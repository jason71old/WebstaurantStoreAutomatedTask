package webstaurantstore.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import webstaurantstore.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{

	ExtentTest extentTest;
	ExtentReports extentReporter = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTestTheadLocal = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) 
	{
		extentTest = extentReporter.createTest(result.getMethod().getMethodName());
		extentTestTheadLocal.set(extentTest);
	}
	
	@Override
	public void onTestSuccess(ITestResult result) 
	{
		extentTestTheadLocal.get().log(Status.PASS, "Test Passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) 
	{
 		extentTestTheadLocal.get().fail(result.getThrowable());
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		String filePath = null;
 		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		extentTestTheadLocal.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}
	
	@Override
	public void onTestSkipped(ITestResult result) 
	{
		
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
		
	}
	
	@Override
	public void onStart(ITestContext context) 
	{
		
	}
	
	@Override
	public void onFinish(ITestContext context) 
	{
		//At the end of every test execution use flush() to publish the tests reports 
		extentReporter.flush();
	}	
}
