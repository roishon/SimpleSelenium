package com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs;



import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPageImpl;
import com.github.roishon.simpleselenium.utils.LoadablePage;
import com.github.roishon.simpleselenium.utils.ReflectUtil;
import com.github.roishon.simpleselenium.utils.TestSingleton;
import org.openqa.selenium.WebElement;

/**
 * Class represents a button, which opens a Dialog when it being clicked.
 * The Method toggle of this Dialog makes sure the Dialog is fully opened after the toggle ist clicked.
 * Created by Roi Shachor on 09.07.15.
 */
public class DialogToggleImpl <T extends Dialog> extends SubPageImpl implements DialogToggle<T> {

    /**
     * The attribute in the Page Class, which refers to the Dialog to be opened when clicking this toggle
     */
    private String dialogAttribute;

    /**
     * The class of the Dialog to be opened when clicking this toggle
     */
    private Class<?> dialogClass;


    /**The index of a Dialog in a list*/
    private int dialogIndex = -1;


    public DialogToggleImpl(WebElement element) {
        super(element);
    }


    /**
     * Clicks the toggle button, which is represented by this class,
     * and assures the dialog is fully opended.
     * returning the Dialog
     *
     * @param <T> A Subclass of Subdialog
     * @param <D> A Subclass of Dialog
     * @return A Dialog Object, of the Dialog opened by this button
     */
    @Override
    public <T extends LoadablePage, D extends Dialog> D toggle() {

        Dialog dialog;

        click(getRoot());

        T page = (T) TestSingleton.getCurrentPage();

        if(dialogClass != Dialog.class)
            dialog = (Dialog) ReflectUtil.getValueOfFieldRecursiveByFieldType(dialogClass, page, dialogIndex);

        else
            dialog = (Dialog)ReflectUtil.getValueOfFieldRecursive(dialogAttribute, page, dialogIndex);

        dialog.validateDialogOpen();

        return (D)dialog;
    }


    /**
     * Sets the Value of the Attributes, which hold the information about the class of the dialog
     * which will be opened when clicking the button.
     * @param dialogClazz - class of the dialog, that will be opened when clicking the toggle
     * @param dialogAttribute - attribute holding reference to the Dialog, that will be opened when toggle is clicked
     */
    public void setDialogClass(Class<?> dialogClazz, String dialogAttribute) {
        this.dialogAttribute = dialogAttribute;
        this.dialogClass = dialogClazz;
    }





}
