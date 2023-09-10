package tests;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

public class AlertTests {

	private ChromeDriver driver;
	private Actions actions;
	private WebDriverWait wait;
	
	private static final String URL = "https://the-internet.herokuapp.com/javascript_alerts";
	
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void canCloseAlertTest() throws InterruptedException {
		
		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
		Thread.sleep(3000);
		clickJSAlert();
		Thread.sleep(3000);
		click_JS_OK_Button();
		Thread.sleep(3000);
		
	}

	@Test
	public void canConfirmAlertTest() throws InterruptedException {
		
		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
		Thread.sleep(3000);
		clickJSConfirmButton();
		Thread.sleep(3000);
		clickAcceptButton();
		Thread.sleep(3000);
		
	}

	
	
	@Test
	public void canTypeIntoAndConfirmAlertTest() throws InterruptedException {
		
		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
		Thread.sleep(3000);
		clickJSPrompt();
		Thread.sleep(3000);
		
		enterSampleText();
		clickAcceptButton();				
		
		Thread.sleep(3000);
		
	}
	private void click_JS_OK_Button() {
		driver.switchTo().alert().dismiss();
	}


	private void clickAcceptButton() {
		driver.switchTo().alert().accept();
	}

	private void enterSampleText() {
		driver.switchTo().alert().sendKeys("sample text");
	}

	private void clickJSPrompt() {
		WebElement button = driver.findElement(By.xpath("//button[text() = 'Click for JS Prompt']"));
		button.click();
	}
	private void clickJSConfirmButton() {
		WebElement button = driver.findElement(By.xpath("//button[text() = 'Click for JS Confirm']"));
		button.click();
	}
	private void clickJSAlert() {
		WebElement button = driver.findElement(By.xpath("//button[text() = 'Click for JS Alert']"));
		button.click();
	}
	private void openHomePage() {
		driver.get(URL);
	}
	
}
