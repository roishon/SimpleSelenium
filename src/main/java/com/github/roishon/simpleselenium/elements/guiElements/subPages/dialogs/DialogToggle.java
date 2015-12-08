package com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;
import com.github.roishon.simpleselenium.utils.LoadablePage;

/**
 * Interface represents a button, which opens a Dialog when it being clicked.
 * The Method toggle of this Dialog makes sure the Dialog is fully opened after the toggle ist clicked.
 * Created by Roi Shachor on 09.07.15.
 */
@ImplementingClass(DialogToggleImpl.class)
public interface DialogToggle <T extends Dialog> extends SubPage {


    /**
     * Clicks the toggle button, which is represented by this class,
     * and assures the dialog is fully opended.
     * returning the Dialog
     *
     * @param <T> A Subclass of Subdialog
     * @param <D> A Subclass of Dialog
     * @return A Dialog Object, of the Dialog opened by this button
     */
    public <T extends LoadablePage, D extends Dialog> D toggle();


    /**
     * Sets the Value of the Attributes, which hold the information about the class of the dialog
     * which will be opened when clicking the button.
     * @param dialogClazz - class of the dialog, that will be opened when clicking the toggle
     * @param dialogAttribute - attribute holding reference to the Dialog, that will be opened when toggle is clicked
     */
    public void setDialogClass(Class<?> dialogClazz, String dialogAttribute);


}
