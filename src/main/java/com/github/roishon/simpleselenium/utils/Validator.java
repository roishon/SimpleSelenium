package com.github.roishon.simpleselenium.utils;


import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.TextInput;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.Dialog;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.DialogToggle;
import org.openqa.selenium.WebElement;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Util class which helps to check if Classes objects and fields are of specific types
 * Created by Roi Shachor on 09.07.15.
 */
public class Validator {


    public static boolean isFromWebElement(Object toValidate) {
        return isAssignable(WebElement.class, toValidate);
    }


    public static boolean isFromElement(Object toValidate) {
        return isAssignable(Selement.class, toValidate);
    }


    public static boolean isFromSubPage(Object toValidate) {return isAssignable(SubPage.class, toValidate);}


    public static boolean isFromDialogToogle(Object toValidate) {return isAssignable(DialogToggle.class, toValidate);}


    public static boolean isFromDialog(Object toValidate) {return isAssignable(Dialog.class, toValidate);}


    public static boolean isFromList(Object toValidate) {
        return isAssignable(List.class, toValidate);
    }

    public static boolean isFromTextInput(Object toValidate) {
        return isAssignable(TextInput.class, toValidate);
    }


    public static boolean isFromLoadablePage(Object toValidate) {
        return isAssignable(LoadablePage.class, toValidate);
    }


    private static boolean isAssignable(Class<?> clazz, Object toValidate) {
        return clazz.isAssignableFrom(getTheClass(toValidate));
    }


    private static Class<?> getTheClass(Object obj) {
        if(Field.class.isAssignableFrom(obj.getClass()))
            return ((Field)obj).getType();

        if(Class.class.isAssignableFrom(obj.getClass()))
            return ((Class)obj);

        else return obj.getClass();
    }
}

