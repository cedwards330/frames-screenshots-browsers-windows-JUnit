package tests;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AudoTraderTest2 {
	private ChromeDriver driver;
	private WebDriverWait wait;
	
	private static final String URL = "https://www.autotrader.ca/";
	
	
	//home page
	private static final By postalCodeId = By.id("locationAddressV2");
	private static final By showMeCars_XPATH = By.xpath(("(//a[@id = 'SearchButton'])[1]"));
	
	//results page
	private static final By fordTruck_Xpath = By.xpath(("(//a[@class = 'mvp-button'])[1]"));
	private static final By totalCountId = By.id("sbCount");
	
	//results page
	
	
	//test data
	
	private static final String POSTAL_CODE = "T2S 0C9";
	
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void searchBasedOnLocation() throws InterruptedException {
		driver.get(URL);
		Assert.assertEquals(driver.getCurrentUrl(), URL);
		
		WebElement postalCodeBox = driver.findElement(postalCodeId);
		postalCodeBox.sendKeys("T2S 0C9");
		//postalCodeBox.sendKeys(Keys.ENTER);
		
		WebElement clickShowMeCarsButton = driver.findElement(showMeCars_XPATH);
		clickShowMeCarsButton.click();
		
		WebElement getPopularCarButton = driver.findElement(fordTruck_Xpath);
		getPopularCarButton.click();
		
		WebElement totalCountSpan = driver.findElement(totalCountId);
		String totalCountText = totalCountSpan.getText();
		int totalCount = Integer.parseInt(totalCountText);		
		//Assert.assertTrue(totalCount > 0);

	}

}
