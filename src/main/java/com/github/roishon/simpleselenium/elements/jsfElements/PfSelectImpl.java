package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.annotations.ExplicitElement;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.DialogImpl;
import com.github.roishon.simpleselenium.utils.HTML;
import com.github.roishon.simpleselenium.utils.ListUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import java.util.List;

/**
 * Class represent the PrimeFace component witch behave as a Html <select>,
 * but the options are opened in a separate dialog.
 * Created by Roi Shachor on 09.07.15.
 */
public class PfSelectImpl extends DialogImpl implements PfSelect {


    /**List of Selements, each represent one option in the options Dialog */
    @FindAll({@FindBy(css = "li[data-item-value]"), @FindBy (css = "li[data-label]")})
    List<Selement> options;


    /**The explicitElement of this class (See {@link com.kiongroup.utils.LoadablePage})*/
    @ExplicitElement
    @FindAll({@FindBy(css = ".ui-selectonemenu-item.ui-selectonemenu-list-item.ui-corner-all.ui-state-highlight") ,
            @FindBy(css = ".ui-autocomplete-items.ui-autocomplete-list.ui-widget-content.ui-widget.ui-corner-all.ui-helper-reset")})
    Selement explicitSelement;


    /**
     * Initiate the attribute root, which refers to the HTML-Tag framing this whole options dialog on the GUI
     *
     * @param root to wrap up
     */
    public PfSelectImpl(WebElement root) {
        super(root);
    }


    /**
     * Selects an options from the options dialog upon the given value in the parameter,
     * closing the dialog and makes sure the dialog is closed and that the main page is rendered
     * and makes sure
     *
     * @param text - text upon which the choice option will be made
     */
    public void selectOption(String text) {

        HTML attr = null;

        Selement selement = options.get(0);

        if(selement.getAttribute(HTML.DATA_LABEL.name().toLowerCase().replace("_", "-")) != null)
            attr = HTML.DATA_LABEL;

        else
            attr = HTML.DATA_ITEM_VALUE;


        ListUtil.getElementFromList(options, attr, text).click();

        validateDialogClosed();
    }


}

