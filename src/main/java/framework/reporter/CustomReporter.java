package framework.reporter;

import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

/**
 * @author Andrey Zhelezny
 *         Date: 9/2/15
 */
public class CustomReporter implements IReporter {
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        List<IInvokedMethod> invokedMethods = suites.get(0).getAllInvokedMethods();

        Map<String, RunInfo> classRuns = getClassesRunInfo(invokedMethods);
        // generation of report for all classes
        for (Map.Entry<String, RunInfo> classResult : classRuns.entrySet())
            generateClassResultsReport(classResult.getKey(), classResult.getValue());

        // generation of main report
        generateMainReport(classRuns);

        //creation of CSS files
        ReporterUtils.writeStringsToFile(HtmlCreator.getClassCssFile(), ReportProperties.get().cssDirectory() + "/classes.css");
        ReporterUtils.writeStringsToFile(HtmlCreator.getTestCssFile(), ReportProperties.get().cssDirectory() + "/composite.css");
        ReporterUtils.writeStringsToFile(HtmlCreator.getMainCssFile(), ReportProperties.get().cssDirectory() + "/main.css");
    }

    private void generateMainReport(Map<String, RunInfo> classRuns) {
        RunInfo total = new RunInfo();
        for (Map.Entry<String, RunInfo> current : classRuns.entrySet())
            total.sumRunInfo(current.getValue());

        List<String> fileContent = new ArrayList<String>();
        fileContent.add("<html>");
        fileContent.add("<head>");
        fileContent.add("<link href=\"css/main.css\" type=\"text/css\" rel=\"stylesheet\">");
        fileContent.add("</head>");
        fileContent.add("<body>");
        fileContent.add("<table class=\"clear\">");
        fileContent.add(" <tr>");
        fileContent.add("  <th colspan=\"3\">Test Name</th><th>Result</th><th># Sub Tests</th><th>Duration</th>");
        fileContent.add(" </tr>");

        fileContent.add(" <tr>");
        fileContent.add("  <td class=\"suiteName\">Test classes</td><td class=\"pkgName\"></td>");
        fileContent.add("  <td class=\"className\"></td>");
        fileContent.add("  <td class=\"result\">");
        fileContent.add("   <div id=\"" + total.getStatus().toString().toLowerCase() + "\">" + total.getStatus().toString().toLowerCase() + "</div>");
        fileContent.add("  </td>");
        fileContent.add("  <td class=\"subTests\">" + total.getTotalTests() + "</td>");
        fileContent.add("  <td class=\"duration\">" + ReporterUtils.getHms(total.getDuration()) + "</td>");
        fileContent.add(" </tr>");

        List<String> classNames = new ArrayList<String>();
        for (String classRun : classRuns.keySet())
            classNames.add(classRun);

        for (String className : classNames) {
            RunInfo classInfo = classRuns.get(className);
            fileContent.add(" <tr>");
            fileContent.add("  <td class=\"suiteName\"></td><td class=\"pkgName\"></td><td class=\"className\">");
            fileContent.add("   <div>");
            fileContent.add("    <a href=\"" + className + "/" + className + ".frame.html\">" + className + "</a>");
            fileContent.add("   </div>");
            fileContent.add("  </td>");
            fileContent.add("  <td class=\"result\">");
            fileContent.add("   <div id=\"" + classInfo.getStatus().toString().toLowerCase() + "\">" + classInfo.getStatus().toString().toLowerCase() + "</div>");
            fileContent.add("  </td>");
            fileContent.add("  <td class=\"subTests\">" + classInfo.getTotalTests() + "</td>");
            fileContent.add("  <td class=\"duration\">" + ReporterUtils.getHms(classInfo.getDuration()) + "</td>");
            fileContent.add(" </tr>");
        }

        fileContent.add("</table");
        fileContent.add("</body>");
        fileContent.add("</html>");

        ReporterUtils.writeStringsToFile(fileContent, ReportProperties.get().reportDirectory() + "/main.html");
    }


    private Map<String, RunInfo> getClassesRunInfo(List<IInvokedMethod> invokedMethods) {
        Map<String, RunInfo> result = new LinkedHashMap<String, RunInfo>();
        for (IInvokedMethod method : invokedMethods) {
            String className = method.getTestMethod().getTestClass().getName();
            if (!result.containsKey(className))
                result.put(className, new RunInfo(method));
            else
                result.get(className).sumRunInfo(new RunInfo(method));
        }
        return sortByComparator(result);
    }

    private static Map<String, RunInfo> sortByComparator(Map<String, RunInfo> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, RunInfo>> list =
                new LinkedList<Map.Entry<String, RunInfo>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, RunInfo>>() {
            public int compare(Map.Entry<String, RunInfo> o1,
                               Map.Entry<String, RunInfo> o2) {
                long v1 = o1.getValue().getStart();
                long v2 = o2.getValue().getStart();
                if (v1 == v2)
                    return 0;
                return (v1 < v2) ? -1 : +1;
            }
        });

        // Convert sorted map back to a Map
        Map<String, RunInfo> sortedMap = new LinkedHashMap<String, RunInfo>();
        for (Map.Entry<String, RunInfo> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public void generateClassResultsReport(String className, RunInfo info) {
        String targetDir = ReportProperties.get().reportDirectory() + "/" + className;
        List<String> fileContent = new ArrayList<String>();

        fileContent.add("<html>");
        fileContent.add("<head>");
        fileContent.add("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        fileContent.add("<title>" + className + "</title>");
        fileContent.add("<link href=\"../css/classes.css\" type=\" text/css\" rel=\"stylesheet\">");
        fileContent.add("</head>");

        fileContent.add("<body>");
        fileContent.add(" <table class=\"clear\">");
        fileContent.add("  <tr>");
        fileContent.add("   <th colspan=\"2\">");
        fileContent.add("    <div class=\"className\">");
        fileContent.add("     <div id=\"" + info.getStatus().toString().toLowerCase() + "\">" + className + "</div>");
        fileContent.add("    </div>");
        fileContent.add("   </th>");
        fileContent.add("  </tr>");
        fileContent.add("  <tr>");
        fileContent.add("   <td class=\"topLine\" colspan=\"2\">");
        fileContent.add("    <div>");
        fileContent.add("     <a target=\"_top\" href=\"../main.html\">Main page</a>");
        fileContent.add("    </div>");
        fileContent.add("   </td>");
        fileContent.add("  </tr>");
        fileContent.add("  <tr>");
        fileContent.add("   <td class=\"topLine\" colspan=\"2\">");
        fileContent.add("    <div id=\"skipped\">Started at " + ReporterUtils.getFormattedTime(info.getStart()) + "</div>");
        fileContent.add("   </td>");
        fileContent.add("  </tr>");
        fileContent.add("  <tr>");
        fileContent.add("   <td colspan=\"2\">");
        fileContent.add("    <div id=\"skipped\">Finished at " + ReporterUtils.getFormattedTime(info.getStop()) + "</div>");
        fileContent.add("   </td>");
        fileContent.add("  </tr>");
        fileContent.add("  <tr>");
        fileContent.add("   <td class=\"topBottomLines\" colspan=\"2\">");
        fileContent.add("    <div id=\"skipped\" > Duration " + ReporterUtils.getHms(info.getDuration()) + "</div>");
        fileContent.add("   </td >");
        fileContent.add("  </tr >");

        String lastMethod = null;

        for (File test : getFilesInFolder(targetDir, false, true)) {

            lastMethod = test.getName();
            String testSimpleName = test.getName();
            testSimpleName = testSimpleName.substring(0, testSimpleName.indexOf("("));

            String testStatus = getStatusFromString(test).toString().toLowerCase();
            boolean isConfig = test.getName().contains(CustomListener.CONFIG_REMARK);

            fileContent.add("  <tr>");
            fileContent.add("   <td class=\"configOrTest\">" + (isConfig ? "C" : "T") + "</td>");//<td class=\"testStatus\">");
            fileContent.add("   <td class=\"testName\" ><a href = \"" + test.getName() + "\" target = \"logs\">");
            fileContent.add("    <div id=\"" + testStatus + "\">" + testSimpleName + "</div>");
            fileContent.add("    </a>");
            fileContent.add("   </td >");
            fileContent.add("  </tr >");
        }

        fileContent.add(" </table>");
        fileContent.add("</body>");
        fileContent.add("</html>");

        ReporterUtils.writeStringsToFile(fileContent, targetDir + "/" + className + ".html");

        List<String> frameContent = new ArrayList<String>();
        frameContent.add("<html><head><link href=\"main.css\"/></head><frameset cols=\"20%,80%\">");
        frameContent.add("<frame src=\"" + className + ".html\" name=\"menu\">");
        frameContent.add("<frame src=\"" + lastMethod + "\" name=\"logs\"></frameset></html>");

        ReporterUtils.writeStringsToFile(frameContent, targetDir + "/" + className + ".frame.html");
    }

    private Status getStatusFromString(File file) {
        if (file.getName().contains(CustomListener.STATUS_REMARK_FAILED)) return Status.FAILED;
        if (file.getName().contains(CustomListener.STATUS_REMARK_SKIPPED)) return Status.SKIPPED;
        if (file.getName().contains(CustomListener.STATUS_REMARK_PASSED)) return Status.PASSED;
        throw new ReportException("unable to find acceptable status in string: " + file);
    }

    private List<File> getFilesInFolder(String dir, final boolean dirsOnly, boolean orderByName) {
        File[] dirs = new File(dir).listFiles(new FilenameFilter() {
            public boolean accept(File current, String name) {
                return !dirsOnly || new File(current, name).isDirectory();
            }
        });
        if (!orderByName)
            Arrays.sort(dirs, new Comparator() {
                public int compare(Object o1, Object o2) {
                    File first = (File) o1;
                    File second = (File) o2;
                    if (first.lastModified() > second.lastModified())
                        return -1;
                    if (first.lastModified() < second.lastModified())
                        return +1;
                    return 0;
                }
            });
        else
            Arrays.sort(dirs, new Comparator() {
                public int compare(Object o1, Object o2) {
                    String first = ((File) o1).getName();
                    String second = ((File) o2).getName();
                    int n1 = Integer.parseInt(first.substring(first.indexOf("[") + 1, first.indexOf("]")));
                    int n2 = Integer.parseInt(second.substring(second.indexOf("[") + 1, second.indexOf("]")));
                    return n1 - n2;
                }
            });
        return Arrays.asList(dirs);
    }
}
