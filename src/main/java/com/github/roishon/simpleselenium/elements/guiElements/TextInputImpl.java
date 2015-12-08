package com.github.roishon.simpleselenium.elements.guiElements;

import com.github.roishon.simpleselenium.utils.TestSingleton;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Class abstracts an input field on the user interface, formed by the HTML-TAG <input type="text">
 * Created by Roi Shachor on 09.06.15.
 */
public class TextInputImpl extends SelementImpl implements TextInput {


    /**
     * Wraps a WebElement with TextInput functionality.
     *
     * @param element to wrap up
     */
    public TextInputImpl(WebElement element) {
        super(element);
    }


    /**
     * Inserts text into the input field, after making clearing the field from previous text.
     * Subsequent the input field will lose the focus.
     * @param text - The input text
     */
    @Override
    public void set(String text) {
        WebElement element = getWrappedElement();
        element.clear();
        element.sendKeys(text + Keys.TAB);

        if(TestSingleton.getDriver().switchTo().activeElement().equals(element))
            element.sendKeys(Keys.TAB);
    }


}
