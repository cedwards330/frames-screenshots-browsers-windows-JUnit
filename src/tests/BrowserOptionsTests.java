package tests;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserOptionsTests {

	private static final String URL = "https://www.vpl.ca/";
	
	private ChromeDriver driver;
	
	@After
	public void tearDown() {
		driver.quit();
	}
		
	@Test
	public void canOpenSiteInNormalMode() {
		
		openChromeDriver();
		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
	}
	
	@Test
	public void canOpenSiteInIncognitoMode() {
		
		ChromeOptions options = openInCognito();
		driverOptions(options);
		
		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
	}

	@Test
	public void canOpenSiteInHeadlessMode() {
		
		ChromeOptions options = openInHeadless();
		driverOptions(options);
		
		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
	}

	
	
	@Test
	public void canOpenSiteWithOtherOptions() {
		
		ChromeOptions options = new ChromeOptions();
		
		options.setAcceptInsecureCerts(true);
		options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		options.setPageLoadTimeout(Duration.ofSeconds(30));
		
		driverOptions(options);
		
		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
	}
	private void driverOptions(ChromeOptions options) {
		driver = new ChromeDriver(options);
	}

	private ChromeOptions openInHeadless() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		return options;
	}
	private ChromeOptions openInCognito() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		return options;
	}
	private void openHomePage() {
		driver.get(URL);
	}

	private void openChromeDriver() {
		ChromeOptions options = new ChromeOptions();
		driverOptions(options);
	}
	
}
