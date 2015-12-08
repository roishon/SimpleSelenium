package com.github.roishon.simpleselenium.elements.guiElements;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;

/**
 * Abstracts an input field on the user interface, formed by the HTML-TAG <input type="text">
 * The need for this interface is because the java.reflect.proxy object can be crated only with list of interfaces,
 * in order to be able to invoke methods for the proxied objects.
 * Created by Roi Shachor on 09.06.15.
 */
@ImplementingClass(TextInputImpl.class)
public interface TextInput extends Selement {


    /**
     * Inserts text into the input field, after making clearing the field from previous text.
     * Subsequent the input field will lose the focus.
     * @param text - The input text
     */
    void set(String text);
}
