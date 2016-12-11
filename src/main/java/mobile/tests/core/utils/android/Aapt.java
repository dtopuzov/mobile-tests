package mobile.tests.core.utils.android;

import mobile.tests.core.enums.OSType;
import mobile.tests.core.settings.Settings;
import mobile.tests.core.utils.os.FileSystem;
import mobile.tests.core.utils.os.Process;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Use aapt to get info from APK files.
 */
public class Aapt {

    private static String aaptPath;
    private static Logger log = LogManager.getLogger(Process.class.getName());

    public Aapt() {
        this.aaptPath = getAaptPath();
    }

    private static String getAaptPath() {
        String aaptPath;

        String aaptExecutableName = "aapt";
        if (Settings.os == OSType.Windows) {
            aaptExecutableName += ".exe";
        }

        File androidHome = new File(AndroidPaths.ANDROID_HOME);
        File aaptExecutablePath = FileSystem.find(androidHome, aaptExecutableName);

        if (aaptExecutablePath == null) {
            aaptPath = null;
        } else {
            aaptPath = aaptExecutablePath.getAbsolutePath();
        }

        return aaptPath;
    }

    private static String runAaptCommand(String property) {
        String value;
        Process osUtils = new Process();
        String command = aaptPath + " dump badging testapps" + File.separator + Settings.app.testApp
                + " | grep " + property;

        String result = osUtils.runProcess(command);

        if (result.contains(property)) {
            value = result.substring(result.indexOf("'") + 1);
            value = value.substring(0, value.indexOf("'"));
        } else {
            value = null;
        }

        return value;
    }

    public String getPackage() {
        return this.runAaptCommand("package:");
    }

    public String getLaunchableActivity() {
        return this.runAaptCommand("activity:");
    }

    public String getApplicationLabel() {
        return this.runAaptCommand("label-");
    }
}
