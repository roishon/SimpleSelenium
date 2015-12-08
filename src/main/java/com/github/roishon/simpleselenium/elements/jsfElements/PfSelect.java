package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.Dialog;

/**
 * Interface represent the PrimeFace component witch behave as a Html <select>,
 * but the options are opened in a separate dialog.
 * Created by Roi Shachor on 09.07.15.
 */
@ImplementingClass(PfSelectImpl.class)
public interface PfSelect extends Dialog {

    public void selectOption(String text);

}
