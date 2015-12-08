package com.github.roishon.simpleselenium.utils;

import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * Created by Roi Shachor on 09.07.15.
 */
public final class TypesExtractor {
    private TypesExtractor() {
    }




    /**
     * Gets the class that implements the interface Type of the field, according to the information
     * of the annotation @implementingClass annotating the field
     * @param field - The implementing class of the interface, which is type of this field, will be returend
     * @param <T> Subclass of SearchContext
     * @return the class which implements the interface, which is the type of that field
     */
    public static  <T extends SearchContext> Class<T> getImplementingClass(Field field) {

        if(field.isAnnotationPresent(ImplementingClass.class))
            return getImplementingClass(field.getAnnotation(ImplementingClass.class));

        else return getImplementingClass(getType(field).getAnnotation(ImplementingClass.class));
    }


    /**
     * As method getImplementingClass(Field) but based on the interface itself, and not on the field
     * @param iface - the implementing class of this interface will be returned
     * @param <T> Subclass of SearchContext
     * @return the class which implements the interface given as parameter
     */
    public static <T extends SearchContext> Class<T> getImplementingClass(Class<?> iface) {

        if (iface.isAnnotationPresent(ImplementingClass.class))
            return getImplementingClass(iface.getAnnotation(ImplementingClass.class));

        throw new UnsupportedOperationException();
    }


    /**
     * Reads the value of the annotation @ImplementingClass
     * @param annotation - The annotation itself
     * @param <T> Subclass of SearchContext
     * @return a Class which implements the interface annotated with @ImplementingClass
     */
    private static <T extends SearchContext> Class<T> getImplementingClass(ImplementingClass annotation) {
        Class<?> clazz = annotation.value();
        if(Validator.isFromWebElement(clazz) || Validator.isFromSubPage(clazz))
            return (Class<T>)annotation.value();

        throw new UnsupportedOperationException();
    }


    /**
     * Returns the Type of a GenericType, used to extract the Type of a list
     * @param field - of type List<?>
     * @return The Type
     */
    public static Class<?> getErasureClass(Field field) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }


    /**
     * Getting the root Element from SubPage, Dialog and their subclasses.
     * @param value
     * @return
     */
    public static WebElement getRootElement(Object value) {

        if(Validator.isFromList(value)) {

            Object tempValue = ((List)value).get(0);

            if(Validator.isFromSubPage(tempValue))
                return ((SubPage)tempValue).getRoot();

            else return (WebElement)tempValue;
        }

        if(Validator.isFromSubPage(value))
            return ((SubPage)value).getRoot();

        if(Validator.isFromWebElement(value))
            return (WebElement)value;


        throw new RuntimeException();

    }


    /**
     * Returns the type of the field. If the field is from type list, returns the parameter type of the list
     * @param field - which type is to be determine
     * @return Type of the field
     */
    public static Class<?> getType(Field field) {
        if(Validator.isFromList(field))
            return getErasureClass(field);

        else
            return field.getType();

    }


}
