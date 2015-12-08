package com.github.roishon.simpleselenium.elements.guiElements.subPages;


import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.utils.ListUtil;
import com.github.roishon.simpleselenium.utils.Waiting;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Class represents the GUI-Element created from a  <Select> HTML-TAG
 * Created by Roi Shachor on 16.06.15.
 */
public class SelectImpl extends SubPageImpl implements Select {

    /**
     * List of Selements, each of which represents an <option> TAG
     */
    @FindBy(tagName = "option")
    List<Selement> options;


    /**
     * Represents the select toggle-button
     */
    @FindBy(tagName = "select")
    Selement toggle;


    /**
     * Wraps a WebElement with select functionality.
     *
     * @param element to wrap up
     */
    public SelectImpl(WebElement element) {
        super(element);
    }


    /**
     * Makes the selection upon the the text given by Parameter. 
     * @param option - Text to click (parameter value may be only part of the selected option)
     */
    @Override
    public void select(String option) {
        click(toggle);
        Selement selement = ListUtil.getElementFromListByText(options, option);
        click(selement);
        Waiting.waitForElementToBeSelected(selement);
    }


}

