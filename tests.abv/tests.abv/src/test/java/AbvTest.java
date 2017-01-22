import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.swing.*;
import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import java.lang.Thread;

/**
 * Created by User on 30.8.2015 г..
 */
public class AbvTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver=new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }
//    @Test
    public void TestLogin_ValidCred_ShLogCor() {
        driver.get("http://abv.bg");
        String validUsername = "testermails";
        String validPassword = "testermails";
        String validUrl = "https://nm50.abv.bg/Mail.html";
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginBut"));
        usernameField.clear();
        usernameField.sendKeys(validUsername);
        passwordField.clear();
        passwordField.sendKeys(validPassword);
        loginButton.click();
         
        assertEquals(validUrl,
                driver.getCurrentUrl()
        );
        WebElement fullName = driver.findElement(By.className("userName"));
        assertEquals("Tester Testerson",
                fullName.getText()
        );
    }

     @Test
		 public void sendAndRecieveEmail() throws InterruptedException {
         this.TestLogin_ValidCred_ShLogCor();

         WebElement sendButton = driver.findElement(By.className("abv-button"));
         sendButton.click();

         String validEmail = "testermails@abv.bg";
         WebElement sendToField = driver.findElement(By.cssSelector("input.fl"));
         sendToField.click();
         sendToField.clear();
         sendToField.sendKeys(validEmail);

         String subject = "THE SUBJECT";
         WebElement subjectField = driver.findElement(By.cssSelector("input.gwt-TextBox"));
         subjectField.click();
         subjectField.clear();
         subjectField.sendKeys(subject);

         String textOfEmail = "CONGRATULATIONS!";
         driver.switchTo().frame(driver.findElement(By.className("gwt-RichTextArea"))); // enter in iframe tag
         WebElement textField = driver.findElement(By.tagName("body"));
         textField.sendKeys(textOfEmail);
         driver.switchTo().defaultContent();

         WebElement sendButton2 = driver.findElement(By.className("abv-button"));
         sendButton2.click();
         Thread.sleep(4000);
         driver.get("https://nm50.abv.bg/Mail.html#inbox:fid/10:pid/0");

         WebElement sender = driver.findElement(By.xpath(".//*[@id='inboxTable']//div[.='Tester Testerson']"));
         assertEquals("Tester Testerson",
                 sender.getText()
         );
         WebElement headText = driver.findElement(By.xpath(".//*[@id='inboxTable']//div[.='THE SUBJECT']"));
         assertEquals("THE SUBJECT",
                 headText.getText()
         );

         String mailBoxInnerText = "Кутия";
         WebElement increment = driver.findElement(By.xpath("//table[@class='abv-foldersTable']//tr[1]//em"));
         assertEquals("1",
                increment.getText()
         );

    }

    @After
    public void tearDown() {
    }
}





