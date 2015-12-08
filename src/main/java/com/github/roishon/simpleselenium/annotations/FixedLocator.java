package com.github.roishon.simpleselenium.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to annotate Selements, which  appear in several pages and always have the same locator.
 * The actual locator will be placed in the annotation above the interface class of that Selement component.
 *
 * Created by Roi on 09.07.15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLocator {


}
