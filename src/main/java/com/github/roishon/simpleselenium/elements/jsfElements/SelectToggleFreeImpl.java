package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPageImpl;
import com.github.roishon.simpleselenium.utils.HTML;
import com.github.roishon.simpleselenium.utils.ListUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by roi on 09.07.15.
 */
public class SelectToggleFreeImpl extends SubPageImpl implements SelectToggleFree {

    @FindBy(tagName = "option")
    List<Selement> options;

    public SelectToggleFreeImpl(WebElement element) {
        super(element);
    }

    public void select(String option) {
        ListUtil.getElementFromList(options, HTML.VALUE, option).click();
    }


}

