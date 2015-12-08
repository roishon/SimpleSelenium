package com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;

/**
 * Interface represents a Dialog. Contains method to open and close the dialog, as well as assuring
 * this processes are fully done.
 * Created by Rio Shachor on 09.06.15.
 */
@ImplementingClass(DialogImpl.class)
public interface Dialog extends SubPage {

    /**
     * Inserts text to an input field upon the label of the field
     * @param field - the label, or part of it, of the wanted field
     * @param text - text to be insert
     * @return This object, after inserting the text
     */
    public Dialog inputText(String field, String text);


    /**
     * Initiates the Attributes of the Dialogs, which refer to GUI-Elements
     * and make sure the dialog is fully loaded and rendered.
     */
    public void validateDialogOpen();


    /**
     * Clicks on the button to close the Dialog
     */
    public void clickButtonSave();



}
