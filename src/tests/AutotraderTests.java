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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class AutotraderTests {

	private ChromeDriver driver;
	private WebDriverWait wait;
	
	private static final String URL = "https://www.autotrader.ca/";
	
	//home page
	private static final By makeListId   = By.id("rfMakes");
	private static final By modelListId  = By.id("rfModel");
	private static final By postalCodeId = By.id("locationAddressV2");

	//results page
	private static final By titleCountId = By.id("titleCount");
	private static final By totalCountId = By.id("sbCount");
	private static final By locationId   = By.id("faceted-Location");
	private static final By makeId       = By.id("faceted-Make");
	private static final By modelId      = By.id("faceted-Model");
	
	//test data
	private static final String MAKE        = "BMW";
	private static final String MODEL       = "4 Series";
	private static final String POSTAL_CODE = "V6L 2Y3";
	
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
	public void searchReturnsResultsTest() throws InterruptedException {
		
		openHomePage();
		
		String homePageUrl = getHomePageUrl();
		Assert.assertEquals(homePageUrl, URL);
				
		selectMake(MAKE);
		selectModel(MODEL);
		typePostalCode(POSTAL_CODE);
		executeSearch();
		
		Thread.sleep(3000);
		
		String resultsPageTitle = getResultsPageTitle();
		Assert.assertTrue(resultsPageTitle.contains(MAKE));
		Assert.assertTrue(resultsPageTitle.contains(MODEL));
				
		int count = getTitleCount();		
		Assert.assertTrue(count > 0);
		
		int totalCount = getTotalCount();		
		Assert.assertEquals(count, totalCount);
		
		Assert.assertEquals(getLocation(), POSTAL_CODE);
		Assert.assertEquals(getMake(), MAKE);
		Assert.assertEquals(getModel(), MODEL);

	}

	public String getModel() {
		WebElement modelSpan = driver.findElement(modelId);
		String model = modelSpan.getText();
		return model;
	}

	public String getMake() {
		WebElement makeSpan = driver.findElement(makeId);
		String make = makeSpan.getText();
		return make;
	}

	public String getLocation() {
		WebElement locationSpan = driver.findElement(locationId);
		String location = locationSpan.getText();
		return location;
	}

	public int getTotalCount() {
		WebElement totalCountSpan = driver.findElement(totalCountId);
		String totalCountText = totalCountSpan.getText();
		int totalCount = Integer.parseInt(totalCountText);
		return totalCount;
	}

	public int getTitleCount() {
		WebElement titleCountSpan = driver.findElement(titleCountId);
		String countText = titleCountSpan.getText();
		int count = Integer.parseInt(countText);
		return count;
	}

	public String getResultsPageTitle() {
		return driver.getTitle();
	}

	public void executeSearch() {
		WebElement postalCodeBox = driver.findElement(postalCodeId);
		postalCodeBox.sendKeys(Keys.ENTER);
	}

	public WebElement typePostalCode(String postalCode) {
		WebElement postalCodeBox = driver.findElement(postalCodeId);
		postalCodeBox.sendKeys(postalCode);
		return postalCodeBox;
	}

	public void selectModel(String model) {
		wait.until(ExpectedConditions.elementToBeClickable(modelListId));
		WebElement modelElement = driver.findElement(modelListId);
		Select modelList = new Select(modelElement);
		modelList.selectByValue(model);
	}

	public void selectMake(String make) {
		WebElement makeElement = driver.findElement(makeListId);
		Select makeList = new Select(makeElement);
		makeList.selectByValue(make);
	}

	public String getHomePageUrl() {
		return driver.getCurrentUrl();
	}

	public void openHomePage() {
		driver.get(URL);
	}
}
