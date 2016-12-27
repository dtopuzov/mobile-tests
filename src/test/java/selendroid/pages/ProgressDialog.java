package selendroid.pages;

import io.appium.java_client.MobileElement;
import mobile.tests.core.base.context.TestContext;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Progress dialog page.
 */
public class ProgressDialog extends BasePage {

    @FindBy(className = "android.widget.ProgressBar")
    private MobileElement progressBar;

    public ProgressDialog(TestContext context) {
        super(context);
        Assert.assertTrue(this.progressBar.isDisplayed());
        this.log.info("Progress dialog loaded.");
    }

    public void waitUntilDisappear() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions
                        .invisibilityOfElementLocated(By.className("android.widget.ProgressBar")));
    }
}
