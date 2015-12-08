package com.github.roishon.simpleselenium.utils;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import java.util.concurrent.TimeUnit;

/**
 * This class generate waiting conditions, similar to {@link org.openqa.selenium.support.ui.ExpectedConditions}
 * One of the most important difference, is that all conditions of this class catch more exceptions, than
 * the set of ExpectedConditions of selenium would do.
 * Created by Roi Shachor on 09.07.15.
 */
public class Waiting {

    /**The instance of WebDriver to run the selenium test with*/
    private static WebDriver driver = TestSingleton.getDriver();

    /**Root of SubPages, which is a Selement object referring to the HTML-Tag which wraps the whole SubPage*/
    private static SearchContext root;



    /**
     * Waits for element to be accessible and visible on the screen.
     * @param inRoot - The search context for the Element - can be the whole page or part of it (SubPage or dialog)
     * @param selement - The element that should be visible and accessible
     * @return true if the element is visible and accessible within 10 sec
     */
    public static  Boolean waitForElement(final SearchContext inRoot, final Selement selement) {

        Function<SearchContext, Boolean> condition = getCondition(selement);

        return waitForElement(inRoot, selement, condition);
    }


    /**
     * Waits for element to be accessible and visible on the screen.
     * @param inRoot - The search context for the Element - can be the whole page or part of it (SubPage or dialog)
     * @param element - The element that should be visible and accessible
     * @param condition - The condition to be filled.
     * @return true if the element is visible and accessible within 10 sec
     */
    private static  Boolean waitForElement(final SearchContext inRoot, final WebElement element,
                                          Function<SearchContext, Boolean> condition ) {

        root = inRoot == null ? driver : inRoot;

        try {
            return generateWait().until(condition);
        }

        catch (Exception e) {
            return false;
        }

    }



    /**
     * Waits for element to be accessible and visible on the screen.
     * @param context - The search context for the Element - can be the whole page or part of it (SubPage or dialog)
     * @param element - The element that should be visible and accessible
     * @return true if the element is visible and accessible within 10 sec
     */
    public static Boolean waitForElement(final SearchContext context, final WebElement element) {
        return waitForElement(context, element, getCondition(element));
    }


    /**
     *
     * @param element
     * @return
     */
    public static Boolean waitForElement(final WebElement element) {
        return waitForElement(driver, element, getCondition(element));
    }


    /**
     *  Waits for element to be selected.
     * @param element - the element which should get invisible
     * @return - true if condition was filled
     */
    public static Boolean waitForElementToBeSelected(final WebElement element) {
        return waitForElement(driver, element, getConditionElementSelected(element));
    }



    /**
     * Waits for element to get invisible or removed from the DOM.
     * @param element - the element which should get invisible
     * @return - true if condition was filled
     */
    public static Boolean waitForElementToDisappear(final WebElement element) {
        return waitForElement(driver, element, getConditionElementIsStaleOrInvisible(element));
    }






    /**
     * Generates a Wait object to ignore NoSuchElementException, StaleElementReferenceException
     * and ElementNotVisibleException. Waiting time is limited to 10 seconds
     * @return
     */
    private static FluentWait<SearchContext> generateWait() {

        return new FluentWait<SearchContext>(root)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .withTimeout(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotVisibleException.class);
    }


    /**
     * Create a condition for an Element to be either invisible on the screen or complete stale (removed from the DOM)
     * @param element - The element to be selected.
     * @return Function, with the condition
     * @param element
     * @return
     */
    private static Function<SearchContext, Boolean> getConditionElementIsStaleOrInvisible(final WebElement element) {
        return new Function<SearchContext, Boolean>() {

            @Override
            public Boolean apply(SearchContext driver) {
                try {
                    return !element.isDisplayed();
                }

                catch (StaleElementReferenceException e) {
                    return true;
                }
                catch (ElementNotVisibleException e) {
                    return true;
                }
                catch (ElementNotFoundException e) {
                    return true;
                }
                catch(NoSuchElementException e) {
                    return true;
                }
                catch (TimeoutException e) {
                    return true;
                }

            }
        };
    }



    /**
     * Create a condition for an Element to be visible on the screen and accessible
     * @param element - The element to be selected.
     * @return Function, with the condition
     */
    private static Function<SearchContext, Boolean> getCondition(final WebElement element) {

        return new Function<SearchContext, Boolean>() {
            @Override
            public Boolean apply(SearchContext driver) {
                return element.isEnabled() && element.isDisplayed();
            }
        };

    }


    /**
     * Create a condition for an Element to be selected.
     * @param element - The element to be selected.
     * @return Function, with the condition
     */
    private static Function<SearchContext, Boolean> getConditionElementSelected(final WebElement element) {

        return new Function<SearchContext, Boolean>() {
            @Override
            public Boolean apply(SearchContext driver) {
                return element.isSelected();
            }
        };

    }




}
