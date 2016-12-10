package google.tests;

import google.pages.HomePage;
import io.restassured.RestAssured;
import mobile.tests.core.base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * Smoke tests for google home page.
 */
public class GoogleTests extends BaseTest {

    @Test(enabled = false)
    public void searchTest() {
        HomePage home = new HomePage(this.client.driver);
        home.searchFor("Appium").verifyLinkExist("http://appium.io/");
    }

    @Test
    public void temperature() {
        HomePage home = new HomePage(this.client.driver);
        home.searchFor("sofia temperature");

        // Get temperature displayed on web page
        WebElement temp = home.driver.findElement(By.id("wob_tm"));
        String tempFromWeb = temp.getText();

        // Get temperature via web service
        // API Doc: http://openweathermap.org/api
        // Requirement:
        // 1. Register at
        // 2. Get API_KEY from https://home.openweathermap.org/api_keys
        // 3. Set API_KEY environment variable (set value to your API_KEY)

        // Get value of API_KEY environment variable
        String apiKey = System.getenv("API_KEY");

        // Set base url for all RestAssured requests in this class
        RestAssured.baseURI = "http://api.openweathermap.org/data/2.5/weather";

        // Enable logging on request/response failure
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Get temperature in Sofia via rest api
        int tempFromApiInt =
                // Actual request is:
                // http://api.openweathermap.org/data/2.5/weather?q=Sofia,bg&appid=8017f999788ede35b72096696203fe4d&units=metric
                // queryParam are used to specify query params like appid and units
                given().
                        queryParam("q", "Sofia,bg").
                        queryParam("units", "metric").
                        queryParam("appid", apiKey).
                        when().
                        get().
                        then().
                        statusCode(200). // Assert Status code is 200
                        extract().
                        path("main.temp"); // Extract temperature from response body

        // Verify temperature
        double tempFromWebD = Double.valueOf(tempFromWeb);
        double tempFromApiD = Double.valueOf(String.valueOf(tempFromApiInt));
        this.log.info("Temperature via Web Site: " + tempFromWebD);
        this.log.info("Temperature via Rest API: " + tempFromApiD);
        Assert.assertTrue(Math.abs(tempFromWebD - tempFromApiD) <= 2.0,
                "Temperature is not correct!");

    }
}
