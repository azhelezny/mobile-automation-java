package pages;

import framework.mobile_ui_entries.impl.Button;
import framework.mobile_ui_entries.impl.InputField;
import org.openqa.selenium.By;

public class HelloKittyPage {

    public HelloKittyPage() {
    }

    public String getCaption() {
        InputField field = new InputField(By.id("textView"));
        return field.getText();
    }

    public void clickButton() {
        Button button = new Button(By.id("imageButton"));
        button.click();
    }

    public void setText(String text) {
        InputField field = new InputField(By.id("editText"));
        field.sendKeys(text);
    }
}
