package com.github.roishon.simpleselenium.easyFactory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import java.lang.reflect.Field;

/**
 * As selenium See{@link org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory}
 * but creates an instance of EasySelementLocator instead of
 * {@link org.openqa.selenium.support.pagefactory.DefaultElementLocator}
 * Created by Roi Shachor on 09.07.15.
 */
public class EasySelementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;



    public EasySelementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    /**
     * Create an instance of EasySelementLocator for a field
     * @param field - Locator will be generated for this field
     * @return an instance of EasySelementLocator
     */
    public ElementLocator createLocator(Field field) {
        return new EasySelementLocator(searchContext, field);
    }
}
