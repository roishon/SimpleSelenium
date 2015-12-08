package com.github.roishon.simpleselenium.easyFactory;


/**
 * Replaces DefaultLocatingElementListHandler
 * (See {@link org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler}).
 * Simply opens it up to descendants of the WebElement interface, and other
 * mix-ins of WebElement and Locatable, etc. Saves the wrapping type for calling the constructor of the wrapped classes.
 * Created by Roi Shachor on 09.07.15.
 */


import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPageImpl;
import com.github.roishon.simpleselenium.utils.TypesExtractor;
import com.github.roishon.simpleselenium.utils.Validator;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class EasyListInvocationHandler<T extends SearchContext> implements InvocationHandler {

    private ElementLocator locator;
    private Class<T> wrappingType;


    /**
     * Given an interface and a locator, apply a wrapper over a list of elements.
     *
     * @param interfaceType interface type we're trying to wrap around the element.
     * @param locator       locator on the page for the elements.
     */
    public EasyListInvocationHandler(Class<T> interfaceType, ElementLocator locator) {
        this.locator = locator;
        if (!Validator.isFromWebElement(interfaceType) && !Validator.isFromSubPage(interfaceType))
            throw new RuntimeException("interface not assignable to Element.");

        wrappingType = TypesExtractor.getImplementingClass(interfaceType);

    }



    /**
     * Executed on invoke of the requested proxy. Used to gather a list of wrapped WebElements.
     *
     * @param o       object to invoke on
     * @param method  method to invoke
     * @param objects parameters for method
     * @return return attrForDialog from method
     * @throws Throwable when frightened.
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        List<Object> wrappedList = new ArrayList<Object>();
        Constructor<?> constructor = wrappingType.getConstructor(WebElement.class);



        for (Object obj : locator.findElements()) {

            Object thing = constructor.newInstance(obj);

            if (Validator.isFromSubPage(wrappingType))
                ((SubPageImpl) thing).initElements();

            wrappedList.add(wrappingType.cast(thing));
        }


        try {
            return method.invoke(wrappedList, objects);
        } catch (InvocationTargetException e) {

            e.printStackTrace();
        }

        throw new RuntimeException();
    }




}

