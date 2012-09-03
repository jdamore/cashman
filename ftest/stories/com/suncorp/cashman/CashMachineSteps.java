package stories.com.suncorp.cashman;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

public class CashMachineSteps {

    static
    {
        System.setProperty("webdriver.chrome.driver", "/Users/jdamore/dev/projects/java/cashman/lib/selenium/chromedriver");
    }

    private WebDriver driver = new HtmlUnitDriver();


    @Given("I go to the Cash Machine Home page")
    public void goToHome() {
        driver.get("http://localhost:8080/cashman");
    }

    @When("I ask for amount $amount")
    public void enterAmount(int amount) {
        WebElement withdrawalAmountField = driver.findElement(By.id("cashmachine_withdrawalAmount"));
        withdrawalAmountField.clear();
        withdrawalAmountField.sendKeys((new Integer(amount)).toString());
    }

    @When("I click on Withdraw")
    public void withdraw() {
        WebElement withdrawalButton = driver.findElement(By.id("cashmachine_Withdraw"));
        withdrawalButton.click();
    }

    @Then("I should be on the Cash Machine Home page")
    public void isHomePage() {
        WebElement title = driver.findElement(By.className("title"));
        assertNotNull(title);
    }

    @Then("The machine should give me $quantity notes of $note")
    public void withdrawal(int quantity, int note) {
        WebElement withdrawalTable = driver.findElement(By.id("cashmachine_Withdrawal"));
        assertNotNull(withdrawalTable);
        List<WebElement> withdrawalRows = withdrawalTable.findElements(By.xpath("id('cashmachine_Withdrawal')/tbody/tr"));
        for(WebElement withdrawalRow : withdrawalRows) {
            List<WebElement> withdrawalDetails = withdrawalRow.findElements(By.xpath("td"));
            if(withdrawalDetails.size()==1) continue;
            assertEquals(2, withdrawalDetails.size());
            if(withdrawalDetails.get(0).getText().equals("$"+(new Integer(note)).toString()) &&
               withdrawalDetails.get(1).getText().equals((new Integer(quantity)).toString()))
                return;
        }
        fail("The machine has not provided " + quantity + " of $" + note + " notes as expected");
    }

    @Then("The quantity for $note should be $quantity")
    public void checkStockQuantity(int note, int quantity) {
        WebElement stockQuantityField = driver.findElement(By.id("cashmachine_stockMap_'$"+note+"'__quantity"));
        assertNotNull(stockQuantityField);
        assertEquals(quantity, Integer.parseInt(stockQuantityField.getAttribute("value")));
    }


}
