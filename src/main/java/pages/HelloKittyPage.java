package pages;

import framework.utils.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelloKittyPage {

    public HelloKittyPage() {
    }

    public String getCaption() {
        WebElement element = Actor.findVisibleElement(By.id("textView"));
        return element.getText();
    }

    public void clickButton() {
        WebElement element = Actor.findVisibleElement(By.id("imageButton"));
        element.click();
    }

    public void setText(String text) {
        WebElement element = Actor.findVisibleElement(By.id("editText"));
        element.sendKeys(text);
    }
}
