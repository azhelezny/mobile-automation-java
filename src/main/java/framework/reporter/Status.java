package framework.reporter;

import org.testng.ITestResult;

/**
 * @author Andrey Zhelezny
 *         Date: 9/8/15
 */
public enum Status {
    PASSED,
    SKIPPED,
    FAILED;

    public static Status getStatus(int iTestResultStatus) {
        switch (iTestResultStatus) {
            case ITestResult.FAILURE:
                return FAILED;
            case ITestResult.SKIP:
                return SKIPPED;
            default:
                return PASSED;
        }
    }
}
