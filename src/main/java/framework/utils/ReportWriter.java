package framework.utils;

import framework.reporter.CustomListener;
import framework.reporter.ReportProperties;
import org.apache.commons.lang.StringEscapeUtils;
import org.testng.Reporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrey Zhelezny
 *         Date: 9/2/15
 */
public class ReportWriter {

    private static final int LENGTH_LIMIT = 1024; // 1K
    private static AtomicInteger linkNumerator = new AtomicInteger(0);
    private static final String LINK_FILE_HEADER = "<html><body><pre>\n";
    private static final String LINK_FILE_FOOTER = "\n</pre></body></html>";

    private static List<String> shortMessage(String message) {
        List<String> result = new LinkedList<String>();
        if (message.length() > LENGTH_LIMIT) {
            String shortMessage = message.substring(0, LENGTH_LIMIT);
            String linkName = String.format("link%s.html", linkNumerator);
            String linkText = String.format("<br><a href='../%s/%s'>...%s</a>", ReportProperties.LINKS_DIR_NAME, linkName, linkName);
            Path path = Paths.get(ReportProperties.get().reportDirectory(), ReportProperties.LINKS_DIR_NAME, linkName);
            FileUtils.writeStringToFile(LINK_FILE_HEADER + message.replace("\n", "<br>") + LINK_FILE_FOOTER, path.toString(), false);
            linkNumerator.set(linkNumerator.get() + 1);
            result.add(shortMessage);
            result.add(linkText);
        } else {
            result.add(message);
            result.add("");
        }
        return result;
    }

    private static void logWhole(String message, boolean toStdOut) {
        if (toStdOut)
            System.out.println(message);
        Reporter.log(message);
    }

    private static void log(String message, boolean toStdOut) {
        if (toStdOut)
            System.out.println(message);
        List<String> shortResult = shortMessage(message);
        Reporter.log(StringEscapeUtils.escapeXml(shortResult.get(0)) + shortResult.get(1));
    }

    public static void logNoEscape(String message) {
        List<String> shortResult = shortMessage(message);
        Reporter.log(shortResult.get(0) + shortResult.get(1));
    }

    public static void log(String message) {
        log("-- " + message, false);
    }

    public static void logStdout(String message) {
        log("-- " + message, true);
    }

    public static void logSql(String message) {
        if (!message.trim().endsWith(";"))
            message += ";";
        log(message, false);
    }

    public static void logDiff(String caption, String expectedResult, String actualResult) {
        logDiff(true, caption, expectedResult, actualResult);
    }

    public static void logDiff(boolean isBadDiff, String caption, String expectedResult, String actualResult) {

        String entryId = String.valueOf(new Random().nextDouble() * new Random().nextLong());
        String diffOperationsDivId = CustomListener.DIFF_OPERATIONS_DIV_ID_PREFIX + entryId;
        String diffContentTableId = CustomListener.DIFF_CONTENT_TABLE_ID_PREFIX + entryId;

        List<String> expected = shortMessage(StringEscapeUtils.escapeXml(expectedResult).replace("\n", "<br>"));
        List<String> actual = shortMessage(StringEscapeUtils.escapeXml(actualResult).replace("\n", "<br>"));

        String reporterMessage = "<div id='" + diffOperationsDivId + "'++>\n" +
                "       <strong>" + (isBadDiff ? "<font color = 'red'>" : "") + "--" + StringEscapeUtils.escapeXml(caption) + (isBadDiff ? "</font>" : "") + "</strong>\n" +
                "    </div>\n" +
                "    <table id='" + diffContentTableId + "' class='" + CustomListener.DIFF_CONTENT_TABLE_CLASS_NAME + "' style='display: none'>\n" +
                "      <tr><td colspan=2>--Expect: " + expected.get(0) + expected.get(1) + "<br></td><tr>\n" +
                "      <tr><td colspan=2>--Actual: " + actual.get(0) + actual.get(1) + "<br></td><tr>\n" +
                "      <tr>\n" +
                "        <td colspan = 2>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>";//</div>";

        logWhole(reporterMessage, false);
    }

    public static void logExpandable(String caption, String text) {
        String entryId = String.valueOf(new Random().nextDouble() * new Random().nextLong());
        String diffOperationsDivId = CustomListener.DIFF_OPERATIONS_DIV_ID_PREFIX + entryId;
        String diffContentTableId = CustomListener.DIFF_CONTENT_TABLE_ID_PREFIX + entryId;
        List<String> captionShort = shortMessage(StringEscapeUtils.escapeXml(caption));
        List<String> textShort = shortMessage(StringEscapeUtils.escapeXml(text));
        String reporterMessage =
                "    <pre id='" + diffOperationsDivId + "' class ='" + CustomListener.DIFF_CONTENT_TABLE_CLASS_NAME + "'>\n" +
                        captionShort.get(0) + captionShort.get(1) + "    </pre>" +
                        "<pre id='" + diffContentTableId + "' style='display: none;'>\n" +
                        textShort.get(0) + textShort.get(1) + "</pre>";
        logWhole(reporterMessage, false);

    }
}
