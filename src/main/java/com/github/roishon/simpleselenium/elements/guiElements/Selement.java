package com.github.roishon.simpleselenium.elements.guiElements;

import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

/**
 * This interface wraps the Interface WebElement.
 * The need for this interface is because the java.reflect.proxy object can be crated only with list of interfaces,
 * in order to be able to invoke methods for the proxied objects.
 * WebElement is an interface, and by default Selenium use RemoteWebElement to implements this interface.
 * This class does not replace RemoteWebElement. Its only used as type for attributes, which is important to the
 * process of initiating page class by this project. see {{@link com.kiongroup.easyFactory}}
 * Created by Roi Shachor on 09.06.15.
 */
@ImplementingClass(SelementImpl.class)
public interface Selement extends WebElement, WrapsElement, Locatable {



}
