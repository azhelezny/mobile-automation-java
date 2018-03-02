package framework;

import framework.enums.MobileOS;
import framework.utils.PathUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Settings {
    public static final String projectDir = PathUtils.getOsPath(System.getProperty("user.dir"));

    public static final String mainDir = PathUtils.getOsPath(projectDir, "main");
    public static final String mainResourcesDir = PathUtils.getOsPath(mainDir, "resources");

    public static final String testsDir = PathUtils.getOsPath(projectDir, "test");
    public static final String testsResourcesDir = PathUtils.getOsPath(testsDir, "resources");

    public static final MobileOS targetOs = MobileOS.get(System.getProperty("phoneOS"));

    public static final String serverURL = "http://127.0.0.1:4723/wd/hub";

    private static DesiredCapabilities desiredCapabilities = null;

    public static DesiredCapabilities getDesiredCapabilities() {
        if (desiredCapabilities == null)
            desiredCapabilities = new DesiredCapabilities();
        return desiredCapabilities;
    }
}
