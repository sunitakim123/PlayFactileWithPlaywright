package Resource;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;
import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Base {
	 
		    public Playwright playwright;
		    public Browser browser;
		    public static Page page;
		   
		    public  static Properties config = new Properties();

		    // Static block to load the configuration file once

		    
		    public void setUp() throws IOException{

			FileInputStream fis = new FileInputStream( "./src/main/java/Resource/config.properties");
			config.load(fis);

		    // Setup method to initialize the browser and page
		            String browserName = config.getProperty("browser");
		        boolean isHeadless = Boolean.parseBoolean(config.getProperty("headless"));

		        // Initialize Playwright
		        playwright = Playwright.create();

		        // Launch browser based on configuration
		        if ("chromium".equalsIgnoreCase(browserName)) {
		            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
		        } else if ("firefox".equalsIgnoreCase(browserName)) {
		            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
		        } else if ("webkit".equalsIgnoreCase(browserName)) {
		            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
		        } else {
		            throw new IllegalArgumentException("Invalid browser specified in config.properties: " + browserName);
		        }

		        // Create a new page
		        page = browser.newPage();
		    }

		    // Teardown method to close browser and Playwright instance
		  /*  public void tearDown() {
		            if (playwright != null) {
		           playwright.close();
		        }
		    
		        }
		       */
		    public  String takeScreenshot(String testcaseName)
		    {
		    	String path = System.getProperty("user.dir") + "\\reports\\" + testcaseName + ".png";
		    	page.screenshot(new Page.ScreenshotOptions()
		    			.setPath(Paths.get(path))
		    			.setFullPage(true));
		    			return path;
		    			
		    	
		    }
		}
