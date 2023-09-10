package tests;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class TakeScreenshotTests {

	private ChromeDriver driver;
	
	private String url = "https://www.vpl.ca/";
	private String resultsPageUrl = "vpl.bibliocommons.com";
	
	@Before
	public void setUp() throws IOException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();		
	}
	
	@BeforeClass
	public static void cleanData() throws IOException {
		cleanExistingScreenshots();
	}

	@After
	public void tearDown() throws IOException {
		getScreenshot("error");
		driver.quit();
	}
	
	@Test
	public void broadenSearchTest() throws InterruptedException, IOException {	

		String testName = "broadenSearchTest";
		
		driver.get("https://www.vpl.ca");
		
		Assert.assertEquals(driver.getCurrentUrl(), url);
		
		getScreenshot(testName);
		
		
		WebElement searchBox = driver.findElement(By.id("edit-keyword"));
		searchBox.sendKeys("java");
		
		WebElement searchButton = driver.findElement(By.id("edit-submit"));
		searchButton.click();
		
		Assert.assertTrue(driver.getCurrentUrl().contains(resultsPageUrl));
		
		getScreenshot(testName);		
		
		
		WebElement broadenSearchLink = driver.findElement(By.xpath("//a[@class = 'broaden-search-link']"));
		broadenSearchLink.click();
		
		Assert.assertTrue(driver.getCurrentUrl().contains("searchType=bkw"));
				
		Thread.sleep(5000);
		
		getScreenshot(testName);

	}

	
	@Test
	public void broadenSearchTest2() throws InterruptedException, IOException {
		
		String testName = "broadenSearchTest2";

		driver.get("https://www.vpl.ca");
		
		Assert.assertEquals(driver.getCurrentUrl(), url);
		
		getScreenshot(testName);
		
		
		WebElement searchBox = driver.findElement(By.id("edit-keyword"));
		searchBox.sendKeys("java");
		
		WebElement searchButton = driver.findElement(By.id("edit-submit"));
		searchButton.click();
		
		Assert.assertTrue(driver.getCurrentUrl().contains(resultsPageUrl));
		
		getScreenshot(testName);		
		
		
		WebElement broadenSearchLink = driver.findElement(By.xpath("//a[@class = 'broaden-search-link']"));
		broadenSearchLink.click();
		
		Assert.assertTrue(driver.getCurrentUrl().contains("searchType=bkw"));
				
		Thread.sleep(5000);
		
		getScreenshot(testName);

	}
	
	public void getScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		Random random = new Random();
		
		int n = random.nextInt(10000);
		
		String screenshotName = "./src/screenshots/screenshot" + n + ".jpg";
					
		FileUtils.copyFile(scrFile, new File(screenshotName));
	}

	public void getScreenshot(String testName) throws IOException {
		String dateTimeText = getCurrentDateTime();
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		String screenshotName = "./src/screenshots/" + testName + "_" + dateTimeText + ".jpg";					
		
		FileUtils.copyFile(scrFile, new File(screenshotName));
	}

	public String getCurrentDateTime() {
		LocalDateTime dateTime = LocalDateTime.now();
		
		String dateTimeText = dateTime.toString();
		
		dateTimeText = dateTimeText.replace(":", "");
		dateTimeText = dateTimeText.replace(".", "_");
		dateTimeText = dateTimeText.replace("T", "_");
		
		return dateTimeText;
	}
	
	public static void cleanExistingScreenshots() throws IOException {
		FileUtils.cleanDirectory(new File("./src/screenshots/"));
	}

}

