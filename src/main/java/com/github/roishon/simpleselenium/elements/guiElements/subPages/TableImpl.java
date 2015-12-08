package com.github.roishon.simpleselenium.elements.guiElements.subPages;



import com.github.roishon.simpleselenium.annotations.ExplicitElement;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

/**
 * Class represents the GUI-Element created by a HTML-Tag <table>
 * Created by Roi Shachor on 09.06.15.
 */
public class TableImpl extends SubPageImpl implements Table {



    /**
     * List of the table rows, each row represented by a Selement object
     */
    @FindBy (css = "tbody > tr")
    protected List<Selement> rows;

    /**
     * The explicit Element of the Table object (see class LoadablePage)
     */
    @ExplicitElement
    @FindBy(tagName = "thead")
    Selement exSelement;


    public TableImpl(WebElement element) {
        super(element);
    }


    /**
     * Returns the number of the visible table rows, excluding the header
     * @return the number of the visible rows
     */
    @Override
    public int getBodyRowCount() {
        return rows.size();
    }



    /**
     * Returning the content of a table cell, according to the given index of the row and column of the cell
     * @param rowIdx - the index of the row of the wanted cell
     * @param colIdx - the index of the column of the wanted cell
     * @return Text content of the cell
     */
    @Override
    public String getTextAtCellByIndex(int rowIdx, int colIdx) {
        return getCellAtIndex(rowIdx, colIdx).getText().trim();
    }



    /**
     * Returning a Selement representing the a table cell, according to the given index of the row and column of the cell
     * @param rowIdx - the index of the row of the wanted cell
     * @param colIdx - the index of the column of the wanted cell
     * @return Selement representing the a table cell
     */
    @Override
    public WebElement getCellAtIndex(int rowIdx, int colIdx) {

        WebElement row = rows.get(rowIdx);

        if (row != null)
            return row.findElements(By.tagName("td")).get(colIdx);

        else
            return root.findElements(By.cssSelector("thead tr th")).get(colIdx);

    }


}

