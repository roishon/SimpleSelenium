package com.github.roishon.simpleselenium.elements.guiElements.subPages;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.utils.Loadable;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * Interface represents a limited section of the the screen, which refers to a specific HTML-Tag and accordingly to a DOM-Element. 
 * 
 * Created by Roi Shachor on 09.06.15.
 */
@ImplementingClass(SubPageImpl.class)
public interface SubPage extends SearchContext, Loadable {


    /**
     * Returning a reference to the DOM-Element, which comprise the bounded area on the screen
     * @return WebElement
     */
    WebElement getRoot();


}
