package framework.mobile_ui_entries.basics;

import framework.utils.Actor;
import framework.utils.ReportWriter;
import org.openqa.selenium.*;

import java.util.List;

public abstract class AbstractBasicUiEntry {
    private By locator;

    public AbstractBasicUiEntry(By locator) {
        this.locator = locator;
    }

    protected WebElement getElement() {
        return Actor.findElement(this.locator);
    }

    protected WebElement getElements() {
        return Actor.findElement(this.locator);
    }

    public Point getLocation() {
        ReportWriter.logStdout("Getting element location: " + locator.toString());
        Point result = getElement().getLocation();
        ReportWriter.logStdout("Result: " + result.toString());
        return result;
    }

    public Dimension getSize() {
        ReportWriter.logStdout("Getting element size: " + locator.toString());
        Dimension result = getElement().getSize();
        ReportWriter.logStdout("Result: " + result.toString());
        return result;
    }

    public String getAttribute(String attributeName) {
        ReportWriter.logStdout(String.format("Getting attribute [%s] for element [%s]: ", attributeName, locator.toString()));
        String result = getElement().getAttribute(attributeName);
        ReportWriter.logStdout("Result: " + result);
        return result;
    }

    public String getText() {
        ReportWriter.logStdout(String.format("Getting text for element [%s]: ", locator.toString()));
        String result = getElement().getText();
        ReportWriter.logStdout("Result: " + result);
        return result;
    }

    public boolean isVisible(int timeout) {
        List<? extends WebElement> elementList = Actor.findVisibleElements(this.locator, timeout);
        return elementList.size() > 0;
    }

    public boolean isVisible() {
        return isVisible(Actor.explicitWait);
    }

    public boolean isClickable(int timeout) {
        try {
            Actor.findClickableElement(this.locator, timeout);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isClickable() {
        return isClickable(Actor.explicitWait);
    }

    public boolean isPresented(int timeout) {
        List<? extends WebElement> elementList = Actor.findPresentedElements(this.locator, timeout);
        return elementList.size() > 0;
    }

    public boolean isPresented() {
        return isPresented(Actor.explicitWait);
    }
}
