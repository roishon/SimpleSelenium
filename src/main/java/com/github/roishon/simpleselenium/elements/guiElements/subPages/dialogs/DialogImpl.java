package com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs;


import com.github.roishon.simpleselenium.easyFactory.EasyFactory;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.TextInput;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPageImpl;
import com.github.roishon.simpleselenium.utils.HTML;
import com.github.roishon.simpleselenium.utils.LoadablePage;
import com.github.roishon.simpleselenium.utils.TestSingleton;
import com.github.roishon.simpleselenium.utils.Waiting;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Class represents a Dialog. Contains method to open and close the dialog, as well as assuring
 * this processes are fully done.
 * Created by Roi Shachor on 09.06.15.
 */
public class DialogImpl extends SubPageImpl implements Dialog {

    /**
     * A list with TextInput Objects, each represents an input Field of the dialog
     */
    @FindBy(css = ".ui-inputfield.ui-inputtext.ui-widget.ui-state-default.ui-corner-all")
    protected List<TextInput> inputFields;


    /**
     * A list with Selement Objects, each represents an Button of the dialog
     */
    @FindBy(css = ".dialog-buttons-wrapper > div.dialog-buttons > button")
    protected List<Selement> buttons;

    /**
     * Initiate the root element, which refer the to the HTML-Tag, which frames the whole Dialog
     * @param root - The WebElement referring to the HTML-Tag wrapping the whole Dialog
     */
    public DialogImpl(WebElement root) {
        super(root);
    }


    /**
     * Inserts text to an input field upon the label of the field
     * @param field - the label, or part of it, of the wanted field
     * @param text - text to be insert
     * @return This object, after inserting the text
     */
    public Dialog inputText(String field, String text) {
        sendKeys(inputFields, HTML.ID, field, text);
        return this;
    }




    /**
     * See initElements() of class SubPage
     * @param <T> A Subclass of LoadablePage
     * @return An Object of this class after initiating the Attributes, which refer to GUI-Elements
     */
    @Override
    public <T extends LoadablePage> T initElements() {
        T t = (T) EasyFactory.initElements(root, this);

        setExplicitElement();

        return t;
    }


    /**
     * Initiates the Attributes of the Dialogs, which refer to GUI-Elements
     * and make sure the dialog is fully loaded and rendered.
     */
    public void validateDialogOpen() {

        initElements();

        isLoaded();

        TestSingleton.addDialog(this);

    }

    /**
     * Makes sure that the dialog is fully closed.
     */
    public void validateDialogClosed() {
        TestSingleton.removeDialog(this);
        Waiting.waitForElementToDisappear(root);
    }


    /**
     * Clicks on the button to close the Dialog
     */
    public void clickButtonSave() {
        clickClosingButton(0);
    }


    /**
     * Clicks on the button to close the Dialog, based on a given index value as parameter.
     * This index refers to the position of the relevant button in a list of button elements.
     * @param index - the position of the button in a List
     */
    protected void clickClosingButton(int index) {
        buttons.get(index).click();
        afterClosingDialog();
    }


    /**
     * Makes sure that the dialog is fully closed, and that the GUI is fully rendered
     */
    protected void afterClosingDialog() {
        validateDialogClosed();
        TestSingleton.refreshPage();
    }


}
