package framework.mobile_ui_entries.basics;

import org.openqa.selenium.By;

public class ClickableUiEntry extends AbstractBasicUiEntry {
    public ClickableUiEntry(By locator) {
        super(locator);
    }

    public void click() {
        getElement().click();
    }
}
