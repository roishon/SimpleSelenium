package com.github.roishon.simpleselenium.elements.guiElements;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;

/**
 * Refer to the HTML <input type="checkbox">
 * The need for this interface is because the java.reflect.proxy object can be crated only with list of interfaces,
 * in order to be able to invoke methods for the proxied objects.
 * Created by Roi Shachor on 09.06.15.
 */
@ImplementingClass(CheckBoxImpl.class)
public interface CheckBox extends Selement {


    /**
     * Toggle the state of the checkbox.
     */
    void toggle();

    /**
     * Checks checkbox if unchecked.
     */
    void check();

    /**
     * Un-checks checkbox if checked.
     */
    void uncheck();


    /**
     * Check if an element is selected, and return boolean.
     *
     * @return true if check is checked, return false in other case
     */
    boolean isChecked();


}

