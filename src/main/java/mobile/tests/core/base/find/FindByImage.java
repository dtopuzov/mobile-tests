package mobile.tests.core.base.find;

import io.appium.java_client.AppiumDriver;
import mobile.tests.core.base.app.App;
import mobile.tests.core.base.uimap.UIMapItem;
import mobile.tests.core.utils.image.ImageUtils;
import mobile.tests.core.utils.image.Sikuli;
import mobile.tests.core.utils.image.UIRectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Find elements by image.
 */
public class FindByImage {

    private AppiumDriver driver;
    private Sikuli sikuli;
    private App app;

    public FindByImage(AppiumDriver driver) {
        this.driver = driver;
        this.sikuli = new Sikuli();
        this.app = new App(this.driver);
    }

    public UIRectangle findByImage(UIMapItem item) {
        BufferedImage screen = this.app.getScreen();
        BufferedImage targetImage = item.image;
        Rectangle rowRect = this.sikuli.findImage(screen, targetImage, 0.80f);
        UIRectangle rect = new UIRectangle(item.name, rowRect);
        return rect;
    }

    public UIRectangle findByText(String text) {
        BufferedImage screen = this.app.getScreen();
        Rectangle rowRect = this.sikuli.findText(text, screen);
        UIRectangle rect = new UIRectangle(text, rowRect);
        return rect;
    }
}
