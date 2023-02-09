import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PlanitTestingSuit {
	WebDriver driver;

	@BeforeMethod
	public void initDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://jupiter.cloud.planittesting.com");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void TC01() {
		
		// From the home page go to contact page
		driver.findElement(By.xpath("//a[normalize-space()='Contact']")).click();
		// Click submit button
		driver.findElement(By.xpath("//a[normalize-space()='Submit']")).click();

		// Verify error messages and Populate mandatory fields
		String AcutalStringErrorMsg1 = driver.findElement(By.xpath("//span[@id='forename-err']")).getText();
		String ExpectedStringErrorMsg1 = "Forename is required";
		Assert.assertEquals(ExpectedStringErrorMsg1, AcutalStringErrorMsg1);

		String AcutalStringErrorMsg2 = driver.findElement(By.xpath("//span[@id='email-err']")).getText();
		String ExpectedStringErrorMsg2 = "Email is required";
		Assert.assertEquals(ExpectedStringErrorMsg2, AcutalStringErrorMsg2);

		String AcutalStringErrorMsg3 = driver.findElement(By.xpath("//span[@id='message-err']")).getText();
		String ExpectedStringErrorMsg3 = "Message is required";
		Assert.assertEquals(ExpectedStringErrorMsg3, AcutalStringErrorMsg3);

		// Validate errors are gone
		driver.findElement(By.xpath("//input[@id='forename']")).sendKeys("Planit Testing");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("Automationdemo@gmail.com");
		driver.findElement(By.xpath("//textarea[@id='message']")).sendKeys("Welcome to Automation World");

		// Click submit button
		driver.findElement(By.xpath("//a[normalize-space()='Submit']")).click();
	}

	@Test(invocationCount=5)
	public void TC02() {
		
		// From the home page go to contact page
		driver.findElement(By.xpath("//a[normalize-space()='Contact']")).click();
		// Click submit button
		//driver.findElement(By.xpath("//a[normalize-space()='Submit']")).click();
		// Populate mandatory fields
		driver.findElement(By.xpath("//input[@id='forename']")).sendKeys("Selenium");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("demo@gmail.com");
		driver.findElement(By.xpath("//textarea[@id='message']")).sendKeys(" Automation World");

		// Click submit button
		driver.findElement(By.xpath("//a[normalize-space()='Submit']")).click();
		
		String SuccessMsg;
		SuccessMsg = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		
		String expected_string = "Thanks Selenium, we appreciate your feedback.";
		Assert.assertEquals(SuccessMsg, expected_string);
			
	}

	@Test()
	public void TC03() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//a[normalize-space()='Shop']")).click();

		// Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear

		for (int i = 0; i < 2; i++) {
			driver.findElement(By.xpath("//li[@id='product-2']//a[@class='btn btn-success'][normalize-space()='Buy']"))
					.click();
		}

		for (int i = 0; i < 5; i++) {
			driver.findElement(By.xpath("//li[@id='product-4']//a[@class='btn btn-success'][normalize-space()='Buy']"))
					.click();
		}

		for (int i = 0; i < 3; i++) {
			driver.findElement(By.xpath("//li[@id='product-7']//a[@class='btn btn-success'][normalize-space()='Buy']"))
					.click();
		}
		
		// Go to the cart page
		driver.findElement(By.xpath("//a[@href='#/cart']")).click();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {	
			e.printStackTrace();
		}
		
			// Verify the subtotal for each product is correct
			String StuffedFrog = driver.findElement(By.xpath("//td[normalize-space()='$21.98']")).getText();
			String StuffedFrog_subtotal = StuffedFrog.substring(1);
			Assert.assertEquals(StuffedFrog_subtotal, "21.98");

			String FluffyBunny = driver.findElement(By.xpath("//td[normalize-space()='$49.95']")).getText();
			String FluffyBunny_subtotal = FluffyBunny.substring(1);
			Assert.assertEquals(FluffyBunny_subtotal, "49.95");

			String ValentineBear = driver.findElement(By.xpath("//td[normalize-space()='$44.97']")).getText();
			String ValentineBear_subtotal = ValentineBear.substring(1);
			Assert.assertEquals(ValentineBear_subtotal, "44.97");

			// Verify the price for each product
			String StuffedFrog1 = driver.findElement(By.xpath("//td[normalize-space()='$10.99']")).getText();
			String StuffedFrog1_price = StuffedFrog1.substring(1);
			Assert.assertEquals(StuffedFrog1_price, "10.99");

			String FluffyBunny1 = driver.findElement(By.xpath("//td[normalize-space()='$9.99']")).getText();
			String FluffyBunny1_price = FluffyBunny1.substring(1);
			Assert.assertEquals(FluffyBunny1_price, "9.99");

			String ValentineBear1 = driver.findElement(By.xpath("//td[normalize-space()='$14.99']")).getText();
			String ValentineBear1_price = ValentineBear1.substring(1);
			Assert.assertEquals(ValentineBear1_price, "14.99");

			float newStuffedFrog_subtotalsum = Float.valueOf(StuffedFrog_subtotal);
			float newFluffyBunny_subtotalsum = Float.valueOf(FluffyBunny_subtotal);
			float newValentineBear_subtotalsum = Float.valueOf(ValentineBear_subtotal);

			float subtotalsum = newStuffedFrog_subtotalsum + newFluffyBunny_subtotalsum
					+ newValentineBear_subtotalsum;
			float expectedsubtotalsum=(float) 116.9;
			Assert.assertEquals(expectedsubtotalsum,subtotalsum);
		
		}
	
	@AfterMethod
	public void tearDown() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		driver.close();
	}
}
