package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.annotations.ImplementingClass;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.Dialog;
import org.openqa.selenium.support.FindBy;

import java.util.Calendar;

/**
 * Interface represent the Calendar component of PrimeFaces.
 * Contains methods to choose a date base on the given parameters
 * Created by roi on 09.07.15.
 */
@FindBy(id = "ui-datepicker-div")
@ImplementingClass(PfCalendarImpl.class)
public interface PfCalendar extends Dialog {


    /**
     * Clicks on the "Today" button, which turn the calendar to current month, and clicks the current day
     */
    public void clickButtonToday();

    /**
     * Clicks a day on the current presented month page of the calendar
     * @param day - the day to be clicked (1-31)
     */
    public void clickADay(int day);

    /**
     * Turn the Calendar to reach the month according to the date of the Calendar object, given as parameter
     * @param calendar - A java.util.Calendar object with the target date
     */
    public void switchToDate(Calendar calendar);


    /**
     * Turn the Calendar to reach the month according to the year, month and day given as parameter
     * @param year - the target year for the calendar
     * @param month - the target month for the calendar
     * @param day - The day to be clicked (1-31)
     */
    public void switchToDate(int year, int month, int day);

    /**
     * Closes the calendar. Makes sure the date on the screen is rendered
     */
    @Override
    public void clickButtonSave();


}
