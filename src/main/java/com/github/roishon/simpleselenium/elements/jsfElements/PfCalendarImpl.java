package com.github.roishon.simpleselenium.elements.jsfElements;


import com.github.roishon.simpleselenium.annotations.ExplicitElement;
import com.github.roishon.simpleselenium.elements.guiElements.Selement;
import com.github.roishon.simpleselenium.elements.guiElements.subPages.dialogs.DialogImpl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.Calendar;
import java.util.List;


/**
 * Class represent the Calendar component of PrimeFaces.
 * Contains methods to choose a date base on the given parameters
 * Created by roi on 09.07.15.
 */
public class PfCalendarImpl extends DialogImpl implements PfCalendar {


    /**
     * Initiate the attribute root, which refers to the HTML-Tag framing this whole Calendar on the GUI
     *
     * @param root - The WebElement that wraps the calendar
     */
    public PfCalendarImpl(WebElement root) {
        super(root);
    }



    /** Represents button to switch to previous month on the calendar*/
    @FindBy(css = "a[data-handler = 'prev']")
    private Selement prevButton;

    /** Represents button to switch to following month on the calendar*/
    @FindBy(css = "a[data-handler = 'next']")
    private Selement nextButton;

    /**A list with Selements, each represents a day on the calender*/
    @ExplicitElement
    @FindBy (css = "td > a")
    List<Selement> days;

    /**Represents the "today" button*/
    @FindBy (css = ".ui-datepicker-current.ui-state-default.ui-priority-secondary.ui-corner-all")
    private Selement todayButton;

    /**Represents the year select field*/
    @FindBy (css = ".ui-datepicker-year")
    private Selement currentYearField;

    /**Represents the month select field*/
    @FindBy (css = ".ui-datepicker-month")
    //SelectToggleFree currentMonthField;
    private Selement currentMonthField;


    /**
     * Closes the calendar. Makes sure the date on the screen is rendered
     */
    @Override
    public void clickButtonSave() {
        clickClosingButton(1);
    }


    /**
     * Clicks on the "Today" button, which turn the calendar to current month, and clicks the current day
     */
    public void clickButtonToday() {
        todayButton.click();
        afterClosingDialog();
    }


    /**
     * Clicks a day on the current presented month page of the calendar
     * @param day - the day to be clicked (1-31)
     */
    public void clickADay(int day) {
        days.get(day - 1).click();
    }



    /**
     * Turn the Calendar to reach the month according to the year, month and day given as parameter
     * @param year - the target year for the calendar
     * @param month - the target month for the calendar
     * @param day - The day to be clicked (1-31)
     */
    public void switchToDate(int year, int month, int day) {

        int pagesToTurn = calculatePagesToTurn(year, month);

        Selement turnButton = pagesToTurn > 0 ? nextButton : prevButton;

        for(int i = 0; i < Math.abs(pagesToTurn); i++)
            turnButton.click();

        clickADay(day);

    }


    /**
     * Turn the Calendar to reach the month according to the date of the Calendar object, given as parameter
     * @param calendar - A java.util.Calendar object with the target date
     */
    public void switchToDate (Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        switchToDate(year, month, day);

    }


    /**
     * Calculates the number of pages, that need to be turn in the calender base on the year and month
     * given as parameter
     * @param year - the target year for the calendar
     * @param month - the target month for the calendar
     * @return number of pages to be turned, to reach the target month page.
     * If the number is negative, pages must be turned to the past. If positive than to the future month to come
     */
    private int calculatePagesToTurn(int year, int month) {
        month ++;
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        int yearDiff = year - currentYear;

        return  12 * yearDiff + month - currentMonth;

    }



}


