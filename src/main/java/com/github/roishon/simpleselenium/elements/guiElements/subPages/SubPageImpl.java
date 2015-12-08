package com.github.roishon.simpleselenium.elements.guiElements.subPages;


import com.github.roishon.simpleselenium.easyFactory.EasyFactory;
import com.github.roishon.simpleselenium.utils.LoadablePage;
import com.github.roishon.simpleselenium.utils.Waiting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;






/**
  * Class represents a limited section of the the screen, which refers to a specific HTML-Tag and accordingly to a DOM-Element.   
* Created by Roi Shachor on 09.06.15.
 */
public class SubPageImpl extends LoadablePage implements SubPage {


    /**
     * This WebElement refer to the DOM-Element, which contains the whole SubPage section
     */
    protected WebElement root;


    /**
     * Initiate the attribute root, which refers to the DOM-Element framing this whole SubPage on the GUI
     *
     * @param root  - WebElement which wraps the whole SubPage
     */
    public SubPageImpl(WebElement root) {
        this.root = root;
    }



    /**
     * Similar to initElements() of class EasyFactory, but use only the SubPage, e.g. the represented part on the screen,
     * as SearchContext, so only GUI-Elements within this part will be initiate
     * @param <T> A Subclass of LoadablePage
     * @return An Object of this class after initiating the Attributes, which refer to GUI-Elements
     */
    @Override
    public <T extends LoadablePage> T initElements() {
        T t = (T) EasyFactory.initElements(root, this);

        setExplicitElement();
        isLoaded();
        return t;
    }



    /**
     * Controls if the subpage is fully loaded, by checking if the root Eelemnt and the explicitElement
     * of the SubPage are available (for more details on explictElement see LoadablePage).
     * This control will be taken place only if an Attribute on the SubPage was annotated with @ExplicitElement
     */
    @Override
    public void isLoaded() {

        if(explicitElement != null)
            try {
                root.isDisplayed();
                explicitElement.isDisplayed();
            }
            catch (Exception e) {
                Waiting.waitForElement(driver, root);
                Waiting.waitForElement(root, explicitElement);
            }
    }



    /**
     * Returning the root Element, which refer to the HTML-Tag which frames this whole part on the screen
     * @return WebElement
     */
    public WebElement getRoot() {return root;}



    @Override
    public List<WebElement> findElements(By by) {
        return root.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return root.findElement(by);
    }






}

