package com.github.roishon.simpleselenium.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate a class member in a page or dialog. This class member refers to a GUI-Element,
 * which must be visible and available after the page / dialog is loads, before the test proceeds.
 * This allows the test to ensure the page is loaded before proceeding with the test
 * Created by roi on 09.07.15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExplicitElement {
}
