package com.github.roishon.simpleselenium.utils;

import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.Dialog;
import org.openqa.selenium.WebDriver;


import java.util.ArrayList;
import java.util.List;

/**
 * This singleton class holds references to the instance of page classes and dialogs.
 * Those are referring to the displayed Page and dialogs at any given method.
 * Methods of this class enable access to this instances and initiating them again if needed.
 * Created by Roi Shachor on 09.07.15.
 */
public class TestSingleton {


    /**An instance of this singleton class*/
    private static TestSingleton singleton;

    /**An instance of WebDriver which runs the selenium test*/
    private static WebDriver driver;


    /**List holding references to dialogs, which are opened on the screen*/
    private static List<Dialog> dialogs  = new ArrayList<Dialog>();

    /**A Page Object reflecting the current page displayed on the screen*/
    private static LoadablePage currentPage;



    /**Preventing a direct instantiation of this class */
    private TestSingleton(){}


    /**
     * A method to get instance of this class, having this method is a singleton
     * @return the instance of this class
     */
    public static TestSingleton getInstatnce() {
        if(singleton == null) {
            singleton = new TestSingleton();
        }

        return singleton;
    }


    /**
     * Returns a reference to the driver running the selenium test
     * @return - reference to the instance of WebDriver
     */
    public static WebDriver getDriver() {
        getInstatnce();
        if(driver != null)
            return driver;
        else
            throw new RuntimeException("Driver was not yed initialed.");
    }


    /**
     * Sets the instance of WebDriver, that is used for the whole test
     * @param in_driver - The driver used to run the selenium test
     */
    public static void setDriver(WebDriver in_driver) {
        getInstatnce();
        driver = in_driver;
    }

    /**
     * Returns a reference to the Page Object which refers to the Page displayed at
     * the moment of this method call
     * @return
     */
    public static LoadablePage getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the class member currentPage, which holds reference to the Page Object, which refer to the
     * Page displayed in each given moment of the test
     * @param currentPage - Object of a Subclass of LoadablePage, represents the current Page Object displayed
     */
    public static void setCurrentPage(LoadablePage currentPage) {
        TestSingleton.currentPage = currentPage;
    }


    /**
     * Re-initiate all attributes of the current Page-class
     */
    public static void refreshPage() {
        currentPage.initElements();
    }


    /**
     * Re-initiate all attributes of the last dialog opened
     */
    public static void refreshDialog() {
        getLastDialog().initElements();
    }


    /**
     * Returns list with reference to all dialog, which are open in the website
     * as for the moment of this method call
     * @return list with reference to all open dialogs
     */
    public static List<Dialog> getDialogs() {
        return dialogs;
    }


    /**
     * Adds a dialog to the dialog list. Called when a new dialog is being opened.
     * @param dialog - The dialog to add to the list
     */
    public static void addDialog(Dialog dialog) {
        dialogs.add(dialog);
    }


    /**
     * Removes dialog from the list of opened dialogs. Called when dialog is being closed
     * @param dialog - the dialog that was closed and need to be removed from the list
     */
    public static void removeDialog(Dialog dialog) {
        dialogs.remove(dialog);
    }

    /**
     * Returns the last opened dialog
     * @return last opened dialog
     */
    public static Dialog getLastDialog() {return dialogs.get(dialogs.size() - 1);}



}
