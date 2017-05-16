# Selenium-Testing-Checkbox.IO

Unit testing using Selenium on a survey hosting site, http://checkbox.io

## Objective 

To write unit tests using Selenium that verify the following on **http://checkbox.io/studies.html**

* The participant count of "Frustration of Software Developers" is 55
* The total number of studies closed is 5.
* If a status of a study is open, you can click on a "Participate" button.
* Check if the "Software Changes Survey" has a Amazon reward image.

## Descriptions of Each Test Case

### The Participant Count of "Frustration of Software Developers" is 55

This test case checks whether the `<SPAN>` web element corresponding to the participant count of "Frustration of Software Developers" survey equals the text "55". If this is true, the test case passes.

```
@Test
	public void participantCount() throws Exception
	{
		//Wait until the span element containing participant count of "Frustration of Software Developers" is loaded
		driver.get("http://www.checkbox.io/studies.html");
		WebDriverWait waitTime = new WebDriverWait(driver, 15);
		waitTime.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='span4']//a[@href='http://checkbox.io/studies/?id=5432cbcd00b6a6f9270002ca']/following-sibling::p/span[1]")));
		
		//Check if the text on the span element equals 55
		WebElement msg = driver.findElement(By.xpath("//div[@class='span4']//a[@href='http://checkbox.io/studies/?id=5432cbcd00b6a6f9270002ca']/following-sibling::p/span[1]"));
		assertEquals("55", msg.getText());
	}

```

### The Total Number of Studies Closed is 5

This test case stores all web elements corresponding to CLOSED surveys in a list and then checks whether the size of the list equals 5. If this is true, the test case passes.

```
@Test
	public void closedCount() throws Exception
	{
		//Wait until web page with CLOSED surveys are loaded
		driver.get("http://www.checkbox.io/studies.html");
		WebDriverWait waitTime = new WebDriverWait(driver, 15);
		waitTime.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='status']/span[.='CLOSED']")));
		
		//Store all closed survey web elements in a list
		List<WebElement> closed = driver.findElements(By.xpath("//a[@class='status']/span[.='CLOSED']"));
		
		//Check if the size of the list is 5
		assertNotNull(closed);
		assertEquals(5, closed.size());
	}

```

### If a status of a study is open, you can click on a "Participate" button

This test case tries to click on each "Participate" button on an OPEN study and checks whether it opens the "Participate" link. If this is true, the test case passes.

```
@Test
	public void buttonCanBeClicked() throws Exception
	{
		driver.get("http://www.checkbox.io/studies.html");
		
		//Wait until all participate buttons in OPEN studies are clickable
		WebDriverWait waitTime = new WebDriverWait(driver, 15);
		waitTime.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='span4']//a[@class='status']/span[.='OPEN']/../following-sibling::div/button[.='Participate']")));
		
		//Store all participate buttons (Web elements) in a list
		List<WebElement> msg = driver.findElements(By.xpath("//div[@class='span4']//a[@class='status']/span[.='OPEN']/../following-sibling::div/button[.='Participate']"));
		
		//Iterate for each individual element in the list
		for (WebElement element : msg){
			
			//click each individual participate button
			element.click();
			
			//Switch tabs to the newly-opened link
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(1));
		    
		    //Wait until the SURVEY SECTION header is loaded and find the SURVEY link in the HEADER
		    waitTime = new WebDriverWait(driver, 15);
			waitTime.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#surveySection']")));
			element = driver.findElement(By.xpath("//a[@href='#surveySection']"));
			
			//Check if the SURVEY link in the HEADER has the text "Survey" 
			assertEquals("Survey", element.getText());
			
			//Close the tab and reload the studies page
		    driver.close();
		    driver.switchTo().window(tabs.get(0));
			}
	}

```

### Check if the "Software Changes Survey" has a Amazon reward image

This test case checks whether the `<img>` web element corresponding to "Software Changes Survey" has the same source attribute as the Amazon Reward Image. If this is true, the test case passes.

```
@Test
	public void checkAmazonImage() throws Exception
	{
		//Wait until the image tag containing Amazon Reward image is loaded
		driver.get("http://www.checkbox.io/studies.html");	
		WebDriverWait waitTime = new WebDriverWait(driver, 15);
		waitTime.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='span8']/h3/span[.='Software Changes Survey']/../../div[1]/p/following-sibling::div/span/img")));
		
		//Get the source attribute of image tag
		WebElement msg = driver.findElement(By.xpath("//div[@class='span8']/h3/span[.='Software Changes Survey']/../../div[1]/p/following-sibling::div/span/img"));
		msg.getAttribute("src");
		
		//Check if the source attribute points to Amazon Reward Image
		assertNotNull(msg);
		assertEquals("http://www.checkbox.io/media/amazongc-micro.jpg", msg.getAttribute("src"));
	}

```