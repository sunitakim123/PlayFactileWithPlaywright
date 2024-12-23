package Resource;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class  ExtentReporterNG1 {
	public static ExtentReports extent;

	public static ExtentReports getReportObject()
	{
		//ExtentReports //ExtentSparkReports
		String path =	System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("PlayFactile  Automation TestCases Result");
		reporter.config().setDocumentTitle("Test Results");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "sunita kumari");
		return extent;
		

}}
