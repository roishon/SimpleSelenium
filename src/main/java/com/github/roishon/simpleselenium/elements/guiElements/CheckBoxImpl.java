package com.github.roishon.simpleselenium.elements.guiElements;

import org.openqa.selenium.WebElement;

/**
 * Refer to the HTML <input type="checkbox">
 * Created by Roi Shachor on 09.06.15.
 */
public class CheckBoxImpl extends SelementImpl implements CheckBox {

    /**
     * Wraps a WebElement with checkbox functionality.
     *
     * @param element to wrap up
     */
    public CheckBoxImpl(WebElement element) {
        super(element);
    }


    /**
     * Toggle the state of the checkbox.
     */
    public void toggle() {
        getWrappedElement().click();
    }


    /**
     * Checks checkbox if unchecked.
     */
    public void check() {
        if (!isChecked()) {
            toggle();
        }
    }


    /**
     * Check if an element is selected, and return boolean.
     *
     * @return true if check is checked, return false in other case
     */
    public void uncheck() {
        if (isChecked()) {
            toggle();
        }
    }


    /**
     * Check if an element is selected, and return boolean.
     *
     * @return true if check is checked, return false in other case
     */
    public boolean isChecked() {
        return getWrappedElement().isSelected();
    }
}


