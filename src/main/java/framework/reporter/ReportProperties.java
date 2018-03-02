package framework.reporter;

/**
 * @author Andrey Zhelezny
 *         Date: 9/4/15
 */
public class ReportProperties {
    private String reportsDir;
    private String currentReportDir;
    public static final String CSS_DIR_NAME = "css";
    public static final String LINKS_DIR_NAME = "links";

    private static ReportProperties reportProperties = null;

    private ReportProperties() {
        this.reportsDir = System.getProperty("reports.dir");
        if (reportsDir == null || reportsDir.isEmpty())
            this.reportsDir = System.getProperty("user.dir") + "/target/reports";
        this.currentReportDir = ReporterUtils.getDirFormattedTime();
    }

    public static ReportProperties get() {
        if (reportProperties == null)
            reportProperties = new ReportProperties();
        return reportProperties;
    }

    public String reportDirectory() {
        return this.reportsDir + "/" + this.currentReportDir;
    }

    public String cssDirectory() {
        return this.reportDirectory() + "/" + CSS_DIR_NAME;
    }
}
