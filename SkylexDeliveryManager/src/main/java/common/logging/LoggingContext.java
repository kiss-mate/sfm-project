package common.logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class LoggingContext {
    public static void getProperties(String file) {
        try {
            LogManager.getLogManager().readConfiguration(
                    new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
