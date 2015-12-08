package com.github.roishon.simpleselenium.annotations;

import com.github.roishon.simpleselenium.elements.guiElements.SelementImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates class members from types, which are interfaces.
 * Used to indicate which class will implement an interface.
 * Created by Roi Shachor on 09.07.15.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementingClass {

    /**The implementing class of the interface*/
    Class<?> value() default SelementImpl.class;
}
