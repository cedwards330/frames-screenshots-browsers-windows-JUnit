package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FramesTest {
private ChromeDriver driver;
private static final String URL = "https://the-internet.herokuapp.com/nested_frames";
private static final By CONTENT_ID = By.id("content");
private static final By bodyXpath = By.xpath("//body");
	
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Ignore  //this test will fail cause the frames are all individual html codes, 
	//so this needs to be broken down further
	public void cannotSelectElementFromFrame() {

		openHomePage();
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
		WebElement bodyElement = findLocatorTopFrame();
		Assert.assertTrue(bodyElement.getText().contains("LEFT"));
		
	}

	@Test
	public void canSelectElementFromFrame() {
		
		driver.get(URL);
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
		
		topFrameLocator();// switch to top frame
		switchToTopLeftFrameLocator();//switch to left frame
		
		By bodyXpath = By.xpath("//body");
		WebElement bodyElement = driver.findElement(bodyXpath);
		
		Assert.assertTrue(bodyElement.getText().contains("LEFT"));
		
		switchToParentFrameLocator();//leave left frame, still in top frame
		
		
		
		driver.switchTo().frame("frame-middle");//switch to middle frame
		
		WebElement contentElement = goToMiddleFrameLocator();
		Assert.assertTrue(contentElement.getText().contains("MIDDLE"));
		
		switchToParentFrameLocator();//leave middle frame, still in top frame
		switchToTopRightFrameLocator();
		
		bodyElement = driver.findElement(bodyXpath);
		
		Assert.assertTrue(bodyElement.getText().contains("RIGHT"));
		
		switchToParentFrameLocator();//leave right frame, still in top frame
		
		driver.switchTo().defaultContent();
	}
	
	@Test
	public void canSelectElementFromFrame2() {
		driver.get(URL);
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
		
		driver.switchTo().frame(0);// switch to top frame
		driver.switchTo().frame(0);//switch to left frame
		
		By bodyXpath = By.xpath("//body");
		WebElement bodyElement = driver.findElement(bodyXpath);		
		
		Assert.assertTrue(bodyElement.getText().contains("LEFT"));
		
		switchToParentFrameLocator();//leave left frame, still in top frame
		
		driver.switchTo().frame(1);//switch to middle frame
		
		WebElement contentElement = goToMiddleFrameLocator();
		Assert.assertTrue(contentElement.getText().contains("MIDDLE"));
		
		switchToParentFrameLocator();//leave middle frame, still in top frame
		
		driver.switchTo().frame(2);
		
		bodyElement = driver.findElement(bodyXpath);
		Assert.assertTrue(bodyElement.getText().contains("RIGHT"));
		
		switchToParentFrameLocator();//leave right frame, still in top frame
		
		driver.switchTo().defaultContent();
	}
	
	@Test
	public void canSelectElementFromFrame3() {
		driver.get(URL);
		Assert.assertTrue(driver.getCurrentUrl().equals(URL));
		
		WebElement topFrame = topFrameByXPath();
		
		switchToTopFrame(topFrame);// switch to top frame
		switchToLeftFrameXPath();
		
		By bodyXpath = By.xpath("//body");
		WebElement bodyElement = driver.findElement(bodyXpath);		
		
		Assert.assertTrue(bodyElement.getText().contains("LEFT"));
		
		switchToParentFrameLocator();//leave left frame, still in top frame
		
		switchToMiddleFrameXPath();
		
		By contentId = By.id("content");
		WebElement contentElement = driver.findElement(contentId);
		
		Assert.assertTrue(contentElement.getText().contains("MIDDLE"));
		
		switchToParentFrameLocator();//leave middle frame, still in top frame
		
		
		
		By rightFrameBy = By.xpath("//frame[@src = '/frame_right']");
		WebElement rightFrame = driver.findElement(rightFrameBy);
		switchToTopFrame(rightFrame);
		
		bodyElement = driver.findElement(bodyXpath);
		
		Assert.assertTrue(bodyElement.getText().contains("RIGHT"));
		
		switchToParentFrameLocator();//leave right frame, still in top frame
		
		driver.switchTo().defaultContent();
	}

	private void switchToMiddleFrameXPath() {
		By middleFrameBy = By.xpath("//frame[@src = '/frame_middle']");
		WebElement middleFrame = driver.findElement(middleFrameBy);
		switchToTopFrame(middleFrame);
	}

	private void switchToLeftFrameXPath() {
		By leftFrameBy = By.xpath("//frame[@src = '/frame_left']");
		WebElement leftFrame = driver.findElement(leftFrameBy);
		switchToTopFrame(leftFrame);
	}

	private void switchToTopFrame(WebElement topFrame) {
		driver.switchTo().frame(topFrame);
	}

	private WebElement topFrameByXPath() {
		By topFrameBy = By.xpath("//frame[@src = '/frame_top']");
		WebElement topFrame = driver.findElement(topFrameBy);
		return topFrame;
	}
	private WebDriver switchToTopLeftFrameLocator() {
		return driver.switchTo().frame("frame-left");
	}

	private WebDriver switchToTopRightFrameLocator() {
		return driver.switchTo().frame("frame-right");
	}
	private WebDriver switchToParentFrameLocator() {
		return driver.switchTo().parentFrame();
	}

	private WebElement goToMiddleFrameLocator() {
		By contentId = By.id("content");
		WebElement contentElement = driver.findElement(CONTENT_ID);
		return contentElement;
	}

	private WebDriver topFrameLocator() {
		return driver.switchTo().frame("frame-top");
	}
	private WebElement findLocatorTopFrame() {
		By bodyXpath = By.xpath("//body");
		WebElement bodyElement = driver.findElement(bodyXpath);
		return bodyElement;
	}
	private void openHomePage() {
		driver.get(URL);
	}
		
}