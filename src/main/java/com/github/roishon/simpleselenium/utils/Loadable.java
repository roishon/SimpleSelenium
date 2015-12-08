package com.github.roishon.simpleselenium.utils;

/**
 * Interface contains methods to handle operations on LoadablePage
 * Created by Roi Shachor on 09.06.15.
 */
public interface Loadable {


    /**
     * Makes sure the page is loaded by assuring that the ExplicitElement is displayed and available
     */
    public void isLoaded();


    /**
     *  See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.WebDriver driver, Class)}
     *  Initiates the Attributes of a Page class, which are referring to GUI-Elements,
     *  makes sure the page is fully loaded and register it to the TestSigleton class
     * @param <T> Subclass of LoadablePage
     * @return an Object of a Subclass of LoadablePage
     */
    public <T extends LoadablePage> T initElements();

    /**
     * Initialize the explicitElement.
     * Retrive all attributes looking for one Attribute, which annotated with @ExplicitElement.
     * This Attribute must refer to a GUI-Element and be annotated with @FindBy, @FindAll, @FindBys or @FixedLocator
     * The attribute explicitElement will refer to this annotated attribute. If The attribute is of type List,
     * explicitElement will refer to  the last member of the list
     */
    public void setExplicitElement();

}
