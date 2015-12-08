package com.github.roishon.simpleselenium.elements.guiElements;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import java.util.List;

/**
 * This class wraps the Interface WebElement.
 * WebElement is an interface, and by default Selenium use RemoteWebElement to implements this interface.
 * This class does not replace RemoteWebElement. Its only used as type for attributes, which is important to the
 * process of initiating page class by this project. see {{@link com.kiongroup.easyFactory}}
 * Created by Roi Shachor on 09.06.15.
 */
public class SelementImpl implements Selement {


    /**
     * The wrapped WebElement
     */
    private final WebElement element;

    /**
     * Creates a Selement for a given WebElement.
     *
     * @param element element to wrap up
     */
    public SelementImpl(final WebElement element) {
        this.element = element;
    }



    @Override
    public WebElement findElement(By by) {
        return element.findElement(by);
    }


    @Override
    public List<WebElement> findElements(By by) {
        try {
            return element.findElements(by);
        }

        catch (StaleElementReferenceException e) {

        }

        return null;
    }


    @Override
    public boolean isEnabled() {
        return element.isEnabled();
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return element.getScreenshotAs(outputType);
    }

    /****************************************************************************************
     /*   All methods below are deligated from the WebElement bat are deprecated.            *
     /*   Instead the equivalent method in class LoadablePage should be used                 *
     /*   so EasySelenium first controls, ist the relevant objects are available on the GUI  *
     /****************************************************************************************/


    /**
     * Method is deprecated, use instead click(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public void click() {
        element.click();
    }

    /**
     * Method is deprecated, use instead sendKeys(Selement, String) in LoadablePage
     */
    @Deprecated
    @Override
    public void sendKeys(CharSequence... keysToSend) {
        element.sendKeys(keysToSend);
    }

    /**
     * Method is deprecated, use instead getLocation(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public Point getLocation() {
        return element.getLocation();
    }


    /**
     * Method is deprecated, use instead submit(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public void submit() {
        element.submit();
    }

    /**
     * Method is deprecated, use instead getAttribute(Selement, String) in LoadablePage
     */
    @Deprecated
    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    /**
     * Method is deprecated, use instead getCssValue(Selement, String) in LoadablePage
     */
    @Deprecated
    @Override
    public String getCssValue(String propertyName) {
        return element.getCssValue(propertyName);
    }

    /**
     * Method is deprecated, use instead getSize(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public Dimension getSize() {
        return element.getSize();
    }



    /**
     * Method is deprecated, use instead getText(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public String getText() {
        return element.getText().trim();
    }



    /**
     * Method is deprecated, use instead getTagName(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public String getTagName() {
        return element.getTagName();
    }


    /**
     * Method is deprecated, use instead isSelected(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    /**
     * Method is deprecated, use instead clear() in TextInput
     */
    @Deprecated
    @Override
    public void clear() {
        element.clear();
    }


    /**
     * Method is deprecated, use instead getCoordinates(Selement) in LoadablePage
     */
    @Deprecated
    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) element).getCoordinates();
    }


}

