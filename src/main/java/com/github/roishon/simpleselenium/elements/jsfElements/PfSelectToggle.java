package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.DialogToggle;

/**
 * Created by Roi Shachor on 09.07.15.
 */
@ImplementingClass(PfSelectToggleImpl.class)
public interface PfSelectToggle extends DialogToggle {

    public <S extends PfSelect> S clickSelectToggle();

    public void selectOption(String option);

}