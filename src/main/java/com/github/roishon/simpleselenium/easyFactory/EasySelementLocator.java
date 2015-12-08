package com.github.roishon.simpleselenium.easyFactory;


import com.github.roishon.simpleselenium.annotations.FixedLocator;
import com.github.roishon.simpleselenium.utils.TypesExtractor;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Class replace the Selenium DefualtSelementLocator class but can handle annotation @FixedLocator
 * See {@Link org.openqa.selenium.support.PageFactory.DefaultElementLocator}
 * Created by Roi Shachor on 09.07.15.
 */
public class EasySelementLocator implements ElementLocator {

    /**The context in which the locator looks for elements*/
    private final SearchContext searchContext;


    /**True if elements should be cached (not recommended to use with dynamic websites)*/
    private final boolean shouldCache;

    /**The By object used to find element in the DOM*/
    private final By by;

    /**Cached Elements (not recommended to use with dynamic websites)*/
    private WebElement cachedElement;

    /**List of Cached Elements (not recommended to use with dynamic websites)*/
    private List<WebElement> cachedElementList;




    /**
     * This Constructor is similar to the selenium class DefaultElementLocator of selenium,
     * but can also process fields annotated with @FixedLocator
     *
     * @param searchContext - The context to use when finding the element
     * @param field  - the field for which the locator is created
     */
    public EasySelementLocator(SearchContext searchContext, Field field) {
        this.searchContext = searchContext;
        Annotations annotations = new Annotations(field);

        this.shouldCache = annotations.isLookupCached();

        if(field.isAnnotationPresent(FixedLocator.class))
            by = getByFromFixedAnnotation(field);


        else
            this.by = annotations.buildBy();

    }



    /**
     * Create a By object from fields, which are annotated with @FixedLocator.
     * @param field - for which a By object should be created
     * @return a By Object
     */
    private By getByFromFixedAnnotation(Field field) {


        FindBy findBy;
        Class<?> type = TypesExtractor.getType(field);

        if (type.isAnnotationPresent(FindBy.class))
            findBy = type.getAnnotation(FindBy.class);

        else
            findBy = TypesExtractor.getImplementingClass(type).getAnnotation(FindBy.class);


        By by = null;
        int usingCase = -1;

        String usingValue = findBy.using() + findBy.tagName() + findBy.partialLinkText() + findBy.linkText() +
                findBy.className() + findBy.css() + findBy.name() + findBy.id() + findBy.xpath();



        if(!findBy.className().equals(""))
            usingCase = 0;

        else if(!findBy.css().equals(""))
            usingCase = 1;

        else if(!findBy.id().equals(""))
            usingCase = 2;

        else if(!findBy.linkText().equals(""))
            usingCase = 4;

        else if(!findBy.name().equals(""))
            usingCase = 5;

        else if(!findBy.partialLinkText().equals(""))
            usingCase = 6;

        else if(!findBy.tagName().equals(""))
            usingCase = 7;

        else if(!findBy.xpath().equals(""))
            usingCase = 8;

        else usingCase = findBy.how().ordinal();





        switch(usingCase){

            case(0):
                by = By.className(usingValue);
                break;

            case(1):
                by = By.cssSelector(usingValue);
                break;

            case(2):
                by = By.id(usingValue);
                break;

            case(3):

                //TODO

                break;

            case(4):
                by = By.linkText(usingValue);
                break;

            case(5):
                by = By.name(usingValue);
                break;

            case(6):
                by = By.partialLinkText(usingValue);
                break;

            case(7):
                by = By.tagName(usingValue);
                break;

            case(8):
                by = By.xpath(usingValue);
                break;
        }

        return by;
    }


    /**
     * Use this constructor in order to process custom annotaions.
     *
     * @param searchContext The context to use when finding the element
     * @param annotations AbstractAnnotations class implementation
     */
    public EasySelementLocator(SearchContext searchContext, Annotations annotations) {
        this.searchContext = searchContext;
        this.shouldCache = annotations.isLookupCached();

        this.by = annotations.buildBy();
    }

    /**
     * same as:
     * @link org.openqa.selenium.support.pagefactory.DefaultElementLocator#findElement()
     * @return WebElement
     */
    public WebElement findElement() {
        if (cachedElement != null && shouldCache) {
            return cachedElement;
        }

        WebElement element = searchContext.findElement(by);
        if (shouldCache) {
            cachedElement = element;
        }

        return element;
    }



    /**
     * same as
     * @link org.openqa.selenium.support.pagefactory.DefaultElementLocator#findElements()
     * @return List of WebElements
     */
    public List<WebElement> findElements() {
        if (cachedElementList != null && shouldCache) {
            return cachedElementList;
        }

        List<WebElement> elements = searchContext.findElements(by);
        if (shouldCache) {
            cachedElementList = elements;
        }

        return elements;
    }



}

