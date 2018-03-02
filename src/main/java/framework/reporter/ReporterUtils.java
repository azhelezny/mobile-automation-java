package framework.reporter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Andrey Zhelezny
 *         Date: 9/4/15
 */
public class ReporterUtils {
    private static final String format = "yyyy.MM.dd 'at' HH:mm:ss S";
    private static final String shortFormat = "HH:mm:ss S";
    private static final String dirFormat = "yyyy.MM.dd 'at' HH-mm-ss";

    public static String getExceptionAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }

    public static String getFormattedTime(Long date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(date));
    }

    public static String getShortFormattedTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(shortFormat);
        return dateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));
    }

    public static String getShortFormattedTime(Long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(shortFormat);
        return dateFormat.format(new Date(date));
    }

    public static String getDirFormattedTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dirFormat);
        return dateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));
    }

    public static Long getUnixDate() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getHms(Long time) {
        Long milliseconds = time;
        // get hours
        Long hours = milliseconds / 3600000L;
        milliseconds = milliseconds % 3600000L;
        // get minutes
        Long minutes = milliseconds / 60000L;
        milliseconds = milliseconds % 60000L;
        // get seconds
        Long seconds = milliseconds / 1000L;
        milliseconds = milliseconds % 1000L;
        return String.format("%s:%s:%s and ~ %sms", hours, minutes, seconds, milliseconds);
    }

    public static void writeStringsToFile(List<String> strings, String filePath) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(new BufferedWriter(new FileWriter(file, file.exists())));
            for (String fileLine : strings)
                fw.println(fileLine);
        } catch (IOException e) {
            throw new ReportException("Unable to work with file " + filePath, e);
        } finally {
            assert fw != null;
            fw.close();
        }
    }

}
