package google.pages;

import io.appium.java_client.AppiumDriver;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Home page of www.google.com.
 */
public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(id = "lst-ib")
    public WebElement searchBox;

    @FindBy(id = "tsbb")
    public WebElement searchButton;

    public HomePage verifyLinkExist(String linkText) {
        WebElement link = this.driver.findElement(By.xpath("//a[@href='" + linkText + "']"));
        Assert.assertTrue(link.isDisplayed(), linkText + " is not visible.");
        this.log.info(linkText + " is visible.");
        return this;
    }

    public HomePage searchFor(String searchTerm) {
        this.searchBox.sendKeys(searchTerm);
        this.log.info("Search for " + searchTerm);
        this.searchButton.click();
        this.log.info("Tap search button.");
        return this;
    }
}
