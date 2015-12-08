package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.TextInput;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.TableImpl;
import com.github.roishon.simpleselenium.utils.HTML;
import com.github.roishon.simpleselenium.utils.ListUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Class represents the PrimeFaces Component Table.
 * Methods of the class enable inserting text in the table header for sort purposes
 * Created by Roi Shachor on 10.07.15.
 */
public class PfTableImpl extends TableImpl implements PfTable{

    /**
     * Wraps a WebElement with PrimeFaces Table functionality.
     *
     * @param element to wrap up
     */
    public PfTableImpl(WebElement element) {
        super(element);
    }


    /**
     * List of table header input fields. Each field is represented by an TextInput object.
     */
    @FindAll({@FindBy(css = "thead > tr > th > input"),
            @FindBy(css = "thead > tr > th > span")})
    protected List<TextInput> header;


    /**
     * Returning a Selement, representing the header of a specific row by the header title
     * @param name - the title of the specific header
     * @return Selement representing the wanted header
     */
    @Override
    public Selement getHeader(String name) {
        return ListUtil.getElementFromListById(header, name);
    }


    /**
     * Setting text inside the Table header for sorting purposes.
     * After inserting the name, the table will be sorted automatically
     * @param headerName - the title of the specific table head* @param text - The text to be inserted
     * @return this Table-Object after being sorted
     */
    @Override
    public TableImpl setHeaderText(String headerName, String text) {

        sendKeys(header, HTML.ID, headerName, text);

        refreshPage();

        return this;
    }
}
