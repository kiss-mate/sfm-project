package common.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getLevel() + "|" +
                record.getLoggerName() + "|" +
                record.getMessage() + "|" +
                (record.getThrown() == null ? "<NoException>" : record.getThrown()) + "|\n";
    }
}
