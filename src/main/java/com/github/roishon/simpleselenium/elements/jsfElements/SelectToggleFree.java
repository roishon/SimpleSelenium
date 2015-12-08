package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;

/**
 * Created by roi on 09.07.15.
 */
@ImplementingClass(SelectToggleFreeImpl.class)
public interface SelectToggleFree extends SubPage {

    public void select(String option);

}
