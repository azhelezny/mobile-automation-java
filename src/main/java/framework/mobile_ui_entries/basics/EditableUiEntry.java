package framework.mobile_ui_entries.basics;

import org.openqa.selenium.By;

public class EditableUiEntry extends AbstractBasicUiEntry {
    public EditableUiEntry(By locator) {
        super(locator);
    }

    public void sendKeys(String text){
        getElement().sendKeys(text);
    }
}
