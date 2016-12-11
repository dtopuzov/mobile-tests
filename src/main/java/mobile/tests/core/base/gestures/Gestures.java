package mobile.tests.core.base.gestures;

import io.appium.java_client.AppiumDriver;
import mobile.tests.core.base.uimap.UIMapItem;
import mobile.tests.core.utils.image.UIRectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Gestures helper.
 */
public class Gestures {

    private AppiumDriver driver;
    private Logger log = LogManager.getLogger(Gestures.class.getName());

    public Gestures(AppiumDriver driver) {

        this.driver = driver;
    }

    public void tap(UIMapItem item) {
        this.tap(item, 250);
    }

    public void tap(UIMapItem item, int duration) {
        UIRectangle rect = this.findByImage.findByImage(item.name);
        int x = ((Double) rect.getCenterX()).intValue();
        int y = ((Double) rect.getCenterY()).intValue();
        this.driver.tap(1, x, y, duration);
        this.log.info("Tap on " + rect.elementName);
    }
}
