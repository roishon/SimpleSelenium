package com.github.roishon.simpleselenium.utils;


import com.github.roishon.simpleselenium.annotations.ExplicitElement;
import com.github.roishon.simpleselenium.easyFactory.EasyFactory;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.TextInput;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.LoadableComponent;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Class should be the Superclass of all page classes in an EasySelenium Test.
 * This class extending the selenium LoadableComponent, but the factory procedure is not handeled by the selenium
 * PageFactory directly but through the EasySelenium factory classes (See class EasyFactory). By doing so, it supports
 * the many classes of EasySelenium such as SubPage, Dialog, @ExplicitElement, @FixedLocator
 * See {{@link com.github.roishon.simpleselenium.easyFactory}, {@link com.github.roishon.simpleselenium.elements.guiElements}}
 *
 *
 * Methods of this class controls if Selements and other Object, which refers to GUI-Elements are available,
 * before executing operations with this objects. They also enable to initiate all the attributes of an existing
 * Page Object again.
 * Created by Roi Shachor on 09.06.15.
 */
public class LoadablePage extends LoadableComponent<LoadablePage> implements Loadable {

    /**
     * The driver to run the selenium Test
     */
    protected static WebDriver driver = TestSingleton.getDriver();


    /**
     * Each Object which represent and Page Class, SubPage or Dialog
     * should have one Attribute, which stands for a GUI-Element annotate with @ExplicitElement
     * When loading new pages or opening new Dialog, EasySelenium will first make sure this GUI-Element is
     * displayed and available
     */
    protected WebElement explicitElement;


    /**
     * The constructor of this class loading the page.
     * As a matter of fact, the method load() in this class was override to be empty
     * since only the first page of a selenium test should be loaded with calling a URL
     * the rest should be loaded as native as a user will execute it on the browser
     */
    public LoadablePage() {
        load();
    }


    /**
     *  See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.WebDriver driver, Class)}
     *  Initiates the Attributes of a Page class, which are referring to GUI-Elements,
     *  makes sure the page is fully loaded and register it to the TestSigleton class
     * @param <T> Subclass of LoadablePage
     * @return an Object of a Subclass of LoadablePage
     */
    public <T extends LoadablePage> T initElements() {
        T t = (T) EasyFactory.initElements(driver, this);

        setExplicitElement();
        isLoaded();
        TestSingleton.setCurrentPage(this);

        return t;
    }



    /**
     * This method overrides the method load() of org.openqa.selenium.support.ui.LoadableComponent
     * and leave it empty since only the first page of a selenium test should be loaded by calling a URL.
     * the rest should be loaded as native as a user will execute it on the browser
     */
    @Override
    public void load() {

    }


    /**
     * Makes sure the page is loaded by assuring that the ExplicitElement is displayed and available
     */
    @Override
    public void isLoaded() {
        assureElementActive(explicitElement);
    }


    /**
     * Makes sure a SubPage is available before returning it to the caller.
     * If that's not the case, all the Attributes of the Page Object will be initialized again
     *
     * @param subPage - The Sub to be returned
     * @param <T> - A Subclass of SubPage
     * @return subPage
     */
    public <T extends SubPage> T getSubPageElement(T subPage) {
        assureElementActive(subPage);
        return (T)subPage;
    }


    /**
     * Makes sure a SubPages within a list is available before returning it to the caller.
     * If that's not the case, all the Attributes of the Page Object will be initialized again
     * @param subPageList - The SubPage to be returned
     * @param index - the position of the SubPage in the list
     * @param <T> - A Subclass of SubPage
     * @return SubPage Object
     */
    public <T extends SubPage> T getSubPageElement(List<T> subPageList, int index) {
        while(subPageList.size() < index - 1)
            ;

        T subPage = subPageList.get(index);
        return getSubPageElement(subPage);
    }


    /**
     * Initialize the explicitElement.
     * Retrive all attributes looking for one Attribute, which annotated with @ExplicitElement.
     * This Attribute must refer to a GUI-Element and be annotated with @FindBy, @FindAll, @FindBys or @FixedLocator
     * The attribute explicitElement will refer to this annotated attribute. If The attribute is of type List,
     * explicitElement will refer to  the last member of the list
     */
    public void setExplicitElement() {

        for (Field field : getClass().getDeclaredFields())
            if (field.isAnnotationPresent(ExplicitElement.class)) {

                Object value = ReflectUtil.getValueOfField(field, this, this.getClass());

                if (Validator.isFromList(field)) {
                    List list = List.class.cast(value);
                    value = list.get(list.size() - 1);
                }

                explicitElement = TypesExtractor.getRootElement(value);
                break;
            }
    }


    /**
     * Initiates all the attributes of the current LoadablePage Object.
     * This Method should be used after executing operation in the browser, which invoke such changes in the DOM,
     * after which Selements or other GUI referring attributes are no longer available.
     * Normally the test will be ending with a StaleElementReferenceException
     */
    public void refreshPage() {
        initElements();
    }



    /**
     * Initiates all the attribute of the last opened dialog only
     */
    public void refreshDialog() {
        TestSingleton.refreshDialog();
    }


    /**
     * Assures an Objects which refers to a GUI-Element is displayed and available.
     * If that's not the case, all the attributes of the current object of a page class will be initiate again.
     * In case the object is an instance of SubPage, the method will check the availability of its root element.
     * In case the object is as instance of a list, the method will check the availability of the first list member
     * @param element
     */
    public void assureElementActive(Object element) {
        assureElementActive(TypesExtractor.getRootElement(element));
    }



    /**
     * Assures an Objects which refers to a GUI-Element is displayed and available.
     * If that's not the case, all the attributes of the current object of a page class will be initiate again
     * @param element
     */
    private void assureElementActive(SearchContext element) {

        WebElement webElement = (WebElement)element;

        try {
            webElement.isEnabled();
            webElement.isDisplayed();
        }
        catch(Exception e) {
            Waiting.waitForElement(webElement);
        }
    }



    public static void dragAndDrop(WebElement source, WebElement target) {
        (new Actions(driver)).dragAndDrop(source, target).build().perform();

    }



    /*****************************************************************
    /*   All methods below wrap WebElement (Selement) methods,       *
    /*   but assure first that the Selement is available             *
    /******************************************************************/



    public <T extends WebElement> void sendKeys(List<T> elements, HTML attr, String attrValue, String text) {
        if(text != null && !text.equals("")) {
            assureElementActive(elements);
            SearchContext context = ListUtil.getElementFromList(elements, attr, attrValue);
            sendKeys((WebElement) context, text);
        }
    }



    public <T extends WebElement>void sendKeys(T element, String text) {

        assureElementActive(element);

        if(Validator.isFromTextInput(element))
            ((TextInput)element).set(text);
        else
            element.sendKeys(text);
    }


    public void click(SubPage subPage) {
        assureElementActive(subPage);
        click(subPage.getRoot());
    }


    public <T extends SearchContext> void click (List<T> elements, HTML attribute, String value) {
        assureElementActive(elements);
        T element = ListUtil.getElementFromList(elements, attribute, value);
        click(element);
    }


    public <T extends SearchContext>void click(T t) {

        assureElementActive(t);
        TypesExtractor.getRootElement(t).click();
    }

    public Point getLocation(Selement selement) {
        assureElementActive(selement);
        return selement.getLocation();
    }

    public void submit(Selement selement) {
        assureElementActive(selement);
        selement.submit();
    }

    public String getAttribute(Selement selement, String name) {
        assureElementActive(selement);
        return selement.getAttribute(name);
    }

    public String getCssValue(Selement selement, String propertyName) {
        assureElementActive(selement);
        return selement.getCssValue(propertyName);
    }


    public Dimension getSize(Selement selement) {
        assureElementActive(selement);
        return selement.getSize();
    }


    public String getText(Selement selement) {
        assureElementActive(selement);
        return selement.getText().trim();
    }


    public String getTagName(Selement selement) {
        assureElementActive(selement);
        return selement.getTagName();
    }

    public boolean isSelected(Selement selement) {
        assureElementActive(selement);
        return selement.isSelected();
    }


    public Coordinates getCoordinates(Selement selement) {
        assureElementActive(selement);
        return ((Locatable) selement).getCoordinates();
    }
}
