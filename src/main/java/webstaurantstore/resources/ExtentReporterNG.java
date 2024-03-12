package webstaurantstore.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	public static ExtentReports getReportObject() 
	{
		String path = System.getProperty("user.dir")+ "\\reports\\index.html";
		ExtentSparkReporter  extentSparkReporter = new ExtentSparkReporter(path);
		extentSparkReporter.config().setReportName("Web Automation Results");
		extentSparkReporter.config().setDocumentTitle("Test Results");
		ExtentReports extentReporter = new ExtentReports();
		extentReporter.attachReporter(extentSparkReporter);
		extentReporter.setSystemInfo("Tester", "Timothy Smith");
		return extentReporter;
	}	
}
