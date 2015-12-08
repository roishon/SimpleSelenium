package com.github.roishon.simpleselenium.easyFactory;



import com.github.roishon.simpleselenium.annotations.FixedLocator;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.utils.TypesExtractor;
import com.github.roishon.simpleselenium.utils.Validator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * EasyFieldDecorator replaces Selenium class DefaultFieldDecorator
 * to work with self written Classes as Element, ElementImpl, SubPage and their helpers
 *
 * Created by Roi Shachor on 09.07.15.
 */
public class EasyFieldDecorator implements FieldDecorator {


    /**
     * factory to use when generating ElementLocator.
     */
    protected ElementLocatorFactory factory;


    /**
     * Constructor for an ElementLocatorFactory. This class is designed to replace DefaultFieldDecorator.
     *
     * @param factory for locating elements.
     */
    public EasyFieldDecorator(ElementLocatorFactory factory) {
        this.factory = factory;
    }



    /**
     * Method called by EasyFactory and decide how the field will be decorated.
     * Similar to selenium DefualtFieldDecorator.
     * By Fields of type Selement, SubPage, Dialog and its subclasses,
     * it returns a Proxy<Selement> object just as DefaultFieldDecorator
     * By Fields of type List, with Type of list being one of the types mentioned above,
     * The return value will be proxy for List<Selement>.

     * @param loader - the ClassLoader of the the page, to which the field belongs
     * @param field  -  the field to be decorate
     * @return proxy object of type Selement or List<Selement>
     */
    @Override
    public Object decorate(ClassLoader loader, Field field) {



        if(!validFieldParameter(field))
            return null;

        ElementLocator locator;


        locator = factory.createLocator(field);

        if (locator == null)
            return new RuntimeException("Element could not be decorated with a locator. Value of @FindBy for this field might be not correct.");



        Class<?> fieldType = field.getType();

        if (Validator.isFromList(field)) {
            Class<?> erasureClass = TypesExtractor.getErasureClass(field);
            return  proxyForListLocator(loader, erasureClass, locator);
        }

        //FieldType of type SubPage is being converted here to Element.class, because this method only interested in the
        //proxy object of type Element, which will represent the html tag on the page
        else if(fieldType == WebElement.class || Validator.isFromSubPage(field))
            fieldType = Selement.class;


        return proxyForLocator(loader, fieldType, locator);

    }



    /**
     * Validate if the field is of type, that can be decorated by this class
     * @param field - to be decorated
     * @return true, if the field is decoratable
     */
    private boolean validFieldParameter(Field field) {
        if (!isDecoratableField(field))
            return false;

        if (!Validator.isFromElement(field) && !Validator.isFromSubPage(field) && !isDecoratableList(field))
            return false;

        return true;
    }




    /**
     * Generate a type-parameterized locator proxy for the element in question. We use our customized InvocationHandler
     * here to wrap classes.
     *
     * @param loader        ClassLoader of the wrapping class
     * @param interfaceType Interface wrapping the underlying WebElement
     * @param locator       ElementLocator pointing at a proxy of the object on the page
     * @param <T>           The interface of the proxy.
     * @return a proxy representing the class we need to wrap.
     */
    @SuppressWarnings("unchecked")
    protected <T> T proxyForLocator(ClassLoader loader, Class<T> interfaceType, ElementLocator locator) {


        java.lang.reflect.InvocationHandler handler = new EasyInvocationHandler(interfaceType, locator);

        return interfaceType.cast(Proxy.newProxyInstance(loader, new Class[]{interfaceType}, handler));
    }


    /**
     * generates a proxy for a list of elements to be wrapped.
     *
     * @param loader        classloader for the class we're presently wrapping with proxies
     * @param interfaceType type of the element to be wrapped
     * @param locator       locator for items on the page being wrapped
     * @param <T>           class of the interface.
     * @return proxy with the same type as we started with.
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> proxyForListLocator(ClassLoader loader, Class<?> interfaceType, ElementLocator locator) {

        InvocationHandler handler = new EasyListInvocationHandler(interfaceType, locator);

        return  (List<T>) Proxy.newProxyInstance(loader, new Class[]{List.class, interfaceType}, handler);
    }



    /**
     * checks if a List<> can be decorated with a locator
     * @param field the field of type List to be decorated
     * @return True if field is of type List and can be decorated with a locator
     */
    private boolean isDecoratableList(Field field) {
        if(!Validator.isFromList(field))
            return false;


        Class<?> erasureClass = TypesExtractor.getErasureClass(field);
        if (erasureClass == null)
            return false;


         if(!Validator.isFromElement(erasureClass) && !Validator.isFromSubPage(erasureClass))
            return false;


        if (!isDecoratableField(field))
            return false;


        return true;
    }



    /**
     * Controls if a field is annotated corectly in order to refer to a GUI-Element.
     * @param field- field to be checked if  decoratable
     * @return true if this field is correctly annotated
     */
    public static boolean isDecoratableField(Field field) {
        return field.getAnnotation(FindBy.class) != null ||
                field.getAnnotation(FindBys.class) != null ||
                field.getAnnotation(FindAll.class) != null ||
                field.getAnnotation(FixedLocator.class) != null;
    }


}



