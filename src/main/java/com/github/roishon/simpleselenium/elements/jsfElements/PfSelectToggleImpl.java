package com.github.roishon.simpleselenium.elements.jsfElements;

import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.DialogToggleImpl;
import org.openqa.selenium.WebElement;

/**
 * Represents a button, which opens an options dialog (made to look like drop down menu)
 * Created by Roi Shachor on 09.07.15.
 */
public class PfSelectToggleImpl extends DialogToggleImpl implements PfSelectToggle {


    public PfSelectToggleImpl(WebElement element) {
        super(element);
    }



    public <S extends PfSelect> S clickSelectToggle() {
        return (S)super.toggle();
    }


    public void selectOption(String option) {
        clickSelectToggle().selectOption(option);
    }


}
