package selenium.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class WebTest
{
	private static WebDriver driver;
	
	@BeforeClass
	public static void setUp() throws Exception 
	{
		ChromeDriverManager.getInstance().setup();
		driver = new ChromeDriver();
	}
	
	@AfterClass
	public static void  tearDown() throws Exception
	{
		driver.close();
		driver.quit();
	}

	@Test
	public void closedCount() throws Exception
	{
		//Wait until web page with CLOSED studies are loaded
		driver.get("http://www.checkbox.io/studies.html");
		WebDriverWait waitTime = new WebDriverWait(driver, 15);
		waitTime.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='status']/span[.='CLOSED']")));
		
		//Store all closed survey web elements in a list
		List<WebElement> closed = driver.findElements(By.xpath("//a[@class='status']/span[.='CLOSED']"));
		
		//Check if the size of the list is 5
		assertNotNull(closed);
		assertEquals(5, closed.size());
	}
	
	

}