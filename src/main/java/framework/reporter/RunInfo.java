package framework.reporter;

import org.testng.IInvokedMethod;
import org.testng.ITestResult;

/**
 * @author Andrey Zhelezny
 *         Date: 9/9/15
 */
public class RunInfo {
    private long start;
    private long stop;
    private long duration;
    private Status status;
    private int totalMethods;
    private int failedMethods;
    private int totalTests;
    private int failedTests;

    public RunInfo() {
        this.status = Status.SKIPPED;
        this.start = Long.MAX_VALUE;
        this.stop = Long.MIN_VALUE;
        this.duration = 0;
        this.totalMethods = 0;
        this.failedMethods = 0;
        this.totalTests = 0;
        this.failedTests = 0;
    }

    public RunInfo(IInvokedMethod method) {
        ITestResult result = method.getTestResult();
        this.status = Status.getStatus(result.getStatus());
        this.start = result.getStartMillis();
        this.stop = result.getEndMillis();
        this.duration = stop - start;

        this.totalMethods = 1;
        this.failedMethods = status.equals(Status.FAILED) ? 1 : 0;

        this.totalTests = method.isTestMethod() ? 1 : 0;
        this.failedTests = status.equals(Status.FAILED) ? totalTests : 0;
    }

    public void sumRunInfo(RunInfo runInfo) {
        this.duration += runInfo.calculateDuration();
        if (runInfo.start < this.start)
            this.start = runInfo.start;
        if (runInfo.stop > this.stop)
            this.stop = runInfo.stop;

        if (this.status.equals(Status.SKIPPED) && !runInfo.status.equals(Status.SKIPPED))
            this.status = runInfo.status;
        if (!this.status.equals(Status.FAILED) && runInfo.status.equals(Status.FAILED))
            this.status = Status.FAILED;

        this.failedTests += runInfo.failedTests;
        this.failedMethods += runInfo.failedMethods;
        this.totalMethods += runInfo.totalMethods;
        this.totalTests += runInfo.totalTests;
    }

    public long calculateDuration() {
        return this.stop - this.start;
    }

    public long getStart() {
        return start;
    }

    public long getStop() {
        return stop;
    }

    public long getDuration() {
        return duration;
    }

    public Status getStatus() {
        return status;
    }

    public int getTotalMethods() {
        return totalMethods;
    }

    public int getFailedMethods() {
        return failedMethods;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public int getFailedTests() {
        return failedTests;
    }
}
