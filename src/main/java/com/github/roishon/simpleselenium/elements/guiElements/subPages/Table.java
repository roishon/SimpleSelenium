package com.github.roishon.simpleselenium.elements.guiElements.subPages;

import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import org.openqa.selenium.WebElement;


/**
 * Class represents the GUI-Element created by a HTML-Tag <table>
 * Created by Roi Shachor on 09.06.15.
 */
@ImplementingClass(TableImpl.class)
public interface Table extends SubPage {


    /**
     * Returning a Selement representing the a table cell, according to the given index of the row and column of the cell
     * @param rowIdx - the index of the row of the wanted cell
     * @param colIdx - the index of the column of the wanted cell
     * @return Selement representing the a table cell
     */
    WebElement getCellAtIndex(int rowIdx, int colIdx);


    /**
     * Returning the content of a table cell, according to the given index of the row and column of the cell
     * @param rowIdx - the index of the row of the wanted cell
     * @param colIdx - the index of the column of the wanted cell
     * @return Text content of the cell
     */

    public String getTextAtCellByIndex(int rowIdx, int colIdx);





    /**
     * Returns the number of the visible table rows, excluding the header
     * @return the number of the visible rows
     */
    public int getBodyRowCount();
}


