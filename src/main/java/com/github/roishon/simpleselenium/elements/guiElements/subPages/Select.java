package com.github.roishon.simpleselenium.elements.guiElements.subPages;

import com.github.roishon.simpleselenium.annotations.ImplementingClass;

/**
 * The Interface represents the GUI-Element created from a  <Select> HTML-TAG
 * 
 * Created by Roi Shachor on 16.06.15.
 */
@ImplementingClass(SelectImpl.class)
public interface Select extends SubPage {

    /**
     * Makes the selection upon the the text given by Parameter.
     * @param option - option to be selected upon the displayed text or part of it. 
     * E.g. the options of a select Element are ‚Blue‘, ‚Red‘ and ‚Yellow‘, by parameter with String value ‚Yellow‘ or ‚Yel‘ the third option will be selected.
     */
    public void select(String option);

}
