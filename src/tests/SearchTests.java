package tests;

import java.time.Duration;
import java.util.List;

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

public class SearchTests {

	private ChromeDriver driver;
	private WebDriverWait wait;
	
	private static final String URL = "https://www.bestbuy.ca/en-ca";
	
	private By searchButtonXpath = By.xpath("//button[@aria-label = 'Search']");
	private By searchBoxXpath    = By.name("search");
	private By searchFormXpath   = By.xpath("//form[contains(@class, 'searchForm')]");
	private By suggestionXpath   = By.xpath("//a[contains(@data-automation, 'autocomplete-entry')]");
	
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
	public void searchByClickingSearchButtonTest() {

		openHomePage();
		Assert.assertEquals(driver.getCurrentUrl(), URL);
		
		searchProduct();		
		clickSearchButton();
		waitUntilSearchContainsProduct();
		
	}

	@Test
	public void searchByPressingEnterTest() {

		openHomePage();
		Assert.assertEquals(driver.getCurrentUrl(), URL);
		
		searchAndThenHitEnterKey();
		waitUntilSearchContainsProduct();
		
	}

	@Test
	public void searchBySubmittingSearchFormTest() {

		openHomePage();
		Assert.assertEquals(driver.getCurrentUrl(), URL);
		
		searchProduct();
		submitWithInFormBox();
		waitUntilSearchContainsProduct();
		
	}

	@Test
	public void searchByClickingSuggestionTest() throws InterruptedException {

		openHomePage();
		Assert.assertEquals(driver.getCurrentUrl(), URL);
		
		searchUsingSuggestionField();
		List<WebElement> suggestionList = waitUntilListCount();
		Thread.sleep(3000);
		suggestionList.get(2).click();
		waitUntilSearchContainsResults();
		
	}
	private void searchAndThenHitEnterKey() {
		WebElement searchBox = driver.findElement(searchBoxXpath);
		searchBox.sendKeys("iphone");
		searchBox.sendKeys(Keys.ENTER);
	}

	private void waitUntilSearchContainsResults() {
		wait.until(ExpectedConditions.urlContains("https://www.bestbuy.ca/en-ca/search"));
	}

	private List<WebElement> waitUntilListCount() {
		wait.until(ExpectedConditions.numberOfElementsToBe(suggestionXpath, 10));
		List<WebElement> suggestionList = driver.findElements(suggestionXpath);
		return suggestionList;
	}
	private void waitUntilSearchContainsProduct() {
		wait.until(ExpectedConditions.urlContains("https://www.bestbuy.ca/en-ca/category/iphones"));
	}

	private void clickSearchButton() {
		WebElement searchButton = driver.findElement(searchButtonXpath);
		searchButton.click();
	}

	private void searchProduct() {
		WebElement searchBox = driver.findElement(searchBoxXpath);
		searchBox.sendKeys("iphone");
	}

	private void openHomePage() {
		driver.get(URL);
	}

	private void searchUsingSuggestionField() {
		WebElement searchBox = driver.findElement(By.name("search"));
		searchBox.sendKeys("iphone");
	}
	private void submitWithInFormBox() {
		WebElement searchForm = driver.findElement(searchFormXpath);
		searchForm.submit();
	}
}
