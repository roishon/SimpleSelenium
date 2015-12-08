package com.github.roishon.simpleselenium.easyFactory;



import com.github.roishon.simpleselenium.utils.TypesExtractor;
import com.github.roishon.simpleselenium.utils.Validator;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * Replaces DefaultLocatingElementHandler
 * (See {@link org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler}).
 * Simply opens it up to descendants of the WebElement interface, and other
 * mix-ins of WebElement and Locatable, etc. Saves the wrapping type for calling the constructor of the wrapped classes.
 * Created by Roi Shachor on 09.07.15.
 */
public class EasyInvocationHandler<T extends SearchContext> implements InvocationHandler {

    /**The locator to help the proxy object find the proxied object in the DOM*/
    private final ElementLocator locator;

    /**The class to be proxied*/
    private final Class<T> implementingType;


    /**
     * Generates a handler to retrieve the WebElement from a locator for a given WebElement interface descendant.
     *
     * @param interfaceType Interface wrapping this class. It contains a reference the the implementation.
     * @param locator       Element locator that finds the element on a page.
     * @param <?>           type of the interface
     */
    public EasyInvocationHandler(Class<?> interfaceType, ElementLocator locator) {
        this.locator = locator;

        if (Validator.isFromWebElement(interfaceType) || Validator.isFromSubPage(interfaceType))
            this.implementingType = TypesExtractor.getImplementingClass(interfaceType);


        else
            throw new RuntimeException("interface not assignable to WebElement");

    }


    /**
     * Overrides the java.reflect.InvocationHandler invoke() method.
     * Invokes method on the proxy object, which invoke the methods on the target Selement objects
     * @param object - the proxy object
     * @param method - the method to be invoked.
     * @param objects - array of parameters for the method.
     * @return an Object, as the original method of the proxied object will return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {

        if(!checkElementNotStale(locator))
            return null;

        WebElement element = locator.findElement();



        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }
        Constructor<?> cons = implementingType.getConstructor(WebElement.class);
        Object thing = cons.newInstance(element);
        try {
            return method.invoke(implementingType.cast(thing), objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }





    /**
     * Method checks if locator is still relevant to the DOM.
     * Aim is to catch a StaleElementReferenceException so the test will not break in the following steps.
     * @param locator - locator to be tested
     * @return - true if locator can still locate elements in the DOM
     */
    public static boolean checkElementNotStale(ElementLocator locator) {


        try {
            locator.findElements();
        }
        catch (StaleElementReferenceException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
