package framework.reporter;

/**
 * @author Andrey Zhelezny
 *         Date: 9/4/15
 */
public class ReportException extends RuntimeException {
    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable e) {
        super(message, e);
    }
}
