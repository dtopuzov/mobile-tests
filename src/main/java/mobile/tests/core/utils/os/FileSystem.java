package mobile.tests.core.utils.os;

import java.io.File;

/**
 * Read and write files.
 * Used for both text logs and images.
 */
public class FileSystem {

    /**
     * This is a convenience method that calls find(File, String, boolean) with
     * the last parameter set to "false" (does not match directories).
     *
     * @see #find(File, String, boolean)
     */
    public static File find(File contextRoot, String fileName) {
        return find(contextRoot, fileName, false);
    }

    /**
     * Searches through the directory tree under the given context directory and
     * finds the first file that matches the file name. If the third parameter is
     * true, the method will also try to match directories, not just "regular"
     * files.
     *
     * @param contextRoot      The directory to start the search from.
     * @param fileName         The name of the file (or directory) to search for.
     * @param matchDirectories True if the method should try and match the name against directory
     *                         names, not just file names.
     * @return The java.io.File representing the <em>first</em> file or
     * directory with the given name, or null if it was not found.
     */
    public static File find(File contextRoot, String fileName, boolean matchDirectories) {
        if (contextRoot == null) {
            throw new NullPointerException("NullContextRoot");
        }

        if (fileName == null) {
            throw new NullPointerException("NullFileName");
        }

        if (!contextRoot.isDirectory()) {
            Object[] filler = {contextRoot.getAbsolutePath()};
            String message = "NotDirectory";
            throw new IllegalArgumentException(message);
        }

        File[] files = contextRoot.listFiles();

        //
        // for all children of the current directory...
        //
        for (int n = 0; n < files.length; ++n) {
            String nextName = files[n].getName();

            //
            // if we find a directory, there are two possibilities:
            //
            // 1. the names match, AND we are told to match directories.
            // in this case we're done
            //
            // 2. not told to match directories, so recurse
            //
            if (files[n].isDirectory()) {
                if (nextName.equals(fileName) && matchDirectories) {
                    return files[n];
                }

                File match = find(files[n], fileName);

                if (match != null) {
                    return match;
                }
            } else if (nextName.equals(fileName)) {
                // in the case of regular files, just check the names
                return files[n];
            }
        }

        return null;
    }
}
