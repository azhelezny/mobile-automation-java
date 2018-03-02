package framework.reporter;

import org.testng.*;

import java.util.ArrayList;
import java.util.List;

public class CustomListener implements ITestListener, IConfigurationListener2 {

    public static final String DIFF_OPERATIONS_DIV_ID_PREFIX = "diffOperations_";
    public static final String DIFF_CONTENT_TABLE_ID_PREFIX = "diffContent_";
    public static final String DIFF_CONTENT_TABLE_CLASS_NAME = "diffContentTableClass";

    public static final String CONFIG_REMARK = "-config";
    public static final String TEST_REMARK = "-test";

    public static final String STATUS_REMARK_PASSED = "(Passed)";
    public static final String STATUS_REMARK_FAILED = "(Failed)";
    public static final String STATUS_REMARK_SKIPPED = "(Skipped)";

    private int incrementalNumber = 0;

    public void onTestStart(ITestResult result) {
    }

    private void logEntry(ITestResult result, boolean isConfig) {
        boolean isParametrized = (result.getParameters().length != 0);
        List<String> fileContent = new ArrayList<String>();
        StringBuilder title = new StringBuilder();
        ITestNGMethod method = result.getMethod();
        title.append((method != null) ? method.getMethodName() : "unknown").append("(");
        if (isParametrized) {
            for (Object parameter : result.getParameters())
                title.append(parameter.toString()).append(", ");
            title.deleteCharAt(title.length() - 1).deleteCharAt(title.length() - 1);
        }
        title.append(")");
        fileContent.add("<html>");
        fileContent.add("<head>");
        fileContent.add("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        fileContent.add("<title>" + title.toString() + "</title>");
        fileContent.add("<link href=\"../css/composite.css\" type=\" text/css\" rel=\"stylesheet\">");
        fileContent.add("</head>");

        fileContent.add("<body>");
        fileContent.add("<h2 class=\"testName\" id=\"" + Status.getStatus(result.getStatus()).toString().toLowerCase() + "\">" + title.toString() + "</h2>");
        long begin = result.getStartMillis(), end = result.getEndMillis();
        fileContent.add("<h3> Started: " + ReporterUtils.getFormattedTime(begin) + "</h3>");
        fileContent.add("<h3> Finished: " + ReporterUtils.getFormattedTime(end) + "</h3>");
        fileContent.add("<h3> Time Spend: " + ReporterUtils.getHms(end - begin) + "</h3>");
        fileContent.add("<div class=\"invisible\"></div>");
        fileContent.add("<table class=\"testResults\">");
        for (String reportOutputSpling : Reporter.getOutput(result)) {
            fileContent.add("<tr>");
            fileContent.add("<td><pre>");
            fileContent.add(reportOutputSpling);
            fileContent.add("</pre></td>");
            fileContent.add("</tr>");
        }
        Throwable exception = result.getThrowable();
        if ((exception) != null) {
            fileContent.add("<tr>");
            fileContent.add("<td>");
            fileContent.add("<div class=\"testTdException\">");
            fileContent.add(exception.getMessage());
            fileContent.add(ReporterUtils.getExceptionAsString(exception));
            fileContent.add("</div>");
            fileContent.add("</td>");
            fileContent.add("</tr>");
        }
        // Expand/Collapse script for DIFF file reporting
        fileContent.add(" <script language=\"javascript\" type=\"text/javascript\">\n" +
                "        function expandCollapse(id){\n" +
                "          var element = document.getElementById('" + CustomListener.DIFF_CONTENT_TABLE_ID_PREFIX + "'+id);\n" +
                "          var currentElement = document.getElementById('btn_'+id);\n" +
                "           if(element.style.display == 'none'){" +
                "               element.style.display = 'block';" +
                "               currentElement.value = '--Collapse';" +
                "           }" +
                "           else{" +
                "               element.style.display = 'none';" +
                "               currentElement.value = '--Expand';" +
                "           }" +
                "         }\n" +
                "        function addButton(toElement, id) {\n" +
                "          var btn = document.createElement(\"input\");\n" +
                "          btn.setAttribute(\"type\", \"button\");\n" +
                "          btn.setAttribute(\"value\", \"--Expand\");\n" +
                "          btn.setAttribute(\"name\", id);\n" +
                "          btn.setAttribute(\"id\", 'btn_'+id);\n" +
                "          btn.onclick = function(){expandCollapse(id);}\n" +
                "          var foo = document.getElementById(toElement);\n" +
                "          foo.appendChild(btn);}\n" +
                "        window.onload = function(){\n" +
                "          var tables = document.getElementsByClassName(\"" + CustomListener.DIFF_CONTENT_TABLE_CLASS_NAME + "\");\n" +
                "          for (i = 0; i < tables.length; i++) {\n" +
                "            var id = tables[i].id;\n" +
                "            id=id.substring(id.indexOf('_')+1);\n" +
                "            addButton('" + CustomListener.DIFF_OPERATIONS_DIV_ID_PREFIX + "' + id, id);\n" +
                "          }\n" +
                "       }\n</script>\n");
        fileContent.add("</table>");
        fileContent.add("</body>");
        fileContent.add("</html>");

        String configRemark = ((isConfig) ? CONFIG_REMARK : TEST_REMARK);
        String statusRemark;
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                statusRemark = STATUS_REMARK_FAILED;
                break;
            case ITestResult.SKIP:
                statusRemark = STATUS_REMARK_SKIPPED;
                break;
            default:
                statusRemark = STATUS_REMARK_PASSED;
        }

        String fileName = ReportProperties.get().reportDirectory() + "/" + result.getTestClass().getName() + "/" + "[" + incrementalNumber + "] " + result.getName();
        String fullFileName = fileName + statusRemark + configRemark;
        incrementalNumber += 1;

        ReporterUtils.writeStringsToFile(fileContent, fullFileName + ".html");

    }

    public void onTestSuccess(ITestResult result) {
        logEntry(result, false);
    }

    public void onTestFailure(ITestResult result) {
        logEntry(result, false);
    }

    public void onTestSkipped(ITestResult result) {
        logEntry(result, false);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
    }

    public void onConfigurationSuccess(ITestResult itr) {
        logEntry(itr, true);
    }

    public void onConfigurationFailure(ITestResult itr) {
        logEntry(itr, true);
    }

    public void onConfigurationSkip(ITestResult itr) {
        logEntry(itr, true);
    }

    public void beforeConfiguration(ITestResult tr) {
    }
}