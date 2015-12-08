package com.github.roishon.simpleselenium.utils;

import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import org.openqa.selenium.SearchContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * An Util class used to help with various operation with the java.reflect classes and methods
 *
 * Created by Roi Shachor on 09.07.15.
 */
public class ReflectUtil {


    /**
     * Sets a value to a field of a Page Object.
     * @param field - the field which get the value assigned.
     * @param page - the Page object with the field.
     * @param value - the value to be assigned.
     */
    public static void setValueToField(Field field, Object page, Object value) {
        try {
            field.setAccessible(true);
            field.set(page, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e.toString());
        }
    }





    /**
     * Getting the value of a field from a page Object according to the name of the field.
     * The Object may extends a few superclasses, but the method will look only for fields declared in one of those classes.
     * If the field is from type List the returned value will be upon the index value given as parameter
     *
     * @param fieldName - name of the field with the value to be returned
     * @param page - the page object, which may extends a few Superclass
     * @param superClazz - The class, which declared fields will be inspected
     * @param listIndex - Position of the field in a list. Should be -1 if the field is not from type list
     * @return The value referred by field
     */
    public static Object getValueOfField(String fieldName, Object page, Class<?> superClazz, int listIndex) {

        Object object = null;


        for (Field field : superClazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                try {


                    field.setAccessible(true);

                    object = field.get(page);

                    if(Validator.isFromList(field) && listIndex >= 0)
                        object = ((List)object).get(listIndex);

                    break;
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }


        return object;
    }

    public static Object getValueOfFieldRecursive(String fieldName, Object page) {
        return getValueOfFieldRecursive(fieldName, page, -1);
    }



    /**
     * As getValueOfField(String, Object, Class<?>, int listIndex) but for fields, which are not from type List
     * @param field - value of this field will be returned
     * @param page - the page object, which may extends a few Superclass
     * @param superClazz - The class, which declared fields will be inspected
     * @return The value referred by field
     */
    public static Object getValueOfField(Field field, Object page, Class<?> superClazz) {
        return getValueOfField(field.getName(), page, superClazz, -1);
    }



    /**
     * As getValueOfField(Field, Object page, Class<?>) with the different, that this method will not look
     * for fields in a super class of the page object, but in its class itself
     * @param field - value of this field will be returned
     * @param page - the page object
     * @return The value referred by field
     */
    public static Object getValueOfField(Field field, Object page) {
        return getValueOfField(field.getName(), page, page.getClass(), -1);
    }



    /**
     * As method getValueOfField() but looking for the field in whole the hirarchiy of
     * the super classes up till Object class exclusive.
     *
     * @param fieldName - name of the field with the value to be returned
     * @param page - the page object, which may extends a few Superclass
     * @param listIndex - Position of the field in a list. Should be -1 if the field is not from type list
     * @return The value referred by field
     */
    public static Object getValueOfFieldRecursive(String fieldName, Object page, int listIndex) {

        Object object = null;

        Class<?> clazz = page.getClass();

        while(object == null && clazz != Object.class) {
            object = getValueOfField(fieldName, page, clazz, listIndex);
            clazz = clazz.getSuperclass();
        }

        return object;

    }



    /**
     * As method getValueFromField() but the search is not going by field name but by the class implementing the
     * class Type of the field. The method will look recursive through all the super classes of the object
     * up till class Object exclusive.
     *
     * @param implementingClass - the class implementing the Type class of the field, which value will be returned
     * @param page - the page object, which may extends a few Superclass
     * @param listIndex - Position of the field in a list. Should be -1 if the field is not from type list
     * @param <T>
     * @return The value referred by field
     */
    public static <T extends SearchContext> Object getValueOfFieldRecursiveByFieldType(Class<?> implementingClass, Object page, int listIndex) {

        Object object = null;

        Class<?> clazz = page.getClass();

        while(object == null && clazz != Object.class) {

            for(Field field :clazz.getDeclaredFields()) {
                if(field.getType() == implementingClass ||
                        (field.isAnnotationPresent(ImplementingClass.class) && field.getAnnotation(ImplementingClass.class).value() == implementingClass))
                    object = getValueOfField(field.getName(), page, clazz, listIndex);
            }

            clazz = clazz.getSuperclass();
        }

        return object;

    }


    /**
     * As getValueOfFieldsRecursive, but not for fields of type List.
     * @param implementingClass - the class implementing the Type class of the field, which value will be returned
     * @param page - the page object, which may extends a few Superclass
     * @return The value referred by field
     */
    public static Object getValueOfFieldRecursive(Class<?> implementingClass, Object page) {
        return  getValueOfFieldRecursiveByFieldType(implementingClass, page, -1);
    }


    /**
     * Returns a field which contains or refer to a value given as a parameter.
     * @param value - the value of field that will be returned.
     * @param page - the page object, which may extends a few Superclass
     * @param superClass - The class, which declared fields will be inspected
     * @return Field
     */
    public static Field getFieldFromValue(Object value, Object page, Class<?> superClass) {

        for(Field field : superClass.getDeclaredFields()) {
            try {
                field.setAccessible(true);

                if (field.get(page) == value)
                    return field;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * As getFieldFromValue(Object, Object, Class) but will search all the super classes of
     * the given page object recursive up till Object class exclusive
     * @param value - the value of field that will be returned.
     * @param page - the page object, which may extends a few Superclass
     * @return Field
     */
    public static Field getFieldFromValueRecursive(Object value, Object page) {

        Field field = null;
        Class<?> clazz = page.getClass();

        while(clazz != Object.class) {
            field = getFieldFromValue(value, page, clazz);
            if(field != null)
                return field;
        }

        return field;
    }



     /**
     * Invokes a new Object upon given class and object to used as parameter for the constructor of the new object.
     * @param objectClazz - The class to get instance from
     * @param parameterClass - The class of the parameter the object need to get per constructor
     * @param parameter - the object that will be hand over as parameter to the constructor
     * @param <T>
     * @param <S>
     * @return - an instance of the class
     */
    public static <T, S> S invokeObject(Class<S> objectClazz, Class<T> parameterClass, Object parameter) {

        S proxiObject = null;
        Object object = null;

        Constructor constructor = null;

        try {
            constructor = objectClazz.getConstructor(parameterClass);
        } catch (NoSuchMethodException e1) {
            try {
                constructor = objectClazz.getConstructor();
                object = constructor.newInstance();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if(object == null) {
            try {

                object = constructor.newInstance(parameter);



            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        proxiObject = objectClazz.cast(object);

        return proxiObject;
    }




}
