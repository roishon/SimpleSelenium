SimpleSelenium Manual

Basic knowledge of selenium is required in order to use SimpleSelenium and this manual.
Please download the SimpleSelenium.jar to your test project.

The biggest advantage when using SimpleSelenium, is creating a test with no need to write any expected conditions (ExpectedCondition). An Exception often thrown when running a test with Selenium is StaleElementReferenceException
This happens after the DOM was dynamically changed and the the test can not use the Objects referenced by the class members to contact the relevant nodes in the current DOM. The methods in §8b- §8d makes sure the test will not fail even though there are no expected condition at all.  


1) Selement - should be used instead of WebElement

Class members of a page class, which refer to GUI-Elements on the screen, should be of Interface type Selement instead of WebElement. The Selenium 'WebElement' is considered deprecated when using SimpleSelenium.



2) LoadablePage- The Supper class for all page classes

As in Selenium, the 'Page Object' pattern should be used with SimpleSelenium.
Each Page class of a test should extends LoadablePage.

Some of the class members of a Page class refer to GUI elements. Same as in a selenium code they should be annotated with @FindBy, @FindBys or @FindAll and may be of type List<Selement>.

class SomePartOfAPage extends LoadbalePage {

	@FindBy(css = „button[name*=submit]“)
	private Selement button;


	@FindBy(css = „button[id*=menu_button]“)
	private List<Selement> menuButtons;
}

Some of the important methods of class LoadablePage will be introduced in §8.






3) SubPage, SubPageImpl - a class member which represent a section of the page


A Limited section on the screen may be represented by a separate class. This class should extend SubPageImpl instead of LoadablePage (SubPage is an interface). The Constructor of this class will receive a Selement as parameter. This Selement represent a GUI-Element, which contains this section on the screen. So the programmer needs to find the locator that contains this section. E.g.:

<html>
<div>
	<div id=“some_section“> Everything in this tag belongs in the section 	</div>
</div>
</html>


@FindBy (id = '84some_section')
private SubPage subSection;


This class allows the locators to be more simple. 
For example - a page maintains many buttons. Each button is represented by a class member. The locators for this buttons must distinguish each button from the others and must be very specific. But if one button is located in an section, and this section is represented in the test by a separate SubPage, the locator may be very simple: 

class MainPage extends LoadablePage {
	
	@ImplementingClass
	@FindBy(tagname = „button“)
	private SubPage someSection; //SubPage refers to the class below; 
}


class SomeSection extends SubPageImpl { //extends the class SubPageImpl

    public SomeSection(Selement element) {
       super(element);
    }
}

!Important - The class member representing the sub section should be of type interface SubPage. 

In General ALWAYS USE INTERFACE AS TYPE FOR TYPE OF CLASS MEMBERS, not classes;
The class which represents the this section should extends the class SubPageImpl.


4) Dialog, DialogImpl

Similar to SubPage, a class which represents a Dialog can extends DialogImpl. Using this class helps SimpleSelenium to make sure dialogs are fully opened or closed, before proceeding with the test, which helps avoiding test failing. 

Once again, the Page class which represents this dialog extends the class DialogImpl. 
The class member within the main class is of type interface Dialog.

class MainPage extends LoadablePage {

    private Dialog someDialog; //refers to the Dialog class below
}


class SomeDialog extends DialogImpl { . . . }



5) @ExplicitElement - 
annotating a class member, which must be fully loaded before test proceeds 

One and only one class member in each Page/SubPage/Dialog class must be annotated with @ExplicitElement. Class members represent GUI elements. Some elements are static in the HTML and some are dynamically added elements (for example table head is static, in oppose to table lines, which may be dynamically added ). The dynamic element should be annotated with @ExplicitElement. 

class SomePage extends LoadablePage {

	@FindBy (tagName = „th“)
	Selement tableHead;

	@ExplicitElement
	@FindBy  (tagName = "tr")
	List<Selement> tableLines;

}

This helps SimpleSelenium to makes sure that a new page if fully loaded, before proceeding with the test. Only when SimpleSelenium can locate the explicit Element in the DOM the test will continue to the next step.



6) Specific Elements - classes of specific HTML (and PrimeFaces) GUI element 

In Selenium all GUI-Elements are covered only by WebElement. SimpleSelenium has various of interface and class to represents simple html elements (input fields, buttons), and Primefaces elements. Those are this interfaces:
HTML elements:
Checkbox, TextInput (for input fields), Select (drop down menu), Table (which extends SubPageImpl class).

Some of the JSF or Primefaces components:
PfTree, PfCalendar, PfSelect (stands for select option, which are not based on the HTML select tag, but on primefaces button, which opens a Dialog that looks like an drop down options), PfTable (table which can be sorted as per entering text in the table head input fields).
Those component where created upon a specific version of Primeface project, and may be deprecated or not working correctly for other projects. The interface above of the HTML group are ever green.

Always use the interface for all class members, never the class.


@FindBy(xpath = "//input[@name='q']")
private TextInput  searchField; <= Use interface TextInput NOT TextInutImpl 



7) DialogToggle and @OpenDialog - for buttons that open Dialogs

Another specific type is DialogToggle. Use this for buttons, that open Dialogs when clicked.In the example below the class representing the main page, there are references to the button as well as for the dialog; The button (DialogToggle) is annotated with @OpenDialog(). This annotation can receive a String value or a Class<?>. The String value is the name of the class member, which represents the Dialog. A class member of type DialogToggle MUST be annotated with @OpenDialog;

public class MainPage extends LoadablePage {

	@FindBy(. . .)
	@OpenDialog("someDialog")  <= or => @OpenDialog(ASpecificDialog.class)
	private DialogToggle toggle;

	@FindBy(. . .)
	private ASpecificDialog someDialog;}
8) Methods of class LoadablePage


8a)  
initElements() - allocate the class members with object to connect the DOM

Should be called when creating a new class - new SomePage().initElements();
For example:

public class MainPage extends LoadablePage {
	@FindBy( . . .)
	private Selement button;

	public NextPage clickButton() {
		click(button);

		return new NextPage().initElements();
	}
}



8b) refreshPage() - avoiding all ExpectedCondition

An Exception often thrown when running a test with Selenium is 
StaleElementReferenceException. This happens after the DOM was dynamically changed and the the test can not use the Objects referenced by the class members to contact the relevant nodes in the current DOM. Calling refreshPage() will dismiss all the reference Object and will allocate all class members of the page class with new Objects. This prevents the test from failing and most important save the programmer a lot of time because THERE IS NO NEED TO WRITE ANY SPECIFIC ExpectedCondition IN THE WHOLE TEST! This saves a great amount of time comparing to a regular test with selenium. 
For example, changes on the screen and in the DOM are being made after provoked by choose in a drop down menu:


class SomePage extends LoadablePage {

	private Select countries;  //a drop down menu

	public void selectACountry(String country) {
		countries.select(country);  //choosing from a drop down menu 
							 invocing changes in the DOM 

		refreshPage();   //calling refreshPage to make sure all class 
						members can address the current DOM
		return this;
 	}
}


refreshPage() should be used:
– After an action that changes the DOM dynamically
– When the test fails repeatedly with StaleElementReferenceException. The Method refreshPage() should be called previous to the step where the test fails. 


8c) Wrapper methods for methods of WebElement - making sure class members can address the GUI elements. In order to avoid the StaleElementReferenceException (see 8a), LoadablePage has Methods to wrap all the known methods of Selenium's WebElement. Those methods have one more parameter comparing to the original WebElement methods. This parameter is of type Selement. E.g. Methods of WebElement and their equivalent of Selement:



WebElement 				Selement 
click();				click(Selement);
isSelected();			isSelected(Selement);
sendKeys(String);			sendKeys(Selement, String);

This methods make sure that the object allocated to the class members is indeed able to address the relevant node in the DOM. If not, SimpleSelenium will automatically search again in the DOM and will allocate the class members new objects so the text will not fail.


8d) Method getSubPageElement
making sure class members of type SubPage or Dialog are available.

"Get' methods in java are used to hand over references or values of class members to caller from outside the class. In order for SimpleSelenium to make sure that the objects allocated to the class members of type SubPage or Dialog can still successfully connect the relevant nodes in the DOM, the method getSubPageElement should be called within the "get' method.
For example:

public class MainPage extends LoadablePage {

	@FindBy(. . .)
	private SubPage sidebar; //Class member of type SubPage

	@FindBy(. . .)	
	private List<SubPage> manySections; //Class member of type List<SubPage>

	public SubPage getSidebar() {
		return getSubPageElement(sidebar);  
	}


	public SubPage getASection(int index) {
		return getSubPageElement(manySections, index); 
						//getting a section 										  from a list according to the index

	}
}



8e) dragAndDrop 

public void dragAndDrop (Selement dragged, Selement target);

Drag and drop of an element (1st parameter) to a target on the screen (2nd parameter).(Often after calling this method, refreshPage() needs to be called).


8f) load()

Normally during a Selenium Test an URL must be called only one time. The rest of the navigation between pages and dialogs should be made native as a user of the website would do it, by clicking links, buttons and other activities. In order to load the first page (normally a landing page or a login page) the page class for this page should have this method overridden:

public class loginPage  {

	private final String url = ". . .";

    @Override
    public void load() {
        driver.get(url);
    }
}


For the rest of the page classes this method should be ignored.




9)  Creating a new type of class representing an Element on the GUI.

In order to form an new class, similar to the specific classes in §6, follow this two steps:

9a) Create an interface
This interface should extends Selement (or SubPage or Dialog. Depends of the nature of that element). This interface must be annotated with @ImplementingClass, containing the class name that implements this interface. (The most simple consistent solution is to add "Impl' to the name of the interface).


@ImplementingClass(NewGuiElementImpl.class)
public interface NewGuiElement extends Selement {
	. . . method signatures . . . 
}


9b) Create a class, which implements the new interface and extends SelementImpl (or SubPageImpl or DialogImpl).

public class NewGuiElement extends SelementImpl implements NewGuiElement {
}

The new Element may extends SubPageImpl or DialogImpl instead of SelementImpl according to its nature.
Now the new interface can be used as a type for class members. 


10) Setting the WebDriver

As in a selenium Test, an instance of WebDriver should be created when the starting running the test.
In addition to that, in SimpleSelenium the driver must be set to a class named TestSingleton. This is done one time in the beginning of the test instead of passing over a reference of WebDriver between classes many times as normally done in a selenium test:


private WebDriver driver;

@Before
public void startTest{
	driver = new FirefoxDriver;  //creating an instance, here of FirefoxDriver	TestSingleton.setDriver(driver);
}



