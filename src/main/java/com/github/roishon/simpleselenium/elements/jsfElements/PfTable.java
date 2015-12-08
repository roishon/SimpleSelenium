package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.Table;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.TableImpl;

/**
 * Interface represents the PrimeFaces Component Table.
 * Methods of the class enable inserting text in the table header for sort purposes
 * Created by Roi Shachor on 10.07.15.
 */
public interface PfTable extends Table {

    /**
     * Returning a Selement, representing the header of a specific row by the header title
     * @param name - the title of the specific header
     * @return Selement representing the wanted header
     */
    public Selement getHeader(String name);


    /**
     * Setting text inside the Table header for sorting purposes.
     * After inserting the name, the table will be sorted automatically
     * @param headerName - the title of the specific table head* @param text - The text to be inserted
     * @return this Table-Object after being sorted
     */
    public TableImpl setHeaderText(String headerName, String text);
}
