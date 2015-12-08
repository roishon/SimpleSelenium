package com.github.roishon.simpleselenium.easyFactory;


import com.github.roishon.simpleselenium.annotations.OpenDialog;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPage;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.SubPageImpl;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.Dialog;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.DialogToggle;
import com.github.roishon.simpleselenium.utils.LoadablePage;
import com.github.roishon.simpleselenium.utils.ReflectUtil;
import com.github.roishon.simpleselenium.utils.TypesExtractor;
import com.github.roishon.simpleselenium.utils.Validator;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Create an instance of a Page class and initiates its attributes, or just re-initiates the attributes of an
 * exicting Page Objects. Its similar to selenium PageFactoriy, but while selenium create only instance of the
 * java.reflect.proxy class and assign them to attributes of type WebElement and List<WebElement>, EasyFactory
 * also does the same with Attributes of type Selement, List<Selement> and all their subclasses.
 * For attributes of type SubPage and Dialog EasyFactory create an actual instance of that type, and not a proxy.
 * In case of SubPage and its subclasses, the attributes of this classes will also be initiated.
 * Created by Roi Shachor on 09.07.15.
 */
public class EasyFactory {


    /**
     *  See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.WebDriver driver, Class)}
     */
    public static Object initElements(WebDriver driver, Class<?> pageClassToProxy) {
        Object page = instantiatePage(driver, pageClassToProxy);
        return initElements(driver, page);
    }


    /**
     * As initElements of WebDriver but with first parameter SearchContex instead of WebDriver,
     * because the intiated searchContex may be only a SubPage or a dialog
     *
     * @param context - The context in which the attributes should be initiated. Can be the whole page or a part of it
     * @param pageClassToProxy - the class to be intantieted
     * @return an instance of this page class with initialized attributes referring the GUI-Elements
     */
    public static Object initElements(SearchContext context, Class<?> pageClassToProxy) {
        Object page = instantiatePage(context, pageClassToProxy);
        return initElements(context, page);
    }



    /**
     * As
     * {@link EasyFactory#initElements(org.openqa.selenium.WebDriver, Object)}
     * but will only replace the fields of an already instantiated Page Object.
     *
     * @param driver A search context that will be used to look up the elements
     * @param page The object with WebElement and List<WebElement> fields that should be proxied.
     * @return the initialized page-object.
     */
    public static Object initElements(WebDriver driver, Object page) {
        initElements((SearchContext)driver, page);
        return page;
    }



    /**
     * As initElements(WebDriver, object) but with SearchContex as first parameter,
     * so the initiated searchContex may be only a SubPage or a dialog.
     * Another different to the selenium method, is that this method generate an instance of
     * EasyFieldDecorator instead of the selenium class DefaultFieldDecorator,
     * and an instance of EasySelementLocatorFactory instead of the selenium class DefaultElementLocatorFactory.
     *
     * @param searchContext - The context in which the attributes should be initiated. Can be the whole page or a part of it
     * @param page - object of the page class
     * @return the initialized page-object.
     */
    public static Object initElements(SearchContext searchContext, Object page) {
        initElements(new EasyFieldDecorator(new EasySelementLocatorFactory(searchContext)), page);
        return page;
    }



    /**
     * As the equivalent selenium method, but this generate an instance of the class EasyFieldDecorator
     * instead of the selenium class DefaultFieldDecorator.
     * see {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.ElementLocatorFactory, Object)}
     */
    public static void initElements(ElementLocatorFactory factory, Object page) {
        final ElementLocatorFactory factoryRef = factory;
        initElements(new EasyFieldDecorator(factoryRef), page);
    }



    /**
     * As selenium method but first parameter is EasyFieldDecorator instead of FieldDecorator
     * See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.FieldDecorator, Object)}
     * @param decorator - the decorator to use
     * @param page - object of the page class

     */
    public static void initElements(EasyFieldDecorator decorator, Object page) {
        Class<?> proxyIn = page.getClass();

        while (proxyIn != Object.class && proxyIn != LoadablePage.class ) {
            proxyFields(decorator, page, proxyIn);
            proxyIn = proxyIn.getSuperclass();
        }

    }



    /**
     * Retrieves all the fields of a class and assigned objects to the attributes, which refer to GUI-Elements.
     * If the attributes are from type Selement or List<Selemets> or their subclasses they will be assigned
     * with Objects of the java.reflec.proxy class. If the attributes are of the type SubPage, Dialog or
     * their subclasses, they will be assigned with object of those classes.
     *
     * @param decorator - A decorator object for this fields (See EasyFieldDecorator)
     * @param page - The Object of the Page class, which fields art to be decorated (assigned with objects)
     * @param proxyIn - The class which declared attributes are to be retrieved. This can differ from the page,
     *                because of the class of the page Object may extends the proxyIn class
     */
    private static void proxyFields(EasyFieldDecorator decorator, Object page, Class<?> proxyIn) {

        for (Field field : proxyIn.getDeclaredFields())
            proxyOneField(decorator, page, field);

    }



    /**
     * If the field refer to a GUI-Element and annotated correctly (see EasyFieldDecorator) it will be assigned
     * with an object.
     * If it is from type Selement or List<Selemets> it will be assigned with Objects of the java.reflec.proxy class.
     * If it is of the type SubPage, Dialog or their subclasses, it will be assigned with object of thos classes.
     * @param decorator - A decorator object for this field (See EasyFieldDecorator)
     * @param page - The Object of the Page class, which fields art to be decorated (assigned with objects)
     * @param field - The field that will be assigned with an object
     */
    private static void proxyOneField(EasyFieldDecorator decorator, Object page, Field field) {

        Object value = decorator.decorate(page.getClass().getClassLoader(), field);

        if (value != null) {


            if (Validator.isFromSubPage(field) && Validator.isFromElement(value))
                value = initSubPage(field, value);


            ReflectUtil.setValueToField(field, page, value);


            if (Validator.isFromDialogToogle(field))
                initDialogToggles(page, field);
        }

    }




    /**
     * Create an instance of SubPage, Dialog and their subclasses.
     * If the field is not from type Dialog and its subclasses,
     * the attributes of the instance will be initiate recursive, just as it is done with the current page class
     * @param field - The field to be assigned with the instance
     * @param value - The proxy object for a Selement, will be used as the root Attribute of the SubPage
     *              (See {@link com.kiongroup.elements.guiElements.subPages.SubPageImpl})
     * @param <T> A Subclass of Subpage
     * @return an instance of the SubPage. If its not from Type Dialog, the instance will have it attributes initiated
     */
    public static <T extends SubPage> T initSubPage(Field field, Object value) {

        if(!Validator.isFromSubPage(field))
            throw new RuntimeException("Field " + field.getName()+ " of class " +
                    field.getDeclaringClass().getSimpleName() +
                    " is not from type SubPage and can not be hand over to method initSubPage");

        Class clazz = TypesExtractor.getImplementingClass(field);

        SubPageImpl subPage = (SubPageImpl) ReflectUtil.invokeObject(clazz, WebElement.class, value);

        if(!Validator.isFromDialog(field))
            subPage.initElements();

        return (T)subPage;

    }





    /**
     * Reads the value of the @OpenDialog annotation from the field. It the field is not from type DialogToggle
     * or its not annotated with @OpenDialog a RunTimeException will be thrown.
     * The value of the class will be set into the actual Toggle Object
     *
     * @param page - the page Object with the field
     * @param toggleField - The field of type OpenDialog
     */
    public static void initDialogToggles(Object page, Field toggleField) {
        try {
            toggleField.setAccessible(true);
            Class<?> clazz = toggleField.getAnnotation(OpenDialog.class).value();
            String attribute = toggleField.getAnnotation(OpenDialog.class).attrForDialog();



            if(attribute.equals("") && clazz == Dialog.class) {
                String message = String.format("Field %s at class %s must be annotated with @OpenDialog " +
                                "with the name of Dialog class or class member as reference to the Dialog to be opened",
                        toggleField.getName(), toggleField.getDeclaringClass());

                throw new RuntimeException(message);
            }


            if(Validator.isFromList(toggleField)) {

                List<DialogToggle> toggles = ((List<DialogToggle>)toggleField.get(page));

                for(int i = 0; i < toggles.size(); i++)
                    toggles.get(i).setDialogClass(clazz, attribute);


            }
            else
                ((DialogToggle) toggleField.get(page)).setDialogClass(clazz, attribute);
        }

        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }





    /**
     * Copy of {@link org.openqa.selenium.support.PageFactory#instantiatePage(org.openqa.selenium.WebDriver, Class)}
     */
    private static Object instantiatePage(SearchContext context, Class<?> pageClassToProxy) {
        return ReflectUtil.invokeObject(pageClassToProxy, context.getClass(), context);
    }





}

