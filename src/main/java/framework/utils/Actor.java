package framework.utils;

import framework.Settings;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

// additional abstraction layer
public class Actor {

    public static int implicitWait = 10;
    public static int pageLoadWait = 30;
    public static int explicitWait = 10;

    private static AppiumDriver<? extends WebElement> driver = null;

    private static AppiumDriver<? extends WebElement> getDriver() {
        if (driver == null)
            setupDriverDefault();
        return driver;
    }

    private static void setupDriverDefault() {
        setupDriver(implicitWait, pageLoadWait);
    }

    private static void setupDriver(int implicitWait, int pageLoadWait) {
        URL serverUrl;
        try {
            serverUrl = new URL(Settings.serverURL);
        } catch (MalformedURLException e) {
            throw new QaException("unable to instantiate driver", e);
        }
        switch (Settings.targetOs) {
            case Android:
            case undefined:
                driver = new AndroidDriver<>(serverUrl, Settings.getDesiredCapabilities());
                break;
            case IOS:
                driver = new IOSDriver<>(serverUrl, Settings.getDesiredCapabilities());
        }
        driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(pageLoadWait, TimeUnit.SECONDS);
    }

    private static WebDriverWait getWait(int timeoutInSeconds) {
        return new WebDriverWait(getDriver(), timeoutInSeconds);
    }

    private static WebDriverWait getWait() {
        return getWait(explicitWait);
    }

    public static WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    public static WebElement findVisibleElement(By by) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static List<? extends WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }

    public static List<? extends WebElement> findVisibleElements(By by, int waitTimeout) {
        return getWait(waitTimeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public static WebElement findClickableElement(By by, int waitTimeout) {
        return getWait(waitTimeout).until(ExpectedConditions.elementToBeClickable(by));
    }

    public static List<? extends WebElement> findPresentedElements(By by, int waitTimeout) {
        return getWait(waitTimeout).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
}
