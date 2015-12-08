{\rtf1\ansi\ansicpg1252\cocoartf1265\cocoasubrtf210
{\fonttbl\f0\fnil\fcharset0 Menlo-BoldItalic;\f1\fnil\fcharset0 Menlo-Italic;\f2\fnil\fcharset0 Menlo-Regular;
\f3\fnil\fcharset0 Menlo-Bold;}
{\colortbl;\red255\green255\blue255;\red255\green255\blue255;}
\paperw11900\paperh16840\margl1440\margr1440\vieww22580\viewh11060\viewkind0
\deftab720
\pard\pardeftab720

\f0\i\b\fs32 \cf0 \cb2 SimpleSelenium Manual
\f1\b0 \
\
Basic knowledge of selenium is required in order to use SimpleSelenium and this manual.\
Please download the SimpleSelenium.jar to your test project.\
\
\pard\pardeftab720

\f2\i0 \cf0 \cb1 The biggest advantage when using SimpleSelenium, is creating a test with no need to write any expected conditions (ExpectedCondition). An Exception often thrown when running a test with Selenium is 
\f3\b StaleElementReferenceException
\f2\b0 . This happens after the DOM was dynamically changed and the the test can not use the Objects referenced by the class members to contact the relevant nodes in the current DOM. The methods in \'a78b - \'a78d makes sure the test will not fail even though there are no expected condition at all.  
\f1\i \cb2 \
\pard\pardeftab720
\cf0 \
\
1) 
\f0\b Selement
\f1\b0  - should be used instead of WebElement\
\
Class members of a page class, which refer to GUI-Elements on the screen, \
should be of Interface type \'82Selement\'92 instead of \'82WebElement\'92. The Selenium \'82WebElement\'92 is \
considered deprecated when using SimpleSelenium.
\f2\i0 \
\
\
\
2) 
\f0\i\b \cb1 LoadablePage
\f1\b0  - The Supper class for all page classes\
\
\pard\pardeftab720

\f2\i0 \cf0 \cb2 As in Selenium, the \ul \'82Page Object\'91\ulnone  pattern should be used with SimpleSelenium.\
Each Page class of a test should extends LoadablePage.\
\pard\pardeftab720
\cf0 \cb1 \
Some of the class members of a Page class refer to GUI elements. Same as in a selenium code they should be annotated with @FindBy, @FindBys or @FindAll and may be of type List<Selement>.\
\

\f3\b class SomePartOfAPage extends LoadbalePage \{\
\
	@FindBy(css = \'84button[name*=submit]\'93)\
	private \ul Selement\ulnone  button;
\f2\b0 \
\

\f3\b 	@FindBy(css = \'84button[id*=menu_button]\'93)\
	private \ul List<Selement>\ulnone  menuButtons;
\f2\b0 \

\f3\b \
\}\cb2  
\f2\b0 \
\
Some of the most important methods of class LoadablePage will be introduced in \'a78.\
\
\
\pard\pardeftab720
\cf0 \
3) 
\f3\b SubPage, SubPageImpl
\f2\b0  - a class member which represent a section of the page\
\
\cb1 A Limited section on the screen may be represented by a separate class. This class should extend SubPageImpl instead of LoadablePage (SubPage is an interface). \
\pard\pardeftab720
\cf0 The Constructor of this class will receive a Selement as parameter. This Selement represent a GUI-Element, which contains this section on the screen. So the programmer needs to find the locator that frame this section. E.g.:\

\f3\b \
<html>\
<div>\
 . . .<div id=\'84some_section\'93> Everything in this tag will be displayed in the section </div>    . . .\
</div>\
</html>\
\
\
@FindBy (id = \'84some_section\'93)\
private SubPage subSection;\

\f2\b0 \
\
\pard\pardeftab720
\cf0 This class allows the locators to be more simple. \
\pard\pardeftab720
\cf0 For example - a page maintains many buttons. Each button is represented by a class member. The locators for this buttons must distinguish each button from the others and must be very specific. But if one button is located in an section, and this section is represented in the test by a separate SubPage, the locator may be very simple: \
\pard\pardeftab720
\cf0 \cb2 \

\f3\b \
class \ul MainPage\ulnone  extends LoadablePage\'a0\{\

\f2\b0 \

\f3\b 	@ImplementingClass\

\f2\b0 	
\f3\b \cb1 @FindBy(tagname = \'84button\'93)\
\pard\pardeftab720
\cf0     private SubPage someSection;  
\f2\b0 //Interface SubPage refers to the class below; 
\f3\b \
\pard\pardeftab720

\f2\b0 \cf0 \cb2 \
\}\
\
\

\f3\b class \ul SomeSection\ulnone  extends SubPageImpl \{ 
\f2\b0 //extends the class SubPageImpl
\f3\b \
\
    public SomeSection(Selement element) \{\
       super(element);\
    \}\
\pard\pardeftab720
\cf0 \}
\f2\b0 \
\pard\pardeftab720
\cf0 \
\pard\pardeftab720
\cf0 \cb1 !Important - The class member representing the sub section should be of type interface SubPage. \
In General \ul ALWAYS USE INTERFACE AS TYPE FOR TYPE OF CLASS MEMBERS\ulnone , not classes;\
The class which represents the this section should extends the class SubPageImpl.\
\pard\pardeftab720
\cf0 \cb2 \
\
\
4) 
\f3\b Dialog, DialogImpl
\f2\b0 \
\
Similar to SubPage, a class which represents a Dialog can extends DialogImpl. Using this class helps SimpleSelenium to make sure dialogs are fully opened or closed, before proceeding with the test, which helps avoiding test failing. \
\
Once again, the Page class which represents this dialog extends the class DialogImpl. \
The class member within the main class is of type interface Dialog.\
\
\
\pard\pardeftab720

\f3\b \cf0 \cb1 class MainPage extends LoadablePage \{\
\
    private Dialog someDialog; 
\f2\b0  //refers to the Dialog class below
\f3\b \
\
\}\
\
class SomeDialog extends DialogImpl \{ . . . \}
\f2\b0 \cb2 \
\pard\pardeftab720
\cf0 \
\
\
5) 
\f3\b @ExplicitElement
\f2\b0  - annotating a class member, which must be fully loaded before test proceeds \
\
One and only one class member in each Page/SubPage/Dialog class must be annotated with @ExplicitElement.\
Class members represent GUI elements. Some of those elements are static in the HTML and some are dynamically added elements (for example table head is static, in oppose to table lines, which may be dynamically added ). The dynamic element should be annotated with @ExplicitElement. \

\f3\b \
class SomePage extends LoadablePage \{\
\
	@FindBy (tagName = \'84th\'93)\
	Selement tableHead;\
\
	@ExplicitElement\
	@FindBy  (tagName = \'84tr\'93)\
	List<Selement> tableLines;\
\
\}\

\f2\b0 \
This helps SimpleSelenium to makes sure that a new page if fully loaded, before proceeding with the test. \
Only when SimpleSelenium can locate the explicit Element in the DOM the test will continue to the next step.\
\
\
\
6) 
\f3\b Specific Elements
\f2\b0  - classes of specific HTML (and PrimeFaces) GUI element \
\
In Selenium all GUI-Elements are covered only by WebElement. SimpleSelenium has various of interface and class to represents simple html elements (input fields, buttons), and Primefaces elements. \
Those are this interfaces:\
HTML elements:\
Checkbox, TextInput (for input fields), Select (drop down menu), Table (which extends SubPageImpl class).\
\
Some of the JSF or Primefaces components:\
PfTree, PfCalendar, PfSelect (stands for select option, which are not based on the HTML select tag, but on primefaces button, which opens a Dialog that looks like an drop down options), PfTable (table which can be sorted as per entering text in the table head input fields).\
Those component where created upon a specific version of Primeface project, and may be deprecated or not working correctly for other projects. The interface above of the HTML group are ever green.\
\
Always use the interface for all class members, never the class.\
\

\f3\b @FindBy(xpath = \'84//input[@name=\'82q\'91]\'93)\
private TextInput  searchField; 
\f2\b0 <= Use interface \ul TextInput\ulnone , NOT \ul TextInutImpl\ulnone \
\
\
\
\
7) 
\f3\b DialogToggle and @OpenDialog
\f2\b0  - for buttons that open Dialogs\
\
Another specific type is DialogToggle. Use this for buttons, that open Dialogs when clicked.\
In the example below the class representing the main page, there are references to the button as well as for the dialog; The button (DialogToggle) is annotated with @OpenDialog(). \
This annotation can receive a String value or a Class<?>. \
The String value is the name of the class member, which represents the Dialog. \
A class member of type DialogToggle MUST be annotated with @OpenDialog;\

\f3\b \
public class MainPage extends LoadablePage \{\
\
	@FindBy(. . .)\
	@OpenDialog(\'84someDialog\'93)  
\f2\b0 <=
\f3\b  
\f2\b0 or
\f3\b  
\f2\b0 =>
\f3\b  @OpenDialog(ASpecificDialog.class)\
	private DialogToggle toggle;\
\pard\pardeftab720
\cf0 \cb1 \
	@FindBy(. . .)\
	private ASpecificDialog someDialog;\cb2 \
\pard\pardeftab720
\cf0 \}
\f2\b0 \
\
\
\
8) 
\f3\b Methods of class LoadablePage
\f2\b0 \
\
8a)  
\f3\b initElements()
\f2\b0  - allocate the class members with object to connect the DOM\
\
Should be called when creating a new class - new SomePage().initElements();\
For example:\
\
\pard\pardeftab720

\f3\b \cf0 \cb1 public class MainPage extends LoadablePage \{\
\
	@FindBy( . . .)\
	private Selement button;\
\
	public NextPage clickButton() \{\
		click(button);\
\
		return new NextPage().initElements();\
	\}\
\}
\f2\b0 \
\pard\pardeftab720
\cf0 \cb2 \
\
\
8b) 
\f3\b refreshPage()
\f2\b0  - avoiding all ExpectedCondition\
\
An Exception often thrown when running a test with Selenium is 
\f3\b StaleElementReferenceException
\f2\b0 . This happens after the DOM was dynamically changed and the the test can not use the Objects referenced by the class members to contact the relevant nodes in the current DOM. Calling refreshPage() will dismiss all the reference Object and will allocate all class members of the page class with new Objects. This prevents the test from failing and most important save the programmer a lot of time because \ul THERE IS NO NEED TO WRITE ANY SPECIFIC ExpectedCondition IN THE WHOLE TEST!\ulnone  This saves a great amount of time comparing to a regular test with selenium. \
\pard\pardeftab720
\cf0 \cb1 For example, changes on the screen and in the DOM are being made after provoked by choose in a drop down menu:\

\f3\b \
class SomePage extends LoadablePage \{\
\
	private Select countries;  
\f2\b0 //a drop down menu\
\
\

\f3\b 	public void selectACountry(String country) \{\
\
        countries.select(country);   
\f2\b0 //choosing a country from the drop down menu \
										    after which the DOM and the presentation on the screen will be changed
\f3\b \
\
        refreshPage();   
\f2\b0 //calling refreshPage to make sure all class members can address the current DOM
\f3\b \
\
        return this;\
 	\}\
\}
\f2\b0 \cb2 \
\pard\pardeftab720
\cf0 \
\
refreshPage() should be used:\
\'97 After an action that changes the DOM dynamically\
\'97 When during running the test it fails several times with StaleElementReferenceException. Method refreshPage() should be called previous to the step where the test fails. \
 \
\
\
8c) 
\f3\b Wrapper methods for methods of WebElement
\f2\b0  - making sure class members can address the GUI elements \
\
In order to avoid the StaleElementReferenceException (see 8a), LoadablePage has Methods to wrap all the known methods of Selenium\'92s WebElement. Those methods have one more parameter comparing to the original WebElement methods. This parameter is of type Selement. \
\
E.g. Methods of WebElement and their equivalent of Selement:\

\f3\b \
\ul WebElement\ulnone 				\ul Selement\ulnone \
click();				click(Selement);\
isSelected();			isSelected(Selement);\
sendKeys(String);		sendKeys(Selement, String);\

\f2\b0 \
\
\pard\pardeftab720
\cf0 \cb1 This methods make sure that the object allocated to the class members is indeed able to address the relevant node in the DOM. If not, SimpleSelenium will automatically search again in the DOM and will allocate the class members new objects so the text will not fail.\
\
\
\
8d) Method 
\f3\b getSubPageElement
\f2\b0   - making sure class members of type SubPage or Dialog are available.\
\
\'82Get\'91 methods in java are used to hand over references or values of class members to caller from outside the class. In order for SimpleSelenium to make sure that the objects allocated to the class members of type SubPage or Dialog can still successfully connect the relevant nodes in the DOM, the method getSubPageElement should be called within the \'82get\'91 method.\
For example:\
\

\f3\b public class MainPage extends LoadablePage \{\
\
	@FindBy(. . .)\
	private SubPage sidebar; 
\f2\b0 //Class member of type SubPage
\f3\b \
\
\
	@FindBy(. . .)	\
	private List<SubPage> manySections; 
\f2\b0 //Class member of type List<SubPage>
\f3\b \
\
\
	public SubPage getSidebar() \{\
		return getSubPageElement(sidebar);  \
	\}\
\
\
	public SubPage getASection(int index) \{\
		return getSubPageElement(manySections, index);  
\f2\b0 //getting a section from a list according to the index
\f3\b \
	\}\
\
\}\

\f2\b0 \
\
\pard\pardeftab720
\cf0 \cb2 \
\pard\pardeftab720
\cf0 \cb1 8e) 
\f3\b dragAndDrop
\f2\b0  (Selement dragged, Selement target)\
\
public void dragAndDrop (Selement dragged, Selement target);\
\pard\pardeftab720
\cf0 Drag and drop of an element (1st parameter) a target on the screen (2nd parameter).\
(Often after calling this method, refreshPage() needs to be called).\
\
\
8f) 
\f3\b load()
\f2\b0 \
\
Normally during a Selenium Test an URL must be called only one time. The rest of the navigation between pages and dialogs should be made native as a user of the website would do it, by clicking links, buttons and other activities. In order to load the first page (normally a landing page or a login page) the page class for this page should have this method overridden:\
\pard\pardeftab720
\cf0 \

\f3\b public class loginPage  \{\
\
	private final String url = \'84\'85\'93;\
\
    @Override\
    public void load()\{\
        driver.get(url);\
    \}\
\}
\f2\b0 \
\pard\pardeftab720
\cf0 \
For the rest of the page classes this method should be ignored.\
\
\
\
9)  
\f3\b Creating a new type
\f2\b0  of class representing an Element on the GUI.\
\cb2 \
In order to form an new class, similar to the specific classes in \'a76, follow this two steps:\
\
9a) 
\f3\b Create an interface
\f2\b0 . This interface should extends Selement (or SubPage or Dialog. Depends of the nature of that element). This interface must be annotated with 
\f3\b @ImplementingClass
\f2\b0 , containing the class name that implements this interface. (The most simple consistent solution is to add \'82Impl\'91 to the name of the interface).\
\

\f3\b @ImplementingClass(NewGuiElementImpl.class)\
	public interface NewGuiElement extends Selement \{\
\}
\f2\b0 \
\
9b) 
\f3\b Create a class
\f2\b0 , which implements the new interface and extends SelementImpl (or SubPageImpl or DialogImpl).\
\

\f3\b public class NewGuiElement extends SelementImpl implements NewGuiElement \{\
\
\}
\f2\b0 \
\
The new Element may extends SubPageImpl or DialogImpl instead of SelementImpl according to its nature.\
Now the new interface can be used as a type for class members. \
\
\
\
\
10) 
\f3\b Setting the WebDriver
\f2\b0 \
\
As in a selenium Test, an instance of WebDriver should be created when the starting running the test.\
In addition to that, in SimpleSelenium the driver must be set to a class named TestSingleton. This is done one time in the beginning of the test instead of passing over a reference of WebDriver between classes many times as normally done in a selenium test:\

\f3\b \
WebDriver driver;\
\
@Before\
public void startTest\{\
	driver = new FirefoxDriver;  
\f2\b0 //creating an instance, for example of FirefoxDriver, as done in a selenium Test
\f3\b \
    TestSingleton.setDriver(driver);\
\}
\f2\b0 \
	\
\
}