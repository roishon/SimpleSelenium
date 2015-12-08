package com.github.roishon.simpleselenium.utils;


import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.Dialog;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Class contains util methods to handle lists with Elements which refer to GUI-Elements.
 * Created by Roi Shachor on 09.07.15.
 */
public class ListUtil {


    /**
     * Makes sure a List with objects, which refer to GUI-Elements can still refer to those elements,
     * otherwise the the attributes of the page object will be reinitialized.
     * @param elements - List with the elements referring the GUI-elements
     * @param <T> Subclass of SearchContext
     */
    public static <T extends SearchContext> void refreshListElements(List<T> elements) {


        try {

            for (SearchContext element : elements) {

                WebElement el;

                if(Validator.isFromSubPage(element))
                    el = ((SubPage)element).getRoot();
                else
                    el = (WebElement)element;

                if (!el.isDisplayed()) {

                    Dialog dialog = TestSingleton.getLastDialog();

                    if(dialog != null)
                        dialog.initElements();

                    else
                        TestSingleton.refreshPage();

                    break;
                }
            }

        } catch (StaleElementReferenceException e){
            TestSingleton.refreshPage();
        }

    }


    /**
     * Get a Selement form a list by the value of the 'an attribute in HTML-Tag defining all the selements in the list.
     * This method used by methods below.
     *
     * @param elements - list with the selements to choose from
     * @param tagAttribute - a member of the HTML enum (see @com.kiongroup.utils.HTML)
     * @param value - String value which helps to determine which element from the list is the wanted one
     * @param <Context> Subclass of selenium SearchContext
     * @return an instance of SearchContext which is the wanted selemnt from the list
     */
    private static <Context extends SearchContext> Context getElement(List<Context> elements, HTML tagAttribute, String value) {

        String atrr = convertHTML(tagAttribute);

        for (Context element : elements)  {
            Context temp = Validator.isFromSubPage(element) ?  (Context)((SubPage)element).getRoot() : element;



            if (((WebElement)temp).getAttribute(atrr).toLowerCase().contains(value.toLowerCase()))
                return element;
        }


        return null;

    }


    /**
     * Get a Selement form a list by the value of the 'an attribute in HTML-Tag defining all the selements in the list
     * @param elements - list with the selements to choose from
     * @param tagAttribute - a member of the HTML enum (see @com.kiongroup.utils.HTML)
     * @param value - String value which helps to determine which element from the list is the wanted one
     * @param <Context> Subclass of selenium SearchContext
     * @return an instance of SearchContext which is the wanted selemnt from the list
     */
    public static <Context extends SearchContext> Context getElementFromList(List<Context> elements, HTML tagAttribute, String value) {

        Context context;


        try {
            context =  getElement(elements, tagAttribute, value);
            if(context != null)
                return context;

        } catch(StaleElementReferenceException e1) {
            try {
                Waiting.waitForElement((WebElement)elements.get(0));

            } catch (StaleElementReferenceException e2) {
                refreshListElements(elements);
            }

        }

        return getElement(elements, tagAttribute, value);

    }


    /**
     * Get a Selement form a list by the value of the 'id' attribute in HTML-Tag defining all the selements in the list
     * @param elements - list with the selements to choose from
     * @param text - String value which helps to determine which element from the list is the wanted one
     * @param <T> - Subclass of Selement
     * @return an instance of Selement or its Subclasses
     */
    public static <T extends Selement> T getElementFromListById(List<T> elements, String text) {
        return getElementFromList(elements, HTML.ID, text);
    }


    /**
     * Get a Selement form a list by the text displayed
     * @param elements - list with the selements to choose from
     * @param text - String value which helps to determine which element from the list is the wanted one
     * @param <T> - Subclass of Selement
     * @return an instance of Selement or its Subclasses
     */
    public static <T extends Selement> T getElementFromListByText(List<T> elements, String text) {
        return getElementFromList(elements, HTML.TEXT, text);
    }


    /**
     * Get a Selement form a list by the value of the 'href' attribute in HTML-Tag defining all the selements in the list
     * @param elements - list with the selements to choose from
     * @param text - String value which helps to determine which element from the list is the wanted one
     * @param <T> - Subclass of Selement
     * @return an instance of Selement or its Subclasses
     */
    public static <T extends Selement> T getElementFromListByHref(List<T> elements, String text) {
        return getElementFromList(elements, HTML.HREF, text);
    }


    /**
     * Get a Selement form a list by the value of the 'name' attribute in HTML-Tag defining all the selements in the list
     * @param elements - list with the selements to choose from
     * @param text - String value which helps to determine which element from the list is the wanted one
     * @param <T> - Subclass of Selement
     * @return an instance of Selement or its Subclasses
     */
    public static <T extends Selement> T getElementFromListByName(List<T> elements, String text) {
        return getElementFromList(elements, HTML.NAME, text);
    }



    private static String convertHTML(HTML html) {
        return html.name().toLowerCase().replace("_", "-");
    }


}

