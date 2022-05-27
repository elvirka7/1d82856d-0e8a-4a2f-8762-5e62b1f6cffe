package com.hackerrank.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FormSubmissionTest {
    static WebDriver driver = null;
    static String pagUrl = null;
    static Map<String,String> before = null;
    static Map<String,String> after = null;

    @BeforeClass
    public static void setup() {
        driver = new HtmlUnitDriver(BrowserVersion.CHROME, false) {
            @Override
            protected WebClient newWebClient(BrowserVersion version) {
                WebClient webClient = super.newWebClient(version);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                return webClient;
            }
        };

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        before = new HashMap() {
            {
                put("Fizz", "fname");
                put("Buzz", "lname");
                put("fizz_buzz@hackerrank.com", "email");
                put("fizz_buzz@Hrw", "password");
                put("c_fizz_buzz@Hrw", "c_password");
            }
        };

        after = new HashMap(){
            {
                put("Your first name","fname");
                put("Your last name","lname");
                put("Your email","email");
                put("Your password","password");
                put("Confirm your password","c_password");
            }
        };

        pagUrl = "file://" + System.getProperty("user.dir") + "/website/home.html";
    }

    @Test
    public void testFillForm() {
        FormSubmission.fillForm(driver, pagUrl);

        List<WebElement> formElements = driver.findElements(By.xpath("//input"));

        assertEquals(5,formElements.size());
        for (WebElement element : formElements){
            assertEquals(before.get(element.getAttribute("value")), element.getAttribute("id"));
        }
    }

    @Test
    public void testSubmitForm() {
        FormSubmission.submitForm(driver);

        List<WebElement> formElements = driver.findElements(By.xpath("//input"));

        assertEquals(5,formElements.size());
        for (WebElement element : formElements) {
            assertEquals("", element.getAttribute("value"));
            assertEquals(after.get(element.getAttribute("placeholder")), element.getAttribute("id"));
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
