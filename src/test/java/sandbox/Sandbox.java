package sandbox;

import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayU8;
import framework.Settings;
import framework.utils.FileUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HelloKittyPage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Sandbox {

    public static void main(String[] args) throws IOException {
        // test boofCV
        System.out.println(Settings.projectDir);
        String inputFile = "D:\\threshoulds.jpg";
        String outputFile = "D:\\weirdLined.jpg";
        String threshould1 = "D:\\weirdThresholdStats.jpg";
        String threshould2 = "D:\\weirdThresholdOtsu.jpg";
        String threshould3 = "D:\\weirdThresholdEntropy.jpg";

        BufferedImage input = FileUtils.readImageFromFile(inputFile);
        GrayU8 gray = ConvertBufferedImage.convertFrom(input, (GrayU8) null);
        GrayU8 grayOutput = new GrayU8(gray.width, gray.height);
        for (int x = 0, y = 0; x < gray.getWidth() && y < gray.getHeight(); x = y = y + 1) {
            gray.set(x, y, 255);
        }
        BufferedImage output = ConvertBufferedImage.convertTo(gray, input);
        FileUtils.writeImageToFile(output, outputFile);


        gray = ConvertBufferedImage.convertFrom(input, (GrayU8) null);
        GThresholdImageOps.threshold(gray, grayOutput, ImageStatistics.mean(gray), true);
        output = ConvertBufferedImage.convertTo(grayOutput, output);
        FileUtils.writeImageToFile(output, threshould1);


        gray = ConvertBufferedImage.convertFrom(input, (GrayU8) null);
        GThresholdImageOps.threshold(gray, grayOutput, GThresholdImageOps.computeOtsu(gray, 0, 256), true);
        output = ConvertBufferedImage.convertTo(grayOutput, output);
        FileUtils.writeImageToFile(output, threshould2);
    }

    private WebDriverWait wait;

    @BeforeClass
    public void setup() throws MalformedURLException {
        System.out.println(Settings.targetOs);
        AndroidDriver<MobileElement> driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), new DesiredCapabilities());
        wait = new WebDriverWait(driver, 35);
    }

    @Test
    public void test() {
        HelloKittyPage helloKittyPage = new HelloKittyPage(wait);
        Assert.assertEquals("Large Text", helloKittyPage.getCaption());
        helloKittyPage.clickButton();
        Assert.assertEquals("Hello, Kitty!", helloKittyPage.getCaption());
        helloKittyPage.setText("Lab");
        helloKittyPage.clickButton();
        Assert.assertEquals("Hello, Lab", helloKittyPage.getCaption());
    }
}
