package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class WindowsTests {

	private ChromeDriver driver;
	
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void canSelectElementFromWindow() throws InterruptedException {
		String url = "https://the-internet.herokuapp.com/windows";
		
		driver.get(url);
		Assert.assertTrue(driver.getCurrentUrl().equals(url));
		
		//get handle of first page
		String firstPageHandle = driver.getWindowHandle();
		System.out.println("first page handle = " + firstPageHandle);
		
		Thread.sleep(3000);
		
		By linkXpath = By.xpath("//a[@href = '/windows/new']");
		WebElement link = driver.findElement(linkXpath);
		link.click();
		
		Thread.sleep(3000);
		
		//get list of all window handles
		List<String> pageHandles = new ArrayList<>(driver.getWindowHandles());
		Assert.assertEquals(2, pageHandles.size());
		
		//remove first window handle from list
		pageHandles.remove(firstPageHandle);
		
		//get handle of second window
		String secondPageHandle = pageHandles.get(0);
		System.out.println("second page handle = " + secondPageHandle);
		
		//switch to second window
		driver.switchTo().window(secondPageHandle);
		
		By labelXpath = By.xpath("//h3");
		WebElement label = driver.findElement(labelXpath);
		
		Assert.assertTrue(label.getText().contains("New Window"));
		
		driver.close();
		
		Thread.sleep(3000);
		
		//switch to first window
		driver.switchTo().window(firstPageHandle);		
		label = driver.findElement(labelXpath);
		
		Assert.assertTrue(label.getText().contains("Opening a new window"));
				
	}
		
}
