package Basic;

import java.io.IOException;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import Resource.Base;

public class Signup extends Base {

	@BeforeMethod
	public void initilize() throws IOException {// String WebURL = config.getProperty("rootUrl");
		setUp();

		// Navigate to the login page
		page.navigate(config.getProperty("rootUrl"));
	}

	@Test(priority=1)
	public void Sign_Up_with_Valid_Credentials() throws IOException, InterruptedException {
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up Sign Up For Free")).click();
		page.navigate("https://awspf.com/signup");
		page.getByPlaceholder("Email Address").fill(generateRandomEmail());
		page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)).fill("ValidPass123");
		page.getByPlaceholder("Confirm Password").fill("ValidPass123");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign Up")).click();
		//String expectedTitle = "Signup – Create your free PlayFactile Account";
		String actualTitle = page.title();
		System.out.println("title of page:-" + actualTitle);
		assertEquals("Signup – Create your free PlayFactile Account", actualTitle,"After sign up Page title is mismatching that's why it's getting fail");
		DeleteAccount();
	}
	
	
	@Test(priority=2)
	 public void Sign_Up_with_an_already_Registered_Email()  throws IOException, InterruptedException {
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up Sign Up For Free")).click();
		page.navigate("https://awspf.com/signup");
	      page.getByPlaceholder("Email Address").fill("sunita.deligence@gmail.com");
	      page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)).fill("123456");
	      page.getByPlaceholder("Confirm Password").fill("123456");
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign Up")).click();
	     // Locator ExpectedMessage = page.getByText("Email already exist!");
	   	      assertThat(page.getByText("Email already exist!")).isVisible();

	    	}
	
	
	@Test(priority=3)
	 public void SignUp_with_an_Invalid_EmailID()  throws IOException, InterruptedException {
	      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up").setExact(true)).click();
	      page.getByPlaceholder("Email Address").fill("valid@");
	      page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)).fill("ValidPass123");
	      page.getByPlaceholder("Confirm Password").fill("ValidPass@123");
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign Up")).click();
	      assertThat(page.getByText("Invalid email")).isVisible();
    	}
	@Test(priority=4)
	 public void SignUp_with_Password_ConfirmPassword_Missmatch()  throws IOException, InterruptedException {
	      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up").setExact(true)).click();
	      page.getByPlaceholder("Email Address").fill(generateRandomEmail());
	      page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)).fill("ValidPass123");
	      page.getByPlaceholder("Confirm Password").fill("ValidPass@12");
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign Up")).click();
	      assertThat(page.getByText("Password mismatch")).isVisible();
	}
	

	@Test(priority=5)
	 public void SignUp_with_a_Password_Less_Than_6_Characters()  throws IOException, InterruptedException {
	      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up").setExact(true)).click();
	      page.getByPlaceholder("Email Address").fill(generateRandomEmail());
	      page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)).fill("12345");
	      page.getByPlaceholder("Confirm Password").fill("12345");
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign Up")).click();
	      assertThat(page.getByText("Password must contain at least 6 characters")).isVisible();
	}

	@Test(priority=6)
	 public void SignUp_with_an_Email_Containing_Invalid_Characters_Space()  throws IOException, InterruptedException {
	      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up").setExact(true)).click();
	      page.getByPlaceholder("Email Address").fill("     ");
	      page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)).fill("123456");
	      page.getByPlaceholder("Confirm Password").fill("123456");
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign Up")).click();
	      assertThat(page.getByText("Email required")).isVisible();
	}
	


	@Test(priority=7)
	 public void SignUp_with_an_Password_Containing_Invalid_Characters_Space()  throws IOException, InterruptedException {
	      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Up").setExact(true)).click();
	      page.getByPlaceholder("Email Address").fill(generateRandomEmail());
	      page.getByPlaceholder("Password", new Page.GetByPlaceholderOptions().setExact(true)).fill("     ");
	      page.getByPlaceholder("Confirm Password").fill("     ");
	      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign Up")).click();
	      assertThat(page.getByText("Password must contain at least 6 characters")).isVisible();
	}

	
	
	// Generate random email
	private String generateRandomEmail() {
		String randomString = UUID.randomUUID().toString().substring(0, 8); // Generates a random 8-character string
		return randomString + "@example.com";
	}

	@AfterMethod
	public void close() {
		// playwright.close();
	}

	public void DeleteAccount() {
		page.locator("#freeAccount").click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("×")).click();
		page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Account")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete Account")).click();
		page.getByRole(AriaRole.TEXTBOX).fill("ValidPass123");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next!")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete!")).click();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes!")).click();
	}

}
