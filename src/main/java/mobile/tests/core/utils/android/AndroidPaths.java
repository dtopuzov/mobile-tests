package mobile.tests.core.utils.android;

import java.io.File;

/**
 * Paths to android tools.
 */
public interface AndroidPaths {
    String ANDROID_HOME = System.getenv("ANDROID_HOME") + File.separator + "build-tools";
}
