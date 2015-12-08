package com.github.roishon.simpleselenium.annotations;

import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.Dialog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotates class members of Type DialogToggle. The given parameter of this annotation
 * indicates which Dialog will opens when the toggle is clicked.
 * Created by Roi Shachor on 09.07.15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenDialog {

    /**The class of the dialog, which will be opened after clicking the toggle*/
    Class<?> value() default Dialog.class;

    /**The name of the class member holding a reference to the dialog, which will be opened after clicking the toggle*/
    String attrForDialog() default "";


}
