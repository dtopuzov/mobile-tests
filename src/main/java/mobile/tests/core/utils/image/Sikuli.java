package mobile.tests.core.utils.image;

import org.sikuli.basics.Settings;
import org.sikuli.script.Finder;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Sikuli helper class.
 */
public class Sikuli {

    public static Rectangle findImage(BufferedImage screenImage, BufferedImage targetImage, float similarity) {
        Image searchedImage = new Image(targetImage);
        Pattern searchedImagePattern = new Pattern(searchedImage);
        Image mainImage = new Image(screenImage);
        searchedImagePattern.similar(similarity);

        Finder finder = new Finder(mainImage);
        finder.findAll(searchedImagePattern);
        Match searchedImageMatch = finder.next();
        Rectangle rectangle = searchedImageMatch.getRect();

        return rectangle;
    }

    public static Rectangle findText(String text, BufferedImage image) {
        Settings.InfoLogs = true;
        Settings.OcrTextSearch = true;
        Settings.OcrTextRead = true;

        Image mainImage = new Image(image);
        Finder finder = new Finder(mainImage);
        finder.findAllText(text);
        Match searchedImageMatch = finder.next();
        Rectangle rectangle = searchedImageMatch.getRect();

        return rectangle;
    }
}
