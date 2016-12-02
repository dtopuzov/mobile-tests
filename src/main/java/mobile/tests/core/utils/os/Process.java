package mobile.tests.core.utils.os;

import mobile.tests.core.enums.OSType;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Manage host OS processes.
 */
public class Process {

    private static final String[] CMD = {"cmd.exe", "/C"};
    private static final String[] BASH = {"/bin/bash", "-l", "-c"};
    private static Settings settings;
    private static Logger log = LogManager.getLogger(Process.class.getName());

    public Process(Settings settings) {
        this.settings = settings;
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * Run command.
     *
     * @param waitFor If true wait for command to finish, else just run and exit
     * @param timeOut Timeout in seconds
     * @param command Command
     * @return If waitFor return output of the command, else null
     */
    public static String runProcess(boolean waitFor, int timeOut, String... command) {
        String[] allCommand;

        String finalCommand = "";
        for (String s : command) {
            finalCommand = finalCommand + s;
        }

        try {
            if (settings.os == OSType.Windows) {
                allCommand = concat(CMD, command);
            } else {
                allCommand = concat(BASH, command);
            }
            ProcessBuilder pb = new ProcessBuilder(allCommand);
            java.lang.Process p = pb.start();

            if (waitFor) {
                StringBuilder output = new StringBuilder();

                // Note: No idea why reader should be before p.waitFor(),
                //       but when it is after p.waitFor() execution of
                //       some adb command freeze on Windows
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }

                p.waitFor(timeOut, TimeUnit.SECONDS);

                log.debug("Execute command: " + finalCommand);
                log.trace("Result: " + output.toString());

                return output.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Run command.
     *
     * @param command Command
     * @return A String containing the contents of the output
     */
    public static String runProcess(String... command) {
        return runProcess(true, 10 * 60, command);
    }

    /**
     * Run command.
     *
     * @param timeOut Timeout in seconds
     * @param command Command
     * @return A String containing the contents of the output
     */
    public static String runProcess(int timeOut, String... command) {
        return runProcess(true, timeOut, command);
    }
}
